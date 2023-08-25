package com.faq.common.Repositories;

import com.faq.common.Entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AccountEntity, Long> {
    boolean existsByEmail(String email);

    Optional<AccountEntity> findByEmail(String email);
}
