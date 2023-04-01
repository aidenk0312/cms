package com.zerobase.cms.service.customer;

import com.zerobase.cms.domain.SignUpForm;
import com.zerobase.cms.domain.model.Customer;
import com.zerobase.cms.domain.repository.CustomerRepository;
import com.zerobase.cms.excepition.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import static com.zerobase.cms.excepition.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class SignUpCustomerService {

    private final CustomerRepository customerRepository;

    public Customer signUp(SignUpForm form) {
        return customerRepository.save(Customer.from(form));
    }

    public boolean isEmailExist(String email) {
        return customerRepository.findByEmail(email.toLowerCase(Locale.ROOT))
                .isPresent();
    }

    @Transactional
    public void verifyEmail(String email, String code) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        if (customer.isVerify()) {
            throw new CustomException(ALREADY_VERIFY);
        } else if (!customer.getVerificationCode().equals(code)) {
            throw new CustomException(WRONG_VERIFICATION);
        } else if (customer.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(EXPIRE_CODE);
        }
        customer.setVerify(true);

    }

    @Transactional
    public LocalDateTime changeCustomerValidateEmail(Long customerId, String verificationCode) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isPresent()) {

            Customer customer = customerOptional.get();
            customer.setVerificationCode(verificationCode);
            customer.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
            return customer.getVerifyExpiredAt();
        }
        throw new CustomException(NOT_FOUND_USER);
    }
}
