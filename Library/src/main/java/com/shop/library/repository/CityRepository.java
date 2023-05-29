package com.shop.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shop.library.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}