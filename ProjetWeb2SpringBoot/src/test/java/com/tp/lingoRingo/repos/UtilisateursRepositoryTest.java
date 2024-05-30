package com.tp.lingoRingo.repos;


import com.tp.lingoRingo.entities.Role;
import com.tp.lingoRingo.entities.Utilisateur;
import com.tp.lingoRingo.repos.UtilisateursRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)  // Empêcher la suppression de données créées
class UtilisateursRepositoryTest {
    @Autowired
    UtilisateursRepository repos;
    @Autowired
    private TestEntityManager entityManager;  // Pour gérer les testes

    @Test
    public void testFindAll(){

        Iterable<Utilisateur> listUtilisateurs =  repos.findAll();

        listUtilisateurs.forEach(utilisateurs -> System.out.println(utilisateurs.toString()));

    }


    @Test
    public void testFindUtilisateurParNomUtilisateurContient() {
        System.out.println("Entrez le nom d'utilisateur à rechercher : ");
        String nomUtilisateur = "a";
        Iterable<String> listUtilisateurs =  repos.findAllUtilisateursByNomUtilisateurContient(nomUtilisateur);
        System.out.println("Résultats de la recherche : ");
        listUtilisateurs.forEach(System.out::println);

    }
    @Test
    public void connexionUtilisateur(){
        String email = "mikeywilliams@email.com";
        String mot_de_passe = "Williams123";
        Utilisateur utilisateur = repos.connexionUtilisateur(email,mot_de_passe);
        System.out.println(utilisateur.toString());
        assertThat(utilisateur).isNotNull();
    }

    @Test
    public void findAllUtilisateurByForumAdmin(){
        int id_forum = 2;
        Iterable<Utilisateur> listUtilisateurs =  repos.findAllUtilisateurByForumAdmin(id_forum);
        listUtilisateurs.forEach(utilisateur -> System.out.println(utilisateur.toString()));
    }
    @Test
    public void findAllUtilisateurByForum(){
        int id_forum = 1;
        Iterable<String> listUtilisateurs =  repos.findAllUtilisateurByForum(id_forum);
        listUtilisateurs.forEach(utilisateur -> System.out.println(utilisateur.toString()));
    }

    @Test
    public void testFindByNom(){
        System.out.println("Test #01");
        String nom ="Tian Tren";
        Utilisateur utilisateur =  repos.findByNom(nom);
        System.out.println(utilisateur);
        assertThat(utilisateur).isNotNull();
    }

    @Test
    public void testSaveUtilisateur(){
        //String email, String nom_utilisateur, String nom, String mot_de_passe, String langue, String interets_culturels, boolean actif

        Role roleAdmin = entityManager.find(Role.class, 1);
        Utilisateur userYoussef = new Utilisateur("youssef.ayad@gmail.com", "YoussBack", "Youssef", "root", "Francais", "Japanimation", true);
        userYoussef.setDocument("textdocument.docx");
        userYoussef.setPhoto("textphoto.jgp");
        userYoussef.ajouter(roleAdmin);
        Utilisateur savedUser =  repos.save(userYoussef);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testSaveAutreUtilisateur(){
        //String email, String nom_utilisateur, String nom, String mot_de_passe, String langue, String interets_culturels, boolean actif

        Role roleAdmin = entityManager.find(Role.class, 2);
        Utilisateur userRachid = new Utilisateur("chidra@gmail.com", "Chidra", "Rachid", "root", "Francais", "Japanimation", true);
        userRachid.ajouter(roleAdmin);
        Utilisateur savedUser =  repos.save(userRachid);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }



}
