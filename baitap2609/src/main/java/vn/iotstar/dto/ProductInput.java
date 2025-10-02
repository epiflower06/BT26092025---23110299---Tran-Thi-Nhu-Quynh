package vn.iotstar.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInput {
    @NotBlank
    private String title;

    @Min(0)
    private Integer quantity;

    private String description;

    @DecimalMin(value="0.0", inclusive=false)
    private Double price;

    private Long userId;

    private List<Long> categoryIds;


}
