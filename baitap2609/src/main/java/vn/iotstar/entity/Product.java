package vn.iotstar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Tên sản phẩm không được rỗng")
    private String title;

    @Min(value = 0, message="Số lượng phải >= 0")
    private Integer quantity;

    @Column(length = 1000)
    private String description;

    @DecimalMin(value="0.0", inclusive=false, message="Giá phải > 0")
    private BigDecimal price;

    // Mỗi sản phẩm do 1 user tạo (nhiều sản phẩm -> 1 user)
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    // sản phẩm thuộc nhiều category
    @ManyToMany(mappedBy = "products")
    private Set<Category> categories;

	
}
