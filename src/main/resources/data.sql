insert into product (name, price, image_url)
values('맥북에어', 1250000, 'https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcTVEsMB9-Mm17xUSCrL2oIxZF6H7mn1CZweoW_lyPRCuTKZ066oiJuHaLGVgf1mDqbLf9SAeKmAdA');

insert into product (name, price, image_url)
values('애플워치', 123000, 'https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcQFcxc2JlxNMqqmlpX3_D6hC5ITnTDEZmytNsgR6K3jnwTF5G_Lk3LJ1xivRZz9yWLh3Tmlw3nN');

insert into product (name, price, image_url)
values('맥북프로', 2250000, 'https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcS5oCkTuSJ0409B_5FFITHRaXypcr3-hJHtplxcXiXyYK9y8K7Lx-kgWbVhFaHVUP1NeexGHAsqVg');

insert into member (role, email, password)
values('ADMIN','testadmin1@kakao.com', '12345678');

insert into member (role, email, password)
values('ADMIN','testadmin@samsung.com', '12345678');

insert into member (role, email, password)
values('USER','testuser1@naver.com', '12345678');

insert into member (role, email, password)
values('USER','testuser2@apple.com', '12345678');

insert into product(id, name, price, image_url)
values(9999L, '아이폰16Pro', 1550000, 'https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcQtIiGp7I3QmpgBrbgqKJP4TMmjCAQQ5PKZADLBuzDere8Z9iWFUzfv-jvW0F7qP9gLewthFhuVUA');

insert into option(name, quantity, price, product_id)
values ('128GB', 999, 100000, 9999L),
       ('256GB', 999, 200000, 9999L),
       ('512GB', 999, 300000, 9999L),
       ('1TB', 999, 400000, 9999L)