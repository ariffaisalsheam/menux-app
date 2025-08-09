-- Sample Data for Menu.X System
-- Insert sample data for testing and demonstration

-- Insert Super Admin
INSERT INTO users (id, email, password_hash, first_name, last_name, role) VALUES
('550e8400-e29b-41d4-a716-446655440000', 'admin@menux.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.Uo0yCQOXYsxcpXMF.8zQoQwbQrzAaC', 'Super', 'Admin', 'SUPER_ADMIN');

-- Insert Restaurant Owners
INSERT INTO users (id, email, password_hash, first_name, last_name, phone, role) VALUES
('550e8400-e29b-41d4-a716-446655440001', 'owner1@restaurant.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.Uo0yCQOXYsxcpXMF.8zQoQwbQrzAaC', 'John', 'Smith', '+8801712345678', 'RESTAURANT_OWNER'),
('550e8400-e29b-41d4-a716-446655440002', 'owner2@restaurant.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.Uo0yCQOXYsxcpXMF.8zQoQwbQrzAaC', 'Sarah', 'Johnson', '+8801712345679', 'RESTAURANT_OWNER'),
('550e8400-e29b-41d4-a716-446655440003', 'owner3@restaurant.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.Uo0yCQOXYsxcpXMF.8zQoQwbQrzAaC', 'Ahmed', 'Rahman', '+8801712345680', 'RESTAURANT_OWNER');

-- Insert Restaurants
INSERT INTO restaurants (id, owner_id, name, description, address, phone, email, subscription_type) VALUES
('660e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440001', 'The Golden Spoon', 'Fine dining restaurant with authentic Bengali cuisine', 'House 123, Road 15, Dhanmondi, Dhaka', '+8802123456789', 'info@goldenspoon.com', 'PRO'),
('660e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440002', 'Pizza Corner', 'Best pizza in town with fresh ingredients', 'Shop 45, Gulshan Avenue, Dhaka', '+8802123456790', 'contact@pizzacorner.com', 'FREE'),
('660e8400-e29b-41d4-a716-446655440003', '550e8400-e29b-41d4-a716-446655440003', 'Spice Garden', 'Traditional Indian and Pakistani cuisine', 'Building 78, Banani, Dhaka', '+8802123456791', 'hello@spicegarden.com', 'PRO');

-- Insert Menu Categories
INSERT INTO menu_categories (id, restaurant_id, name, description, display_order) VALUES
-- Golden Spoon categories
('770e8400-e29b-41d4-a716-446655440001', '660e8400-e29b-41d4-a716-446655440001', 'Appetizers', 'Start your meal with our delicious appetizers', 1),
('770e8400-e29b-41d4-a716-446655440002', '660e8400-e29b-41d4-a716-446655440001', 'Main Course', 'Traditional Bengali main dishes', 2),
('770e8400-e29b-41d4-a716-446655440003', '660e8400-e29b-41d4-a716-446655440001', 'Desserts', 'Sweet endings to your meal', 3),
('770e8400-e29b-41d4-a716-446655440004', '660e8400-e29b-41d4-a716-446655440001', 'Beverages', 'Refreshing drinks', 4),
-- Pizza Corner categories
('770e8400-e29b-41d4-a716-446655440005', '660e8400-e29b-41d4-a716-446655440002', 'Pizzas', 'Our signature pizzas', 1),
('770e8400-e29b-41d4-a716-446655440006', '660e8400-e29b-41d4-a716-446655440002', 'Sides', 'Perfect sides for your pizza', 2),
('770e8400-e29b-41d4-a716-446655440007', '660e8400-e29b-41d4-a716-446655440002', 'Drinks', 'Cold beverages', 3),
-- Spice Garden categories
('770e8400-e29b-41d4-a716-446655440008', '660e8400-e29b-41d4-a716-446655440003', 'Starters', 'Flavorful appetizers', 1),
('770e8400-e29b-41d4-a716-446655440009', '660e8400-e29b-41d4-a716-446655440003', 'Curries', 'Rich and aromatic curries', 2),
('770e8400-e29b-41d4-a716-446655440010', '660e8400-e29b-41d4-a716-446655440003', 'Breads', 'Fresh baked breads', 3);

-- Insert Menu Items
INSERT INTO menu_items (id, restaurant_id, category_id, name, description, price, is_vegetarian, is_vegan, preparation_time, display_order) VALUES
-- Golden Spoon items
('880e8400-e29b-41d4-a716-446655440001', '660e8400-e29b-41d4-a716-446655440001', '770e8400-e29b-41d4-a716-446655440001', 'Shrimp Cutlet', 'Crispy fried shrimp cutlets with special sauce', 350.00, false, false, 15, 1),
('880e8400-e29b-41d4-a716-446655440002', '660e8400-e29b-41d4-a716-446655440001', '770e8400-e29b-41d4-a716-446655440001', 'Vegetable Samosa', 'Traditional samosas filled with spiced vegetables', 120.00, true, true, 10, 2),
('880e8400-e29b-41d4-a716-446655440003', '660e8400-e29b-41d4-a716-446655440001', '770e8400-e29b-41d4-a716-446655440002', 'Hilsa Fish Curry', 'Traditional Bengali hilsa fish curry with rice', 850.00, false, false, 30, 1),
('880e8400-e29b-41d4-a716-446655440004', '660e8400-e29b-41d4-a716-446655440001', '770e8400-e29b-41d4-a716-446655440002', 'Chicken Biryani', 'Aromatic basmati rice with tender chicken', 650.00, false, false, 45, 2),
('880e8400-e29b-41d4-a716-446655440005', '660e8400-e29b-41d4-a716-446655440001', '770e8400-e29b-41d4-a716-446655440003', 'Roshogolla', 'Traditional Bengali sweet in sugar syrup', 180.00, true, false, 5, 1),
('880e8400-e29b-41d4-a716-446655440006', '660e8400-e29b-41d4-a716-446655440001', '770e8400-e29b-41d4-a716-446655440004', 'Mango Lassi', 'Refreshing mango yogurt drink', 220.00, true, false, 5, 1),

