package vn.iotstar.controller;

import vn.iotstar.entity.Product;
import vn.iotstar.entity.Category;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.repository.CategoryRepository;
import vn.iotstar.repository.UserRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin/product")
public class ProductController {

    @Autowired private ProductRepository productRepo;
    @Autowired private CategoryRepository categoryRepo;
    @Autowired private UserRepository userRepo;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productRepo.findAll());
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("product", new Product());
        return "admin/product";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("product") Product product,
                      @RequestParam(required = false) Long userId,
                      @RequestParam(required = false) Set<Long> categoryIds,
                      BindingResult result,
                      Model model) {
        if (result.hasErrors()) {
            model.addAttribute("products", productRepo.findAll());
            model.addAttribute("categories", categoryRepo.findAll());
            model.addAttribute("users", userRepo.findAll());
            return "admin/product";
        }

        if (userId != null) {
            userRepo.findById(userId).ifPresent(product::setUser);
        }
        if (categoryIds != null) {
            Set<Category> cats = new HashSet<>();
            categoryIds.forEach(cid -> categoryRepo.findById(cid).ifPresent(cats::add));
            product.setCategories(cats);
        }

        productRepo.save(product);
        return "redirect:/admin/product";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        productRepo.deleteById(id);
        return "redirect:/admin/product";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("product", productRepo.findById(id).orElse(new Product()));
        model.addAttribute("products", productRepo.findAll());
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("users", userRepo.findAll());
        return "admin/product";
    }
}
