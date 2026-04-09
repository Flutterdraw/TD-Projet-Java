package sn.FatyNdao.l2gl.app.app;
import sn.FatyNdao.l2gl.app.model.*;
import sn.FatyNdao.l2gl.app.repo.InMemoryCrud;
import sn.FatyNdao.l2gl.app.service.ParcAutoService;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

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
        InMemoryCrud<Vehicule> service1 = new InMemoryCrud<>();

        List<Vehicule> flotte = new ArrayList<>();
        Vehicule v1 = new Vehicule(1L, "DK-123-AA", "Toyota", 2020, EtatVehicule.DISPONIBLE, 2000);
        Vehicule v2 = new Vehicule(2L, "TH-456-BB", "Peugeot", 2015, EtatVehicule.EN_REVISION, 1200);
        Vehicule v3 = new Vehicule(3L, "SL-789-CC", "Mercedes", 2022, EtatVehicule.DISPONIBLE, 1700);
        Vehicule v4 = new Vehicule(4L, "ZG-001-DD", "Renault", 2018, EtatVehicule.EN_LOCATION, 1500);
        flotte.add(v1);
        flotte.add(v2);
        flotte.add(v3);
        flotte.add(v4);

        Conducteur c1 = new Conducteur(1L, "Modou", "B-12345");
        Conducteur c2 = new Conducteur(2L, "Awa", "A-98765");

        Entretien e1 = new Entretien(1L, v2, LocalDate.of(2024,2,3), "ploblème léger", 5000);
        Entretien e2 = new Entretien(2L, v3, LocalDate.of(2025,7,15), "très abîmé", 12000);
        Entretien e3 = new Entretien(3L, v3, LocalDate.of(2025,8,2), "abîmé", 10000);

        Vehicule v5 = new Vehicule(5L, "RT-769-PP", "Mercedes", 2022, EtatVehicule.DISPONIBLE, 1700);
        Vehicule v6 = new Vehicule(6L, "PL-002-TT", "Renault", 2018, EtatVehicule.EN_LOCATION, 1500);

        Location l1 = new Location(1L, v5, c2, LocalDate.of(2026, 1, 8), 14000);
        Location l2 = new Location(2L, v6, c1, LocalDate.of(2025, 10, 8), 9000);
        Location l3 = new Location(3L, v2, c2, LocalDate.of(2026, 4, 4), 12000);
        Location l4 = new Location(4L, v3, c1, LocalDate.of(2026, 2, 13), 10000);

        // A - Test
        Tests<Vehicule> estDispo = v -> v.getEtat() == EtatVehicule.DISPONIBLE;
        Tests<Vehicule> enPanne = v -> v.getEtat() == EtatVehicule.EN_PANNE;
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

        IO.println("--- Test 2 entretiens 1 véhicule ---");
        service.ajouterEntretien(e2);
        service.ajouterEntretien(e3);
        List<Entretien> VehiculeparID = service.getEntretiens(3L);
        VehiculeparID.forEach(e -> IO.println(e.afficher()));

        IO.println("--- Kilomètrage Moyen ---");
        IO.println("Le kilomètrage moyen des véhicules est de: "+service.getKilometrageMoyen());

        IO.println("--- Véhicule par Etat ---");
        Map<EtatVehicule, Long> vehicules2 = service.getNombreVehiculesParEtat();
        vehicules2.forEach((v, l) -> IO.println("Le nombre de véhicule "+ v +" : "+ l));

        IO.println("--- Coût Entretien ---");
        Map<String, Integer> Entretiens = service.getTotalCoutsParVehicule();
        Entretiens.forEach((i, t) -> IO.println("Le coût de l'entretien de cet véhicule("+ i +") est de : "+ t +" cfa"));

        IO.println("--- Affichage ---");
        IO.println(v1.afficher());
        IO.println(c1.afficher());
        IO.println(e1.afficher());
        IO.println(l1.afficher());

        IO.println("--- Optional ---");
        service1.create(new Vehicule(1L, "TH-486-AA", "Toyotra", 1998, EtatVehicule.EN_PANNE, 400));
        service1.create(new Vehicule(2L, "SL-246-BB", "Lamborghini", 2023, EtatVehicule.EN_LOCATION, 44400));
        Vehicule v01 = service1.readOpt(1L).orElse(new Vehicule(0L, "XX-XXX-XX", "Inconnu", 1990, EtatVehicule.EN_REVISION, 0));
        Vehicule v02 = service1.readOpt(2L).orElseThrow(() -> new NoSuchElementException("Véhicule inexistant"));
        IO.println(v01.afficher());
        IO.println(v02.afficher());
        service1.readOpt(1L).ifPresent(v -> IO.println(v.afficher()));

        IO.println("--- Rapport ---");
        List<LigneRapport> genererRapport = List.of(
                new LigneRapport("RE-852-LL", "Buggati", EtatVehicule.DISPONIBLE, 85000),
                new LigneRapport("FM-911-AA", "Toyota", EtatVehicule.EN_LOCATION, 90000),
                new LigneRapport("AC-976-RR", "Coccinelle", EtatVehicule.EN_LOCATION, 30000)
        );
        genererRapport.forEach(IO::println);

        IO.println("--- Finalisation ---");
        service.DemarrerLocation(l1);
        service.TerminerLocation(l2);
        IO.println(v5.afficher());
        IO.println(v6.afficher());
        service.ajouterLocation(l1);
        service.ajouterLocation(l2);
        service.ajouterLocation(l3);
        service.ajouterLocation(l4);
        int year = LocalDate.now().getYear();
        Predicate<Location> rules = l -> l.getVehicule().getKilometrage() > 1000 || year-l.getVehicule().getAnnee() > 20 || l.dureeJours() > 15;
        List<Vehicule> VehiculesAreviser = service.VaReviser(rules);
        IO.println("--- Liste à réviser ---");
        VehiculesAreviser.forEach(v -> IO.println(v.afficher()));


    }
}