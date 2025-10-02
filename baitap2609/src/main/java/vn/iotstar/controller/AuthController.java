package vn.iotstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.repository.UserRepository;

@Controller
public class AuthController {
    @Autowired private UserRepository userRepo;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String email,
                          @RequestParam String password,
                          HttpServletRequest request) {
        var opt = userRepo.findByEmail(email);
        if (opt.isPresent() && opt.get().getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("USER_ID", opt.get().getId());
            session.setAttribute("ROLE", opt.get().getRole());

            if ("ADMIN".equals(opt.get().getRole())) {
                return "redirect:/admin/home";
            } else {
                return "redirect:/user/home";
            }
        }
        return "redirect:/login?error=true";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login";
    }
}
