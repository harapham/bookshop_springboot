package com.shop.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shop.library.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
	Admin findByUsername(String username);
}
