-- Géneros musicales (añadido por Adrian)
INSERT INTO generos (nombre, descripcion) VALUES ('Rock', 'Música rock clásica y moderna');
INSERT INTO generos (nombre, descripcion) VALUES ('Pop', 'Música popular contemporánea');
INSERT INTO generos (nombre, descripcion) VALUES ('Jazz', 'Improvisación y swing');
INSERT INTO generos (nombre, descripcion) VALUES ('Electronica', 'Música generada por sintetizadores');

-- Artistas (de Yeison — necesario para las FKs de álbumes)
INSERT INTO artistas (nombre, pais, activo) VALUES ('The Beatles', 'Reino Unido', false);
INSERT INTO artistas (nombre, pais, activo) VALUES ('Daft Punk', 'Francia', false);
INSERT INTO artistas (nombre, pais, activo) VALUES ('Miles Davis', 'Estados Unidos', false);

-- Álbumes (de Yeison — necesario para las FKs de canciones)
INSERT INTO albumes (titulo, anio, artista_id) VALUES ('Abbey Road', 1969, 1);
INSERT INTO albumes (titulo, anio, artista_id) VALUES ('Let It Be', 1970, 1);
INSERT INTO albumes (titulo, anio, artista_id) VALUES ('Discovery', 2001, 2);
INSERT INTO albumes (titulo, anio, artista_id) VALUES ('Random Access Memories', 2013, 2);
INSERT INTO albumes (titulo, anio, artista_id) VALUES ('Kind of Blue', 1959, 3);
INSERT INTO albumes (titulo, anio, artista_id) VALUES ('Bitches Brew', 1970, 3);

-- Canciones (añadido por Adrian)
INSERT INTO canciones (titulo, duracion, album_id) VALUES ('Come Together', 259, 1);
INSERT INTO canciones (titulo, duracion, album_id) VALUES ('Something', 182, 1);
INSERT INTO canciones (titulo, duracion, album_id) VALUES ('Here Comes the Sun', 185, 1);
INSERT INTO canciones (titulo, duracion, album_id) VALUES ('Let It Be', 243, 2);
INSERT INTO canciones (titulo, duracion, album_id) VALUES ('Get Back', 190, 2);
INSERT INTO canciones (titulo, duracion, album_id) VALUES ('One More Time', 320, 3);
INSERT INTO canciones (titulo, duracion, album_id) VALUES ('Harder Better Faster Stronger', 224, 3);
INSERT INTO canciones (titulo, duracion, album_id) VALUES ('Instant Crush', 337, 4);
INSERT INTO canciones (titulo, duracion, album_id) VALUES ('Get Lucky', 369, 4);
INSERT INTO canciones (titulo, duracion, album_id) VALUES ('So What', 562, 5);
INSERT INTO canciones (titulo, duracion, album_id) VALUES ('Freddie Freeloader', 589, 5);
INSERT INTO canciones (titulo, duracion, album_id) VALUES ('Pharaohs Dance', 1099, 6);

-- Relaciones cancion_genero — tabla intermedia ManyToMany (añadido por Adrian)
INSERT INTO cancion_genero (cancion_id, genero_id) VALUES (1, 1);
INSERT INTO cancion_genero (cancion_id, genero_id) VALUES (2, 1);
INSERT INTO cancion_genero (cancion_id, genero_id) VALUES (3, 1);
INSERT INTO cancion_genero (cancion_id, genero_id) VALUES (3, 2);
INSERT INTO cancion_genero (cancion_id, genero_id) VALUES (4, 1);
INSERT INTO cancion_genero (cancion_id, genero_id) VALUES (5, 1);
INSERT INTO cancion_genero (cancion_id, genero_id) VALUES (6, 4);
INSERT INTO cancion_genero (cancion_id, genero_id) VALUES (7, 4);
INSERT INTO cancion_genero (cancion_id, genero_id) VALUES (8, 4);
INSERT INTO cancion_genero (cancion_id, genero_id) VALUES (8, 2);
INSERT INTO cancion_genero (cancion_id, genero_id) VALUES (9, 4);
INSERT INTO cancion_genero (cancion_id, genero_id) VALUES (9, 2);
INSERT INTO cancion_genero (cancion_id, genero_id) VALUES (10, 3);
INSERT INTO cancion_genero (cancion_id, genero_id) VALUES (11, 3);
INSERT INTO cancion_genero (cancion_id, genero_id) VALUES (12, 3);
