package sn.FatyNdao.l2gl.app.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class Location extends Entite{
    private final Vehicule vehicule;
    private final Conducteur conducteur;
    private final LocalDate dateDebut;
    private Optional<LocalDate> dateFin;
    private final int prixJour;

    public Location(Long id, Vehicule vehicule, Conducteur conducteur, LocalDate dateDebut, int prixJour) {
        if (prixJour < 0) {
            throw new IllegalArgumentException("Le prix ne peut pas être négatif");
        }
        super(id);
        this.vehicule = vehicule;
        this.conducteur = conducteur;
        this.dateDebut = dateDebut;
        this.prixJour = prixJour;
        this.dateFin = Optional.empty();
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public Conducteur getConducteur() {
        return conducteur;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public Optional<LocalDate> getDateFin() {
        return dateFin;
    }

    public int getPrixJour() {
        return prixJour;
    }

    public void terminer(LocalDate fin) {
        if (fin.isBefore(this.dateDebut))
            throw new IllegalArgumentException("La date de fin ne peut pas être avant celle du début");

        this.dateFin = Optional.of(fin);
    }

    public long dureeJours() {
        LocalDate finEffective = dateFin.orElse(LocalDate.now());

        return dateDebut.until(finEffective, ChronoUnit.DAYS);
    }

    @Override
    public String afficher() {
        return "Location{" +
                "vehicule=" + vehicule.getImmatriculation() +
                ", conducteur=" + conducteur.getPermis() +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", prixJour=" + prixJour +
                '}';
    }
}
