SELECT *
FROM users
ORDER BY id;


SELECT *
FROM ads;

SELECT *
FROM message
ORDER BY id;

SELECT id, username
FROM users u
WHERE username IN (SELECT username
                   FROM users u2
                            JOIN message m on u2.id = m.user_add or m.user_id != 2
                            JOIN message m2 ON u2.id = m2.user_id or m2.user_add != 2
                   WHERE m.user_id = 2
                      or (m.user_id != 2 AND m2.user_add = 2));

SELECT m.id, m.removed, m.user_add, m.text, u.username sender
FROM message m
         JOIN users u on m.user_add = u.id
         JOIN users u2 on u2.id = m.user_id
WHERE user_add = 2 AND user_id = 1 AND m.removed = false
   OR user_add = 1 AND user_id = 2 AND m.removed = false
ORDER BY created;

SELECT m.id, m.removed, m.user_add, m.text, u.username sender
FROM message m
         JOIN users u on m.user_add = u.id
         JOIN users u2 on u2.id = m.user_id
WHERE user_id = 2
   OR user_id = 1
   OR user_add != 1
   OR user_add != 2
ORDER BY created;

UPDATE message
SET removed = false
WHERE user_add = 1
   OR user_add = 2
   OR user_add = 3
RETURNING text;

UPDATE message
SET removed = true
WHERE id = 3 AND user_id != 1 AND user_add = 1
   OR user_add != 1 AND user_id = 1
RETURNING text;

SELECT *
FROM categories;

INSERT INTO users (login, password, role)
VALUES ('dima', 'secret', 'USER');

SELECT ads.id, ads.title, ads.price, categories.name, users.username
FROM ads
         JOIN categories ON ads.categories_id = categories.id
         JOIN users ON ads.user_id = users.id
WHERE user_id = 2
ORDER BY price;

SELECT ads.id, ads.title, ads.description, ads.price, ads.created, c.name, users.id userId
FROM users
         JOIN categories c ON name
         JOIN ads ON ads.user_id = users.id
WHERE user_id = 3;



SELECT u.id, u.username, u.role, a.title ads
FROM users u
         JOIN ads a ON a.id = u.id
--JOIN massage a ON a.sender = m.id
ORDER BY id;

SELECT users.id, users.login, users.role, massage.text, massage.sender
FROM users
         JOIN massage ON massage.user_id = users.id;

INSERT INTO categories (name)
VALUES ('smartphone'),
       ('Books'),
       ('недвижимость');


DELETE
FROM users
WHERE username = 'ADMIN';

SELECT u.id, u.username, u.role, count(ads.id) ко
FROM ads
         RIGHT JOIN users u on u.id = ads.user_id
GROUP BY u.id
ORDER BY id;

SELECT s.username,
       m.text
FROM message m
         RIGHT JOIN users s ON s.id = m.user_add
group by s.username, m.text
ORDER BY s.username;



SELECT ads.id,
       ads.removed,
       ads.title,
       ads.description,
       ads.price,
       ads.created,
       categories.name,
       m.text
FROM ads
         JOIN categories ON ads.categories_id = categories.id
         JOIN users u ON ads.user_id = u.id
         JOIN message m on ads.id = m.ads_id
--JOIN users u2 on u2.username = m.user_add
WHERE user_id = 1
  AND ads.removed = false
  AND u.removed = false;

SELECT a.title обьявление, text, u.username сообщение
FROM message
         JOIN users u on u.id = message.user_add
         JOIN ads a on a.id = message.ads_id;

SELECT a.id, a.title, a.description, a.price, a.created, u.username
FROM message
         JOIN users u on u.id = message.user_add
         LEFT JOIN ads a on a.id = message.ads_id
WHERE a.user_id = 1
  AND a.removed = false
  AND u.removed = false;

SELECT a.id, a.title, a.description, a.price, a.created, u.username
FROM message
         RIGHT JOIN users u on u.id = message.user_add
         RIGHT JOIN ads a on a.id = message.ads_id
WHERE a.user_id = 1
  AND a.removed = false
  AND u.removed = false;

SELECT m.id,
       m.text,
       s.username user_add,
       s.id       add_id,
       r.username user_id
FROM message m
         JOIN users s ON s.id = m.user_add
         JOIN users r ON r.id = m.user_id;

SELECT text, user_add, u.username
FROM message
         JOIN users u on u.id = message.user_add
WHERE user_add = 2
ORDER BY created;

SELECT u.username, u.id, m.text
FROM users u
         JOIN message m on m.user_add = u.id
--WHERE u.id = 2
GROUP BY m.text, u.id, u.username;

SELECT u.id,
       s.username  user_add,
       u.username,


       count(u.id) count
FROM message m
         JOIN users s ON s.id = m.user_add
         JOIN users u ON m.user_id = u.id
WHERE m.user_id = 1
group by m.user_add, u.username, u.id, s.username;

SELECT DISTINCT m.text, u.username
FROM message m
         JOIN users u on m.user_add = u.id
         JOIN users s on m.user_id = s.id;
