package com.tp.lingoRingo.controller;

import com.tp.lingoRingo.entities.Forum;
import com.tp.lingoRingo.services.*;
import com.tp.lingoRingo.entities.Messages;
import com.tp.lingoRingo.entities.Utilisateur;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.List;


@Controller
public class ForumController {
    @Autowired
    ServiceForum service;
    @Autowired
    ServiceUtilisateur serviceUtilisateur;
    @Autowired
    ServiceMessage serviceMessage;
    @GetMapping("/forum")
    public String pageAccueil (){
        return "forum";
    }

    // les redirection pour les ajout forum
    @GetMapping("/acceuil")
    public String revenirAcceuil(Model model) {

        return "acceuil";
    }



    //afficher tout les forums
    @GetMapping("/forums")
    public String afficherForums(Model model){
        List<Forum> listForums = service.afficherForums();
        model.addAttribute("listForums",listForums);
        return "acceuil";
    }



    @GetMapping("/forums/langue")
    public String afficherForumsParLangue(@RequestParam String langue, Model model) {
        List<Forum> listForums = service.afficherForumsParLangue(langue);
        model.addAttribute("listForums", listForums);
        return "acceuil";
    }

    @GetMapping("/forums/interets")
    public String rechercherForumsParInteretsCulturels(@RequestParam String interets, Model model) {
        List<Forum> listForums = service.rechercherForumsParInteretsCulturels(interets);
        model.addAttribute("listForums", listForums);
        return "acceuil";
    }

    @GetMapping("/forums/titre")
    public String findForumsByTitre(@RequestParam("titre") String titre, Model model) {
        List<Forum> forums = service.rechercherForumsParTitre(titre);
        model.addAttribute("listForums", forums);
        return "acceuil";
    }

    @GetMapping("/forums/new")
    public String formulaireForum(Model model) {
        model.addAttribute("forum", new Forum());
        return "ajouteForum"; //envoi vers le formulaire
    }

    @PostMapping("/forums/add")
    public String ajouterForum(@ModelAttribute Forum forum, Model model) {
        if (service.existeForumParNom(forum.getTitre_forum())) {
            model.addAttribute("errorMessage", "Un forum avec le même nom existe déjà.");
            return "ajouteForum";
        }

        Forum savedForum = service.enregistrerForum(forum);
        if (savedForum != null && savedForum.getId_forum() > 0) {
            model.addAttribute("successMessage", "Le forum a été créé avec succès!");
            return "ajouteForum";  // Assurez-vous que la route est correcte
        } else {
            model.addAttribute("errorMessage", "Échec de la création du forum.");
            return "ajouteForum";
        }
    }











    //afficher tout les forums les admin
    @GetMapping("/forumsAdmin")
    public String afficherForumsPourAdmin(Model model){
        List<Forum> listForums = service.afficherForums();
        for (Forum forum: listForums){
           System.out.println("formu : " + forum);
        }
        model.addAttribute("listForums",listForums);
        return "acceuilAdmin";
    }

    @GetMapping("/forumsAdmin/langue")
    public String afficherForumsParLanguePourAdmin(@RequestParam String langue, Model model) {
        List<Forum> listForums = service.afficherForumsParLangue(langue);
        model.addAttribute("listForums", listForums);
        return "acceuilAdmin";
    }

    @GetMapping("/forumsAdmin/interets")
    public String rechercherForumsParInteretsCulturelsPourAdmin(@RequestParam String interets, Model model) {
        List<Forum> listForums = service.rechercherForumsParInteretsCulturels(interets);
        model.addAttribute("listForums", listForums);
        return "acceuilAdmin";
    }

    @GetMapping("/forumsAdmin/titre")
    public String findForumsByTitrePourAdmin(@RequestParam("titre") String titre, Model model) {
        List<Forum> forums = service.rechercherForumsParTitre(titre);
        model.addAttribute("listForums", forums);
        return "acceuilAdmin";
    }

    @GetMapping("/forumsAdmin/new")
    public String formulaireForumAdmin(Model model) {
        model.addAttribute("forum", new Forum());
        return "ajouteForumAdmin"; //envoi vers le formulaire
    }

    @PostMapping("/forumsAdmin/add")
    public String ajouterForumAdmin(@ModelAttribute Forum forum, Model model) {
        if (service.existeForumParNom(forum.getTitre_forum())) {
            model.addAttribute("errorMessage", "Un forum avec le même nom existe déjà.");
            return "ajouteForumAdmin";
        }

        Forum savedForum = service.enregistrerForum(forum);
        if (savedForum != null && savedForum.getId_forum() > 0) {
            List<Forum> listForums = service.afficherForums();
            model.addAttribute("listForums",listForums);
            model.addAttribute("message", "Le forum a été créé avec succès!");
            return "acceuilAdmin";  // Assurez-vous que la route est correcte
        } else {
            model.addAttribute("errorMessage", "Échec de la création du forum.");
            return "ajouteForumAdmin";
        }
    }

