-- Create books table
CREATE TABLE IF NOT EXISTS books (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL UNIQUE,
    author VARCHAR(255),
    isbn VARCHAR(20) UNIQUE,
    available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample data
INSERT INTO books (title, author, isbn, available) VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', '9780743273565', TRUE),
('1984', 'George Orwell', '9780451524935', FALSE),
('To Kill a Mockingbird', 'Harper Lee', '9780061120084', TRUE),
('Pride and Prejudice', 'Jane Austen', '9780141439518', TRUE),
('The Catcher in the Rye', 'J.D. Salinger', '9780316769488', FALSE),
('Brave New World', 'Aldous Huxley', '9780060850524', TRUE),
('Lord of the Flies', 'William Golding', '9780399501487', TRUE);