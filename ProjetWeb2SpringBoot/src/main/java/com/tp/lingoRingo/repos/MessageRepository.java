package com.tp.lingoRingo.repos;

import com.tp.lingoRingo.entities.Forum;
import com.tp.lingoRingo.entities.Messages;
import com.tp.lingoRingo.entities.Utilisateur;
import com.tp.lingoRingo.services.MessagesNotFoundExeption;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public interface MessageRepository extends JpaRepository<Messages,Integer> {
    @Query("SELECT m FROM Messages m WHERE (m.destinateur_id.id_utilisateur=:destinateur_id AND m.destinataire_id.id_utilisateur=:destinataire_id) OR (m.destinateur_id.id_utilisateur=:destinataire_id AND m.destinataire_id.id_utilisateur=:destinateur_id) ORDER BY m.timestamp_envoie ASC")
    public List<Messages> findAllByDestinateurIdAndDestinataireId(@Param("destinateur_id") int destinateur_id, @Param("destinataire_id")int destinataire_id);

    @Query("SELECT m FROM Messages m WHERE m.forumId.id_forum=:forumId ORDER BY m.timestamp_envoie ASC")
    public List<Messages> findAllByForumId(@Param("forumId") int forumId);

    @Query("SELECT m FROM Messages m WHERE m.forumId.id_forum=:forumId ORDER BY m.timestamp_envoie ASC, (m.likes - m.dislikes) DESC")
    public List<Messages> findAllByForumIdOrderByRating(@Param("forumId") int forumId);

    @Query("SELECT m FROM Messages m WHERE m.document = :fileName")
    public List<Messages> findByDocument(@Param("fileName") String fileName);
    @Modifying
    @Transactional
    @Query("UPDATE Messages m SET m.likes=?2 WHERE m.id_message=?1")
    public void updateLikes(Integer id, int likes);
    @Modifying
    @Transactional
    @Query("UPDATE Messages m SET m.dislikes=?2 WHERE m.id_message=?1")
    public void updateDislikes(Integer id_message, int dislikes);

    @Query("SELECT count(m) FROM Messages m WHERE m.id_message = :id")
    public Long countById_message(Integer id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Messages m WHERE m.id_message = :id")
    void deleteById_message(@Param("id") Integer id_message);
}
