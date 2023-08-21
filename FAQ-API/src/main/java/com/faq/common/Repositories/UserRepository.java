package com.faq.common.Repositories;

import com.faq.common.Entities.AccountEntity;
import com.faq.common.Requests.AccountRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<AccountEntity, Long> {
    boolean existsByEmail(String email);

    boolean existsByEmailAndPassword(String email, String password);
}
