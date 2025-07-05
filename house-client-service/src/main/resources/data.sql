CREATE TABLE house (
                       id VARCHAR(50) PRIMARY KEY,
                       building_number VARCHAR(20),
                       floor_room_number VARCHAR(20),
                       house_type VARCHAR(20),
                       price INT,
                       mortgage_status VARCHAR(10),
                       available BOOLEAN,
                       buyer_id VARCHAR(50)
);

INSERT INTO house (
    id,
    building_number,
    floor_room_number,
    house_type,
    price,
    mortgage_status,
    available,
    buyer_id
) VALUES
      ('H001', 'A1', '1F-101', '1 Bed 1 Living', 350000, 'None', true, NULL),
      ('H002', 'A1', '2F-201', '2 Bed 1 Living', 480000, 'Yes', false, 'user01'),
      ('H003', 'A1', '3F-301', '2 Bed 1 Living', 520000, 'None', true, NULL),
      ('H004', 'B1', '5F-502', '3 Bed 2 Living', 680000, 'None', true, NULL),
      ('H005', 'B1', '6F-601', '3 Bed 2 Living', 720000, 'Yes', false, 'user02'),
      ('H006', 'C1', '1F-101', '1 Bed 1 Living', 360000, 'None', true, NULL),
      ('H007', 'C1', '2F-201', '2 Bed 2 Living', 560000, 'Yes', false, 'user03'),
      ('H008', 'C1', '3F-303', '2 Bed 1 Living', 590000, 'None', true, NULL),
      ('H009', 'D1', '4F-404', '4 Bed 2 Living', 880000, 'None', true, NULL),
      ('H010', 'D1', '5F-502', '3 Bed 1 Living', 910000, 'Yes', false, 'user04'),
      ('H011', 'E1', '2F-202', '2 Bed 1 Living', 470000, 'None', true, NULL),
      ('H012', 'E1', '3F-305', '3 Bed 2 Living', 760000, 'None', true, NULL),
      ('H013', 'F1', '1F-102', '1 Bed 1 Living', 340000, 'None', true, NULL),
      ('H014', 'F1', '2F-202', '2 Bed 2 Living', 650000, 'Yes', false, 'user05'),
      ('H015', 'F1', '3F-302', '2 Bed 2 Living', 500000, 'None', true, NULL),
      ('H016', 'G1', '7F-703', '3 Bed 1 Living', 730000, 'None', true, NULL),
      ('H017', 'G1', '8F-802', '4 Bed 2 Living', 970000, 'None', true, NULL),
      ('H018', 'H1', '9F-905', '3 Bed 1 Living', 1150000, 'Yes', false, 'user06'),
      ('H019', 'I1', '10F-1001', '2 Bed 1 Living', 760000, 'None', true, NULL),
      ('H020', 'J1', '11F-1102', '2 Bed 1 Living', 520000, 'Yes', false, 'user07');
