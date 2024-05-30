package com.tp.lingoRingo.rest;

import com.tp.lingoRingo.services.ServiceForum;
import com.tp.lingoRingo.services.ServiceUtilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtilisateurRestController {
    @Autowired
    ServiceUtilisateur service;

    @Autowired
    ServiceForum serviceForum;

    @PostMapping("/utilisateurs/check_email")
    public String verifierDoublonEmail(@Param("email") String email, @Param("id") Integer id) {
        //Pour la simplicité on retourne pas un Objet JSON mais plutôt une chaine de charactère
        // Selon la valeur que retourne la méthode isEmailUnique
        return service.isEmailUnique(email,id) ? "OK" : "Doublon";
    }

    @PostMapping("/forums/check_titre_forum")
    public String verifierDoublonForum(@Param("titre_forum") String titre_forum, @Param("id") Integer id) {
        //Pour la simplicité on retourne pas un Objet JSON mais plutôt une chaine de charactère
        // Selon la valeur que retourne la méthode isEmailUnique
        return serviceForum.isForumUnique(titre_forum,id) ? "OK" : "Doublon";
    }



}
