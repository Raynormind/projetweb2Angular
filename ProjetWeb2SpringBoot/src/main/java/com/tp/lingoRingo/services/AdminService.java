package com.tp.lingoRingo.services;


import com.tp.lingoRingo.entities.Forum;
import com.tp.lingoRingo.entities.Role;
import com.tp.lingoRingo.entities.Utilisateur;
import com.tp.lingoRingo.repos.RoleRepository;
import com.tp.lingoRingo.repos.UtilisateursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    UtilisateursRepository repos;   //     UtilisateurRepository repos = new  UtilisateurRepository ()
    @Autowired   // il s'agit de l'injection de dépendance
    RoleRepository reposRole;
    public List<Utilisateur> afficherUtilisateurs(){
        return ( List<Utilisateur> ) repos.findAll();
    }

    public List<Utilisateur> allByLangue(String langue) { return  ( List<Utilisateur>) repos.findAllByLangue(langue); }

    public List<Utilisateur> userByNom(String nom) { return (List<Utilisateur>) repos.findByNom(nom); }

    public List<Utilisateur> allByInterets(String interets) { return  ( List<Utilisateur>) repos.findAllByInteretsCulturels(interets); }



    public List<Role> afficherRoles(){
        return ( List<Role> ) reposRole.findAll();
    }

    public Utilisateur getUserById(int id) { return repos.findById(id);
    }

    public Utilisateur enregistrer(Utilisateur utilisateur){
        return repos.save(utilisateur);
    }

    public void supprimer(int id) { repos.deleteById(id); }

    public void modifier(int id, String email, String nomUtilisateur, String nom, String mdp, String langue, String interetsCulturels, String photoProfile) { repos.updateById(id, email, nomUtilisateur,nom, mdp, langue, interetsCulturels, photoProfile); }

    public void modifierActif(int id) {
        Utilisateur user = repos.findById(id);
        user.setActif(!user.isActif());
        repos.save(user);
    }

    public List<Utilisateur> rechercherUtilisateurParNom(String nomUtilisateur) {
        return repos.findUtilisateurByNomUser(nomUtilisateur);
    }

    /*public boolean isEmailUnique(String email, Integer id) {
        Utilisateur existingUser = repos.getUtilisateurByEmail(email);
        if (existingUser == null) {
            return true;  // Email is unique
        }
        return existingUser.getId() != null && existingUser.getId().equals(id);
    }*/


}

