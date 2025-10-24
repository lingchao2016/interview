-- Insert sample events (table created by Hibernate)
INSERT INTO events (name, description, event_date, location, created_at, updated_at) VALUES
-- Tech Events
('Spring Boot Workshop', 'Learn the fundamentals of Spring Boot framework', '2025-11-15 10:00:00', 'San Francisco, CA', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Java Conference 2025', 'Annual Java developers conference with industry experts', '2025-12-01 09:00:00', 'New York, NY', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Microservices Meetup', 'Discussion on microservices architecture patterns', '2025-11-20 18:30:00', 'Austin, TX', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),

-- Concerts
('Taylor Swift - The Eras Tour', 'Experience the magic of Taylor Swift performing hits from all her albums', '2025-11-28 19:30:00', 'MetLife Stadium, East Rutherford, NJ', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Taylor Swift - The Eras Tour', 'Taylor Swift live in concert with special guests', '2025-12-05 19:30:00', 'SoFi Stadium, Los Angeles, CA', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Beyoncé - Renaissance World Tour', 'Queen Bey brings the Renaissance experience to life', '2025-12-10 20:00:00', 'AT&T Stadium, Arlington, TX', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Ed Sheeran - Mathematics Tour', 'Ed Sheeran performs acoustic and electric hits', '2025-11-25 19:00:00', 'Madison Square Garden, New York, NY', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('The Weeknd - After Hours Tour', 'The Weeknd live with stunning visual effects', '2025-12-08 20:00:00', 'United Center, Chicago, IL', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),

-- NBA Games
('NBA: Lakers vs Warriors', 'Los Angeles Lakers take on Golden State Warriors in Western Conference showdown', '2025-11-22 19:30:00', 'Crypto.com Arena, Los Angeles, CA', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('NBA: Celtics vs Heat', 'Boston Celtics vs Miami Heat - Eastern Conference rivalry game', '2025-11-26 20:00:00', 'TD Garden, Boston, MA', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('NBA: Nets vs Knicks', 'Brooklyn Nets vs New York Knicks - Battle for New York', '2025-12-03 19:00:00', 'Barclays Center, Brooklyn, NY', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('NBA: Mavericks vs Suns', 'Dallas Mavericks vs Phoenix Suns featuring Luka Doncic', '2025-12-07 20:30:00', 'American Airlines Center, Dallas, TX', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('NBA: Bucks vs 76ers', 'Milwaukee Bucks vs Philadelphia 76ers - Championship contenders clash', '2025-12-12 19:30:00', 'Fiserv Forum, Milwaukee, WI', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),

-- NFL Games
('NFL: Chiefs vs Bills', 'Kansas City Chiefs vs Buffalo Bills - AFC Championship rematch', '2025-11-24 16:25:00', 'Arrowhead Stadium, Kansas City, MO', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('NFL: Cowboys vs Eagles', 'Dallas Cowboys vs Philadelphia Eagles - NFC East rivalry', '2025-12-01 13:00:00', 'AT&T Stadium, Arlington, TX', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),

-- Soccer
('MLS Cup Final 2025', 'Major League Soccer Championship Final', '2025-12-09 15:00:00', 'BMO Stadium, Los Angeles, CA', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),

-- Other Sports
('UFC 300', 'Ultimate Fighting Championship - Stacked fight card', '2025-11-30 22:00:00', 'T-Mobile Arena, Las Vegas, NV', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());