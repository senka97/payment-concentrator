insert into app (app_id, app_name, official_email, web_address)
values ('43d86eb5-b5e9-46db-ae03-af4a84350770', 'Literary Association', 'literaryassociation7@gmail.com', 'https://literaryassociation.com');

-- insert into merchant (merchant_id, merchant_email, password, error_url, failed_url, success_url, app_id)
-- values ('DsTbyWuTlNWR6ziyOnleu9TcMvngn3SKlOvTltzDXxw=|QxwKnPj+Mn96YbCzSSouOg==', 'sb-nsr1z4072854@business.example.com', '4M8HB0qUUuJ6bgQZvzzPAA==|Ue9pvnZiKYCURTXnbLmPnQ==', 'https://localhost:3000/error', 'https://localhost:3000/failed', 'https://localhost:3000/success', 1);

insert into role (name) values ('ROLE_ADMIN');
insert into role (name) values ('ROLE_MERCHANT');

insert into permission (name) values ('create_app'); --admin
insert into permission (name) values ('pm_all_read'); --admin
insert into permission (name) values ('merchant_info'); -- merchant
insert into permission (name) values ('payment_method_add'); --merchant
insert into permission (name) values ('pm_data_read'); --merchant
insert into permission (name) values ('billing_plan_create'); --merchant
insert into role_permissions (role_id, permission_id) values (1,1);
insert into role_permissions (role_id, permission_id) values (1,2);
insert into role_permissions (role_id, permission_id) values (2,3);
insert into role_permissions (role_id, permission_id) values (2,4);
insert into role_permissions (role_id, permission_id) values (2,5);
insert into role_permissions (role_id, permission_id) values (2,6);


-- insert into merchant (merchant_name, merchant_email, merchant_id, merchant_password, password, activated, error_url, failed_url, success_url, app_id)
-- values ('Vulkan knjizare', 'sb-nsr1z4072854@business.example.com','cVnsMpdb6OIwN8y9yPkVqVScGi1q5RGKh8nsmZjUgsk=|8bAC7Y7K5L8cDdoBhj2Erw==', 'eqRcB55gBHQwhvVZpnqxwg==|zZ/AAJw9MZUDdfXbipfbDw==', 'neki_password', true, 'https://localhost:3000/error', 'https://localhost:3000/failed', 'https://localhost:3000/success', 1);

-- password: admin12345
insert into user (type, email, password, enabled, admin_first_name, admin_last_name, merchant_name, merchant_id, merchant_password, password_changed, pm_chosen, activation_url, error_url, failed_url, success_url, app_id)
values ('Admin', 'admin@gmail.com', '$2a$10$3y.kIg0sWXhAFUmB02Bie.UA5dPXyvFfXA4RodXQtiNfZm4HAEhfK', true, 'Admin', 'Adminic', null, null, null, null, null, null, null, null, null, null);

-- password: merchant12345

insert into user (type, email, password, enabled, admin_first_name, admin_last_name, merchant_name, merchant_id, merchant_password, password_changed, pm_chosen, activation_url, error_url, failed_url, success_url, app_id)
values ('Merchant', 'sb-nsr1z4072854@business.example.com', '$2a$10$tllqdp0Swip2gzN0vRX2/erVNJy6vEG2y7agwiZkkiWRP4tthNWCC', true, null, null, 'Vulkan knjizare', 'g9UpDL2GWCR4OfGkOhA7Gn5/arUAzC5aTPxzTUoMAwI=|V4qx3sTETz1MSo0Il4cseg==', 'AVSdMI2bIFQ3zahW4Wkgow==|NjH+Crr/Zb0oymvaz8kVMw==', true, true, 'https://localhost:8080/api/merchant/activate', 'https://localhost:3000/error', 'https://localhost:3000/failed', 'https://localhost:3000/success', 1);

insert into user (type, email, password, enabled, admin_first_name, admin_last_name, merchant_name, merchant_id, merchant_password, password_changed, pm_chosen, activation_url, error_url, failed_url, success_url, app_id)
values ('Merchant', 'laguna@gmail.com', '$2a$10$tllqdp0Swip2gzN0vRX2/erVNJy6vEG2y7agwiZkkiWRP4tthNWCC', true, null, null, 'Laguna', 'Wb4USg1jqh6WCHVssU4gPg==|ErxhHJsw+je462kF7tH5cw==', 'elRzuGdb8T6VkLzTFsj2qg==|ErxhHJsw+je462kF7tH5cw==', true, true, 'https://localhost:8080/api/merchant/activate', 'https://localhost:3000/error', 'https://localhost:3000/failed', 'https://localhost:3000/success', 1);

insert into user_roles (user_id, role_id) values (1,1);
insert into user_roles (user_id, role_id) values (2,2);
insert into user_roles (user_id, role_id) values (3,2);

insert into payment_method (name, deleted) values ('Bank', false);
insert into payment_method (name, deleted) values ('PayPal', false);
insert into payment_method (name, deleted) values ('Bitcoin', false);

insert into apps_payment_methods (app_id, payment_method_id) values (1,1);
insert into apps_payment_methods (app_id, payment_method_id) values (1,2);
insert into apps_payment_methods (app_id, payment_method_id) values (1,3);

insert into merchants_payment_methods (merchant_id, payment_method_id) values (2,1);
insert into merchants_payment_methods (merchant_id, payment_method_id) values (2,2);
insert into merchants_payment_methods (merchant_id, payment_method_id) values (2,3);

--insert into merchants_payment_methods (merchant_id, payment_method_id) values (3,1);
--insert into merchants_payment_methods (merchant_id, payment_method_id) values (3,2);
insert into merchants_payment_methods (merchant_id, payment_method_id) values (3,3);

insert into billing_plan ( cycles_number, price, discount, is_default, type, frequency )
values ( 6, 3, 5, true, 'FIXED', 'MONTH');
insert into billing_plan ( cycles_number, price, discount, is_default, type, frequency )
values ( 12, 2, 10, true, 'FIXED', 'MONTH');
insert into billing_plan ( cycles_number, price, discount, is_default, type, frequency )
values ( 1, 15, 12, true, 'FIXED', 'YEAR');

insert into billing_plan ( cycles_number, price, discount, is_default, merchant_id, type, frequency )
values ( 2, 25, 15, false, 2 ,'FIXED', 'YEAR');
