package vn.iotstar.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private int quantity;
    private String description;
    private double price;

    // Product thuộc về 1 User
    @ManyToOne
    @JoinColumn(name = "user_id")  // Hibernate sẽ tạo cột user_id
    private User user;

    // Product thuộc về 1 Category
    @ManyToOne
    @JoinColumn(name = "category_id") // Hibernate sẽ tạo cột category_id
    private Category category;
}
