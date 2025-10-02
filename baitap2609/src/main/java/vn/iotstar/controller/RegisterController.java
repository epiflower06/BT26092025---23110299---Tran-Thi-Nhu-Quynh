package vn.iotstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ch.qos.logback.core.model.Model;
import jakarta.validation.Valid;
import vn.iotstar.dto.UserInput;
import vn.iotstar.entity.User;
import vn.iotstar.repository.UserRepository;

@Controller
public class RegisterController {
    @Autowired private UserRepository userRepo;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("userInput", new UserInput());
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid @ModelAttribute("userInput") UserInput input,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        User user = User.builder()
                .fullname(input.getFullname())
                .email(input.getEmail())
                .password(input.getPassword())
                .phone(input.getPhone())
                .role(input.getRole() != null ? input.getRole() : "USER")
                .build();
        userRepo.save(user);
        return "redirect:/login";
    }
}
