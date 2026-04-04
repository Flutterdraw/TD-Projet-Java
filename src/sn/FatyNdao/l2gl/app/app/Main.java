package sn.FatyNdao.l2gl.app.app;
import sn.FatyNdao.l2gl.app.model.*;
import sn.FatyNdao.l2gl.app.service.ParcAutoService;

import java.time.LocalDate;
import java.util.*;

public class Main {
    static void main(String[] args) {
    /*
    A - Tests
        1 - Un voiture est disponible lorsqu'il n'est pas loué ou en panne
        2 - Un véhicule est en panne lorsqu'il n'a pas de problème
        3 - Kilométrage > Seuil lorsque le véhicule dépasse la limite prédéfinie
        4 - Le véhicule doit être en maintenace si son kilomètrage dépasse le seuil accordé ou qu'il soit devenue obsoléte
        5 - Le conducteur est autorisé si il est conforme au critère de confirmation
    B - Transformations
        6 - Affiche les informations du véhicule
        7 - Retourne la matricule du véhicule récupérée par un getter
        8 - Retourne l'âge calculer grâce à l'année du véhicule
        9 - Retourne le coût total calculé grâce aux informations monétaires liés au véhicule (ex: coût + taxe fixe)
    C - Actions
        10 - Récupére la matricule de la véhicule et change son état par rapport aux conditions
        11 - Incrémenter le nombre de kilométre d'un véhicule de X.
        12.- Mettre à jour la date de  fin d'une location pour monter qu'elle est rendue.
    D - Comparaison
        13 - Trier 2 véhicules par kilométrage dans l'ordre croissant(du plus faible ou plus élevé).
        14 - Trier 2 véhicules par immatriculation dans l'ordre alphabétique.
    */

    /*
    #Comportement                                                         | Groupe   | Signature pressentie
    1. “Véhicule disponible ?”                                            | Paquet 1 | (Vehicule v) -> booleen
    2. “Véhicule en panne ?”                                              | Paquet 1 | (Vehicule v) -> booleen
    3. “Kilométrage > seuil ?”                                            | Paquet 1 | (Vehicule v) -> booleen
    4. “Véhicule à réviser : (km > seuil) OU (année < seuilAnnée)”        | Paquet 1 | (Vehicule v) -> booleen
    5. “Conducteur autorisé : permis correspond à un format donné”        | Paquet 1 | (Conducteur c) -> booleen
    6. “Résumé véhicule” (String)                                         | Paquet 2 | (Vehicule v) -> String
    7. “Extraire immatriculation” (String)                                | Paquet 2 | (Vehicule v) -> String
    8. “Calculer âge du véhicule” (int)                                   | Paquet 2 | (Vehicule v) -> int
    9. “Coût total d’un entretien” (int)                                  | Paquet 2 | (Entretien e) -> int
    10. “Marquer véhicule en révision”                                    | Paquet 3 | (Vehicule v) -> void
    11. “Augmenter kilométrage d’un véhicule de X”                        | Paquet 3 | (Vehicule v) -> void
    12. “Terminer une location (mettre dateFin)”                          | Paquet 3 | (Location l) -> void
    13. “Comparer deux véhicules par kilométrage (ordre croissant)”       | Paquet 4 | (Vehicule v1, Vehicule v2) -> int
    14. “Comparer deux véhicules par immatriculation (ordre alphabétique)”| Paquet 4 | (Vehicule v1, Vehicule v2) -> int
    */

        ParcAutoService service = new ParcAutoService();

        List<Vehicule> flotte = new ArrayList<>();
        Vehicule v1 = new Vehicule(1L, "DK-123-AA", "Toyota", 2020, EtatVehicule.DISPONIBLE, 2000);
        Vehicule v2 = new Vehicule(2L, "TH-456-BB", "Peugeot", 2015, EtatVehicule.EN_REVISION, 1200);
        Vehicule v3 = new Vehicule(3L, "SL-789-CC", "Mercedes", 2022, EtatVehicule.EN_PANNE, 1700);
        Vehicule v4 = new Vehicule(4L, "ZG-001-DD", "Renault", 2018, EtatVehicule.EN_LOCATION, 1500);
        flotte.add(v1);
        flotte.add(v2);
        flotte.add(v3);
        flotte.add(v4);

        Conducteur c1 = new Conducteur(1L, "Modou", "B-12345");
        Conducteur c2 = new Conducteur(2L, "Awa", "A-98765");

        Entretien e1 = new Entretien(1L, v2, LocalDate.of(2024,2,3), "ploblème léger", 5000);
        Entretien e2 = new Entretien(2L, v3, LocalDate.of(2025,7,15), "très abîmé", 12000);

        Location l1 = new Location(1L, v1, c2, LocalDate.of(2026, 1, 8), 14000);

        // A - Test
        Tests<Vehicule> estDispo = v -> v.getEtat().equals(EtatVehicule.DISPONIBLE);
        Tests<Vehicule> enPanne = v -> v.getEtat().equals(EtatVehicule.EN_PANNE);
        Tests<Vehicule> KmDepasseSeuil = v -> v.getKilometrage() > 100000;
        Tests<Vehicule> Areviser = v -> v.getKilometrage() > 100000 || v.getAnnee() < 2020;
        Tests<Conducteur> estAutorise = c -> c.getPermis().startsWith("A");

        //B - Transformation
        Transformations<Vehicule, String> resumeV = v -> "ID: "+ v.getId() + " | Immatriculation: " + v.getImmatriculation() + " | Marque: " + v.getMarque() + " | Kilometrage: " + v.getKilometrage() + " | Etat: " + v.getEtat() + " | Annee: " + v.getAnnee();
        Transformations<Vehicule, String> ExtraitImmatriculation = v -> "Immatriculation: " + v.getImmatriculation();
        Transformations<Vehicule, Integer> ageV = v -> LocalDate.now().getYear() - v.getAnnee();
        Transformations<Entretien, Integer> coutTotalE = e -> e.getCout() * 1500;

        //C - Action
        Actions<Vehicule> marquerEnRevision = v -> v.setEtat(EtatVehicule.EN_REVISION);
        Actions<Vehicule> augmenterKm = v -> v.setKilometrage(v.getKilometrage() + 10);
        Actions<Location> TerminerL = l -> l.terminer(LocalDate.now());

        //D - Comparaison
        Comparaisons<Vehicule> compareKmV = (v01, v02) -> Integer.compare(v01.getKilometrage(), v02.getKilometrage());
        Comparaisons<Vehicule> compareImmatriculationV = (v01, v02) -> v01.getImmatriculation().compareTo(v02.getImmatriculation());

        IO.println("--- Filtrage Voiture (Disponibilité) ---");
        List<Vehicule> disponibiliteV = service.filtrerVehicules(flotte, estDispo);
        disponibiliteV.forEach(v -> IO.println(v.getImmatriculation()));

        IO.println("--- Filtrage Voiture (En Panne) ---");
        List<Vehicule> enPanneV = service.filtrerVehicules(flotte, enPanne);
        enPanneV.forEach(v -> IO.println(v.getImmatriculation()));

        IO.println("--- Filtrage Voiture (Kilométrage > Seuil) ---");
        List<Vehicule> KmSeuilV = service.filtrerVehicules(flotte, KmDepasseSeuil);
        KmSeuilV.forEach(v -> IO.println(v.getImmatriculation()));

        IO.println("--- Filtrage Voiture (A réviser) ---");
        List<Vehicule> ReviserV = service.filtrerVehicules(flotte, Areviser);
        ReviserV.forEach(v -> IO.println(v.getImmatriculation()));

        IO.println("--- Test simple Conducteur (Autorisation) ---");
        IO.println("Conducteur "+c1.getId()+"|"+c1.getNom()+" autorisé(e): "+estAutorise.test(c1));

        IO.println("--- Mappage Voiture (Résumé) ---");
        List<String> ResumeV = service.mapperVehicules(flotte, resumeV);
        ResumeV.forEach(IO::println);

        IO.println("--- Mappage Voiture (Extrait Immatriculation) ---");
        List<String> extraitV = service.mapperVehicules(flotte, ExtraitImmatriculation);
        extraitV.forEach(IO::println);

        IO.println("--- Test simple Voiture (Age véhicule) ---");
        IO.println("Age du véhicule: "+ageV.transformation(v4));

        IO.println("--- Test simple Entretien (Cout total) ---");
        IO.println("Coût total de l'entretien: "+coutTotalE.transformation(e1));

        IO.println("--- Applique Voiture (change Etat) ---");
        service.appliquerSurVehicules(flotte, marquerEnRevision);
        flotte.forEach(v -> IO.println(v.afficher()));

        IO.println("--- Applique Voiture (Augmente Kilométrage) ---");
        service.appliquerSurVehicules(flotte, augmenterKm);
        flotte.forEach(v -> IO.println(v.afficher()));

        IO.println("--- Test simple Location (Cout total) ---");
        IO.println(l1.afficher());
        TerminerL.action(l1);
        IO.println("Ajout de date Fin: " +l1.afficher());

        IO.println("--- Comparaison Voiture (Kilomètrage) ---");
        service.trierVehicules(flotte, compareKmV);
        flotte.forEach(v -> IO.println(v.afficher()));

         IO.println("--- Comparaison Voiture (Immatriculation) ---");
        service.trierVehicules(flotte, compareImmatriculationV);
        flotte.forEach(v -> IO.println(v.afficher()));
    }
}