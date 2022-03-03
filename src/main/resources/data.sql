INSERT INTO Train (id ,name, number_of_place_first_class, number_of_place_second_class, is_bar) values
   ('1','fast',50, 120, TRUE),
   ('2','oui-oui',72, 180, FALSE),
   ('3','Bjr',34, 156, TRUE);


INSERT INTO Place (id, train_id, is_window) values
    ('1','1', TRUE),
    ('2', '2', TRUE),
    ('3','3', FALSE);
/*
INSERT INTO City (id, name, train_id) values
    ('1','Nancy', '2'),
    ('4','Nantes', '2'),
    ('5','Marseille', '3'),
    ('6','Lille', '1'),
    ('2', 'Bettembourg', '1'),
    ('3','Paris', '3');

 */