package com.example.order_jpa.formDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class ProductUpdateDto {
    //private Long productId;  // readonly
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @Range(min=100, max=1_000_000)
    private Integer price;
//    @NotNull
//    @Max(value = 9999)
//    private Integer quantity;di
}
