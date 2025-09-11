-- Insert categories
INSERT INTO category (name) VALUES
                                ('Fruits'),
                                ('Vegetables'),
                                ('Dairy'),
                                ('Beverages'),
                                ('Snacks');

-- Insert products (10 items across different categories)
INSERT INTO product (name, description, price, category_id) VALUES
                                                                ('Banana', 'Fresh ripe bananas, rich in potassium', 45.00, 1),
                                                                ('Apple', 'Red apples, sweet and crunchy', 120.00, 1),
                                                                ('Tomato', 'Fresh farm tomatoes, juicy and organic', 30.00, 2),
                                                                ('Potato', 'Best quality potatoes for daily cooking', 25.00, 2),
                                                                ('Milk (1L)', 'Amul full cream milk, 1 litre pack', 65.00, 3),
                                                                ('Curd (500g)', 'Fresh thick curd, rich in probiotics', 40.00, 3),
                                                                ('Coca-Cola (2L)', 'Chilled carbonated soft drink, 2 litre bottle', 95.00, 4),
                                                                ('Tata Tea (500g)', 'Premium quality tea leaves for strong flavor', 250.00, 4),
                                                                ('Lays Chips (Classic Salted)', 'Crispy potato chips with salted flavor', 20.00, 5),
                                                                ('Parle-G Biscuits (500g)', 'Classic glucose biscuits loved by all ages', 40.00, 5);
