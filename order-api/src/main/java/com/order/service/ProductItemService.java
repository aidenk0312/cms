package com.order.service;

import com.order.domain.model.Product;
import com.order.domain.model.ProductItem;
import com.order.domain.product.AddProductItemForm;
import com.order.domain.product.UpdateProductItemForm;
import com.order.domain.repository.ProductItemRepository;
import com.order.domain.repository.ProductRepository;
import com.order.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.order.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ProductItemService {
    private final ProductRepository productRepository;
    private final ProductItemRepository productItemRepository;

    @Transactional
    public Product addProductItem(Long sellerId, AddProductItemForm form) {
        Product product = productRepository.findBySellerIdAndId(sellerId, form.getProductId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_PRODUCT));

        if (product.getProductItems().stream()
                .anyMatch(item -> item.getName().equals(form.getName()))) {
            throw new CustomException(SAME_ITEM_NAME);
        }

        ProductItem productItem = ProductItem.of(sellerId, form);
        product.getProductItems().add(productItem);

        return product;
    }

    @Transactional
    public ProductItem updateProductItem(Long sellerId, UpdateProductItemForm form) {
        ProductItem productItem = productItemRepository.findById(form.getId())
                .filter(pi -> pi.getSellerId().equals(sellerId)).orElseThrow(() -> new CustomException(NOT_FOUND_ITEM));
        productItem.setName(form.getName());
        productItem.setCount(form.getCount());
        productItem.setPrice(form.getPrice());

        return productItem;
    }

    @Transactional
    public void deleteProductItem(Long sellerId, Long productItemId) {
        ProductItem productItem = productItemRepository.findById(productItemId)
                .filter(pi -> pi.getSellerId().equals(sellerId)).orElseThrow(() -> new CustomException(NOT_FOUND_ITEM));
        productItemRepository.delete(productItem);
    }
}
