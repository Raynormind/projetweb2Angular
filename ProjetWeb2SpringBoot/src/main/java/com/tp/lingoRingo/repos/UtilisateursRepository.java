package com.tp.lingoRingo.repos;

import com.tp.lingoRingo.entities.Forum;
import org.springframework.stereotype.Repository;
import com.tp.lingoRingo.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UtilisateursRepository extends JpaRepository<Utilisateur,Integer> {

    //Se connecter en tant qu'utilisateur
    @Query("SELECT u FROM Utilisateur u WHERE u.email=:email and u.mot_de_passe=:mot_de_passe")
    public Utilisateur connexionUtilisateur(@Param("email") String email, @Param("mot_de_passe") String password);

    //Rechercher un utilisateur par son nom d'utilisateur(barre de rechercher) (option1)
    @Query("SELECT u FROM Utilisateur u WHERE u.nom_utilisateur=:nom_utilisateur")
    public Utilisateur findUtilisateurByNomUtilisateur(@Param("nom_utilisateur")String nom_utilisateur);

    //Rechercher un utilisateur par une chaine de caractère inclue dans son nom (barre de rechercher) (option2)
    @Query("SELECT u.nom_utilisateur FROM Utilisateur u WHERE u.nom_utilisateur LIKE %:nom_utilisateur%")
    public List<String> findAllUtilisateursByNomUtilisateurContient(@Param("nom_utilisateur") String nomUtilisateur);



    //Afficher toutes les informations des utilisateurs de chaque forums
    @Query("SELECT u FROM Utilisateur u JOIN u.forum_utilisateurs f WHERE f.id_forum=:id_forum")
    public List<Utilisateur> findAllUtilisateurByForumAdmin(int id_forum);

    //Afficher les utilisateurs sur le forum (information perçu par les utilisateurs)
    @Query("SELECT u.nom_utilisateur FROM Utilisateur u JOIN u.forum_utilisateurs f WHERE f.id_forum=:id_forum")
    public List<String> findAllUtilisateurByForum(int id_forum);

    // Query pour récupérer le rôle de l'utilisateur par son email
    @Query("SELECT u.roles FROM Utilisateur u WHERE u.email = :email")
    public String findRoleByEmail(@Param("email") String email);

    @Query("SELECT u FROM Utilisateur u WHERE u.email=:email")
    public Utilisateur getUtilisateurByEmail(@Param("email") String email);

    @Query("SELECT u FROM Utilisateur u WHERE u.photo_profile=:fileName")
    public List<Utilisateur> findByPhoto_profile(@Param("fileName")String fileName);
    public Utilisateur findByNom(String nom);

    @Query("SELECT u FROM Utilisateur u WHERE u.nom_utilisateur LIKE %?1%")
    public List<Utilisateur> findUtilisateurByNomUser(String nomUtilisateur);

    @Query("SELECT u FROM Utilisateur u WHERE u.nom_utilisateur = :nom_utilisateur")
    Utilisateur findByNomUtilisateur(@Param("nom_utilisateur") String nomUtilisateur);

    @Modifying
    @Transactional
    @Query("UPDATE Utilisateur u SET u.email = :email, "
            + "u.nom_utilisateur = :nomUtilisateur, "
            + "u.nom = :nom, "
            + "u.mot_de_passe = :mdp, "
            + "u.langue = :langue, "
            + "u.interets_culturels = :interetsCulturels, "
            + "u.photo_profile = :photoProfile WHERE u.id_utilisateur = :id")
    public void updateById(@Param("id") Integer id,
                           @Param("email") String email,
                           @Param("nomUtilisateur") String nomUtilisateur,
                           @Param("nom") String nom,
                           @Param("mdp") String mdp,
                           @Param("langue") String langue,
                           @Param("interetsCulturels") String interetsCulturels,
                           @Param("photoProfile") String photoProfile);


    //Fais par rafa
    @Query("SELECT u FROM Utilisateur u WHERE u.langue=:langue")
    public List<Utilisateur> findAllByLangue(String langue);

    @Query("SELECT u FROM Utilisateur u WHERE u.interets_culturels=:interetsCulturels")  // verifier avec la bdd de rafael lui a mit interetCulturels
    public List<Utilisateur> findAllByInteretsCulturels(String interetsCulturels);

    //boolean existsByEmail(String email, Integer id);

    //boolean existsByNomUtilisateur(String nomUtilisateur, Integer id);

    public Utilisateur findById(int id);

    @Query("SELECT u FROM Utilisateur u JOIN u.forum_utilisateurs fu WHERE fu.id_forum = ?1")
    public List<Utilisateur>
    findAllById_Forum(Integer Id_Forum);


}
