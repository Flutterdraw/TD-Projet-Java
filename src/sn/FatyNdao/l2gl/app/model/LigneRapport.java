package sn.FatyNdao.l2gl.app.model;

import java.util.List;

public record LigneRapport(String immat, String marque, EtatVehicule etat, int km) {
    public LigneRapport {
        if (immat == null) {
            throw new IllegalArgumentException("Immatriculation invalide");
        }
        if (marque == null) {
            throw new IllegalArgumentException("Marque invalide");
        }
        if (km < 0) {
            throw new IllegalArgumentException("Kilometrage ne peut pas être négatif");
        }
    }
}
