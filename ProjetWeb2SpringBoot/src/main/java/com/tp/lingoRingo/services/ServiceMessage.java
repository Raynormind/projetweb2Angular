package com.tp.lingoRingo.services;

import com.tp.lingoRingo.entities.Messages;
import com.tp.lingoRingo.repos.ForumRepository;
import com.tp.lingoRingo.repos.MessageRepository;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ServiceMessage {
    @Autowired
    MessageRepository messageRepos;
    public List<Messages> afficherMessagesForum(int id_forum){
        return ( List<Messages> ) messageRepos.findAllByForumId(id_forum);
    }

    public List<Messages> afficherMessagesForumByRating(int id_forum){
        return ( List<Messages> ) messageRepos.findAllByForumIdOrderByRating(id_forum);
    }
    public List<Messages> afficherMessagesPrivés(int destinateur_id, int destinataireId){
        return ( List<Messages> ) messageRepos.findAllByDestinateurIdAndDestinataireId(destinateur_id, destinataireId);
    }

    public Messages enregistrer(Messages messages){
        return messageRepos.save(messages);
    }

    public List<Messages> chercherDocument(String document) throws MessagesNotFoundExeption {
        try {
            return messageRepos.findByDocument(document);
        }catch (NoSuchElementException exception){
            throw new MessagesNotFoundExeption("On ne peut pas trouvé un message avec le document "+ document);
        }
    }
    @Transactional
    public boolean updateDislikes(Integer id, int dislikes){

        messageRepos.updateDislikes(id,dislikes);
        return true;
    }

    @Transactional
    public boolean updateLikes(Integer id, int likes){

        messageRepos.updateLikes(id,likes);
        return true;
    }

    public void supprimerMessage(Integer id_message) throws MessagesNotFoundExeption {
        Long countById = messageRepos.countById_message(id_message);
        //pas de forum dans la bd
        if (countById == null || countById == 0) {
            throw new MessagesNotFoundExeption("On ne peut pas trouver le message avec l'id" + id_message);
        }

        messageRepos.deleteById_message(id_message);
    }

}

