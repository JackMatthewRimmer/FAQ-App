package com.faq.common.Repositories;

import com.faq.common.Requests.AccountRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AccountRequest, Long> {}
