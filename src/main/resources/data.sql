
-- you can user gen_random_uuid () to generate random IDs, use this only to generate testdata


insert into users (id, email,first_name,last_name, password)
VALUES ('ba804cb9-fa14-42a5-afaf-be488742fc54', 'admin@example.com', 'Gianluca', 'Daffré', '$2a$10$TM3PAYG3b.H98cbRrHqWa.BM7YyCqV92e/kUTBfj85AjayxGZU7d6'), -- Password: 1234
('0d8fa44c-54fd-4cd0-ace9-2a7da57992de', 'olivia.parker@example.com', 'Olivia', 'Parker', '$2a$10$TM3PAYG3b.H98cbRrHqWa.BM7YyCqV92e/kUTBfj85AjayxGZU7d6'), -- Password: 1234
('8f3dd8a1-7f1a-4c62-8cb5-2c55f9e6a4a1', 'daniel.brooks@example.com', 'Daniel', 'Brooks', '$2a$10$TM3PAYG3b.H98cbRrHqWa.BM7YyCqV92e/kUTBfj85AjayxGZU7d6'), -- Password: 1234
('1b8f87e9-1c02-4a2d-92f8-5a52f7d2e3c4', 'mia.wallace@example.com', 'Mia', 'Wallace', '$2a$10$TM3PAYG3b.H98cbRrHqWa.BM7YyCqV92e/kUTBfj85AjayxGZU7d6'), -- Password: 1234
('9b2f6c1e-9f3a-4d2b-8ad1-5c2a45d6f8a0', 'emma.wilson@example.com', 'Emma', 'Wilson', '$2a$10$TM3PAYG3b.H98cbRrHqWa.BM7YyCqV92e/kUTBfj85AjayxGZU7d6'), -- Password: 1234
('4c1a5f88-2d8f-4e6b-9b8f-8e1f1c0b2a31', 'liam.hughes@example.com', 'Liam', 'Hughes', '$2a$10$TM3PAYG3b.H98cbRrHqWa.BM7YyCqV92e/kUTBfj85AjayxGZU7d6'), -- Password: 1234
('6a0e2d5c-7b92-46d2-9d8e-1c2b9f8a4d10', 'sophia.carter@example.com', 'Sophia', 'Carter', '$2a$10$TM3PAYG3b.H98cbRrHqWa.BM7YyCqV92e/kUTBfj85AjayxGZU7d6'), -- Password: 1234
('a3d7c1b0-5e24-4a95-8f3c-7c2a1b4d9e6f', 'jackson.lee@example.com', 'Jackson', 'Lee', '$2a$10$TM3PAYG3b.H98cbRrHqWa.BM7YyCqV92e/kUTBfj85AjayxGZU7d6'), -- Password: 1234
('f7c2b4a1-3d6e-4b2f-9a1c-5e8d3b1a7c90', 'ava.morgan@example.com', 'Ava', 'Morgan', '$2a$10$TM3PAYG3b.H98cbRrHqWa.BM7YyCqV92e/kUTBfj85AjayxGZU7d6'), -- Password: 1234
('2e8b6a4d-1f93-4c7b-9e2a-0d5f7c3a9b21', 'noah.reed@example.com', 'Noah', 'Reed', '$2a$10$TM3PAYG3b.H98cbRrHqWa.BM7YyCqV92e/kUTBfj85AjayxGZU7d6') -- Password: 1234
ON CONFLICT DO NOTHING;


--ROLES
INSERT INTO role(id, name)
VALUES ('ab505c92-7280-49fd-a7de-258e618df074', 'ADMIN'),
('c6aee32d-8c35-4481-8b3e-a876a39b0c02', 'USER')
ON CONFLICT DO NOTHING;

--AUTHORITIES
INSERT INTO authority(id, name)
VALUES
       -- User Management Authorities
       ('2ebf301e-6c61-4076-98e3-2a38b31daf86', 'USER_CREATE'),
       ('76d2cbf6-5845-470e-ad5f-2edb9e09a868', 'USER_READ'),
('21c942db-a275-43f8-bdd6-d048c21bf5ab', 'USER_DEACTIVATE'),

       -- Image Post Authorities (NEU!)
       ('3f8e901a-1234-4abc-9def-111111111111', 'IMAGE_CREATE'),
       ('3f8e901a-1234-4abc-9def-222222222222', 'IMAGE_READ'),
       ('3f8e901a-1234-4abc-9def-333333333333', 'IMAGE_MODIFY'),
       ('3f8e901a-1234-4abc-9def-444444444444', 'IMAGE_DELETE'),

       -- Like Authorities (NEU!)
       ('3f8e901a-1234-4abc-9def-555555555555', 'LIKE_CREATE'),
       ('3f8e901a-1234-4abc-9def-666666666666', 'LIKE_DELETE')
