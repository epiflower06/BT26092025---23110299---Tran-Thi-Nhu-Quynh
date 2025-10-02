package vn.iotstar.resolver;

import vn.iotstar.entity.*;
import vn.iotstar.dto.*;
import vn.iotstar.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.*;
import jakarta.validation.Valid;

@Controller
public class GraphQLResolver {
    @Autowired private ProductRepository productRepo;
    @Autowired private CategoryRepository categoryRepo;
    @Autowired private UserRepository userRepo;

    /* ================== QUERY ================== */
    @QueryMapping
    public List<Product> productsSortedByPrice() {
        return productRepo.findAllByOrderByPriceAsc();
    }

    @QueryMapping
    public List<Product> productsByCategory(@Argument Long categoryId) {
        return productRepo.findDistinctByCategories_Id(categoryId);
    }

    @QueryMapping
    public List<User> users() { return userRepo.findAll(); }

    @QueryMapping
    public List<Category> categories() { return categoryRepo.findAll(); }

    @QueryMapping
    public List<Product> products() { return productRepo.findAll(); }

    @QueryMapping
    public User user(@Argument Long id) { return userRepo.findById(id).orElse(null); }

    @QueryMapping
    public Category category(@Argument Long id) { return categoryRepo.findById(id).orElse(null); }

    @QueryMapping
    public Product product(@Argument Long id) { return productRepo.findById(id).orElse(null); }

    /* ================== MUTATION ================== */
    @MutationMapping
    public User createUser(@Argument @Valid UserInput input) {
        User u = User.builder()
                .fullname(input.getFullname())
                .email(input.getEmail())
                .password(input.getPassword())
                .phone(input.getPhone())
                .role(input.getRole() != null ? input.getRole() : "USER")
                .build();
        return userRepo.save(u);
    }

    @MutationMapping
    public User updateUser(@Argument Long id, @Argument @Valid UserInput input) {
        return userRepo.findById(id).map(u -> {
            u.setFullname(input.getFullname());
            u.setEmail(input.getEmail());
            u.setPassword(input.getPassword());
            u.setPhone(input.getPhone());
            u.setRole(input.getRole());
            return userRepo.save(u);
        }).orElse(null);
    }

    @MutationMapping
    public Boolean deleteUser(@Argument Long id) {
        if(userRepo.existsById(id)) {
            userRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @MutationMapping
    public Category createCategory(@Argument @Valid CategoryInput input) {
        Category c = Category.builder()
                .name(input.getName())
                .images(input.getImages())
                .build();
        return categoryRepo.save(c);
    }

    @MutationMapping
    public Category updateCategory(@Argument Long id, @Argument @Valid CategoryInput input) {
        return categoryRepo.findById(id).map(c -> {
            c.setName(input.getName());
            c.setImages(input.getImages());
            return categoryRepo.save(c);
        }).orElse(null);
    }

    @MutationMapping
    public Boolean deleteCategory(@Argument Long id) {
        if(categoryRepo.existsById(id)) {
            categoryRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @MutationMapping
    public Product createProduct(@Argument @Valid ProductInput input) {
        Product p = Product.builder()
                .title(input.getTitle())
                .quantity(input.getQuantity())
                .description(input.getDescription())
                .price(BigDecimal.valueOf(input.getPrice()))
                .build();

        if(input.getUserId()!=null) {
            userRepo.findById(input.getUserId()).ifPresent(p::setUser);
        }

        if(input.getCategoryIds()!=null) {
            Set<Category> cats = new HashSet<>();
            input.getCategoryIds().forEach(cid ->
                categoryRepo.findById(cid).ifPresent(cats::add));
            p.setCategories(cats);
        }

        return productRepo.save(p);
    }

    @MutationMapping
    public Product updateProduct(@Argument Long id, @Argument @Valid ProductInput input) {
        return productRepo.findById(id).map(p -> {
            p.setTitle(input.getTitle());
            p.setQuantity(input.getQuantity());
            p.setDescription(input.getDescription());
            p.setPrice(BigDecimal.valueOf(input.getPrice()));
            if(input.getUserId()!=null) {
                userRepo.findById(input.getUserId()).ifPresent(p::setUser);
            }
            if(input.getCategoryIds()!=null) {
                Set<Category> cats = new HashSet<>();
                input.getCategoryIds().forEach(cid ->
                    categoryRepo.findById(cid).ifPresent(cats::add));
                p.setCategories(cats);
            }
            return productRepo.save(p);
        }).orElse(null);
    }

    @MutationMapping
    public Boolean deleteProduct(@Argument Long id) {
        if(productRepo.existsById(id)) {
            productRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
