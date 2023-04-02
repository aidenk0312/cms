package com.order.Application;

import com.order.domain.model.Product;
import com.order.domain.product.AddProductCartForm;
import com.order.domain.product.AddProductForm;
import com.order.domain.product.AddProductItemForm;
import com.order.domain.redis.Cart;
import com.order.domain.repository.ProductRepository;
import com.order.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartApplicationTest {
    @Autowired
    private CartApplication cartApplication;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void ADD_TEST_MODIFY() {

        Long customerId = 100L;

        cartApplication.clearCart(customerId);
        Product p = add_product();
        Product result = productRepository.findWithProductItemsById(p.getId()).get();

        assertNotNull(result);

        assertEquals(result.getName(), "나이키 에어포스");
        assertEquals(result.getDescription(), "신발");
        assertEquals(result.getProductItems().size(), 3);
        assertEquals(result.getProductItems().get(0).getName(), "나이키 에어포스0");
        assertEquals(result.getProductItems().get(0).getPrice(), 10000);
//        assertEquals(result.getProductItems().get(0).getCount(), 1);

        Cart cart = cartApplication.addCart(customerId, makeAddForm(result));
        assertEquals(cart.getMessages().size(), 0);

        cart = cartApplication.getCart(customerId);
        assertEquals(cart.getMessages().size(), 1);

    }

    AddProductCartForm makeAddForm(Product p) {
        AddProductCartForm.ProductItem productItem =
                AddProductCartForm.ProductItem.builder()
                        .id(p.getProductItems().get(0).getId())
                        .name(p.getProductItems().get(0).getName())
                        .count(5)
                        .price(20000)
                        .build();

        return AddProductCartForm.builder()
                        .id(p.getId())
                        .sellerId(p.getSellerId())
                        .name(p.getName())
                        .description(p.getDescription())
                        .items(List.of(productItem)).build();
    }

    Product add_product() {
        Long sellerId = 1L;
        AddProductForm form = makeProductForm("나이키 에어포스", "신발", 3);
        return productService.addProduct(sellerId, form);
    }

    private static AddProductForm makeProductForm(String name, String description, int itemCount) {
        List<AddProductItemForm> itemForms = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            itemForms.add(makeProductItemForm(null, name + i));
        }
        return AddProductForm.builder()
                .name(name)
                .description(description)
                .items(itemForms)
                .build();
    }

    private static AddProductItemForm makeProductItemForm(Long prodictId, String name) {
        return AddProductItemForm.builder()
                .productId(prodictId)
                .name(name)
                .price(10000)
                .count(10)
                .build();
    }
}