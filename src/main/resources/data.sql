-- noinspection SqlDialectInspectionForFile
insert into train (date, is_bar, name, number_of_place_first_class, number_of_place_second_class, start_city, id)
values ('2022-11-24T14:00:00', true, 'RER', 1, 2, 'Nancy', '1');

insert into train_journey (train_id, journey) values ('1', 'Nantes'),
                                                     ('1', 'Marseille'),
                                                     ('1', 'Paris');

insert into place (is_window, occupied, price, train_id, id_place) values
                                                                       (true, false, 50, '1', '1'),
                                                                       (false, false, 60, '1', '2'),
                                                                       (true, false, 20, '1', '3');

insert into train (date, is_bar, name, number_of_place_first_class, number_of_place_second_class, start_city, id)
values ('2022-11-24T14:00:00', true, 'RER', 1, 2, 'Marseille', '2');

insert into train_journey (train_id, journey) values ('2', 'Lille'),
                                                     ('2', 'Nice'),
                                                     ('2', 'Nancy');

insert into place (is_window, occupied, price, train_id, id_place) values
                                                                       (true, false, 50, '2', '4'),
                                                                       (false, false, 60, '2', '5'),
                                                                       (true, false, 20, '2', '6');

