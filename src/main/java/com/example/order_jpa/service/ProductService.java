package com.example.order_jpa.service;

import com.example.order_jpa.apiDtio.ProductApiCreateDto;
import com.example.order_jpa.formDto.ProductCreateDto;
import com.example.order_jpa.formDto.ProductUpdateDto;
import com.example.order_jpa.entity.Product;
import com.example.order_jpa.repository.JPAProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final JPAProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void addProduct(ProductCreateDto productCreateDto) {
        Product product = new Product();
        product.setName(productCreateDto.getName());
        product.setPrice(productCreateDto.getPrice());
        product.setQuantity(productCreateDto.getQuantity());
        productRepository.save(product);
    }

    public void addProductApi(ProductApiCreateDto productCreateDto) {
        Product product = new Product();
        product.setName(productCreateDto.getName());
        product.setPrice(productCreateDto.getPrice());
        product.setQuantity(productCreateDto.getQuantity());
        productRepository.save(product);
    }

    public void deleteProduct(long productId) {
        Product product = productRepository.findById(productId);
        productRepository.remove(product);
    }

    public Product getProductInfo(long productId) {
        return productRepository.findById(productId);
    }

    public void updateProduct(Long productId, ProductUpdateDto productUpdateDto) {
        Product product = productRepository.findById(productId);
        product.setName(productUpdateDto.getName());
        product.setPrice(productUpdateDto.getPrice());
//        product.setQuantity(productUpdateDto.getQuantity());
        productRepository.save(product);
    }
}