    @GetMapping("/forumsAdmin/delete/{id_forum}")
    public String supprimerForum(@PathVariable(name = "id_forum") Integer id_forum,
                                       Model model,
                                       RedirectAttributes redirectAttributes) {
        try {
            service.supprimerForum(id_forum);;
            redirectAttributes.addFlashAttribute("message",
                    "Le forum ID " + id_forum + " a été supprimé avec succès ");
        } catch (ForumNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", "On ne peut pas trouver un Forum avec l'id " + id_forum);
        }
        List<Forum> listForums = service.afficherForums();
        model.addAttribute("listForums",listForums);
        model.addAttribute("message", "Le forum "+ id_forum+" a été supprimer avec succès!");
        //System.out.println("Suppression");
        return "acceuilAdmin";
    }




    //afficher tout les forums les membre
    @GetMapping("/forumsMembre")
    public String afficherForumsPourMembre(Model model){
        List<Forum> listForums = service.afficherForums();
        model.addAttribute("listForums",listForums);
        return "acceuilMembre";
    }

    @GetMapping("/forumsMembre/langue")
    public String afficherForumsParLanguePourMembre(@RequestParam String langue, Model model) {
        List<Forum> listForums = service.afficherForumsParLangue(langue);
        model.addAttribute("listForums", listForums);
        return "acceuilMembre";
    }

    @GetMapping("/forumsMembre/interets")
    public String rechercherForumsParInteretsCulturelsPourMembre(@RequestParam String interets, Model model) {
        List<Forum> listForums = service.rechercherForumsParInteretsCulturels(interets);
        model.addAttribute("listForums", listForums);
        return "acceuilMembre";
    }

    @GetMapping("/forumsMembre/titre")
    public String findForumsByTitrePourMembre(@RequestParam("titre") String titre, Model model) {
        List<Forum> forums = service.rechercherForumsParTitre(titre);
        model.addAttribute("listForums", forums);
        return "acceuilMembre";
    }

    @GetMapping("/forumsMembre/new")
    public String formulaireForumMembre(Model model) {
        model.addAttribute("forum", new Forum());
        return "ajouteForumMembre"; //envoi vers le formulaire
    }

    @PostMapping("/forumsMembre/add")
    public String ajouterForumMembre(@ModelAttribute Forum forum, Model model) {
        if (service.existeForumParNom(forum.getTitre_forum())) {
            model.addAttribute("errorMessage", "Un forum avec le même nom existe déjà.");
            return "ajouteForumMembre";
        }

        Forum savedForum = service.enregistrerForum(forum);
        if (savedForum != null && savedForum.getId_forum() > 0) {
            model.addAttribute("successMessage", "Le forum a été créé avec succès!");
            return "ajouteForumMembre";  // Assurez-vous que la route est correcte
        } else {
            model.addAttribute("errorMessage", "Échec de la création du forum.");
            return "ajouteForumMembre";
        }
    }



    //afficher tout les forums les Privilege
    @GetMapping("/forumsPrivilege")
    public String afficherForumsPourPrivilege(Model model){
        List<Forum> listForums = service.afficherForums();
        model.addAttribute("listForums",listForums);
        return "accueilPrivilege";
    }

    @GetMapping("/forumsPrivilege/langue")
    public String afficherForumsParLanguePourPrivilege(@RequestParam String langue, Model model) {
        List<Forum> listForums = service.afficherForumsParLangue(langue);
        model.addAttribute("listForums", listForums);
        return "accueilPrivilege";
    }

    @GetMapping("/forumsPrivilege/interets")
    public String rechercherForumsParInteretsCulturelsPourPrivilege(@RequestParam String interets, Model model) {
        List<Forum> listForums = service.rechercherForumsParInteretsCulturels(interets);
        model.addAttribute("listForums", listForums);
        return "accueilPrivilege";
    }

    @GetMapping("/forumsPrivilege/titre")
    public String findForumsByTitrePourPrivilege(@RequestParam("titre") String titre, Model model) {
        List<Forum> forums = service.rechercherForumsParTitre(titre);
        model.addAttribute("listForums", forums);
        return "accueilPrivilege";
    }

    @GetMapping("/forumsPrivilege/new")
    public String formulaireForumPrivilege(Model model) {
        model.addAttribute("forum", new Forum());
        return "ajouteForumPrivilege"; //envoi vers le formulaire
    }

