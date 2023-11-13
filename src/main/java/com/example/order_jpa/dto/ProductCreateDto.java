package com.example.order_jpa.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Data
public class ProductCreateDto {
    @NotNull(message="product name은 null이 입력되면 안됩니다.")
    @NotBlank(message="product name은 반드시 입력해야 합니다.")
    @NotEmpty(message="product name은 반드시 입력해야 합니다.")
    private String name;
    @Range(min=100, max=1_000_000, message="price는 100~1000000 사이의 값을 입력해야 합니다.")
    private int price;
    @Max(value = 9999, message="quantity는 9999 보다 작은 값을 입력해야 합니다.")
    private int quantity;
}
