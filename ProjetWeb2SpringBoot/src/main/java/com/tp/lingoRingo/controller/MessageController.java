package com.tp.lingoRingo.controller;

import com.tp.lingoRingo.entities.Forum;
import com.tp.lingoRingo.entities.Messages;
import com.tp.lingoRingo.entities.Utilisateur;
import com.tp.lingoRingo.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Controller
public class MessageController {
    @Autowired
    ServiceForum service;
    @Autowired
    ServiceUtilisateur serviceUtilisateur;
    @Autowired
    ServiceMessage serviceMessage;
    @GetMapping("/messageriePrivee/{destinateur_id}/{destinataire_id}")
    public String pageMessageriePrivee(@PathVariable("destinateur_id") Integer idDestinateur,
                                       @PathVariable("destinataire_id") Integer idDestinataire,
                                       Model model){
        List<Forum> listForums = service.afficherForums();
        model.addAttribute("listForums",listForums);

        List<Utilisateur> listUtilisateurs = serviceUtilisateur.afficherUtilisateurs();
        model.addAttribute("listUtilisateurs",listUtilisateurs);

        List<Messages> listMessages = serviceMessage.afficherMessagesPrivés(idDestinateur, idDestinataire);
        model.addAttribute("listMessages",listMessages);

        Messages messageDestinataire = new Messages();
        model.addAttribute("messageDestinataire", messageDestinataire);
        model.addAttribute("destinataire_id",idDestinataire);
        model.addAttribute("destinateur_id",idDestinateur);

        return "messageriePrivee";
    }

    @PostMapping("/messageriePrivee/send/{destinateur_id}/{destinataire_id}")
    public String ajouterMessage(@PathVariable("destinateur_id") Integer idDestinateur,
                                 @PathVariable("destinataire_id") Integer idDestinataire,
                                 Messages message,
                                 HttpServletRequest request,
                                 RedirectAttributes redirectAttributes,
                                 @RequestParam("fileImage") MultipartFile multipartFile,
                                 Model model) throws IOException, UtilisateurNotFoundException, Exception {

        redirectAttributes.addFlashAttribute("messageDestinataire", "L'utilisateur a été  ajouté avec succès.");

        String chemin = multipartFile.getOriginalFilename();
        String typeContenu = multipartFile.getContentType();
        System.out.println("chemin : " + chemin);
        System.out.println("typeContenu : " + typeContenu);
        String fileName = StringUtils.cleanPath(chemin);
        System.out.println("fileName : " + fileName);
        // Association du nom de fichier à l'utilisateur enregistré

        message.setDocument(fileName);

        HttpSession session = request.getSession();
        Utilisateur utilisateurConnecte;
        utilisateurConnecte = (Utilisateur) session.getAttribute("utilisateurConnecte");



        File directory = new File("src/main/resources/static/images/fichierMessage");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        System.out.println("message dassa : " + message);
        Integer id_destinateur = utilisateurConnecte.getId();
        try {

            Utilisateur utilisateur = serviceUtilisateur.getUtilisateurById(id_destinateur);
            System.out.println("utilisateur  : " + utilisateur);
            message.setDestinateur_id(utilisateur);
        }catch (Exception ex) {
            redirectAttributes.addFlashAttribute("messageErreur", "On ne peut pas trouvé un l'utilisateur avec l'id " + id_destinateur);
        }

        try {
            Utilisateur destinataire =   serviceUtilisateur.getUtilisateurById(idDestinataire);
            System.out.println("destinataire  : " + destinataire);
            message.setDestinataire_id(destinataire);
        }catch (Exception ex) {
            redirectAttributes.addFlashAttribute("messageErreur", "On ne peut pas trouvé un l'utilisateur avec l'id " + idDestinataire);
        }

        message.setTimestamp_envoie(new Timestamp(System.currentTimeMillis()));
        serviceMessage.enregistrer(message);

        List<Forum> listForums = service.afficherForums();
        model.addAttribute("listForums",listForums);

        List<Utilisateur> listUtilisateurs = serviceUtilisateur.afficherUtilisateurs();
        model.addAttribute("listUtilisateurs",listUtilisateurs);

        List<Messages> listMessages = serviceMessage.afficherMessagesPrivés(idDestinateur, idDestinataire);
        model.addAttribute("listMessages",listMessages);

        Messages messageDestinataire = new Messages();
        model.addAttribute("messageDestinataire", messageDestinataire);
        model.addAttribute("destinataire_id",idDestinataire);
        model.addAttribute("destinateur_id",idDestinateur);


        return "messageriePrivee";
    }