    @PostMapping("/forumsPrivilege/add")
    public String ajouterForumPrivilege(@ModelAttribute Forum forum, Model model) {
        if (service.existeForumParNom(forum.getTitre_forum())) {
            model.addAttribute("errorMessage", "Un forum avec le même nom existe déjà.");
            return "ajouteForumPrivilege";
        }

        Forum savedForum = service.enregistrerForum(forum);
        if (savedForum != null && savedForum.getId_forum() > 0) {
            model.addAttribute("successMessage", "Le forum a été créé avec succès!");
            return "ajouteForumPrivilege";  // Assurez-vous que la route est correcte
        } else {
            model.addAttribute("errorMessage", "Échec de la création du forum.");
            return "ajouteForumPrivilege";
        }
    }


    //Parti de Jacob

    @PostMapping("/forums/saveMessage/{id_forum}")
    public String enregitrerMessage(Messages message,
                                    @PathVariable Integer id_forum,
                                    RedirectAttributes redirectAttributes,
                                    @RequestParam("fileImage") MultipartFile multipartFile,
                                    HttpServletRequest request,
                                    Model model) throws ForumNotFoundException, UtilisateurNotFoundException, Exception, IOException{

        redirectAttributes.addFlashAttribute("messageForum", "L'utilisateur a été  ajouté avec succès.");


        HttpSession session = request.getSession();
        Utilisateur utilisateurConnecte;
        utilisateurConnecte = (Utilisateur) session.getAttribute("utilisateurConnecte");

        String chemin = multipartFile.getOriginalFilename();
        String typeContenu = multipartFile.getContentType();
        System.out.println("chemin : " + chemin);
        System.out.println("typeContenu : " + typeContenu);

        String fileName = StringUtils.cleanPath(chemin);
        System.out.println("fileName : " + fileName);
        message.setDocument(fileName);
        File directory = new File("src/main/resources/static/images/fichierMessage");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            Forum curentForum = service.findWithGetId(id_forum);
            message.setForumId(curentForum);
        }catch (ForumNotFoundException e) {
            redirectAttributes.addFlashAttribute("messageErreur", "On ne peut pas trouvé un forum avec l'id " + id_forum);
        }

        System.out.println("id_forum id : " + id_forum);
        System.out.println("message dassa : " + message);

        Integer id_destinateur = utilisateurConnecte.getId();
        try {
            List<Utilisateur> listUtilisateurs = serviceUtilisateur.afficherUtilisateursDuForum(id_forum);
            model.addAttribute("listUtilisateurs",listUtilisateurs);

            Utilisateur utilisateur =   serviceUtilisateur.getUtilisateurById(id_destinateur);
            System.out.println("utilisateur  : " + utilisateur);
            message.setDestinateur_id(utilisateur);
        }catch (Exception ex) {
            redirectAttributes.addFlashAttribute("messageErreur", "On ne peut pas trouvé un l'utilisateur avec l'id " + id_destinateur);
        }


        message.setTimestamp_envoie(new Timestamp(System.currentTimeMillis()));

        System.out.println("message  : " + message);
        serviceMessage.enregistrer(message);


        List<Forum> listForums = service.afficherForums();
        model.addAttribute("listForums",listForums);

        List<Messages> listMessages = serviceMessage.afficherMessagesForum(id_forum);
        model.addAttribute("listMessages",listMessages);

