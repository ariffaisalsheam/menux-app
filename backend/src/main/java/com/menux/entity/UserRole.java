package com.menux.entity;

/**
 * Enum representing different user roles in the Menu.X system
 */
public enum UserRole {
    /**
     * Super administrator with full system access
     */
    SUPER_ADMIN("Super Admin"),
    
    /**
     * Restaurant owner who can manage their restaurant and menu
     */
    RESTAURANT_OWNER("Restaurant Owner"),
    
    /**
     * Regular diner/customer (typically not stored in database)
     */
    DINER("Diner");

    private final String displayName;

    UserRole(String displayName) {
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
