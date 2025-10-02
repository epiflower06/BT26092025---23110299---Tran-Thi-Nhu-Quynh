package vn.iotstar.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.iotstar.entity.User;
import vn.iotstar.repository.UserRepository;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepo;

    // Hiển thị form login
    @GetMapping("/login")
    public String loginForm() {
        return "login"; // login.html
    }

    // Xử lý submit login
    @PostMapping("/login")
    public String doLogin(@RequestParam("email") String email,
                          @RequestParam("password") String password,
                          Model model) {

        Optional<User> user = userRepo.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            // kiểm tra role
            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                return "redirect:/admin/home";
            } else {
                return "redirect:/user/home";
            }
        }

        model.addAttribute("error", "Sai email hoặc mật khẩu");
        return "login";
    }
}
