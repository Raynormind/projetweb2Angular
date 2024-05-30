package com.tp.lingoRingo.services;

import com.tp.lingoRingo.entities.Role;
import com.tp.lingoRingo.entities.Utilisateur;
import com.tp.lingoRingo.repos.RoleRepository;
import com.tp.lingoRingo.repos.UtilisateursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ServiceUtilisateur {
    @Autowired
    UtilisateursRepository repos;   //     UtilisateurRepository repos = new  UtilisateurRepository ()
    @Autowired   // il s'agit de l'injection de dépendance
    RoleRepository reposRole;
    public List<Utilisateur> afficherUtilisateurs(){
        return ( List<Utilisateur> ) repos.findAll();
    }

    public List<Role> afficherRoles(){
        return ( List<Role> ) reposRole.findAll();
    }

    public Utilisateur enregistrer(Utilisateur utilisateur){
        return repos.save(utilisateur);
    }



    public String obtenirRoleUtilisateur(String email) {
        // Appelle la méthode de repository pour obtenir le rôle de l'utilisateur
        return repos.findRoleByEmail(email);
    }


    public Utilisateur login(String email, String password) {
        return repos.connexionUtilisateur(email, password);
    }

    /*public boolean isEmailUnique(String email,Integer id){
        Utilisateur userEmail =   repos.getUtilisateurByEmail(email);
        if(userEmail==null) return true;
        boolean isCreatingNewUser = false;
        if(id==null){
            isCreatingNewUser =true;
        }

        if(isCreatingNewUser){
            if(userEmail!=null) return false;
        }else{
            if(userEmail.getId()!=null) return false;
        }
        return true;
    }*/

    public boolean isEmailUnique(String email, Integer id) {
        Utilisateur existingUser = repos.getUtilisateurByEmail(email);
        if (existingUser == null) {
            return true;  // Email est unique
        }
        return existingUser.getId() != null && existingUser.getId().equals(id);
    }


    public Utilisateur getUtilisateurById(Integer id) throws Exception {
        return repos.findById(id).orElseThrow(() -> new Exception("Aucun utilisateur trouvé avec l'ID : " + id));
    }

    @Transactional
    public Utilisateur updateUtilisateur(Integer id, Utilisateur utilisateur) throws Exception {
        Utilisateur utilisateurExistant = repos.findById(id).orElseThrow(() -> new Exception("Utilisateur non trouvé"));

        /*if (repos.existsByEmail(utilisateur.getEmail(), id)) {
            throw new Exception("L'email est déjà utilisé par un autre utilisateur.");
        }
        if (repos.existsByNomUtilisateur(utilisateur.getNomUtilisateur(), id)) {
            throw new Exception("Le nom d'utilisateur est déjà utilisé par un autre utilisateur.");
        }*/

        utilisateurExistant.setNom(utilisateur.getNom());
        utilisateurExistant.setNomUtilisateur(utilisateur.getNomUtilisateur());
        utilisateurExistant.setEmail(utilisateur.getEmail());
        utilisateurExistant.setLangue(utilisateur.getLangue());
        utilisateurExistant.setInterets_culturels(utilisateur.getInterets_culturels());
        utilisateurExistant.setPhoto(utilisateur.getPhoto());
        repos.save(utilisateurExistant);
        return utilisateurExistant;
    }

    public Utilisateur trouverUtilisateur(Integer id) throws UtilisateurNotFoundException {

        try {
            Utilisateur utilisateur =  repos.findById(id).get();
            return utilisateur;

        }catch (NoSuchElementException ex){
            throw new UtilisateurNotFoundException("On ne peut pas trouver utilisateur avec l'id " +id);
        }

    }

    public List<Utilisateur> afficherUtilisateursDuForum(Integer id_forum){
        return ( List<Utilisateur> ) repos.findAllById_Forum(id_forum);
    }




}
