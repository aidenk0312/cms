package com.order.service;

import com.order.domain.model.Product;
import com.order.domain.repository.ProductRepository;
import com.order.exception.CustomException;
import com.order.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.order.exception.ErrorCode.NOT_FOUND_PRODUCT;

@Service
@RequiredArgsConstructor
public class ProductSearchService {
    private final ProductRepository productRepository;

    public List<Product> searchByName(String name) {
        return productRepository.searchByName(name);
    }

    public Product getByProductId(Long productId) {
        return productRepository.findWithProductItemsById(productId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_PRODUCT));
    }

    public List<Product> getListByProductIds(List<Long> productIds) {
        return productRepository.findAllByIdIn(productIds);
    }
}
