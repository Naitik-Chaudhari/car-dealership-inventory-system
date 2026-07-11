package com.naitik.car_dealership_api.repository;


import com.naitik.car_dealership_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
}