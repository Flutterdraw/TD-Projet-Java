package sn.FatyNdao.l2gl.app.model;

import java.util.Objects;

public class Vehicule extends Entite {
    private final String immatriculation;
    private final String marque;
    private int kilometrage;
    private EtatVehicule Etat;
    private final int annee;

    public Vehicule(Long id, String immatriculation, String marque, int annee, EtatVehicule etat, int kilometrage) {
        super(id);
        if (immatriculation == null) {
            throw new IllegalArgumentException("Immatriculation invalide");
        }
        if (marque == null) {
            throw new IllegalArgumentException("Marque invalide");
        }
        if (kilometrage < 0) {
            throw new IllegalArgumentException("Kilometrage ne peut pas etre negatif");
        }
        if (annee < 1990) {
            throw new IllegalArgumentException("Annee ne peut pas être inférieur à 1990");
        }
        this.immatriculation = immatriculation;
        this.marque = marque;
        this.annee = annee;
        this.Etat = etat;
        this.kilometrage = kilometrage;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public String getMarque() {
        return marque;
    }

    public int getKilometrage() {
        return kilometrage;
    }

    public EtatVehicule getEtat() {
        return Etat;
    }

    public int getAnnee() {
        return annee;
    }

    public void setKilometrage(int kilometrage) {
        this.kilometrage = kilometrage;
    }

    public void setEtat(EtatVehicule etat) {
        Etat = etat;
    }

    @Override
    public String afficher() {
        return "Vehicule{" +
                "immatriculation='" + immatriculation + '\'' +
                ", marque='" + marque + '\'' +
                ", kilometrage=" + kilometrage +
                ", Etat=" + Etat +
                ", annee=" + annee +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vehicule vehicule = (Vehicule) o;
        return getKilometrage() == vehicule.getKilometrage() && Objects.equals(getImmatriculation(), vehicule.getImmatriculation()) && getEtat() == vehicule.getEtat();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getImmatriculation(), getKilometrage(), getEtat());
    }
}