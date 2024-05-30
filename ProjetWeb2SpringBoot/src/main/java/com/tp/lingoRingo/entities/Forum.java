package com.tp.lingoRingo.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "forums_discussions")
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_forum;
    @Column(length = 50)
    private String titre_forum;
    @Column(length = 15)
    private String langue;
    @Column(length = 100)
    private String interets_culturels;
    @Column(length = 50)
    private boolean etat_forum;
    @Column(length = 150)
    private String document_forum;

    @ManyToMany
    @JoinTable(name="forum_utilisateurs",
            joinColumns = @JoinColumn(name = "id_forum"),
            inverseJoinColumns = @JoinColumn(name = "id_utilisateur")
    )
    private Set<Utilisateur> utilisateurs = new HashSet<>();



    //Constructeurs

    public Forum(Integer id_forum, String titre_forum, String langue, String interets_culturels, boolean etat_forum, String document_forum) {
        this.id_forum = id_forum;
        this.titre_forum = titre_forum;
        this.langue = langue;
        this.interets_culturels = interets_culturels;
        this.etat_forum = etat_forum;
        this.document_forum = document_forum;
    }

    public Forum(Integer id_forum, String titre_forum, String langue, String interets_culturels, boolean etat_forum) {
        this.id_forum = id_forum;
        this.titre_forum = titre_forum;
        this.langue = langue;
        this.interets_culturels = interets_culturels;
        this.etat_forum = etat_forum;
    }


    public Forum() {
    }

    public Forum(Integer id_forum, String titre_forum) {
        this.id_forum = id_forum;
        this.titre_forum = titre_forum;

    }
    public Forum(String titre_forum, String langue, String interets_culturels, boolean etat_forum, String document_forum) {
        this.titre_forum = titre_forum;
        this.langue = langue;
        this.interets_culturels = interets_culturels;
        this.etat_forum = etat_forum;
        this.document_forum = document_forum;
    }

    public Forum(String titre_forum, String langue, String interets_culturels, boolean etat_forum) {
        this.titre_forum = titre_forum;
        this.langue = langue;
        this.interets_culturels = interets_culturels;
        this.etat_forum = etat_forum;
    }

    public int getId_forum() {
        return id_forum;
    }

    public void setId_forum(Integer id_forum) {
        this.id_forum = id_forum;
    }

    public String getTitre_forum() {
        return titre_forum;
    }

    public void setTitre_forum(String titre_forum) {
        this.titre_forum = titre_forum;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getInterets_culturels() {
        return interets_culturels;
    }

    public void setInterets_culturels(String interets_culturels) {
        this.interets_culturels = interets_culturels;
    }

    public boolean getEtat_forum() {
        return etat_forum;
    }

    public boolean isEtat_forum() {
        return etat_forum;
    }

    public void setEtat_forum(boolean etat_forum) {
        this.etat_forum = etat_forum;
    }

    public String getDocument_forum() {
        return document_forum;
    }

    public void setDocument_forum(String document_forum) {
        this.document_forum = document_forum;
    }

    public Set<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public String getDocument() {
        return document_forum;
    }

    public void setDocument(String document_forum) {
        this.document_forum = document_forum;
    }

    @Override
    public String toString() {
        return "Forum{" +
                "id_forum=" + id_forum +
                ", titre_forum='" + titre_forum + '\'' +
                ", langue='" + langue + '\'' +
                ", interets_culturels='" + interets_culturels + '\'' +
                ", etat_forum='" + etat_forum + '\'' +
                ", document='" + document_forum + '\'' +
                '}';
    }
}
