package com.tp.lingoRingo.controller;

import com.tp.lingoRingo.entities.Forum;
import com.tp.lingoRingo.entities.Role;
import com.tp.lingoRingo.entities.Utilisateur;
import com.tp.lingoRingo.services.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

@Controller
public class UtilisateurController {

    @Autowired
    ServiceUtilisateur service;

    @Autowired
    ServiceForum serviceForum;

    @Autowired
    ServiceRole serviceRole;

    @Autowired
    AdminService serviceAdmin;

    @Autowired
    EmailService emailService;

    private int utilisateurId;
    private List<Utilisateur> listUtilisateurs;
    private List<Utilisateur> listUtilisateursFiltrés = null;


    @GetMapping("/connexion")
    public String connexionPage() {
        return "connexion";
    }


    @GetMapping("/acceuilAdmin")
    public String acceuilAdmin() {
        return "acceuilAdmin";
    }

    @GetMapping("/acceuilMembre")
    public String acceuilMembre() {
        return "acceuilMembre";
    }

    @GetMapping("/acceuilPrivilege")
    public String acceuilPrivilege() {
        return "acceuilPrivilege";
    }


    @GetMapping("/users/new")
    public String ajouterUtilisateur(Model model, RedirectAttributes redirectAttributes) {
        List<Role> listRoles = service.afficherRoles();
        model.addAttribute("listRoles", listRoles);
        Utilisateur utilisateur = new Utilisateur();
        model.addAttribute("utilisateur", utilisateur);
        if (model.containsAttribute("errorMessage")) {
            model.addAttribute("errorMessage", model.asMap().get("errorMessage"));
        }
        if (model.containsAttribute("message")) {
            model.addAttribute("message", model.asMap().get("message"));
        }
        return "pageInscriptionUtilisateur";
    }

    @PostMapping("/users/add")
    public String ajouterUtilisateur(Utilisateur utilisateur, RedirectAttributes redirectAttributes) {
        if (!service.isEmailUnique(utilisateur.getEmail(), utilisateur.getId())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur: L'email " + utilisateur.getEmail() + " est déjà utilisé par un autre compte.");
            return "redirect:/users/new";
        }
        try {
            serviceAdmin.enregistrer(utilisateur);

            // contenu du mail
            String subject = "Confirmation de création de compte";
            String text = "Bonjour " + utilisateur.getNom() + ",\n\n" +
                    "Votre compte sur Lingo Ringo a été créé et activé avec succès !\n\n" +
                    "Voici vos informations de connexion :\n" +
                    "Nom d'utilisateur : " + utilisateur.getNomUtilisateur() + "\n" +
                    "Mot de passe : " + utilisateur.getMot_de_passe() + "\n\n" +
                    "Vous pouvez maintenant vous connecter en utilisant votre email et votre mot de passe.\n\n" +
                    "Cordialement,\n" +
                    "L'équipe Lingo Ringo Youssef, Dave, Jacob et Rafael";

            // Envoie de email de confirmationn
            emailService.envoieConfirmationEmail(utilisateur.getEmail(), subject, text);

            redirectAttributes.addFlashAttribute("message", "Utilisateur créé avec succès, veuillez vous connecter !");
            return "redirect:/connexion";  // Redirection après succès
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de l'enregistrement de l'utilisateur : " + e.getMessage());
            return "redirect:/users/new";  // Redirection pour réessayer
        }
    }





