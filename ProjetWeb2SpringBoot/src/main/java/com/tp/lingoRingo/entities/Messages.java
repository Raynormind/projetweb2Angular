package com.tp.lingoRingo.entities;


import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "messages")
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_message;
    @Column(length = 999)
    private String texte_messages;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "destinateur_id", nullable = false)
    private Utilisateur destinateur_id;
    @Column(nullable = false)
    private Timestamp timestamp_envoie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinataire_id")
    private Utilisateur destinataire_id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forumId")
    private Forum forumId;
    @Column(length = 150)
    private String document;
    @Column(nullable = false)
    private int likes;
    @Column(nullable = false)
    private int dislikes;


    public Messages() {
    }

    public Messages(String messages, Utilisateur destinateur_id, Timestamp timestamp_envoie, Utilisateur destinataire_id, String document) {
        this.texte_messages = messages;
        this.destinateur_id = destinateur_id;
        this.timestamp_envoie = timestamp_envoie;
        this.destinataire_id = destinataire_id;
        this.document = document;
    }
    public Messages(String messages, Utilisateur destinateur_id, Timestamp timestamp_envoie, Forum forumId, String document) {
        this.texte_messages = messages;
        this.destinateur_id = destinateur_id;
        this.timestamp_envoie = timestamp_envoie;
        this.forumId = forumId;
        this.document = document;

    }
    public Messages(int id_message, String messages, Utilisateur destinateur_id, Timestamp timestamp_envoie, Utilisateur destinataire_id, String document) {
        this.id_message = id_message;
        this.texte_messages = messages;
        this.destinateur_id = destinateur_id;
        this.timestamp_envoie = timestamp_envoie;
        this.destinataire_id = destinataire_id;
        this.document = document;
    }


    public Messages(Integer id_message, String messages, Utilisateur destinateur_id, Timestamp timestamp_envoie, Forum forumId, String document, int likes, int dislikes) {
        this.id_message = id_message;
        this.texte_messages = messages;
        this.destinateur_id = destinateur_id;
        this.timestamp_envoie = timestamp_envoie;
        this.forumId = forumId;
        this.document = document;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public Messages(int id_message, String messages, Utilisateur destinateur_id, Timestamp timestamp_envoie, Forum forumId, String document) {
        this.id_message = id_message;
        this.texte_messages = messages;
        this.destinateur_id = destinateur_id;
        this.timestamp_envoie = timestamp_envoie;
        this.forumId = forumId;
        this.document = document;

    }




    public int getId_message() {
        return id_message;
    }

    public void setId_message(Integer id) {
        this.id_message = id;
    }

    public String getTexte_messages() {
        return texte_messages;
    }

    public void setTexte_messages(String messages) {
        this.texte_messages = messages;
    }

    public Utilisateur getDestinateur_id() {
        return destinateur_id;
    }

    public void setDestinateur_id(Utilisateur destinateur) {
        this.destinateur_id = destinateur;
    }

    public Timestamp getTimestamp_envoie() {
        return timestamp_envoie;
    }

    public void setTimestamp_envoie(Timestamp timestamp_envoie) {
        this.timestamp_envoie = timestamp_envoie;
    }

    public Utilisateur getDestinataire_id() {
        return destinataire_id;
    }

    public void setDestinataire_id(Utilisateur destinataire) {
        this.destinataire_id = destinataire;
    }

    public Forum getForumId() {
        return forumId;
    }

    public void setForumId(Forum forum) {
        this.forumId = forum;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }


    @Override
    public String toString() {
        return "Messages{" +
                "id=" + id_message +
                ", messages='" + texte_messages + '\'' +
                ", destinateur=" + destinateur_id +
                ", timestamp_envoie=" + timestamp_envoie +
                ", destinataire=" + destinataire_id +
                ", forum=" + forumId +
                ", document='" + document + '\'' +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                '}';
    }
}
