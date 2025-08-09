package com.menux.entity;

/**
 * Enum representing sentiment analysis results for feedback
 */
public enum SentimentType {
    /**
     * Positive sentiment (happy, satisfied customers)
     */
    POSITIVE("Positive"),
    
    /**
     * Negative sentiment (unhappy, dissatisfied customers)
     */
    NEGATIVE("Negative"),
    
    /**
     * Neutral sentiment (neither positive nor negative)
     */
    NEUTRAL("Neutral");

    private final String displayName;

    SentimentType(String displayName) {
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
