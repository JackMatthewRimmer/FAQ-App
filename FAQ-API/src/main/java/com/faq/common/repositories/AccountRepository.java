package com.faq.common.repositories;

import com.faq.common.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    boolean existsByEmail(String email);
    Optional<AccountEntity> findByEmail(String email);
}
