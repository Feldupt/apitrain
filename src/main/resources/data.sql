INSERT INTO Train (id ,name, start_city, number_of_place_first_class, number_of_place_second_class, is_bar, date) values
   ('1','fast', 'Nancy', 50, 120, TRUE, '2022-02-23T10:00:00'),
   ('2','oui-oui','Nancy',72, 180, FALSE, '2022-03-01T11:00:00'),
   ('3','Bjr','Marseille', 34, 156, TRUE, '2022-03-02T12:00:00');


INSERT INTO Place (id, train_id, is_window) values
    ('1','1', TRUE),
    ('2','1', TRUE),
    ('3','1', FALSE),
    ('4','1', TRUE),
    ('5', '2', TRUE),
    ('6','3', FALSE);
/*
INSERT INTO City (id, name, train_id) values
    ('1','Nancy', '2'),
    ('4','Nantes', '2'),
    ('5','Marseille', '3'),
    ('6','Lille', '1'),
    ('2', 'Bettembourg', '1'),
    ('3','Paris', '3');

 */