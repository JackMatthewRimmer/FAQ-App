package com.faq.common.Repositories;

import com.faq.common.Requests.AccountRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<AccountRequest, Long> {
    List<AccountRequest> findByEmail(String email);
}
