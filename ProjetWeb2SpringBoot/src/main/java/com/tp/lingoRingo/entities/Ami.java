package com.tp.lingoRingo.entities;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "amis")
public class Ami {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_amitié;


    @ManyToOne
    @JoinColumn(name = "id_utilisateur1", nullable = false)
    private Utilisateur id_utilisateur1;


    @ManyToOne
    @JoinColumn(name = "id_utilisateur2", nullable = false)
    private Utilisateur id_utilisateur2;



    @Column(name = "demande_acceptee",nullable = true)
    private boolean demande_acceptee;

    @Column(name = "favori",nullable = false)
    private boolean favori;

    public Ami(int id_amitié, Utilisateur id_utilisateur1, Utilisateur id_utilisateur2, boolean demande_acceptee, boolean favori) {
        this.id_amitié = id_amitié;
        this.id_utilisateur1 = id_utilisateur1;
        this.id_utilisateur2 = id_utilisateur2;
        this.demande_acceptee = demande_acceptee;
        this.favori = favori;
    }

    public Ami() {
    }


    public int getId_amitié() {
        return id_amitié;
    }

    public void setId_amitié(int id_amitié) {
        this.id_amitié = id_amitié;
    }


    public Utilisateur getId_utilisateur1() {
        return id_utilisateur1;
    }

    public void setId_utilisateur1(Utilisateur id_utilisateur1) {
        this.id_utilisateur1 = id_utilisateur1;
    }

    public Utilisateur getId_utilisateur2() {
        return id_utilisateur2;
    }

    public void setId_utilisateur2(Utilisateur id_utilisateur2) {
        this.id_utilisateur2 = id_utilisateur2;
    }

    public boolean isDemande_acceptee() {
        return demande_acceptee;
    }

    public void setDemande_acceptee(boolean demande_acceptee) {
        this.demande_acceptee = demande_acceptee;
    }

    public boolean isFavori() {
        return favori;
    }

    public void setFavori(boolean favori) {
        this.favori = favori;
    }

    @Override
    public String toString() {
        return "Ami{" +
                "id_amitié=" + id_amitié +
                ", id_utilisateur1=" + id_utilisateur1 +
                ", id_utilisateur2=" + id_utilisateur2 +
                ", demande_acceptee=" + demande_acceptee +
                ", favori=" + favori +
                '}';
    }
}

