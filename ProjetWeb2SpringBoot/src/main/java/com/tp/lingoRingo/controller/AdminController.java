/*
public class AdminController {
}*/
package com.tp.lingoRingo.controller;

import com.tp.lingoRingo.entities.Forum;
import com.tp.lingoRingo.entities.Role;
import com.tp.lingoRingo.entities.Utilisateur;
import com.tp.lingoRingo.services.AdminService;
import com.tp.lingoRingo.services.ServiceUtilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    AdminService serviceAdmin;

    @Autowired
    ServiceUtilisateur serviceUtilisateur;

    private int utilisateurId;
    private List<Utilisateur> listUtilisateurs;
    private List<Utilisateur> listUtilisateursFiltrés = null;


    @GetMapping("/pageAdmin")
    public String afficherUtilisateur(Model model){
        if (listUtilisateursFiltrés==null){
            listUtilisateurs = serviceAdmin.afficherUtilisateurs();
            model.addAttribute("listUtilisateurs",listUtilisateurs);
            return "pageAdmin";
        }
        listUtilisateursFiltrés = null;
        return "pageAdmin";
    }

    @PostMapping("/Admin/ajouter")
    public String ajouterUtilisateur(Model model) {
        List<Role> listRoles = serviceUtilisateur.afficherRoles();
        model.addAttribute("listRoles",listRoles);
        Utilisateur utilisateur = new Utilisateur();
        model.addAttribute("utilisateur", utilisateur);
        return "pageInscription";
    }
    @PostMapping("/utilisateurs/save")
    public String ajouterUtilisateur(Utilisateur utilisateur) {
        serviceAdmin.enregistrer(utilisateur);
        return "redirect:/pageAdmin";
    }

    @GetMapping("/Admin/filtrerLangue")
    public String filtrerLangue(RedirectAttributes redirectAttributes, @RequestParam("langue") String langue){
        if (langue!=null){
            listUtilisateursFiltrés = serviceAdmin.allByLangue(langue);
            redirectAttributes.addFlashAttribute("listUtilisateurs",listUtilisateursFiltrés);
            return "redirect:/pageAdmin";
        }
        return "pageAdmin";
    }

    @GetMapping("/Admin/filtrerNom")
    public String filtrerNom(RedirectAttributes redirectAttributes, @RequestParam("nom") String nomUtilisateur,Model model){
        List<Utilisateur> utilisateurs = serviceAdmin.rechercherUtilisateurParNom(nomUtilisateur);
        model.addAttribute("listUtilisateurs", utilisateurs);
        return "pageAdmin";
    }

    @GetMapping("/Admin/filtrerInterets")
    public String filtrerInterets(RedirectAttributes redirectAttributes, @RequestParam("interets") String interets){
        if (interets!=null){
            listUtilisateursFiltrés = serviceAdmin.allByInterets(interets);
            redirectAttributes.addFlashAttribute("listUtilisateurs",listUtilisateursFiltrés);
            return "redirect:/pageAdmin";
        }
        return "pageAdmin";
    }

    @GetMapping("/Admin/gerer/{id}")
    public String supprimerUtilisateur(@PathVariable("id") int id, @RequestParam("operation") String operation, RedirectAttributes redirectAttributes, Model model) {
        if ("supprimer".equals(operation)) {
            serviceAdmin.supprimer(id);
            return "redirect:/pageAdmin";
        } else if ("modifier".equals(operation)) {
            utilisateurId = id;
            Utilisateur utilisateur = serviceAdmin.getUserById(utilisateurId);
            model.addAttribute("utilisateur", utilisateur);
            return "pageModification";
        } else if ("activer/desactiver".equals(operation)) {
            serviceAdmin.modifierActif(id);
        }
        String message = "User modified successfully!";
        redirectAttributes.addFlashAttribute("modificationMessage", message);
        return "redirect:/pageAdmin";
    }

    @PostMapping("/Admin/modifier")
    public String modifierUtilisateur(@RequestParam("email") String email, @RequestParam("nomUtilisateur") String nomUtilisateur,
                                      @RequestParam("nom") String nom, @RequestParam("motdepasse") String motdepasse, @RequestParam("langue") String langue,
                                      @RequestParam("interetsCulturels") String interetsCulturels,
                                      @RequestParam("photoProfile") String photoProfile, Model model){


        serviceAdmin.modifier(utilisateurId, email, nomUtilisateur, nom, motdepasse, langue, interetsCulturels, photoProfile);
        listUtilisateurs = serviceAdmin.afficherUtilisateurs();
        model.addAttribute("listUtilisateurs",listUtilisateurs);
        return "pageAdmin";
    }



}