-- Pizza Corner items
('880e8400-e29b-41d4-a716-446655440007', '660e8400-e29b-41d4-a716-446655440002', '770e8400-e29b-41d4-a716-446655440005', 'Margherita Pizza', 'Classic pizza with tomato sauce, mozzarella, and basil', 450.00, true, false, 20, 1),
('880e8400-e29b-41d4-a716-446655440008', '660e8400-e29b-41d4-a716-446655440002', '770e8400-e29b-41d4-a716-446655440005', 'Pepperoni Pizza', 'Spicy pepperoni with mozzarella cheese', 550.00, false, false, 20, 2),
('880e8400-e29b-41d4-a716-446655440009', '660e8400-e29b-41d4-a716-446655440002', '770e8400-e29b-41d4-a716-446655440006', 'Garlic Bread', 'Crispy bread with garlic butter', 180.00, true, false, 10, 1),
('880e8400-e29b-41d4-a716-446655440010', '660e8400-e29b-41d4-a716-446655440002', '770e8400-e29b-41d4-a716-446655440007', 'Coca Cola', 'Chilled soft drink', 80.00, true, true, 2, 1),

-- Spice Garden items
('880e8400-e29b-41d4-a716-446655440011', '660e8400-e29b-41d4-a716-446655440003', '770e8400-e29b-41d4-a716-446655440008', 'Chicken Tikka', 'Grilled chicken marinated in spices', 420.00, false, false, 25, 1),
('880e8400-e29b-41d4-a716-446655440012', '660e8400-e29b-41d4-a716-446655440003', '770e8400-e29b-41d4-a716-446655440009', 'Butter Chicken', 'Creamy tomato-based chicken curry', 680.00, false, false, 30, 1),
('880e8400-e29b-41d4-a716-446655440013', '660e8400-e29b-41d4-a716-446655440003', '770e8400-e29b-41d4-a716-446655440010', 'Naan Bread', 'Fresh baked Indian bread', 120.00, true, false, 8, 1);

-- Insert QR Codes
INSERT INTO qr_codes (id, restaurant_id, code, table_number) VALUES
('990e8400-e29b-41d4-a716-446655440001', '660e8400-e29b-41d4-a716-446655440001', 'GS-TABLE-01', 'Table 1'),
('990e8400-e29b-41d4-a716-446655440002', '660e8400-e29b-41d4-a716-446655440001', 'GS-TABLE-02', 'Table 2'),
('990e8400-e29b-41d4-a716-446655440003', '660e8400-e29b-41d4-a716-446655440002', 'PC-TABLE-01', 'Table 1'),
('990e8400-e29b-41d4-a716-446655440004', '660e8400-e29b-41d4-a716-446655440003', 'SG-TABLE-01', 'Table 1');

-- Insert Sample Orders
INSERT INTO orders (id, restaurant_id, table_number, customer_name, customer_phone, status, total_amount, special_instructions) VALUES
('aa0e8400-e29b-41d4-a716-446655440001', '660e8400-e29b-41d4-a716-446655440001', 'Table 1', 'Mohammad Ali', '+8801712345681', 'COMPLETED', 1200.00, 'Less spicy please'),
('aa0e8400-e29b-41d4-a716-446655440002', '660e8400-e29b-41d4-a716-446655440003', 'Table 1', 'Fatima Khan', '+8801712345682', 'PREPARING', 800.00, 'Extra naan bread');

-- Insert Order Items
INSERT INTO order_items (order_id, menu_item_id, quantity, unit_price) VALUES
('aa0e8400-e29b-41d4-a716-446655440001', '880e8400-e29b-41d4-a716-446655440003', 1, 850.00),
('aa0e8400-e29b-41d4-a716-446655440001', '880e8400-e29b-41d4-a716-446655440001', 1, 350.00),
('aa0e8400-e29b-41d4-a716-446655440002', '880e8400-e29b-41d4-a716-446655440012', 1, 680.00),
('aa0e8400-e29b-41d4-a716-446655440002', '880e8400-e29b-41d4-a716-446655440013', 1, 120.00);

-- Insert Sample Feedback
INSERT INTO feedback (restaurant_id, order_id, customer_name, rating, comment, sentiment) VALUES
('660e8400-e29b-41d4-a716-446655440001', 'aa0e8400-e29b-41d4-a716-446655440001', 'Mohammad Ali', 5, 'Excellent food and service! The hilsa curry was amazing.', 'POSITIVE'),
('660e8400-e29b-41d4-a716-446655440002', NULL, 'Anonymous Customer', 4, 'Good pizza, but delivery was a bit slow.', 'POSITIVE'),
('660e8400-e29b-41d4-a716-446655440003', NULL, 'Fatima Khan', 5, 'Best Indian food in Dhaka! Highly recommended.', 'POSITIVE');
