-- Auteur: Dave Jean Baptiste  Date: 2024-03-30
CREATE DATABASE plateformeDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;

USE plateformeDB;

CREATE TABLE administrateurs (
  nom_utilisateur         VARCHAR(15),
  mot_passe               VARCHAR(25),
  CONSTRAINT user_pk PRIMARY KEY (nom_utilisateur)
);
-- Administrateurs de la base de données
INSERT INTO administrateurs VALUES ('root','root');





CREATE TABLE utilisateurs -- Création de la table utilisateurs
(
    id_utilisateur INT NOT NULL AUTO_INCREMENT, -- Attribut : ID de l'utilisateur et implémentation de la clé primaire
    PRIMARY KEY (id_utilisateur),
    email VARCHAR(50), -- Attribut : E-mail de l'utilisateur 
    nom_utilisateur VARCHAR(60), -- Attribut : "Peusdo" de l'utilisateur
    nom VARCHAR(50),-- Attribut : Nom complète de l'utilisateur
    mot_de_passe VARCHAR(50),-- Attribut : Mot de passe de l'utilisateur
    langue VARCHAR(50),-- Attribut : Langue parlée par l'utilisateur
    interets_culturels VARCHAR(100),-- Attribut : Intérets culturels 
    actif boolean,-- Attribut : État du compte (actif,bloqué)
    privilege boolean,
    photo_profile VARCHAR(250) NULL,
    document VARCHAR(150) NULL
    
    
);

-- Langues possibles: Français,Anglais,Japonais,Espagnol
-- Liste d'intérêts culturels possibles : Sports,Automobile et mécanique,Animaux et insectes, Apprentissages et connaissances,Divertissement

INSERT INTO utilisateurs(id_utilisateur,email,nom_utilisateur,nom,mot_de_passe,langue,interets_culturels,actif,privilege,photo_profile,document)
VALUES
(1,'tedcabro@email.com','teddyc','Teddy Cabro','Cabro123','Français','Apprentissages et connaissances',1,0,NULL,NULL),
(2,'tiantren@email.com','ttian','Tian Tren','Tren123','Anglais','Apprentissages et connaissances',1,1,NULL,NULL),
(3,'davidjb@email.com','davidjb','David Jean Baptiste','Baptiste123','Français','Automobile et mécanique',1,1,NULL,NULL),
(4,'miguelohara@email.com','miguelohara',"Miguel O'hara",'Ohara123','Espagnol','Animaux et insectes',1,0,NULL,NULL),
(5,'kevinlu@email.com','kevinu','Kevin Lu','Lu123','Japonais','Apprentissages et connaissances',1,0,NULL,NULL),
(6,'mikeywilliams@email.com','mikey',' Mikey Williams','Williams123','Anglais','Sports',1,0,NULL,NULL),
(7,'airjmichael@email.com','airmjordan',' Michael Jordan','Jordan123','Anglais','Sports',1,0,NULL,NULL),
(8,'fujiwarat@email.com','fujiwarat','Takumi Fujiwara','Fujiwara123','Japonais','Automobile et mécanique',1,1,NULL,NULL),
(9,'isaacc@email.com','cisaac','Isaac Cupiles','Cupiles123','Espagnol','Sports',1,0,NULL,NULL),
(10,'annajiwoo@email.com','anjiwoo',' Anna Ji Woo','JiWoo123','Japonais','Sports',1,0,NULL,NULL),
(11,'jsmith@email.com', 'jsmith', 'Jane Smith', 'Smith123', 'Espagnol', 'Automobile et mécanique',1,0,NULL,NULL),
(12,'watsone@email.com', 'watsone', 'Emily Watson', 'Watson123', 'Anglais', 'Animaux et insectes',1,0,NULL,NULL),
(13,'rtakahashi@email.com', 'ryosuket', 'Ryosuke Takahashi', 'Takahashi123', 'Japonais', 'Automobile et mécanique',1,0,NULL,NULL),
(14,'marthama@email.com', 'mmartha', 'Martha Martinez', 'Martinez123', 'Espagnol', 'Divertissement',1,0,NULL,NULL),
(15,'elsaraoui@email.com', 'elasraoui', 'Isamel El Asraoui', 'Asraoui123', 'Français', 'Divertissement',1,0,NULL,NULL),
(16,'bernadettej@email.com','bernaj','Jacqueline Bernadette','Bernadette123','Français','Divertissement',0,0,NULL,NULL),
(17,'moralesm@email.com','milesm','Miles Morales','Morales123','Espagnol','Animaux et insectes',0,1,NULL,NULL),
(18,'doejohn@email.com','doejh','John Doe','Doe123','Anglais','Animaux et insectes',0,0,NULL,NULL),
(19,'sungjiwoo@email.com','sungjiwooo','Sung Ji Woo','JiWoo123','Japonais','Sports',1,1,NULL,NULL),
(20,'jaradanthonyhiggins@email.com','juicewrld','Jarad Anthony Higgins','juicewrld999','Anglais','Divertissement',1,0,NULL,NULL);





