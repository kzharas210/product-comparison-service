INSERT INTO providers (id, name)
values (1, 'provider1'),
       (2, 'provider2');

INSERT INTO provider_rankings (id, ranking, provider_id)
values (1, 3, 1);

INSERT INTO products (id, name, category)
values (1, 'name1', 'category1'),
       (2, 'name2', 'category2'),
       (3, 'name3', 'category3');

INSERT INTO provider_products (id, provider_id, product_id, price)
values (1, 1, 1, 1),
       (2, 1, 2, 2),
       (3, 1, 3, 4),
       (4, 2, 2, 3),
       (5, 2, 3, 4);