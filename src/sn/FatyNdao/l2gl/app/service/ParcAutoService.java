package sn.FatyNdao.l2gl.app.service;

import sn.FatyNdao.l2gl.app.model.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ParcAutoService {

    private final List<Vehicule> vehicules = new ArrayList<>();
    private final List<Vehicule> vehiculesAreviser = new ArrayList<>();
    private final List<Location> locations = new ArrayList<>();
    private final List<LigneRapport> rapports = new ArrayList<>();
    private final Map<String, Vehicule> indexParImmat = new HashMap<>();
    private final Map<Long, List<Entretien>> entretiensParVehiculeId = new HashMap<>();

    public void ajouterVehicule(Vehicule v) {
        if (v == null) return;

        if (!indexParImmat.containsKey(v.getImmatriculation())) {
            vehicules.add(v);
            indexParImmat.put(v.getImmatriculation(), v);
        }
    }

    public boolean supprimerVehicule(String immat) {
        Vehicule v = indexParImmat.get(immat);
        if (v != null) {
            vehicules.remove(v);
            indexParImmat.remove(immat);
            return true;
        }
        return false;
    }

    public Vehicule rechercher(String immat) {
        return indexParImmat.get(immat);
    }

    public Set<Vehicule> vehiculesUnique() {
        return new HashSet<>(this.vehicules);
    }

    public void ajouterEntretien(Entretien e) {
        if (e == null || e.getVehicule() == null) return;

        Long vId = e.getVehicule().getId();

        entretiensParVehiculeId.putIfAbsent(vId, new ArrayList<>());
        entretiensParVehiculeId.get(vId).add(e);
    }

    public List<Entretien> getEntretiens(Long vehiculeId) {
        return entretiensParVehiculeId.getOrDefault(vehiculeId, new ArrayList<>());
    }

    public List<Vehicule> filtrerVehicules(List<Vehicule> src, Tests<Vehicule> regle) {
        List<Vehicule> resultat = new ArrayList<>();
        for (Vehicule v : src) {
            if (regle.test(v)) {
                resultat.add(v);
            }
        }
        return resultat;
    }

    public List<String> mapperVehicules(List<Vehicule> src, Transformations<Vehicule, String> f) {
        List<String> resultat = new ArrayList<>();
        for (Vehicule v : src) {
            resultat.add(f.transformation(v));
        }
        return resultat;
    }

    public void appliquerSurVehicules(List<Vehicule> src, Actions<Vehicule> action) {
        for (Vehicule v : src) {
            action.action(v);
        }
    }

    public void trierVehicules(List<Vehicule> src, Comparaisons<Vehicule> cmp) {
        for (int i = 0; i < src.size(); i++) {
            for (int j = i + 1; j < src.size(); j++) {
                if (cmp.comparaison(src.get(i), src.get(j)) > 0) {
                    Vehicule temp = src.get(i);
                    src.set(i, src.get(j));
                    src.set(j, temp);
                }
            }
        }
    }

    public List<Vehicule> filtrerVehiculeDispo(Actions<Vehicule> vehiculeActions) {
        return vehicules.stream()
                .filter( v -> v.getEtat() == EtatVehicule.DISPONIBLE)
                .collect(Collectors.toList());
    }

    public List<String> getImmatriculationsTriees() {
        return vehicules.stream()
                .map(Vehicule::getImmatriculation)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Vehicule> TopTroisKm() {
        return vehicules.stream()
                .sorted(Comparator.comparing(Vehicule::getKilometrage).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    public double getKilometrageMoyen() {
        return vehicules.stream()
                .mapToInt(Vehicule::getKilometrage)
                .average()
                .orElse(0.0);
    }

    public Map<EtatVehicule, Long> getNombreVehiculesParEtat() {
        return vehicules.stream()
                .collect(Collectors.groupingBy(Vehicule::getEtat, Collectors.counting()));
    }

    public Map<String, Integer> getTotalCoutsParVehicule() {
        return entretiensParVehiculeId.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(
                        e -> e.getVehicule().getImmatriculation(),
                        Collectors.summingInt(Entretien::getCout)
                ));
    }

    public List<LigneRapport> rapport() {
        return rapports.stream()
                .map(r -> new LigneRapport(
                        r.immat(),
                        r.marque(),
                        r.etat(),
                        r.km()
                )).toList();
    }

    public void DemarrerLocation(Location l){
        if (l.getVehicule().getEtat() != EtatVehicule.DISPONIBLE){
            throw new IllegalArgumentException("Véhicule Indisponible");
        }
        l.getVehicule().setEtat(EtatVehicule.EN_LOCATION);
    }

    public void TerminerLocation(Location l){
        if (l.getVehicule().getEtat() != EtatVehicule.EN_LOCATION){
            throw new IllegalArgumentException("Véhicule non loué");
        }
        l.getVehicule().setEtat(EtatVehicule.DISPONIBLE);
    }

    public void ajouterLocation(Location l) {
        if (l == null) return;

        locations.add(l);
    }

    public List<Vehicule> VaReviser (Predicate<Location> rules){
        return locations.stream()
                .filter(rules)
                .map(Location::getVehicule)
                .toList();
    }

}