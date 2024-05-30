package com.tp.lingoRingo.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "utilisateurs")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_utilisateur;
    @Column(length = 128, nullable = false, unique = true)
    private String email;
    @Column(name = "nom_utilisateur",length = 64, nullable = false)
    private String nom_utilisateur;

    @Column(length = 64, nullable = false)
    private String nom;

    @Column(length = 64, nullable = false)
    private String mot_de_passe;

    @Column(length = 64, nullable = false)
    private String langue;

    @Column(length = 64, nullable = false)
    private String interets_culturels;

    @Column(nullable = false)
    private boolean actif;


    @Column(length = 128)
    private String photo_profile;

    @Column(length = 128) //surement faudra faire un jointure
    private String document;

    //Jointure pour relier les utilisateurs aux forums
    @ManyToMany
    @JoinTable(name="forum_utilisateurs",
            joinColumns = @JoinColumn(name = "id_utilisateur"),
            inverseJoinColumns = @JoinColumn(name = "id_forum")
    )
    private Set<Forum> forum_utilisateurs = new HashSet();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "utilisateurs_roles",
            joinColumns = @JoinColumn(name = "utilisateur_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();



    public Utilisateur(Integer id_utilisateur, String email, String nom_utilisateur, String nom,
                       String mot_de_passe, String langue, String interets_culturels,
                       boolean actif, String photo_profile, String document) {
        this.id_utilisateur = id_utilisateur;
        this.email = email;
        this.nom_utilisateur = nom_utilisateur;
        this.nom = nom;
        this.mot_de_passe= mot_de_passe;
        this.langue = langue;
        this.interets_culturels = interets_culturels;
        this.actif = actif;
        this.photo_profile = photo_profile;
        this.document = document;
    }

    public Utilisateur(String email, String nom_utilisateur, String nom, String mot_de_passe, String langue, String interets_culturels, boolean actif) {

        this.email = email;
        this.nom_utilisateur = nom_utilisateur;
        this.nom = nom;
        this.mot_de_passe = mot_de_passe;
        this.langue = langue;
        this.interets_culturels = interets_culturels;
        this.actif = actif;
        roles = new HashSet();
    }

    public Utilisateur(Integer id_utilisateur, String email, String nom_utilisateur,
                       String nom, String mot_de_passe, String langue,
                       String interets_culturels, boolean actif, String photo_profile,
                       String document, Set<Forum> forum_utilisateurs, Set<Role> roles) {
        this.id_utilisateur = id_utilisateur;
        this.email = email;
        this.nom_utilisateur = nom_utilisateur;
        this.nom = nom;
        this.mot_de_passe = mot_de_passe;
        this.langue = langue;
        this.interets_culturels = interets_culturels;
        this.actif = actif;
        this.photo_profile = photo_profile;
        this.document = document;
        this.forum_utilisateurs = forum_utilisateurs;
        this.roles = roles;
    }

    public Utilisateur(){

    }

    public Integer getId() {
        return id_utilisateur;
    }

    public void setId(Integer id_utilisateur) {
        this.id_utilisateur= id_utilisateur;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomUtilisateur() {
        return nom_utilisateur;
    }

    public void setNomUtilisateur(String nom_utilisateur) {
        this.nom_utilisateur = nom_utilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getInterets_culturels() {
        return interets_culturels;
    }

    public void setInterets_culturels(String interets_culturels) {
        this.interets_culturels = interets_culturels;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }


    public String getPhoto_profile() {
        return photo_profile;
    }

    public void setPhoto_profile(String photo_profile) {
        this.photo_profile = photo_profile;
    }

    public String getPhoto() {
        return photo_profile;
    }

    public void setPhoto(String photo) {
        this.photo_profile = photo;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public void ajouter(Role role){
        this.roles.add(role);
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id_utilisateur=" + id_utilisateur +
                ", email='" + email + '\'' +
                ", nom_utilisateur='" + nom_utilisateur + '\'' +
                ", nom='" + nom + '\'' +
                ", mot_de_passe='" + mot_de_passe + '\'' +
                ", langue='" + langue + '\'' +
                ", interets_culturels='" + interets_culturels + '\'' +
                ", actif=" + actif +
                ", photo_profile='" + photo_profile + '\'' +
                ", document='" + document + '\'' +
                ", forum_utilisateurs=" + forum_utilisateurs +
                ", roles=" + roles +
                '}';
    }
}
