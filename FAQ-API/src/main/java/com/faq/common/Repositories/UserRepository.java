package com.faq.common.Repositories;

import com.faq.common.Entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AccountEntity, Long> {
    boolean existsByEmail(String email);

    AccountEntity findByEmail(String email);
}
