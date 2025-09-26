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

    
    @ManyToOne
    @JoinColumn(name = "user_id")  
    private User user;

    
    @ManyToOne
    @JoinColumn(name = "category_id") 
    private Category category;
}

