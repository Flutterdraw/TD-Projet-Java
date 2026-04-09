package sn.FatyNdao.l2gl.app.repo;

import sn.FatyNdao.l2gl.app.model.Identifiable;

import java.util.List;
import java.util.Optional;

public interface Crud<T extends Identifiable> {
    void create(T creation);
    Optional<T> readOpt(Long id);
    void update(T creation);
    void delete(Long id);
    List<T> findAll();
}