package com.tp.lingoRingo.repos;
import com.tp.lingoRingo.entities.Ami;
import com.tp.lingoRingo.entities.Utilisateur;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmisRepository extends JpaRepository<Ami,Integer> {

    //Afficher tous les amis d'un utilisateur selon un Administrateur(Toutes les demandes peu import l'état)
    @Query("SELECT a FROM Ami a WHERE a.id_utilisateur1.id_utilisateur = :id_utilisateur1")
    List<Ami> findAmiByUtilisateurAdmin(@Param("id_utilisateur1") int id_utilisateur1);


    //Afficher tous les amis d'un utilisateur selon un Utilisateur(Du coup les demandes acceptées)
    @Query("SELECT a FROM Ami a WHERE a.id_utilisateur1.id_utilisateur = :id_utilisateur1 AND a.demande_acceptee = true")
    List<Ami> findAmiByUtilisateur(@Param("id_utilisateur1") int id_utilisateur1);

    //Afficher l'ajout d'ami

    //Créer une demande d'ami
    @Modifying
    @Query(value = "INSERT INTO amis (id_utilisateur1, id_utilisateur2, demande_acceptee, favori) " +
            "VALUES (:idUtilisateur1, :idUtilisateur2, false, false)", nativeQuery = true)
    void creerDemandeAmi(@Param("idUtilisateur1") int idUtilisateur1, @Param("idUtilisateur2") int idUtilisateur2);

    //Afficher les demandes d'ami en attente:
    @Query("SELECT a FROM Ami a WHERE a.id_utilisateur1.id_utilisateur = :id_utilisateur1 AND a.demande_acceptee = false")
    List<Ami> findDemandeAmiByUtilisateur(@Param("id_utilisateur1") int id_utilisateur1);


    //Accepter une demande d'ami
    @Modifying
    @Query("UPDATE Ami a SET a.demande_acceptee = TRUE WHERE a.id_utilisateur1.id_utilisateur = :id_utilisateur1 AND a.id_utilisateur2.id_utilisateur = :id_utilisateur2")
    void accepterDemandeAmi(@Param("id_utilisateur1") int id_utilisateur1, @Param("id_utilisateur2") int id_utilisateur2);

    //Refuser une demande d'ami
    @Modifying
    @Transactional
    @Query("DELETE FROM Ami a WHERE a.id_utilisateur1.id_utilisateur = :id_utilisateur1 AND a.id_utilisateur2.id_utilisateur = :id_utilisateur2 OR a.id_utilisateur1.id_utilisateur = :id_utilisateur2 AND a.id_utilisateur2.id_utilisateur = :id_utilisateur1")
    void refuserDemandeAmi(@Param("id_utilisateur1") int id_utilisateur1, @Param("id_utilisateur2") int id_utilisateur2);


    //Supprimer un ami
    @Modifying
    @Transactional
    @Query("DELETE FROM Ami a WHERE a.id_utilisateur1.id_utilisateur = :id_utilisateur1 AND a.id_utilisateur2.id_utilisateur = :id_utilisateur2 OR a.id_utilisateur1.id_utilisateur = :id_utilisateur2 AND a.id_utilisateur2.id_utilisateur = :id_utilisateur1")
    void supprimerRelationAmi(@Param("id_utilisateur1") int id_utilisateur1, @Param("id_utilisateur2") int id_utilisateur2);

    // Afficher tous les amis favoris d'un utilisateur
    @Query("SELECT a FROM Ami a WHERE a.id_utilisateur1.id_utilisateur = :id_utilisateur1 AND a.favori = true")
    List<Ami> findAmiFavoriByUtilisateur(@Param("id_utilisateur1") int id_utilisateur1);

    // Marquer un ami comme favori
    @Modifying
    @Query("UPDATE Ami a SET a.favori = TRUE WHERE a.id_utilisateur1.id_utilisateur = :id_utilisateur1 AND a.id_utilisateur2.id_utilisateur = :id_utilisateur2")
    void ajouterFavori(@Param("id_utilisateur1") int id_utilisateur1, @Param("id_utilisateur2") int id_utilisateur2);

    // Retirer un ami des favoris
    @Modifying
    @Query("UPDATE Ami a SET a.favori = FALSE WHERE a.id_utilisateur1.id_utilisateur = :id_utilisateur1 AND a.id_utilisateur2.id_utilisateur = :id_utilisateur2")
    void retirerFavori(@Param("id_utilisateur1") int id_utilisateur1, @Param("id_utilisateur2") int id_utilisateur2);


    // Requête pour récupérer les utilisateurs qui ne sont pas amis avec un utilisateur spécifique
    @Query("SELECT u FROM Utilisateur u LEFT JOIN Ami a " +
            "ON u.id_utilisateur = a.id_utilisateur2.id_utilisateur " +
            "AND a.id_utilisateur1.id_utilisateur = :id_utilisateur1 " +
            "WHERE a.id_utilisateur1.id_utilisateur IS NULL")
    List<Utilisateur> findUtilisateursNonAmis(@Param("id_utilisateur1") int id_utilisateur1);




}

