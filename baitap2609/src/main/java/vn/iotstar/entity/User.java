package vn.iotstar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "users")       // đặt tên bảng "users" để tránh trùng với từ khóa SQL
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Họ tên không được rỗng")
    private String fullname;

    @Email(message="Email không hợp lệ")
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    @Size(min = 6, message="Mật khẩu phải >= 6 ký tự")
    private String password;

    @Pattern(regexp="\\d{9,12}", message="Số điện thoại không hợp lệ")
    private String phone;

    @Builder.Default
    private String role = "USER"; // mặc định là USER

    // quan hệ nhiều-nhiều với Category
    @ManyToMany
    @JoinTable(
        name = "user_category",
        joinColumns = @JoinColumn(name="user_id"),
        inverseJoinColumns = @JoinColumn(name="category_id")
    )
    private Set<Category> categories;

	
}
