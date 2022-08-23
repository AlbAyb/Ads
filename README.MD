SELECT id, title, price, description, user_id, categories_id FROM ads
ORDER BY id
LIMIT ? OFFSET ?

SELECT id, login, role FROM users
ORDER BY id
LIMIT ? OFFSET ?