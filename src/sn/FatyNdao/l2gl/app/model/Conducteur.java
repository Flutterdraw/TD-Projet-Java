package sn.FatyNdao.l2gl.app.model;

public class Conducteur extends Entite {
    private final String nom;
    private final String permis;

    public Conducteur(Long id, String nom, String permis) {
        if (nom == null) {
            throw new IllegalArgumentException("Nom ne peut pas être null");
        }
        if (permis == null) {
            throw new IllegalArgumentException("Permis ne peut pas être null");
        }
        super(id);
        this.nom = nom;
        this.permis = permis;
    }

    public String getNom() {
        return nom;
    }

    public String getPermis() {
        return permis;
    }

    @Override
    public String afficher() {
        return "Conducteur{" +
                "nom='" + nom + '\'' +
                ", permis='" + permis + '\'' +
                '}';
    }
}
