INSERT INTO application_user (id, email, name) VALUES (1, 'ir@financialshop.com', 'Jack');
INSERT INTO application_user (id, email, name) VALUES (2, 'noreply@financialshop.com', 'John');


INSERT INTO portfolio (id, created_date, is_public, name, investor_id) VALUES (1, current_date, true, 'Tech stocks', 1);

INSERT INTO comment (id, content, date, investor_id, portfolio_id) VALUES (1, 'Hey, I like it', current_date, 1, 1);
INSERT INTO comment (id, content, date, investor_id, portfolio_id) VALUES (2, 'Would this portfolio do well in a recession?', current_date, 1, 1);