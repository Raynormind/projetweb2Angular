package com.tp.lingoRingo.controller;

import com.tp.lingoRingo.entities.Ami;
import com.tp.lingoRingo.entities.Utilisateur;
import com.tp.lingoRingo.repos.AmisRepository;
import com.tp.lingoRingo.services.ServiceAmi;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AmiController {

    @Autowired
    AmisRepository amisRepository;

    @Autowired
    ServiceAmi serviceAmi;

    @GetMapping("/gestionAmisPrivilege")
    public String pageAmis(Model model, HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        List<Ami> listAmis = serviceAmi.afficherAmiUtilisateur(userId);
        model.addAttribute("listAmis",listAmis);
        return "gestionAmisPrivilege";
    }

    @GetMapping("/gestionAmisMembre")
    public String pageAmisMembre(Model model, HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        List<Ami> listAmis = serviceAmi.afficherAmiUtilisateur(userId);
        model.addAttribute("listAmis",listAmis);
        return "gestionAmisMembre";
    }


    @GetMapping("/gestionDemandesAmis")
    public String pageDemandesAmi(Model model, HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        List<Ami> listDemandesAmis = serviceAmi.afficherDemandesAmiUtilisateur(userId);
        model.addAttribute("listDemandesAmis",listDemandesAmis);
        return "gestionDemandesAmis";
    }

    @GetMapping("/gestionDemandesAmisPrivilege")
    public String pageDemandesAmiPrivilege(Model model, HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        List<Ami> listDemandesAmis = serviceAmi.afficherDemandesAmiUtilisateur(userId);
        model.addAttribute("listDemandesAmis",listDemandesAmis);
        return "gestionDemandesAmisPrivilege";
    }

    @GetMapping("/gestionFavoris")
    public String pageFavoris(Model model, HttpSession session){
        int userId = (int) session.getAttribute("userId");
        List<Ami> listFavoris = serviceAmi.afficherFavoris(userId);
        model.addAttribute("listFavoris",listFavoris);
        return "gestionFavoris";
    }
    @GetMapping("/pageAjoutAmis")
    public String pageRechercherAmi(Model model, HttpSession session){
        int userId = (int) session.getAttribute("userId");
        List<Utilisateur> listUtilisateursNonAmi = serviceAmi.afficherNonAmiUtilisateur(userId);
        model.addAttribute("listUtilisateursNonAmi",listUtilisateursNonAmi);
        return "pageAjoutAmis";
    }

    @GetMapping("/pageAjoutAmisPrivilege")
    public String pageRechercherAmiPrivilege(Model model, HttpSession session){
        int userId = (int) session.getAttribute("userId");
        List<Utilisateur> listUtilisateursNonAmi = serviceAmi.afficherNonAmiUtilisateur(userId);
        model.addAttribute("listUtilisateursNonAmi",listUtilisateursNonAmi);
        return "pageAjoutAmisPrivilege";
    }



    @PostMapping("/ajouter/favoris")
    @Transactional
    public String ajouterFavoris(@RequestParam("id_utilisateur2") String id_utilisateur2, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        int userId = (int) session.getAttribute("userId");
        String nomUtilisateur = (String) session.getAttribute("nomUtilisateur");
        id_utilisateur2 = id_utilisateur2.replaceAll("[^0-9]", "");

        if (id_utilisateur2.isEmpty()) {

            return "erreur";
        }
        redirectAttributes.addFlashAttribute("message", "L'ami  a été ajouté aux favoris.");
        int idUtilisateur2 = Integer.parseInt(id_utilisateur2);
        List<Ami> listAmis = serviceAmi.afficherAmiUtilisateur(userId);
        model.addAttribute("listAmis", listAmis);
        serviceAmi.ajouterFavoris(userId, idUtilisateur2);
        List<Ami> listFavoris = serviceAmi.afficherFavoris(userId);
        model.addAttribute("listFavoris", listFavoris);
        return "redirect:/gestionAmisPrivilege?refresh=" + System.currentTimeMillis();

    }

    @PostMapping("/retirer/favoris")
    @Transactional
    public String retirerFavoris(@RequestParam("id_utilisateur2") String id_utilisateur2, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        int userId = (int) session.getAttribute("userId");
        id_utilisateur2 = id_utilisateur2.replaceAll("[^0-9]", "");

        if (id_utilisateur2.isEmpty()) {

            return "erreur";
        }
        redirectAttributes.addFlashAttribute("message", "L'ami a été retiré des favoris.");

        int idUtilisateur2 = Integer.parseInt(id_utilisateur2);
        List<Ami> listAmis = serviceAmi.afficherAmiUtilisateur(userId);
        model.addAttribute("listAmis", listAmis);
        serviceAmi.supprimerFavoris(userId, idUtilisateur2);
        List<Ami> listFavoris = serviceAmi.afficherFavoris(userId);
        model.addAttribute("listFavoris", listFavoris);
        return "redirect:/gestionFavoris?refresh=" + System.currentTimeMillis();    }

    @PostMapping("/retirer/ami")
    @Transactional
    public String retirerAmis(@RequestParam("id_utilisateur2") String id_utilisateur2, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        int userId = (int) session.getAttribute("userId");
        id_utilisateur2 = id_utilisateur2.replaceAll("[^0-9]", "");

        if (id_utilisateur2.isEmpty()) {

            return "erreur";
        }
        redirectAttributes.addFlashAttribute("message", "Ami retiré de la liste d'amis.");
        int idUtilisateur2 = Integer.parseInt(id_utilisateur2);
        List<Ami> listAmis = serviceAmi.afficherAmiUtilisateur(userId);
        model.addAttribute("listAmis", listAmis);
        serviceAmi.supprimerAmi(userId, idUtilisateur2);
        return "redirect:/gestionAmisMembre?refresh=" + System.currentTimeMillis();
    }




    @PostMapping("/accepter/ami")
    @Transactional
    public String accepterDemandeAmi(@RequestParam("id_utilisateur2") String id_utilisateur2, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        int userId = (int) session.getAttribute("userId");
        String nomUtilisateur = (String) session.getAttribute("nomUtilisateur");
        id_utilisateur2 = id_utilisateur2.replaceAll("[^0-9]", "");
        if (id_utilisateur2.isEmpty()) {
            return "erreur";
        }
        redirectAttributes.addFlashAttribute("message", "La demande d'ami a été accepté.");
        int idUtilisateur2 = Integer.parseInt(id_utilisateur2);
        serviceAmi.accepterDemandeAmi(userId, idUtilisateur2);
        List<Ami> listAmis = serviceAmi.afficherAmiUtilisateur(userId);
        model.addAttribute("listAmis", listAmis);
        List<Ami> listFavoris = serviceAmi.afficherFavoris(userId);
        model.addAttribute("listFavoris", listFavoris);
        return "redirect:/gestionAmisMembre?refresh=" + System.currentTimeMillis();
    }

    @PostMapping("/refuser/ami")
    @Transactional
    public String refuserDemandeAmi(@RequestParam("id_utilisateur2") String id_utilisateur2, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        int userId = (int) session.getAttribute("userId");
        id_utilisateur2 = id_utilisateur2.replaceAll("[^0-9]", "");
        if (id_utilisateur2.isEmpty()) {
            return "erreur";
        }
        redirectAttributes.addFlashAttribute("message", "La demande d'ami a été refusé.");

        int idUtilisateur2 = Integer.parseInt(id_utilisateur2);
        serviceAmi.refuserDemandeAmi(userId, idUtilisateur2);
        List<Ami> listAmis = serviceAmi.afficherAmiUtilisateur(userId);
        model.addAttribute("listAmis", listAmis);
        List<Ami> listFavoris = serviceAmi.afficherFavoris(userId);
        model.addAttribute("listFavoris", listFavoris);
        return "redirect:/gestionDemandesAmis?refresh=" + System.currentTimeMillis();
    }


    @PostMapping("/ajouter/ami")
    @Transactional
    public String ajouterAmi(@RequestParam("id_utilisateur2") String id_utilisateur2, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        int userId = (int) session.getAttribute("userId");
        id_utilisateur2 = id_utilisateur2.replaceAll("[^0-9]", "");
        if (id_utilisateur2.isEmpty()) {
            return "erreur";
        }
        redirectAttributes.addFlashAttribute("message", "Demande d'ami envoyée.");

        int idUtilisateur2 = Integer.parseInt(id_utilisateur2);
        serviceAmi.créerDemandeAmi(userId, idUtilisateur2);
        model.addAttribute("message", "La demande d'ami a été envoyée avec succès !");
        return "redirect:/pageAjoutAmis?refresh=" + System.currentTimeMillis();
    }





    // pour privilege

    @PostMapping("/retirerPrivilege/ami")
    @Transactional
    public String retirerAmisPrivilege(@RequestParam("id_utilisateur2") String id_utilisateur2, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        int userId = (int) session.getAttribute("userId");
        id_utilisateur2 = id_utilisateur2.replaceAll("[^0-9]", "");

        if (id_utilisateur2.isEmpty()) {

            return "erreur";
        }
        redirectAttributes.addFlashAttribute("message", "Ami retiré de la liste d'amis.");
        int idUtilisateur2 = Integer.parseInt(id_utilisateur2);
        List<Ami> listAmis = serviceAmi.afficherAmiUtilisateur(userId);
        model.addAttribute("listAmis", listAmis);
        serviceAmi.supprimerAmi(userId, idUtilisateur2);
        return "redirect:/gestionAmisPrivilege?refresh=" + System.currentTimeMillis();
    }


    @PostMapping("/accepterPrivilege/ami")
    @Transactional
    public String accepterDemandeAmiPrivilege(@RequestParam("id_utilisateur2") String id_utilisateur2, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        int userId = (int) session.getAttribute("userId");
        String nomUtilisateur = (String) session.getAttribute("nomUtilisateur");
        id_utilisateur2 = id_utilisateur2.replaceAll("[^0-9]", "");
        if (id_utilisateur2.isEmpty()) {
            return "erreur";
        }
        redirectAttributes.addFlashAttribute("message", "La demande d'ami a été accepté.");
        int idUtilisateur2 = Integer.parseInt(id_utilisateur2);
        serviceAmi.accepterDemandeAmi(userId, idUtilisateur2);
        List<Ami> listAmis = serviceAmi.afficherAmiUtilisateur(userId);
        model.addAttribute("listAmis", listAmis);
        List<Ami> listFavoris = serviceAmi.afficherFavoris(userId);
        model.addAttribute("listFavoris", listFavoris);
        return "redirect:/gestionAmisPrivilege?refresh=" + System.currentTimeMillis();
    }

    @PostMapping("/refuserPrivilege/ami")
    @Transactional
    public String refuserDemandeAmiPrivilege(@RequestParam("id_utilisateur2") String id_utilisateur2, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        int userId = (int) session.getAttribute("userId");
        id_utilisateur2 = id_utilisateur2.replaceAll("[^0-9]", "");
        if (id_utilisateur2.isEmpty()) {
            return "erreur";
        }
        redirectAttributes.addFlashAttribute("message", "La demande d'ami a été refusé.");

        int idUtilisateur2 = Integer.parseInt(id_utilisateur2);
        serviceAmi.refuserDemandeAmi(userId, idUtilisateur2);
        List<Ami> listAmis = serviceAmi.afficherAmiUtilisateur(userId);
        model.addAttribute("listAmis", listAmis);
        List<Ami> listFavoris = serviceAmi.afficherFavoris(userId);
        model.addAttribute("listFavoris", listFavoris);
        return "redirect:/gestionDemandesAmisPrivilege?refresh=" + System.currentTimeMillis();
    }


    @PostMapping("/ajouterPrivilege/ami")
    @Transactional
    public String ajouterAmiPrivilege(@RequestParam("id_utilisateur2") String id_utilisateur2, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        int userId = (int) session.getAttribute("userId");
        id_utilisateur2 = id_utilisateur2.replaceAll("[^0-9]", "");
        if (id_utilisateur2.isEmpty()) {
            return "erreur";
        }
        redirectAttributes.addFlashAttribute("message", "Demande d'ami envoyée.");

        int idUtilisateur2 = Integer.parseInt(id_utilisateur2);
        serviceAmi.créerDemandeAmi(userId, idUtilisateur2);
        model.addAttribute("message", "La demande d'ami a été envoyée avec succès !");
        return "redirect:/pageAjoutAmisPrivilege?refresh=" + System.currentTimeMillis();
    }



}
