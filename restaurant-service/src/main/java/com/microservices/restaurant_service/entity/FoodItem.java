package com.microservices.restaurant_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "food_items")
public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonBackReference
    private Restaurant restaurant;
    public Long getId()
    { return id; }
    public void setId(Long id)
    { this.id = id; }
    public String getItemName()
    { return itemName; }
    public void setItemName(String itemName)
    { this.itemName = itemName; }

    public Double getPrice()
    { return price; }
    public void setPrice(Double price)
    { this.price = price; }

    public Restaurant getRestaurant()
    { return restaurant; }
    public void setRestaurant(Restaurant restaurant)
    { this.restaurant = restaurant; }
}
