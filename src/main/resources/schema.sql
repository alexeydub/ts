create table `shop_order` (
id integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), 
order_reference VARCHAR(255) not null, 
payment_type VARCHAR(255) not null, 
user_ref integer not null, shop_ref integer not null
);

create table `user` (
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    first_name VARCHAR(255),
	last_name VARCHAR(255),
	email VARCHAR(255) NOT NULL,
	address VARCHAR(255)
);

create table `shop` (
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    url VARCHAR(255) NOT NULL
);