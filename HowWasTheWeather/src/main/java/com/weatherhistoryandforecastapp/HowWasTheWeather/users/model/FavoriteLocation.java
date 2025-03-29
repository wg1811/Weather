package com.weatherhistoryandforecastapp.HowWasTheWeather.users.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id; // this should prolly be the jakarta version.
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Table("favorites")
public class FavoriteLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column("user_id")
    private Long userId;
    @Column("name")
    private String name;
    private BigDecimal latitude;
    private BigDecimal longitude;
    @Column("created_at")
    private LocalDateTime createdAt;

    // Constructors
    public FavoriteLocation() {
    }

    public FavoriteLocation(Long userId, String name, BigDecimal latitude, BigDecimal longitude) {
        this.userId = userId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = LocalDateTime.now();
    }

    // Getters, setters, etc.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
