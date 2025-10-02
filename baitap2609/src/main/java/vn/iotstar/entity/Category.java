package vn.iotstar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Tên category không được rỗng")
    private String name;

    private String images;

    // mapping ngược lại đến User
    @ManyToMany(mappedBy = "categories")
    private Set<User> users;

    // quan hệ nhiều-nhiều với Product
    @ManyToMany
    @JoinTable(
        name = "category_product",
        joinColumns = @JoinColumn(name="category_id"),
        inverseJoinColumns = @JoinColumn(name="product_id")
    )
    private Set<Product> products;


}
