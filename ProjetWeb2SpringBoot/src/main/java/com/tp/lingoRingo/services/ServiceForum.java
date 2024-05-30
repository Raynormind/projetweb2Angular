package com.tp.lingoRingo.services;

import com.tp.lingoRingo.entities.Forum;
import com.tp.lingoRingo.entities.Utilisateur;
import com.tp.lingoRingo.repos.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ServiceForum {
    @Autowired
    ForumRepository forumRepos;


    public List<Forum> afficherForums() {
        return forumRepos.findAll(); // Vérifiez que cela retourne les données attendues
    }


    public Forum findWithGetId(Integer id_forum)throws ForumNotFoundException{
        try {
            Forum forum = forumRepos.findById(id_forum).get();
            return forum;
        }catch (NoSuchElementException ex){
            throw new ForumNotFoundException("On ne peut pas trouvé un forum avec l'id " + id_forum);
        }


    }

    public Forum enregistrer(Forum forum){
        return (Forum) forumRepos.save(forum);
    }

    public List<Forum> afficherForumsParLangue(String langue){
        return ( List<Forum> ) forumRepos.findForumsByLangue(langue);
    }


    public List<Forum> rechercherForumsParInteretsCulturels(String interets) {
        return forumRepos.findForumsByInteretsCulturels(interets);
    }


    public void mettreAJourEtatForum(int idForum, boolean etatForum) {
        forumRepos.updateEtatForum(idForum, etatForum);
    }

    public List<Forum> rechercherForumsParTitre(String titre) {
        return forumRepos.findForumsByTitre(titre);
    }

    public Forum enregistrerForum(Forum forum) {
        return forumRepos.save(forum);
    }

    public boolean existeForumParNom(String titreForum) {
        List<Forum> forums = forumRepos.findForumsByTitre(titreForum);
        return !forums.isEmpty();
    }

    /*public void supprimerForum(int id_forum) {
        forumRepos.deleteById(id_forum);
    }*/

    public void supprimerForum(Integer id_forum) throws ForumNotFoundException {
        Integer countById = forumRepos.countById_forum(id_forum);
        //pas de forum dans la bd
        if (countById == null || countById == 0) {
            throw new ForumNotFoundException("On ne peut pas trouver de Forum avec l'id" + id_forum);
        }

        forumRepos.deleteById_forum(id_forum);
    }


    public boolean isForumUnique(String titre_forum, Integer id_forum) {
        Forum existingForum = forumRepos.getForumByTitre_forum(titre_forum);
        if (existingForum == null) {
            return true;  // Le forum est unique
        }
        // Vérifie si les ID sont égaux
        return id_forum != null && existingForum.getId_forum() == id_forum;
    }





}
