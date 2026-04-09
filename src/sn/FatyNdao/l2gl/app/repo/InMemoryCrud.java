package sn.FatyNdao.l2gl.app.repo;

import sn.FatyNdao.l2gl.app.model.Identifiable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryCrud<T extends Identifiable> implements Crud<T> {
    private final Map<Long, T> storage = new HashMap<>();

    @Override
    public void create(T creation) {
        storage.put(creation.getId(), creation);
    }

    @Override
    public Optional<T> readOpt(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void update(T creation) {
        if (storage.containsKey(creation.getId())) {
            storage.put(creation.getId(), creation);
        } else {
            throw new IllegalArgumentException("Id inexistant");
        }
    }

    @Override
    public void delete(Long id) {
        storage.remove(id);
    }

    @Override
    public List<T> findAll() {
        return (List<T>)storage.values();
    }
}