--WHERE u.id = 2
--group by u.username, u.id, m.text
SELECT m.text, u.username sender, u2.username
FROM message m
         JOIN users u on m.user_add = u.id
         JOIN users u2 on u2.id = m.user_id;

SELECT s.id,
       s.username        user_add,
       count(m.user_add) count
FROM message m
         JOIN users s ON s.id = m.user_add
         JOIN users u ON m.user_id = u.id
group by s.username, s.id, m.user_add, m.user_id, u.username
HAVING user_id = 4
    or user_add = 4

ORDER BY s.username;

SELECT m.text, u.username sender
FROM message m
         JOIN users u on m.user_add = u.id
         JOIN users u2 on u2.id = m.user_id
WHERE user_add = 3
   OR user_add = 1;

SELECT m.text, u.username sender
FROM message m
         JOIN users u on m.user_add = u.id
         JOIN users u2 on u2.id = m.user_id
--GROUP BY m.text, u.username, user_add, user_id, created
WHERE user_add = 1 AND user_id = 2
   or user_add = 2 AND user_id = 1
ORDER BY created;
SELECT m.text, u2.username sender, u.username
FROM message m
         JOIN users u on m.user_add = u.id
         JOIN users u2 on u2.id = m.user_id
WHERE m.user_add = 2
  AND m.user_id = 1;
--ORDER BY created;

SELECT m.text, u.username sender, u2.username
FROM message m
         JOIN users u on m.user_add = u.id
         JOIN users u2 on u2.id = m.user_id
WHERE m.user_id = 1
  AND m.user_add = 2
ORDER BY created;

--SELECT   m.text, u2.username sender, u.username  FROM message m
--JOIN users u on m.user_add = u.id
--JOIN users u2 on u2.id = m.user_id
--WHERE  m.user_add = 2 AND m.user_id = 1


--JOIN users s on m.user_id = s.id

SELECT m.text, u.username sender, u2.username
FROM message m
         JOIN users u on m.user_add = u.id
         JOIN users u2 on u2.id = m.user_id
WHERE m.user_id = ?
  AND m.user_add = ?
ORDER BY created;

SELECT
    --s.id,
    s.username user_add
    -- u.username
    --m.user_id,
    --count(m.user_add) count
FROM message m
         RIGHT JOIN users s ON s.id = m.user_add
         RIGHT JOIN users u ON m.user_id = u.id
--group by s.username, s.id, m.user_add, m.user_id, u.username
WHERE u.id = 3
ORDER BY s.username;
--LIMIT ? OFFSET ?


SELECT username
FROM users
         JOIN message m2 on m2.user_id = m2.user_add
WHERE username IN (SELECT s.username
                   FROM message m
                            JOIN users s ON s.id = m.user_add
                   WHERE username IN (SELECT u.username
                                      FROM message
                                               JOIN users u on message.user_id = u.id
                                      WHERE u.id = 3
                                         or s.id = 3))
--JOIN users u ON m.user_id = u.id
;


SELECT s.id,
       s.username       user_add,
       u.username,
       count(m.user_id) count
FROM message m
         JOIN users s ON s.id = m.user_add
         JOIN users u ON m.user_id = u.id
group by s.username, s.id, m.user_add, m.user_id, u.username
HAVING m.user_id = 1
ORDER BY s.username;

SELECT DISTINCT u.username user_add, u.id, count(m.user_id)
FROM message m
         JOIN users u on u.id = m.user_add
WHERE m.user_id = 1
GROUP BY u.id, u.username, m.id, user_add;
--LIMIT ? OFFSET ?;


SELECT username
FROM users
WHERE username IN (SELECT username user_add
                   FROM message m
                            JOIN users s ON s.id = m.user_add
                            JOIN users u ON m.user_id = u.id
                            JOIN message m2 ON m2.user_id = m2.user_add
                   WHERE m2.user_id = 3);



SELECT u.username
FROM message m
         JOIN users u on u.id = m.user_add
         JOIN users u2 on u2.id = m.user_id
WHERE m.user_id = 3
   or m.user_add = 3;


SELECT m.text, u.username sender
FROM message m
         JOIN users u on m.user_add = u.id
         JOIN users u2 on u2.id = m.user_id
WHERE user_add = 1 AND user_id = 2
   OR user_add = 2 AND user_id = 1
ORDER BY created;

SELECT s.id,
       s.username,

       count(m.user_id) count
FROM message m
         JOIN users s ON s.id = m.user_add
         JOIN users u ON m.user_id = u.id
group by s.username, s.id, m.user_add, m.user_id, u.username
HAVING m.user_add = 3
   AND m.user_id != 3


SELECT s.id,
       s.username
FROM message m
         RIGHT JOIN users s ON s.id = m.user_add
         RIGHT JOIN users u ON m.user_id = u.id
WHERE u.username IN (SELECT s.username
                     FROM users u2
                              RIGHT JOIN message m on u2.id = m.user_add
                              RIGHT JOIN message m2 ON u2.id = m2.user_id
                     WHERE m.user_id = 3
                        or (m.user_id != 3 AND m2.user_add = 3));

