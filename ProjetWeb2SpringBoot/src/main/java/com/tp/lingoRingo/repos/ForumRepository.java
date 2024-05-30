package com.tp.lingoRingo.repos;

import com.tp.lingoRingo.entities.Forum;
import com.tp.lingoRingo.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ForumRepository extends JpaRepository<Forum,Integer> {

    @Query("SELECT f FROM Forum f WHERE f.langue =:langue")
    public List<Forum> findForumsByLangue(@Param("langue") String langue);

    @Query("SELECT f FROM Forum f WHERE f.interets_culturels LIKE %?1%")
    public List<Forum> findForumsByInteretsCulturels(String interets);

    @Query("UPDATE Forum f SET f.etat_forum=?2 WHERE f.id_forum=?1")
    @Modifying
    @Transactional
    public void updateEtatForum(int idForum, boolean etatForum);

    @Query("SELECT f FROM Forum f JOIN f.utilisateurs u WHERE u.id_utilisateur =:idUtilisateur")
    public List<Forum> findForumsByUtilisateur(@Param("idUtilisateur") int idUtilisateur);

    @Query("SELECT f FROM Forum f WHERE f.titre_forum LIKE %?1%")
    public List<Forum> findForumsByTitre(String titre);

    @Query("SELECT count(f) FROM Forum f WHERE f.id_forum = :id")
    Integer countById_forum(@Param("id") Integer id_forum);


    @Transactional
    @Modifying
    @Query("DELETE FROM Forum f WHERE f.id_forum = :id")
    void deleteById_forum(@Param("id") Integer id_forum);


    @Query("SELECT f FROM Forum f WHERE f.titre_forum=:titre_forum")
    public Forum getForumByTitre_forum(@Param("titre_forum") String titre_forum);





}
