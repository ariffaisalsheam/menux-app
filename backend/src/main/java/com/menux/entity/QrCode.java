package com.menux.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Entity representing QR codes for restaurant tables
 */
@Entity
@Table(name = "qr_codes", indexes = {
    @Index(name = "idx_qr_codes_restaurant_id", columnList = "restaurant_id"),
    @Index(name = "idx_qr_codes_code", columnList = "code")
})
public class QrCode extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @NotBlank(message = "QR code is required")
    @Size(max = 255, message = "QR code must not exceed 255 characters")
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Size(max = 20, message = "Table number must not exceed 20 characters")
    @Column(name = "table_number", length = 20)
    private String tableNumber;

    @Column(name = "is_active")
    private Boolean isActive = true;

    // Constructors
    public QrCode() {
    }

    public QrCode(Restaurant restaurant, String code, String tableNumber) {
        this.restaurant = restaurant;
        this.code = code;
        this.tableNumber = tableNumber;
    }

    // Getters and Setters
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "QrCode{" +
                "id=" + getId() +
                ", code='" + code + '\'' +
                ", tableNumber='" + tableNumber + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