        Messages messageForum = new Messages();
        model.addAttribute("messageForum", messageForum);
        try {
            Forum curentForum = service.findWithGetId(id_forum);
            model.addAttribute("curentForum",curentForum);

            List<Utilisateur> listUtilisateurs = serviceUtilisateur.afficherUtilisateursDuForum(id_forum);
            model.addAttribute("listUtilisateurs",listUtilisateurs);
            model.addAttribute("id_forum",id_forum);
            /*
            List<Utilisateur> listUtilisateursForum = service.______________
            model.addAttribute("listUtilisateursForum",listUtilisateursForum);
            */
            return "forum";
        } catch (ForumNotFoundException e) {
            redirectAttributes.addFlashAttribute("messageErreur", "On ne peut pas trouvé un forum avec l'id " + id_forum);
        }
        return "redirect:/acceuilMembre";

    }



    //affiche le forum selectionner
    @GetMapping("/forums/{id_forum}")
    public String afficherForumSelectionner(@PathVariable("id_forum") Integer id_forum,
                                            RedirectAttributes redirectAttributes,
                                            Model model){

        List<Forum> listForums = service.afficherForums();
        model.addAttribute("listForums",listForums);

        List<Messages> listMessages = serviceMessage.afficherMessagesForum(id_forum);
        model.addAttribute("listMessages",listMessages);

        Messages messageForum = new Messages();
        model.addAttribute("messageForum", messageForum);


        try {
            Forum curentForum = service.findWithGetId(id_forum);
            model.addAttribute("curentForum",curentForum);

            List<Utilisateur> listUtilisateurs = serviceUtilisateur.afficherUtilisateursDuForum(id_forum);
            model.addAttribute("listUtilisateurs",listUtilisateurs);
            model.addAttribute("id_forum",id_forum);
            /*
            List<Utilisateur> listUtilisateursForum = service.______________
            model.addAttribute("listUtilisateursForum",listUtilisateursForum);
            */
            return "forum";
        } catch (ForumNotFoundException e) {
            redirectAttributes.addFlashAttribute("messageErreur", "On ne peut pas trouvé un forum avec l'id " + id_forum);
        }
        return "redirect:/acceuilMembre";
        /* code pour retourner le message au bon endroit en fonction de la session


        if ("Admin".equals(roleUtilisateur)) {
            List<Forum> listForums = serviceForum.afficherForums();
            model.addAttribute("listForums",listForums);
            return "acceuilAdmin";

        } else if ("Membre".equals(roleUtilisateur)) {
            List<Forum> listForums = serviceForum.afficherForums();
            model.addAttribute("listForums",listForums);
            return "acceuilMembre";

        } else if ("Privilège".equals(roleUtilisateur)) {
            List<Forum> listForums = serviceForum.afficherForums();
            model.addAttribute("listForums",listForums);
            return "acceuilPrivilege";

        } else {
            return "redirect:/forum";
        }
        */

    }

    @GetMapping("/forums/{id_forum}/likes")
    public String afficherForumSelectionnerLikes(@PathVariable("id_forum") Integer id_forum,
                                                 @RequestParam int likes,
                                                 @RequestParam int dislikes,
                                                 @RequestParam Integer id,
                                                 RedirectAttributes redirectAttributes,
                                                 Model model){

        if(dislikes > 0){
            dislikes--;
        }

        likes++;
        serviceMessage.updateDislikes(id, dislikes);
        serviceMessage.updateLikes(id, likes);


        List<Forum> listForums = service.afficherForums();
        model.addAttribute("listForums",listForums);

        List<Messages> listMessages = serviceMessage.afficherMessagesForum(id_forum);
        model.addAttribute("listMessages",listMessages);

        List<Utilisateur> listUtilisateurs = serviceUtilisateur.afficherUtilisateursDuForum(id_forum);
        model.addAttribute("listUtilisateurs",listUtilisateurs);

        Messages messageForum = new Messages();
        model.addAttribute("messageForum", messageForum);

        try {
            Forum curentForum = service.findWithGetId(id_forum);
            model.addAttribute("curentForum",curentForum);
            model.addAttribute("id_forum",id_forum);
            /*
            List<Utilisateur> listUtilisateursForum = service.______________
            model.addAttribute("listUtilisateursForum",listUtilisateursForum);
            */
            return "forum";
        } catch (ForumNotFoundException e) {
            redirectAttributes.addFlashAttribute("messageErreur", "On ne peut pas trouvé un forum avec l'id " + id_forum);
        }
        return "redirect:/acceuilMembre";


    }

    @GetMapping("/forums/{id_forum}/dislikes")
    public String afficherForumSelectionnerDislikes(@PathVariable("id_forum") Integer id_forum,
                                                    @RequestParam int likes,
                                                    @RequestParam int dislikes,
                                                    @RequestParam Integer id,
                                                    RedirectAttributes redirectAttributes,
                                                    Model model){

        if(likes > 0){
            likes--;
        }

        dislikes++;
        serviceMessage.updateDislikes(id, dislikes);
        serviceMessage.updateLikes(id, likes);

        List<Forum> listForums = service.afficherForums();
        model.addAttribute("listForums",listForums);

        List<Utilisateur> listUtilisateurs = serviceUtilisateur.afficherUtilisateursDuForum(id_forum);
        model.addAttribute("listUtilisateurs",listUtilisateurs);

        List<Messages> listMessages = serviceMessage.afficherMessagesForum(id_forum);
        model.addAttribute("listMessages",listMessages);

        Messages messageForum = new Messages();
        model.addAttribute("messageForum", messageForum);

        try {
            Forum curentForum = service.findWithGetId(id_forum);
            model.addAttribute("curentForum",curentForum);
            model.addAttribute("id_forum",id_forum);
            /*
            List<Utilisateur> listUtilisateursForum = service.______________
            model.addAttribute("listUtilisateursForum",listUtilisateursForum);
            */
            return "forum";
        } catch (ForumNotFoundException e) {
            redirectAttributes.addFlashAttribute("messageErreur", "On ne peut pas trouvé un forum avec l'id " + id_forum);
        }
        return "redirect:/acceuilMembre";


    }









}