    @PostMapping("/connexion/conecter")
    public String connexion(
            @RequestParam("email") String email,
            @RequestParam("motDePasse") String motDePasse,
            @RequestParam(value = "seSouvenir", defaultValue = "false") boolean seSouvenir,
            HttpServletResponse response,
            HttpServletRequest request, Model model) {

        Utilisateur utilisateur = service.login(email, motDePasse);

        if (utilisateur != null && utilisateur.isActif()) {
            HttpSession session = request.getSession();
            session.setAttribute("utilisateurConnecte", utilisateur);
            session.setAttribute("nom", utilisateur.getNom());
            session.setAttribute("nomUtilisateur", utilisateur.getNomUtilisateur());
            session.setAttribute("photo_profile", utilisateur.getPhoto_profile());
            session.setAttribute("id", utilisateur.getId());
            session.setAttribute("userId", utilisateur.getId());

            if (seSouvenir) {

                Cookie emailCookie = new Cookie("email",email);
                Cookie mdpCookie = new Cookie("motDePasse",motDePasse);
                emailCookie.setMaxAge(Duration.ofDays(60*60).getNano());
                mdpCookie.setMaxAge(Duration.ofDays(60*60).getNano());
                response.addCookie(emailCookie);
                response.addCookie(mdpCookie);
            }


            String roleUtilisateur = service.obtenirRoleUtilisateur(email);

            if ("Admin".equals(roleUtilisateur)) {
                List<Forum> listForums = serviceForum.afficherForums();
                model.addAttribute("listForums",listForums);
                return "acceuilAdmin";

            } else if ("Membre".equals(roleUtilisateur)) {
                List<Forum> listForums = serviceForum.afficherForums();
                model.addAttribute("listForums",listForums);
                return "acceuilMembre";

            } else if ("Privilege".equals(roleUtilisateur)) {
                List<Forum> listForums = serviceForum.afficherForums();
                model.addAttribute("listForums",listForums);
                return "accueilPrivilege";

            } else {
                return "connexion";
            }
        } else if (utilisateur != null && !utilisateur.isActif()) {
            model.addAttribute("errorMessage", "Votre compte a été suspendu. Veuillez contacter l'administrateur pour plus d'informations.");
            return "connexion";
        } else {
            model.addAttribute("errorMessage", "Identifiants ou Mot de passe  incorrects.");
            return "connexion";
        }
    }


    @GetMapping("/deconnexion")
    public String deconnexion(HttpSession session, HttpServletResponse response, Model model) {
        model.addAttribute("message", "Vous avez bien ete deconnecter");
        session.invalidate();


        return "connexion";
    }

    @GetMapping("/editProfil/{id}")
    public String formulaireUpdateMembre(@PathVariable Integer id, Model model) {
        Utilisateur utilisateur = null;
        try {
            utilisateur = service.getUtilisateurById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("utilisateur", utilisateur);
        return "profilMembre";
    }

    @GetMapping("/utilisateurs/edit/{id}")
    public String mettreAjourUtilisateur(@PathVariable("id") int id, RedirectAttributes redirectAttributes, Model model, UtilisateurNotFoundException ex){

        System.out.println("Id : " + id);

            utilisateurId = id;
            Utilisateur utilisateur = serviceAdmin.getUserById(utilisateurId);
            model.addAttribute("utilisateur", utilisateur);
            model.addAttribute("pageTitle","Profil Utilisateurs de : " + utilisateur.getNomUtilisateur() );
            return "pageModifUtilisateur";

    }

    @PostMapping("/utilisateurs/modifier")
    public String modifierUtilisateur(@RequestParam("email") String email, @RequestParam("nomUtilisateur") String nomUtilisateur,
                                      @RequestParam("nom") String nom, @RequestParam("motdepasse") String motdepasse, @RequestParam("langue") String langue,
                                      @RequestParam("interetsCulturels") String interetsCulturels,
                                      @RequestParam("photoProfile") String photoProfile, Model model){


        serviceAdmin.modifier(utilisateurId, email, nomUtilisateur, nom, motdepasse, langue, interetsCulturels, photoProfile);
        List<Forum> listForums = serviceForum.afficherForums();
        model.addAttribute("listForums",listForums);
        return "acceuilMembre";
    }


    @GetMapping("/utilisateursPrivilege/edit/{id}")
    public String mettreAjourUtilisateurPrivilege(@PathVariable("id") int id, RedirectAttributes redirectAttributes, Model model, UtilisateurNotFoundException ex){

        System.out.println("Id : " + id);

        utilisateurId = id;
        Utilisateur utilisateur = serviceAdmin.getUserById(utilisateurId);
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("pageTitle","Profil Utilisateurs de : " + utilisateur.getNomUtilisateur() );
        return "pageModifUtilisateurPrivilege";

    }

    @PostMapping("/utilisateursPrivilege/modifier")
    public String modifierUtilisateurPrivilege(@RequestParam("email") String email, @RequestParam("nomUtilisateur") String nomUtilisateur,
                                      @RequestParam("nom") String nom, @RequestParam("motdepasse") String motdepasse, @RequestParam("langue") String langue,
                                      @RequestParam("interetsCulturels") String interetsCulturels,
                                      @RequestParam("photoProfile") String photoProfile, Model model){


        serviceAdmin.modifier(utilisateurId, email, nomUtilisateur, nom, motdepasse, langue, interetsCulturels, photoProfile);
        List<Forum> listForums = serviceForum.afficherForums();
        model.addAttribute("listForums",listForums);
        return "accueilPrivilege";
    }


}
