---------------- CATEGORIAS ----------------

INSERT INTO tb_category(name) VALUES ('Hardware');
INSERT INTO tb_category(name) VALUES ('Periféricos');
INSERT INTO tb_category(name) VALUES ('Componentes');
INSERT INTO tb_category(name) VALUES ('Cabos e Adaptadores');
INSERT INTO tb_category(name) VALUES ('Equipamentos de Rede');
INSERT INTO tb_category(name) VALUES ('Ferramentas');


---------------- PRODUTOS ----------------

INSERT INTO tb_product(name, description, sku, price, active) VALUES ('Processador i5-12400f', 'Processador de 12ª geração popular, com 6 núcleos e 12 threads (2.5GHz, até 4.4GHz Turbo), ideal para jogos e custo-benefício', 'A001-P001', 699, true);
INSERT INTO tb_product(name, description, sku, price, active) VALUES ('Processador i5-12400f', 'Processador de 12ª geração popular, com 6 núcleos e 12 threads (2.5GHz até 4.4GHz Turbo), ideal para jogos e custo-benefício', 'A001-P001', 699, true);
INSERT INTO tb_product(name, description, sku, price, active) VALUES ('Processador Ryzen 5 5600', 'Processador AMD com 6 núcleos e 12 threads, arquitetura Zen 3, excelente desempenho para jogos e aplicações', 'A001-P002', 749, true);
INSERT INTO tb_product(name, description, sku, price, active) VALUES ('Memória RAM 16GB DDR4 3200MHz', 'Memória RAM DDR4 com frequência de 3200MHz, ideal para upgrades e sistemas de alto desempenho', 'A002-P003', 289, true);
INSERT INTO tb_product(name, description, sku, price, active) VALUES ('SSD NVMe 1TB', 'SSD NVMe de alta velocidade com leitura acima de 3000MB/s, ideal para sistemas operacionais e jogos', 'A002-P004', 399, true);
INSERT INTO tb_product(name, description, sku, price, active) VALUES ('Placa de Vídeo RX 6600', 'Placa de vídeo AMD Radeon RX 6600 com 8GB GDDR6, excelente para jogos em Full HD e 1440p', 'A003-P005', 1299, true);
INSERT INTO tb_product(name, description, sku, price, active) VALUES ('Placa-mãe B550M', 'Placa-mãe compatível com processadores AMD Ryzen, suporte a PCIe 4.0 e memórias DDR4', 'A003-P006', 699, true);
INSERT INTO tb_product(name, description, sku, price, active) VALUES ('Mouse Gamer RGB', 'Mouse gamer com sensor óptico de alta precisão e iluminação RGB configurável', 'A004-P007', 89, true);
INSERT INTO tb_product(name, description, sku, price, active) VALUES ('Teclado Mecânico RGB', 'Teclado mecânico com switches azuis, iluminação RGB e estrutura reforçada', 'A004-P008', 249, true);
INSERT INTO tb_product(name, description, sku, price, active) VALUES ('Monitor 24 Full HD', 'Monitor 24 polegadas com resolução Full HD e taxa de atualização de 75Hz', 'A004-P009', 799, true);
INSERT INTO tb_product(name, description, sku, price, active) VALUES ('Cabo HDMI 2.0 2m', 'Cabo HDMI 2.0 de 2 metros compatível com resoluções 4K', 'A005-P010', 29, true);
INSERT INTO tb_product(name, description, sku, price, active) VALUES ('Adaptador USB-C para HDMI', 'Adaptador para conectar dispositivos USB-C a monitores HDMI', 'A005-P011', 79, true);
INSERT INTO tb_product(name, description, sku, price, active) VALUES ('Cabo de Rede Cat6 5m', 'Cabo Ethernet Cat6 de 5 metros para redes gigabit', 'A005-P012', 25, true);
INSERT INTO tb_product(name, description, sku, price, active) VALUES ('Roteador WiFi Gigabit', 'Roteador dual band com suporte a velocidades gigabit e múltiplos dispositivos', 'A006-P013', 349, true);
INSERT INTO tb_product(name, description, sku, price, active) VALUES ('Switch 8 Portas Gigabit', 'Switch de rede com 8 portas gigabit para expansão de rede local', 'A006-P014', 199, true);
INSERT INTO tb_product(name, description, sku, price, active) VALUES ('Kit Chave de Precisão', 'Kit de ferramentas com múltiplas pontas para manutenção de eletrônicos', 'A007-P015', 59, true);
INSERT INTO tb_product(name, description, sku, price, active) VALUES ('Multímetro Digital', 'Multímetro digital para medição de tensão, corrente e resistência', 'A007-P016', 119, true);


