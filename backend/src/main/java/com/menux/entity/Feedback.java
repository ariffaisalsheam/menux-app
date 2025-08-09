package com.menux.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * Entity representing customer feedback for a restaurant
 */
@Entity
@Table(name = "feedback", indexes = {
    @Index(name = "idx_feedback_restaurant_id", columnList = "restaurant_id"),
    @Index(name = "idx_feedback_rating", columnList = "rating"),
    @Index(name = "idx_feedback_sentiment", columnList = "sentiment")
})
public class Feedback extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Size(max = 100, message = "Customer name must not exceed 100 characters")
    @Column(name = "customer_name", length = 100)
    private String customerName;

    @Size(max = 255, message = "Customer email must not exceed 255 characters")
    @Column(name = "customer_email")
    private String customerEmail;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must not exceed 5")
    @Column(name = "rating")
    private Integer rating;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "sentiment")
    private SentimentType sentiment;

    @Column(name = "sentiment_score", precision = 3, scale = 2)
    private BigDecimal sentimentScore; // -1.0 to 1.0

    @Column(name = "is_anonymous")
    private Boolean isAnonymous = false;

    // Constructors
    public Feedback() {
    }

    public Feedback(Restaurant restaurant, Integer rating, String comment) {
        this.restaurant = restaurant;
        this.rating = rating;
        this.comment = comment;
    }

    // Getters and Setters
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public SentimentType getSentiment() {
        return sentiment;
    }

    public void setSentiment(SentimentType sentiment) {
        this.sentiment = sentiment;
    }

    public BigDecimal getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(BigDecimal sentimentScore) {
        this.sentimentScore = sentimentScore;
    }

    public Boolean getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(Boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + getId() +
                ", rating=" + rating +
                ", sentiment=" + sentiment +
                ", isAnonymous=" + isAnonymous +
                '}';
    }
}
