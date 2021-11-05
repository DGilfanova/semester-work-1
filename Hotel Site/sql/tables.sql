create table users (
    uuid uuid not null primary key,
    first_name varchar(30) not null,
    last_name varchar(30) not null,
    patronymic varchar(30) not null,
    passport_number varchar(20) not null,
    mobile_number varchar(16) not null,
    email varchar(40) unique not null,
    hash_password varchar(32) not null,
    role varchar(6) default 'user'                    --guest, worker
);

-- зайти как gilfanova.dianka@mail.ru 12345678 //у этого пользователя статус guest -> он сможет войти
-- а k_iv@mail.ru 12345678 - зайти не сможет, у него статус user

create table booking (
    reservation_number uuid primary key,
    user_uuid uuid not null,
    foreign key (user_uuid) references users(uuid),
    room_number integer not null,
    foreign key (room_number) references room(number),
    arrival_date timestamp not null,
    departure_date timestamp not null
);

insert into booking (reservation_number, user_uuid, room_number, arrival_date, departure_date) values
('3422b448-2460-4fd2-9183-8000de6f8342', '3422b448-2460-4fd2-9183-8000de6f8343',313,'2020-09-01', '2020-09-11');

create table room (
    number integer primary key ,
    name varchar(30) not null,
    price integer not null,
    description text,
    photo_refer text
);

insert into room (number, name, price, description, photo_refer) values (313, 'Two-room suite', 2000,
        'Cool room with 2 beds, self-catering and panoramic sea view', 'https://hotelss.net/images/fiyavalhu-maldives7.jpg');

insert into room (number, name, price, description, photo_refer) values (111, 'One-room suite with sea view', 1500,
                                                          'Cool room with one bed, self-catering and panoramic sea view',
                                                          'https://hotelss.net/images/fiyavalhu-maldives7.jpg');

insert into room (number, name, price, description, photo_refer) values (112, 'Penthouse', 5000,
                                                                         'Luxury penthouse with private pool and spa, own kitchen, triple bed and Jacuzzi',
                                                                         'http://www.maldives-magazine.com/pictures/ja-manafaru-beach-residence.jpg');


create table suggestion (
    id serial primary key,
    name varchar(30) not null,
    user_uuid uuid not null,
    foreign key (user_uuid) references users(uuid),
    description text not null,
    price integer not null,
    photo_refer text not null
);

insert into suggestion (name, user_uuid, description, price, photo_refer) values ('Massage', '2a7bdf3d-aa89-4d6c-bec2-7474d25ea040',
        'A very pleasant Thai massage from a professional masseur. ' ||
        'Enjoy the pleasant smells and peaceful atmosphere.', 100, 'http://www.spabene.com/wp-content/uploads/2015/08/body-massage.jpg');

insert into suggestion (name, user_uuid, description, price, photo_refer) values ('Dinner in the room', '2a7bdf3d-aa89-4d6c-bec2-7474d25ea040',
        'The most delicious dinner in the Arab Emirates. Your chef will have incredible cooking skills and will definitely surprise you.',
        110, 'https://st.depositphotos.com/1518767/4293/i/600/depositphotos_42930411-stock-photo-concentrated-male-chef-garnishing-food.jpg');

insert into suggestion (name, user_uuid, description, price, photo_refer) values ('Hamam', '2a7bdf3d-aa89-4d6c-bec2-7474d25ea040',
                'A wonderful hammam will cleanse your skin and lungs. And also you will get a massage with soapy water',
                300, 'https://na-dache.pro/uploads/posts/2021-04/1618364825_38-na-dache_pro-p-krasivo-turetskaya-banya-khamam-foto-47.jpg');

create table orders (
    id serial primary key,
    user_uuid uuid not null,
    foreign key (user_uuid) references users(uuid),
    suggestion_id integer not null,
    foreign key (suggestion_id) references suggestion(id),
    date timestamp not null,
    status varchar(10) not null default 'active'
