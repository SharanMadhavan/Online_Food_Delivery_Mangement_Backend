package com.fooddelivery.authservice.service;

import com.fooddelivery.authservice.dto.RestaurantDTO;
import com.fooddelivery.authservice.entity.Restaurant;
import com.fooddelivery.authservice.exception.ResourceNotFoundException;
import com.fooddelivery.authservice.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<RestaurantDTO> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantDTO getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", id));
        return convertToDTO(restaurant);
    }

    @Override
    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = convertToEntity(restaurantDTO);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return convertToDTO(savedRestaurant);
    }

    @Override
    public RestaurantDTO updateRestaurant(Long id, RestaurantDTO restaurantDTO) {
        Restaurant existingRestaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", id));
        
        existingRestaurant.setName(restaurantDTO.getName());
        existingRestaurant.setCuisine(restaurantDTO.getCuisine());
        existingRestaurant.setRating(restaurantDTO.getRating());
        existingRestaurant.setDeliveryTime(restaurantDTO.getDeliveryTime());
        existingRestaurant.setImageUrl(restaurantDTO.getImageUrl());
        
        Restaurant updatedRestaurant = restaurantRepository.save(existingRestaurant);
        return convertToDTO(updatedRestaurant);
    }

    @Override
    public void deleteRestaurant(Long id) {
        if (!restaurantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Restaurant", id);
        }
        restaurantRepository.deleteById(id);
    }

    @Override
    public List<RestaurantDTO> getRestaurantsByCuisine(String cuisine) {
        return restaurantRepository.findByCuisine(cuisine).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RestaurantDTO> getRestaurantsByRating(Double minRating) {
        return restaurantRepository.findByRatingGreaterThanEqual(minRating).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private RestaurantDTO convertToDTO(Restaurant restaurant) {
        return new RestaurantDTO(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getCuisine(),
                restaurant.getRating(),
                restaurant.getDeliveryTime(),
                restaurant.getImageUrl(),
                restaurant.getCreatedAt()
        );
    }

    private Restaurant convertToEntity(RestaurantDTO restaurantDTO) {
        return new Restaurant(
                restaurantDTO.getName(),
                restaurantDTO.getCuisine(),
                restaurantDTO.getRating(),
                restaurantDTO.getDeliveryTime(),
                restaurantDTO.getImageUrl()
        );
    }
}
