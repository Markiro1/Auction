INSERT INTO auction_db.public.roles (name) VALUES ('ROLE_USER') ON CONFLICT DO NOTHING;
INSERT INTO auction_db.public.roles (name) VALUES ('ROLE_ADMIN') ON CONFLICT DO NOTHING;

INSERT INTO auction_db.public.auction_statuses (status_name) VALUES ('PAUSED') ON CONFLICT DO NOTHING;
INSERT INTO auction_db.public.auction_statuses (status_name) VALUES ('CLOSED') ON CONFLICT DO NOTHING;
INSERT INTO auction_db.public.auction_statuses (status_name) VALUES ('ACTIVE') ON CONFLICT DO NOTHING;

INSERT INTO auction_db.public.payment_statuses (status_name) VALUES ('PAID') ON CONFLICT DO NOTHING;
INSERT INTO auction_db.public.payment_statuses (status_name) VALUES ('UNPAID') ON CONFLICT DO NOTHING;

INSERT INTO auction_db.public.product_statuses (status_name) VALUES ('ON_REVIEW') ON CONFLICT DO NOTHING;
INSERT INTO auction_db.public.product_statuses (status_name) VALUES ('AUCTIONED') ON CONFLICT DO NOTHING;
INSERT INTO auction_db.public.product_statuses (status_name) VALUES ('SOLD') ON CONFLICT DO NOTHING;
INSERT INTO auction_db.public.product_statuses (status_name) VALUES ('ACCEPTED') ON CONFLICT DO NOTHING;

INSERT INTO auction_db.public.users (email, first_name, last_name, password, role_id, created_at)
VALUES (
        'admin@gmail.com',
        'admin',
        'admin',
        '$2a$10$nl52NhqcY2UKHERad4UZf.aFJedNwYAdNKQ/y9fpjfE/FvD2yf.0i',
        2,
        now()
       ) ON CONFLICT DO NOTHING;