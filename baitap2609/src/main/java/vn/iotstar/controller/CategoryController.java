package vn.iotstar.controller;

import vn.iotstar.entity.Category;
import vn.iotstar.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepo;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("category", new Category());
        return "admin/category";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("category") Category category,
                      BindingResult result,
                      Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryRepo.findAll());
            return "admin/category";
        }
        categoryRepo.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        categoryRepo.deleteById(id);
        return "redirect:/admin/category";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryRepo.findById(id).orElse(new Category()));
        model.addAttribute("categories", categoryRepo.findAll());
        return "admin/category";
    }
}
