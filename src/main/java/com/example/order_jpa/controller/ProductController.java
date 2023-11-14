package com.example.order_jpa.controller;

import com.example.order_jpa.formDto.ProductCreateDto;
import com.example.order_jpa.formDto.ProductUpdateDto;
import com.example.order_jpa.entity.Product;
import com.example.order_jpa.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final MessageSource ms;

    @GetMapping("/list")
    public String getAllProducts(Model model) {
        List<Product> products =  productService.getAllProducts();
        model.addAttribute("products", products);
        return "product/productList";
    }

    @GetMapping("/add")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        return "product/productForm";
    }

    @PostMapping("/add") // @ModelAttribute("product") 수정
    public String addProduct(@Validated @ModelAttribute("product") ProductCreateDto productCreateDto,
                             BindingResult bindingResult,
                             Model model) {
        //System.out.println("product controller 입니다. 입력된 값은 " + productCreateDto.getPrice());

        // 특정 필드 오류가 아닌 전제 예외 처리
        if(productCreateDto.getPrice() != null && productCreateDto.getQuantity() != null){
            int result = productCreateDto.getPrice() * productCreateDto.getQuantity();
            if(result == 0){
                bindingResult.reject("global_quantity", new Object[]{0, result},null);
            }
        }

        boolean b1 = bindingResult.hasErrors();
        boolean b2 = bindingResult.hasFieldErrors();
        boolean b3 = bindingResult.hasGlobalErrors();
        log.info("bindingResult {} : " , bindingResult);
        System.out.println("b1 :" + b1);
        System.out.println("b2 :" + b2);
        System.out.println("b3 :" + b3);
        if(bindingResult.hasErrors()) {
            //model.addAttribute("product", new Product());
            return "product/productForm";
        }
        productService.addProduct(productCreateDto);
        return "redirect:/product/list";
    }

    @PostMapping("/list")
    public String deleteProduct(@RequestParam long productId) {
        productService.deleteProduct(productId);
        return "redirect:/product/list";
    }

    @GetMapping("/info/{productId}")
    public String getProductInfo(Model model, @PathVariable Long productId) {
        Product product = productService.getProductInfo(productId);
        model.addAttribute("product", product);
        return "product/productInfo";
    }

    @GetMapping("/update/{productId}")
    public String updateProduct(Model model, @PathVariable Long productId) {
        Product product = productService.getProductInfo(productId);
        model.addAttribute("productId", productId);
        model.addAttribute("product", product);
        return "product/productUpdate";
    }

    @PostMapping("/update/{productId}")  // @ModelAttribute("product") 수정
    public String updateProduct(@PathVariable Long productId,
            @Validated @ModelAttribute("product") ProductUpdateDto productUpdateDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            return "product/productUpdate";
        }
        productService.updateProduct(productId, productUpdateDto);
        //redirectAttributes.addAttribute("productId", productId);
        redirectAttributes.addAttribute("result", true);
        return "redirect:/product/info/{productId}" ;
    }
}
