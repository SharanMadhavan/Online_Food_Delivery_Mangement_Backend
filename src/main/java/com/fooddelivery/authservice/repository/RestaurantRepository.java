package com.fooddelivery.authservice.repository;

import com.fooddelivery.authservice.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByCuisine(String cuisine);
    List<Restaurant> findByRatingGreaterThanEqual(Double rating);
}
