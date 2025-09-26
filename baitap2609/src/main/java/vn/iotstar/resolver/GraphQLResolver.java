package vn.iotstar.resolver;

import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.User;
import vn.iotstar.repository.CategoryRepository;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GraphQLResolver {

	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private CategoryRepository categoryRepo;

	// =========================
	// Query
	// =========================
	@QueryMapping
	public List<Product> getAllProductsByPrice() {
		return productRepo.findAllByOrderByPriceAsc();
	}

	@QueryMapping
	public List<Product> getProductsByCategory(@Argument Long categoryId) {
	    Category c = categoryRepo.findById(categoryId).orElseThrow();
	    return c.getProducts(); // Lấy trực tiếp products của category
	}


	@QueryMapping
	public List<User> getUsers() {
		return userRepo.findAll();
	}

	@QueryMapping
	public List<Category> getCategories() {
		return categoryRepo.findAll();
	}

	// =========================
	// Mutation - User
	// =========================
	@MutationMapping
	public User createUser(@Argument String fullname, @Argument String email, @Argument String password,
			@Argument String phone) {
		return userRepo.save(User.builder().fullname(fullname).email(email).password(password).phone(phone).build());
	}

	@MutationMapping
	public User updateUser(@Argument Long id, @Argument String fullname, @Argument String email,
			@Argument String password, @Argument String phone) {
		User u = userRepo.findById(id).orElseThrow();
		if (fullname != null)
			u.setFullname(fullname);
		if (email != null)
			u.setEmail(email);
		if (password != null)
			u.setPassword(password);
		if (phone != null)
			u.setPhone(phone);
		return userRepo.save(u);
	}

	@MutationMapping
	public Boolean deleteUser(@Argument Long id) {
		userRepo.deleteById(id);
		return true;
	}

	// =========================
	// Mutation - Category
	// =========================
	@MutationMapping
	public Category createCategory(@Argument String name, @Argument String images) {
		return categoryRepo.save(Category.builder().name(name).images(images).build());
	}

	@MutationMapping
	public Category updateCategory(@Argument Long id, @Argument String name, @Argument String images) {
		Category c = categoryRepo.findById(id).orElseThrow();
		if (name != null)
			c.setName(name);
		if (images != null)
			c.setImages(images);
		return categoryRepo.save(c);
	}

	@MutationMapping
	public Boolean deleteCategory(@Argument Long id) {
		categoryRepo.deleteById(id);
		return true;
	}

// =========================
// Mutation - Product
// =========================
	@MutationMapping
	public Product createProduct(@Argument String title, @Argument int quantity, @Argument String description,
			@Argument double price, @Argument Long userId) {
		User u = userRepo.findById(userId).orElseThrow();
		Product p = Product.builder().title(title).quantity(quantity).description(description).price(price).user(u)
				.build();
		return productRepo.save(p);
	}

	@MutationMapping
	public Product updateProduct(@Argument Long id, @Argument String title, @Argument Integer quantity,
			@Argument String description, @Argument Double price) {
		Product p = productRepo.findById(id).orElseThrow();
		if (title != null)
			p.setTitle(title);
		if (quantity != null)
			p.setQuantity(quantity);
		if (description != null)
			p.setDescription(description);
		if (price != null)
			p.setPrice(price);
		return productRepo.save(p);
	}

	@MutationMapping
	public Boolean deleteProduct(@Argument Long id) {
		productRepo.deleteById(id);
		return true;
	}
}