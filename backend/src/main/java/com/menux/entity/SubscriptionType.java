package com.menux.entity;

/**
 * Enum representing different subscription types for restaurants
 */
public enum SubscriptionType {
    /**
     * Free subscription with basic features
     * - QR code menu viewing
     * - Basic menu management
     * - Customer feedback collection
     */
    FREE("Free"),
    
    /**
     * Pro subscription with advanced features
     * - All free features
     * - Digital ordering
     * - Live order management
     * - AI-powered menu descriptions
     * - Advanced analytics
     * - Sentiment analysis
     */
    PRO("Pro");

    private final String displayName;

    SubscriptionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