-- Création de la table pour les forums et discussions
CREATE TABLE forums_discussions (
    id_forum INT PRIMARY KEY,
    titre_forum VARCHAR(50),
    langue VARCHAR(15),
    interets_culturels VARCHAR(100),
    etat_forum VARCHAR(50),
    document_forum VARCHAR(150) NULL
);

-- Table de liaison entre les forums et les utilisateurs(Comme ça plus besoin de mettre une limite pour le nombre d'utilisateur)
CREATE TABLE forum_utilisateurs (
    id_forum INT,
    id_utilisateur INT,
    FOREIGN KEY (id_forum) REFERENCES forums_discussions(id_forum) ON DELETE CASCADE,
    FOREIGN KEY (id_utilisateur) REFERENCES utilisateurs(id_utilisateur) ON DELETE CASCADE,
    PRIMARY KEY (id_forum, id_utilisateur)
);

-- Insertion des données dans la table forums_discussions
INSERT INTO forums_discussions(id_forum,titre_forum,langue,interets_culturels,etat_forum,document_forum)
VALUES
(1,'Speaking English','Anglais','Divertissement','actif',NULL),
(2,"スポーツ(Supōtsu)",'Japonais','Sports','actif',NULL),
(3,"L'art de la connaissance",'Français','Apprentissages et connaissances','actif',NULL),
(4,"Top des jeux de l'années 2023",'Français',"Divertissement",'actif',NULL);

-- Insertion des données dans la table forum_utilisateurs
INSERT INTO forum_utilisateurs(id_forum, id_utilisateur)
VALUES
(1, 18),
(1, 20),
(1, 12),
(1, 7),
(1, 6),
(2, 19),
(2, 10),
(3, 1),
(4, 15),
(4, 16);


-- Création de la table messages
CREATE TABLE messages(
  id_message INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  messages VARCHAR(999),
  destinateur_id INT,
  timestamp_envoie TIMESTAMP NOT NULL,
  destinataire_id INT NULL,
  forumId INT NULL,
  document VARCHAR(150) NULL,
  likes INT NOT NULL DEFAULT 0,
  dislikes INT NOT NULL DEFAULT 0,
  user_rating INT GENERATED ALWAYS AS (`likes` - `dislikes`) STORED,
  FOREIGN KEY (destinateur_id) REFERENCES utilisateurs(id_utilisateur) ON DELETE CASCADE,
  FOREIGN KEY (destinataire_id) REFERENCES utilisateurs(id_utilisateur) ON DELETE CASCADE,
  FOREIGN KEY (forumId) REFERENCES forums_discussions(id_forum) ON DELETE CASCADE
);


INSERT INTO messages(id_message, destinateur_id, messages, destinataire_id, timestamp_envoie, forumId, document,likes, dislikes)
VALUES
(1, 1, "Salut comment ça va?", 3, '2024-03-23 13:25:00', NULL, NULL, 1, 3),
(2, 3, "Je vais bien, merci!", 1, '2024-03-23 13:25:00', NULL, NULL, 1, 2),
(3, 5, "Bienvenue à tous dans ce forum", NULL, '2024-03-23 14:00:00', 4, NULL, 2, 5);



-- Création de la table commentaire
CREATE TABLE commentaires(
  id_commentaires INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  message VARCHAR(999),
  id_commentateur INT,
  timestamp_envoie TIMESTAMP NOT NULL,
  id_message INT,
  likes INT NOT NULL DEFAULT 0,
  dislikes INT NOT NULL DEFAULT 0,
  user_rating INT GENERATED ALWAYS AS (`likes` - `dislikes`) STORED,
  FOREIGN KEY (id_commentateur) REFERENCES utilisateurs(id_utilisateur) ON DELETE CASCADE,
  FOREIGN KEY (id_message) REFERENCES messages(id_message) ON DELETE CASCADE
);

INSERT INTO commentaires (message, id_commentateur, timestamp_envoie, id_message,likes, dislikes)
VALUES
("Merci pour ce partage!", 2, '2024-03-24 09:30:00', 1, 5, 0),
("Intéressant point de vue.", 10, '2024-03-25 15:45:00', 1, 2, 1),
("Je suis d'accord avec toi.", 15, '2024-03-25 16:20:00', 1, 3, 0),
("Je n'aurais pas dit mieux.", 18, '2024-03-26 10:00:00', 1, 4, 1),
("Vivement la suite!", 6, '2024-03-26 12:30:00', 2, 7, 0),
("Je suis impatient de voir cela.", 9, '2024-03-26 13:15:00', 2, 3, 2);