DELETE
FROM message
WHERE user_add = 1
  and user_id != 1;

SELECT username
FROM users u
WHERE username IN (SELECT username
                   FROM users u2
                            JOIN message m on u2.id = m.user_add or m.user_id != 1
                            JOIN message m2 ON u2.id = m2.user_id
                   WHERE m.user_id = 1
                      or (m.user_id != 1 AND m2.user_add = 1));

SELECT *
FROM message;

SELECT ads.id, ads.title, ads.price, categories.name, users.username
FROM ads
         JOIN categories ON ads.categories_id = categories.id
         JOIN users ON ads.user_id = users.id
WHERE ads.removed = false
  AND users.removed = false
ORDER BY price;


SELECT ads.id, ads.title, ads.price, categories.name, users.username, users.role
FROM ads
         JOIN categories ON ads.categories_id = categories.id
         JOIN users ON ads.user_id = users.id
WHERE ads.removed = false
  AND users.removed = false
ORDER BY price;

SELECT id, username
FROM users u
WHERE username IN (SELECT username
                   FROM users u2
                            JOIN message m on u2.id = m.user_add or m.user_id != 1
                            JOIN message m2 ON u2.id = m2.user_id or m2.user_add != 1
                   WHERE m.user_id = 1 AND m.removed = false AND m2.removed = false
                      or (m.user_id != 1 AND m2.user_add = 1 AND m.removed = false AND m2.removed = false));

SELECT u.id, username
from users u
         JOIN message m on u.id = m.user_add or m.user_id != 1
         JOIN message m2 on u.id = m2.user_id or m2.user_add != 1
WHERE m.user_id = 1 AND m.removed = false AND m2.removed = false
   OR m.user_id != 1 AND m2.user_add = 1 AND m.removed = false AND m2.removed = false
GROUP BY username, u.id;


UPDATE message
SET removed = false
WHERE user_add = 1
   OR user_add = 2
   OR user_add = 3
RETURNING text;

UPDATE remove_message
SET message = 1
WHERE
RETURNING id;

INSERT INTO remove_message (id, text, created)
SELECT id, text, created
FROM message
WHERE id = 1;
DELETE
FROM message
WHERE id = 1;

SELECT *
FROM remove_message;

SELECT *
from message
ORDER BY created;



SELECT m.id, m.user_add, m.text, m.removed, u.username sender
FROM message m
         JOIN users u on m.user_add = u.id
         JOIN users u2 on u2.id = m.user_id
WHERE user_add = 2 AND user_id = 1 AND m.removed = false
   OR m.removed = true
   OR user_add = 1 AND user_id = 2 AND m.removed = false
   OR m.removed = true

ORDER BY created;

SELECT m.id, m.user_add, m.user_id, m.text, u2.username sender
FROM message m
         JOIN users u on m.user_add = u.id
         JOIN users u2 on u2.id = m.user_id
WHERE (user_add = 1 AND user_id = 2)
   OR (user_add = 2 AND user_id = 1)
ORDER BY created;


SELECT m.id, m.user_add, m.user_id, m.text, m.removed
FROM message m
WHERE m.user_add

ORDER BY created;

DELETE
FROM message

returning id;


SELECT outgoing, user_id, user_add, u.username
FROM message m
JOIN users u on u.id = m.user_add
JOIN users u2 on u2.id = m.user_id
WHERE (m.user_add = 1 OR m.user_id != 1) AND outgoing = true OR (m.user_id = 1 OR m.user_add != 1) AND outgoing = true
ORDER BY created;

SELECT m.id, m.user_add, m.user_id, m.text, u.username sender
FROM message m
         JOIN users u on m.user_add = u.id
         JOIN users u2 on u2.id = m.user_id
WHERE m.user_add = 1
   OR m.user_id = 2
ORDER BY created;

SELECT u.username, u2.username
FROM message m
         JOIN users u on m.user_add = u.id
         JOIN users u2 on u2.id = m.user_id
WHERE user_add = 1 AND user_id = 2
   OR user_add = 2 AND user_id = 1;


SELECT m.id, m.created, u.username sender, m.text
FROM users u
         JOIN message m on u.id = m.user_add
         JOIN message m2 on u.id = m2.user_id
WHERE m.user_add = 1
  AND m.user_id = 2
GROUP BY m.id, u.username;



SELECT m.id, m.text, u1.username receiver
FROM message m
         JOIN users u1 on m.user_add = u1.id
         JOIN users u2 on m.user_id = u2.id
WHERE m.user_add = 3 AND m.user_id = 1 AND outgoing = true
   OR m.user_id = 3 AND m.user_add = 1 AND outgoing = false
ORDER BY created;


SELECT ads.id, ads.removed, ads.title, ads.description, ads.price, ads.created, categories.name
FROM ads
         JOIN categories ON ads.categories_id = categories.id
         JOIN users ON ads.user_id = users.id
WHERE ads.removed = false;

SELECT ads.id, ads.removed, ads.title, ads.description, ads.price, ads.created, c.name FROM ads
JOIN categories c on c.id = ads.categories_id
WHERE c.id = 3