ON CONFLICT DO NOTHING;

--assign roles to users
INSERT INTO users_role (users_id, role_id)
VALUES -- Alle User bekommen die USER Rolle
       ('ba804cb9-fa14-42a5-afaf-be488742fc54', 'c6aee32d-8c35-4481-8b3e-a876a39b0c02'),
       ('0d8fa44c-54fd-4cd0-ace9-2a7da57992de', 'c6aee32d-8c35-4481-8b3e-a876a39b0c02'),
       ('8f3dd8a1-7f1a-4c62-8cb5-2c55f9e6a4a1', 'c6aee32d-8c35-4481-8b3e-a876a39b0c02'),
       ('1b8f87e9-1c02-4a2d-92f8-5a52f7d2e3c4', 'c6aee32d-8c35-4481-8b3e-a876a39b0c02'),
       ('9b2f6c1e-9f3a-4d2b-8ad1-5c2a45d6f8a0', 'c6aee32d-8c35-4481-8b3e-a876a39b0c02'),
       ('4c1a5f88-2d8f-4e6b-9b8f-8e1f1c0b2a31', 'c6aee32d-8c35-4481-8b3e-a876a39b0c02'),
       ('6a0e2d5c-7b92-46d2-9d8e-1c2b9f8a4d10', 'c6aee32d-8c35-4481-8b3e-a876a39b0c02'),
       ('a3d7c1b0-5e24-4a95-8f3c-7c2a1b4d9e6f', 'c6aee32d-8c35-4481-8b3e-a876a39b0c02'),
       ('f7c2b4a1-3d6e-4b2f-9a1c-5e8d3b1a7c90', 'c6aee32d-8c35-4481-8b3e-a876a39b0c02'),
       ('2e8b6a4d-1f93-4c7b-9e2a-0d5f7c3a9b21', 'c6aee32d-8c35-4481-8b3e-a876a39b0c02'),

       -- Gianluca Daffré bekommt zusätzlich die ADMIN Rolle
       ('ba804cb9-fa14-42a5-afaf-be488742fc54', 'ab505c92-7280-49fd-a7de-258e618df074')
ON CONFLICT DO NOTHING;

--assign authorities to roles
INSERT INTO role_authority(role_id, authority_id)
VALUES -- ADMIN Role: User Management Rechte
       ('ab505c92-7280-49fd-a7de-258e618df074', '2ebf301e-6c61-4076-98e3-2a38b31daf86'), -- USER_CREATE
       ('ab505c92-7280-49fd-a7de-258e618df074', '76d2cbf6-5845-470e-ad5f-2edb9e09a868'), -- USER_READ
       ('ab505c92-7280-49fd-a7de-258e618df074', '21c942db-a275-43f8-bdd6-d048c21bf5ab'), -- USER_DEACTIVATE
       ('ab505c92-7280-49fd-a7de-258e618df074', '3f8e901a-1234-4abc-9def-222222222222'), -- IMAGE_READ
       ('ab505c92-7280-49fd-a7de-258e618df074', '3f8e901a-1234-4abc-9def-444444444444'), -- IMAGE_DELETE (kann alle Posts löschen)
       ('ab505c92-7280-49fd-a7de-258e618df074', '3f8e901a-1234-4abc-9def-333333333333'), -- IMAGE_MODIFY

       -- USER Role: Volle Image-Post Rechte (NEU!)
       ('c6aee32d-8c35-4481-8b3e-a876a39b0c02', '3f8e901a-1234-4abc-9def-111111111111'), -- IMAGE_CREATE
       ('c6aee32d-8c35-4481-8b3e-a876a39b0c02', '3f8e901a-1234-4abc-9def-222222222222'), -- IMAGE_READ
       ('c6aee32d-8c35-4481-8b3e-a876a39b0c02', '3f8e901a-1234-4abc-9def-333333333333'), -- IMAGE_MODIFY
       ('c6aee32d-8c35-4481-8b3e-a876a39b0c02', '3f8e901a-1234-4abc-9def-444444444444'), -- IMAGE_DELETE
       ('c6aee32d-8c35-4481-8b3e-a876a39b0c02', '3f8e901a-1234-4abc-9def-555555555555'), -- LIKE_CREATE
       ('c6aee32d-8c35-4481-8b3e-a876a39b0c02', '3f8e901a-1234-4abc-9def-666666666666')  -- LIKE_DELETE
ON CONFLICT DO NOTHING;
