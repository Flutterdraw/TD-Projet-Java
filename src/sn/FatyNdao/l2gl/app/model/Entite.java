package sn.FatyNdao.l2gl.app.model;

public abstract class Entite {
    private final Long id;

    protected Entite(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id invalide");
        }
        this.id = id;
    }

    public final Long getId() {
        return id;
    }

    public abstract String afficher();
}
