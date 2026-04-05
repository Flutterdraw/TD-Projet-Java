package sn.FatyNdao.l2gl.app.model;

import java.time.LocalDate;

public class Entretien extends Entite {
    private final Vehicule vehicule;
    private final java.time.LocalDate date;
    private final String description;
    private final int cout;

    public Entretien(Long id, Vehicule vehicule, LocalDate date, String description, int cout) {
        if (cout < 0 ){
            throw new IllegalArgumentException("Count ne peut pas être négatif");
        }
        super(id);
        this.vehicule = vehicule;
        this.date = date;
        this.description = description;
        this.cout = cout;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public int getCout() {
        return cout;
    }

    @Override
    public String afficher() {
        return "Entretien{" +
                "vehicule=" + vehicule.getImmatriculation() +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", cout=" + cout +
                '}';
    }
}
