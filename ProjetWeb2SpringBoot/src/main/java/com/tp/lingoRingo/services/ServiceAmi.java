package com.tp.lingoRingo.services;


import com.tp.lingoRingo.entities.Ami;
import com.tp.lingoRingo.entities.Utilisateur;
import com.tp.lingoRingo.repos.AmisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceAmi {
    @Autowired
    AmisRepository amisRepository;

    public List<Ami> afficherTousLesAmis() {
        return amisRepository.findAll(); // Afficher toutes les relations pour tester
    }

    //Afficher la liste d'amis d'un utilisateur
    public List<Ami> afficherAmiUtilisateur(int id_utilisateur1){
        return amisRepository.findAmiByUtilisateur(id_utilisateur1);
    }
    //Afficher la liste de non amis d'un utilisateur
    /*public List<Ami> afficherNonAmiUtilisateur(int id_utilisateur1){
        return amisRepository.findUtilisateursNonAmis(id_utilisateur1);
    }*/

    //Afficher toute les demandes d'ami
    public List<Ami> afficherDemandesAmiUtilisateur(int id_utilisateur1){
        return amisRepository.findDemandeAmiByUtilisateur(id_utilisateur1);
    }


    //Créer une demande d'un utilisateur à un autre
    public void créerDemandeAmi(int id_utilisateur1,int id_utilisateur2){
        amisRepository.creerDemandeAmi(id_utilisateur1,id_utilisateur2);
    }

    //Accepter une demande d'ami
    public void accepterDemandeAmi(int id_utilisateur1, int id_utilisateur2){
        amisRepository.accepterDemandeAmi(id_utilisateur1,id_utilisateur2);
    }

    //Refuser une demande d'ami
    public void refuserDemandeAmi(int id_utilisateur1,int id_utilisateur2){
        amisRepository.refuserDemandeAmi(id_utilisateur1,id_utilisateur2);
    }
    //Supprimer un ami
    public void supprimerAmi(int id_utilisateur1,int id_utilisateur2){
        amisRepository.supprimerRelationAmi(id_utilisateur1,id_utilisateur2);
    }

    //Afficher tous les favoris d'un utilisateur
    public List<Ami> afficherFavoris(int id_utilisateur1){
        return amisRepository.findAmiFavoriByUtilisateur(id_utilisateur1);
    }

    //Ajouter des favoris
    public void ajouterFavoris(int id_utilisateur1,int id_utilisateur2){
        amisRepository.ajouterFavori(id_utilisateur1,id_utilisateur2);
    }
    //Supprimer Favoris
    public void supprimerFavoris(int id_utilisateur1,int id_utilisateur2){
        amisRepository.retirerFavori(id_utilisateur1, id_utilisateur2);
    }
    //Afficher des utilisateurs non ami avec l'utilisateur connecté
    public List<Utilisateur> afficherNonAmiUtilisateur(int id_utilisateur1){
        return amisRepository.findUtilisateursNonAmis(id_utilisateur1);
    }

}
