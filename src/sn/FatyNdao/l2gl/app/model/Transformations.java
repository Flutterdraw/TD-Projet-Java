package sn.FatyNdao.l2gl.app.model;

@FunctionalInterface
public interface Transformations<T, R> {
        R transformation(T cible);
}