    @GetMapping("/images/fichierMessage/{fileId}")
    public void telechargerFichier(@PathVariable String fileId, HttpServletResponse response) throws IOException, MessagesNotFoundExeption {

        File directory = new File("src/main/resources/static/images/fichierMessage");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        //On crée un fichier correspondant à l'ID passé en paramètre
        File file = new File(directory.getAbsolutePath() + File.separator + fileId);

        List<Messages> ListeMessages = serviceMessage.chercherDocument(fileId);
        for(Messages utilisateur: ListeMessages){
            System.out.println("utilisateur : " + utilisateur);
            if(utilisateur.getDocument()!=null){
                // Si le fichier existe sur le serveur
                if (file.exists()) {
                    // On spécifie le type de contenu de la réponse HTTP
                    response.setContentType("image/jpeg");

                    // On spécifie le nom du fichier à télécharger dans la réponse HTTP
                    response.setHeader("Content-Disposition", "inline; filename=\"" + fileId + "\"");
                    // On lit le contenu du fichier à télécharger
                    FileInputStream fileInputStream = new FileInputStream(file);
                    // On écrit le contenu du fichier dans la réponse HTTP
                    OutputStream outputStream = response.getOutputStream();
                    //on déclare un tableau de bytes (byte[]) appelé buffer de taille 1024,
                    //qui servira de tampon pour la lecture du fichier.
                    byte[] buffer = new byte[1024];
                    //On initialise une variable bytesRead à -1,
                    //qui sera utilisée pour stocker le nombre de bytes
                    //lus à chaque lecture dans le tampon.
                    int bytesRead = -1;
                    //Dans la boucle while, on lit les bytes du fichier
                    //dans le tampon à l'aide de la méthode read()
                    //de l'objet fileInputStream.
                    while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                        //on écrit ces bytes dans le flux de sortie (outputStream)
                        //à l'aide de la méthode write()
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    fileInputStream.close();
                    outputStream.flush();
                    outputStream.close();
                }
            }

        }

    }
    @GetMapping("/forum/{id_forum}/message/delete/{id_message}")
    public String supprimerMessageForum(@PathVariable("id_forum") Integer id_forum,
                                        @PathVariable(name = "id_message") Integer id_message,
                                        Model model,
                                        RedirectAttributes redirectAttributes) {
        try {
            serviceMessage.supprimerMessage(id_message);
            redirectAttributes.addFlashAttribute("messageConfirmation",
                    "Le message ID " + id_message + " a été supprimé avec succès ");
        } catch (MessagesNotFoundExeption ex) {
            redirectAttributes.addFlashAttribute("messageConfirmation", "On ne peut pas trouver un utilisateur avec l'id " + id_message);
        }



        List<Forum> listForums = service.afficherForums();
        model.addAttribute("listForums",listForums);

        List<Utilisateur> listUtilisateurs = serviceUtilisateur.afficherUtilisateursDuForum(id_forum);
        model.addAttribute("listUtilisateurs",listUtilisateurs);

        List<Messages> listMessages = serviceMessage.afficherMessagesForum(id_forum);
        model.addAttribute("listMessages",listMessages);

        Messages messageForum = new Messages();
        model.addAttribute("messageForum", messageForum);
        model.addAttribute("messageConfirmation", "Le message "+ id_message+" a été supprimer avec succès!");
        try {
            Forum curentForum = service.findWithGetId(id_forum);
            model.addAttribute("curentForum",curentForum);
            model.addAttribute("id_forum",id_forum);
            System.out.println("Suppression");
            return "redirect:/forum";
        } catch (ForumNotFoundException e) {
            redirectAttributes.addFlashAttribute("messageErreur", "On ne peut pas trouvé un forum avec l'id " + id_forum);
        }
        return "redirect:/acceuilMembre";

    }

    @GetMapping("/messageriePrivee/{destinateur_id}/{destinataire_id}/message/delete/{id_message}")
    public String supprimerMessagePrivee(@PathVariable("destinateur_id") Integer idDestinateur,
                                         @PathVariable("destinataire_id") Integer idDestinataire,
                                         @PathVariable(name = "id_message") Integer id_message,
                                         Model model,
                                         RedirectAttributes redirectAttributes) {
        try {
            serviceMessage.supprimerMessage(id_message);
            redirectAttributes.addFlashAttribute("messageConfirmation",
                    "Le message ID " + id_message + " a été supprimé avec succès ");
        } catch (MessagesNotFoundExeption ex) {
            redirectAttributes.addFlashAttribute("messageConfirmation", "On ne peut pas trouver un utilisateur avec l'id " + id_message);
        }



        List<Forum> listForums = service.afficherForums();
        model.addAttribute("listForums",listForums);

        List<Utilisateur> listUtilisateurs = serviceUtilisateur.afficherUtilisateurs();
        model.addAttribute("listUtilisateurs",listUtilisateurs);

        List<Messages> listMessages = serviceMessage.afficherMessagesPrivés(idDestinateur, idDestinataire);
        model.addAttribute("listMessages",listMessages);

        Messages messageDestinataire = new Messages();
        model.addAttribute("messageDestinataire", messageDestinataire);
        model.addAttribute("destinataire_id",idDestinataire);
        model.addAttribute("destinateur_id",idDestinateur);
        model.addAttribute("messageConfirmation", "Le message "+ id_message+" a été supprimer avec succès!");

        return "messageriePrivee";

    }
}
