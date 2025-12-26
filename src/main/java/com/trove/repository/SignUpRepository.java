package com.trove.repository;

import com.trove.model.SignUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SignUpRepository extends JpaRepository<SignUp, Integer> {

    boolean existsByEmail(String email);
    Optional<SignUp> findByEmail(String email);
}