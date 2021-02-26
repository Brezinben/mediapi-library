INSERT INTO public.groups (id, name, created_at)
VALUES (1, 'ACDC', '1973-02-01 00:00:00.000000');
INSERT INTO public.groups (id, name, created_at)
VALUES (2, 'Bring Me The Horizon', '2013-02-04 00:00:00.000000');
INSERT INTO public.groups (id, name, created_at)
VALUES (3, 'Alpha Wann', '2021-02-15 21:44:22.000000');

INSERT INTO public.albums (id, title, release, group_id)
VALUES (6, 'Sempiternal (Expanded Edition)', '2013-02-14 00:00:00.000000', 2),
       (2, 'Rock or Bust', '2014-02-06 00:00:00.000000', 1),
       (5, 'That''s The Spirit', '2015-02-13 00:00:00.000000', 2),
       (3, 'Don Dada Mixtape', '2020-02-06 00:00:00.000000', 3),
       (1, 'Power Up', '2020-11-13 00:00:00.000000', 1),
       (4, 'POST HUMAN: SURVIVAL HORROR', '2020-12-31 00:00:00.000000', 2);



INSERT INTO public.items (id, state, type, album_id)
VALUES (1, 'NEUF', 'CD', 2),
       (2, 'NEUF', 'CD', 2),
       (3, 'NEUF', 'CD', 2),
       (4, 'NEUF', 'CD', 2),
       (5, 'NEUF', 'CD', 2),
       (6, 'NEUF', 'CD', 2),
       (7, 'CONVENABLE', 'VINYLE', 3),
       (8, 'CONVENABLE', 'VINYLE', 3),
       (9, 'CONVENABLE', 'VINYLE', 3),
       (10, 'CONVENABLE', 'VINYLE', 3),
       (11, 'CONVENABLE', 'VINYLE', 3),
       (12, 'CONVENABLE', 'VINYLE', 3),
       (13, 'ABIMER', 'CD', 2);