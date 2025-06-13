-- Clear existing data
DELETE FROM bookunits;

-- Insert test book units
INSERT INTO bookunits (id, book_id, language, page_count, availability, cover_image_link, publisher, isbn, created_at)
VALUES 
(1, 1, 'English', 300, true, 'http://example.com/cover1.jpg', 'Test Publisher 1', '978-3-16-148410-0', CURRENT_DATE),
(2, 1, 'Spanish', 350, true, 'http://example.com/cover2.jpg', 'Test Publisher 2', '978-3-16-148410-1', CURRENT_DATE),
(3, 2, 'English', 400, true, 'http://example.com/cover3.jpg', 'Test Publisher 3', '978-3-16-148410-2', CURRENT_DATE); 