---------------- PRODUCT CATEGORY ----------------

INSERT INTO tb_product_category(product_id, category_id) VALUES (1, 3);
INSERT INTO tb_product_category(product_id, category_id) VALUES (2, 3);
INSERT INTO tb_product_category(product_id, category_id) VALUES (3, 3);
INSERT INTO tb_product_category(product_id, category_id) VALUES (4, 3);

INSERT INTO tb_product_category(product_id, category_id) VALUES (5, 1);
INSERT INTO tb_product_category(product_id, category_id) VALUES (6, 1);

INSERT INTO tb_product_category(product_id, category_id) VALUES (7, 2);
INSERT INTO tb_product_category(product_id, category_id) VALUES (8, 2);
INSERT INTO tb_product_category(product_id, category_id) VALUES (9, 2);

INSERT INTO tb_product_category(product_id, category_id) VALUES (10, 4);
INSERT INTO tb_product_category(product_id, category_id) VALUES (11, 4);
INSERT INTO tb_product_category(product_id, category_id) VALUES (12, 4);

INSERT INTO tb_product_category(product_id, category_id) VALUES (13, 5);
INSERT INTO tb_product_category(product_id, category_id) VALUES (14, 5);

INSERT INTO tb_product_category(product_id, category_id) VALUES (15, 6);
INSERT INTO tb_product_category(product_id, category_id) VALUES (16, 6);


---------------- STOCK ----------------

INSERT INTO tb_stock(quantity, minimium_stock, product_id) VALUES (25, 5, 1);
INSERT INTO tb_stock(quantity, minimium_stock, product_id) VALUES (18, 5, 2);
INSERT INTO tb_stock(quantity, minimium_stock, product_id) VALUES (40, 10, 3);
INSERT INTO tb_stock(quantity, minimium_stock, product_id) VALUES (22, 5, 4);

INSERT INTO tb_stock(quantity, minimium_stock, product_id) VALUES (12, 2, 5);
INSERT INTO tb_stock(quantity, minimium_stock, product_id) VALUES (10, 2, 6);

INSERT INTO tb_stock(quantity, minimium_stock, product_id) VALUES (35, 8, 7);
INSERT INTO tb_stock(quantity, minimium_stock, product_id) VALUES (20, 5, 8);
INSERT INTO tb_stock(quantity, minimium_stock, product_id) VALUES (15, 3, 9);

INSERT INTO tb_stock(quantity, minimium_stock, product_id) VALUES (50, 10, 10);
INSERT INTO tb_stock(quantity, minimium_stock, product_id) VALUES (30, 5, 11);
INSERT INTO tb_stock(quantity, minimium_stock, product_id) VALUES (45, 10, 12);

INSERT INTO tb_stock(quantity, minimium_stock, product_id) VALUES (14, 3, 13);
INSERT INTO tb_stock(quantity, minimium_stock, product_id) VALUES (16, 4, 14);

INSERT INTO tb_stock(quantity, minimium_stock, product_id) VALUES (28, 5, 15);
INSERT INTO tb_stock(quantity, minimium_stock, product_id) VALUES (19, 4, 16);