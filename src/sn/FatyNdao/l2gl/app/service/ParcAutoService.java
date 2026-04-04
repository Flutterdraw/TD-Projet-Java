package sn.FatyNdao.l2gl.app.service;

import sn.FatyNdao.l2gl.app.model.*;
import java.util.ArrayList;
import java.util.List;

public class ParcAutoService {

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
}