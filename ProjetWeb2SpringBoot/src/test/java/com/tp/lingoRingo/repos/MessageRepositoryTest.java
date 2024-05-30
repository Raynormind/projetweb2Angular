package com.tp.lingoRingo.repos;

import com.tp.lingoRingo.entities.Ami;
import com.tp.lingoRingo.entities.Messages;
import com.tp.lingoRingo.repos.MessageRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class MessageRepositoryTest {

    @Autowired
    MessageRepository messageRepos;


    @Test
    public void findById(){
        Messages utilisateur =  messageRepos.findById(1).get();
        System.out.println(utilisateur);
        Assertions.assertThat(utilisateur).isNotNull();
    }

    @Test
    public void findAllByForumId() {
        int forumId = 4;
        Iterable<Messages> listeMessages = messageRepos.findAllByForumId(forumId);

        listeMessages.forEach(messages -> System.out.println(messages));
    }

    @Test
    public void findAllByDestinateurIdAndDestinataireId() {
        int id_destinateur = 1;
        int id_destinataire = 3;
        Iterable<Messages> listeMessages = messageRepos.findAllByDestinateurIdAndDestinataireId(id_destinateur,id_destinataire);

        listeMessages.forEach(messages -> System.out.println(messages));

    }

    @Test
    public void findAllByForumIdOrderByRating() {
        int forumId = 4;
        Iterable<Messages> listeMessages = messageRepos.findAllByForumIdOrderByRating(forumId);

        listeMessages.forEach(messages -> System.out.println(messages));
    }



}