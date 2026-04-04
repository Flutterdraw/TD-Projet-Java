package sn.FatyNdao.l2gl.app.model;

@FunctionalInterface
public interface Actions<T> {
    void action(T cible);
}
