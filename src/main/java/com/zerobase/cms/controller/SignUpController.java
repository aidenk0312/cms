package com.zerobase.cms.controller;

import com.zerobase.cms.application.SignUpApplication;
import com.zerobase.cms.domain.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {
    private final SignUpApplication signUpApplication;

    @PostMapping("/customer")
    public ResponseEntity<String> customerSignUp(@RequestBody SignUpForm form) {
        return ResponseEntity.of(signUpApplication.customerSignUp(form).describeConstable());
    }

    @GetMapping("/customer/verify")
    public ResponseEntity<String> verifyCustomer(String email, String code) {
        signUpApplication.customerVerify(email, code);
        return ResponseEntity.ok("인증 완료");
    }

    @PostMapping("/seller")
    public ResponseEntity<String> SellerSignUp(@RequestBody SignUpForm form) {
        return ResponseEntity.of(signUpApplication.sellerSignUp(form).describeConstable());
    }

    @GetMapping("/seller/verify")
    public ResponseEntity<String> verifySeller(String email, String code) {
        signUpApplication.sellerVerify(email, code);
        return ResponseEntity.ok("인증 완료");
    }
}
