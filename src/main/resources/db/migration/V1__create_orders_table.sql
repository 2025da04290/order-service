-- Create orders table
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id VARCHAR(50) NOT NULL UNIQUE,
    user_id VARCHAR(50) NOT NULL,
    description VARCHAR(500),
    amount DECIMAL(10, 2),
    status ENUM('PENDING', 'CONFIRMED', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED') DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_order_id (order_id),
    INDEX idx_user_id (user_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert sample orders
INSERT INTO orders (order_id, user_id, description, amount, status) VALUES
('ORD-00000001', 'admin', 'Sample Order 1', 99.99, 'DELIVERED'),
('ORD-00000002', 'admin', 'Sample Order 2', 149.50, 'PROCESSING'),
('ORD-00000003', 'user', 'Sample Order 3', 299.00, 'PENDING');
