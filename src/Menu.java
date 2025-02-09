import restaurant.Commande;
import restaurant.GestionnairePlats;
import restaurant.Plat;
import restaurant.Restaurant;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Menu {

    private static final Scanner scanner = new Scanner(System.in);
    private static final GestionnairePlats gestionnairePlats = new GestionnairePlats();
    private static final Restaurant restaurant = new Restaurant("McDrummond");

    public static void main(String[] args) {
        boolean done = false;

        while (!done) {
            if (afficherMenu("principal")) {
                String option = scanner.nextLine();
                done = gererOptionMenuPrincipal(option);
            } else {
                done = true;
            }
        }
    }

    private static final Map<String, String> menuStrings = new HashMap<>();

    private static boolean afficherMenu(String name) {
        if (!menuStrings.containsKey(name)) {
            try {
                String menuPath = "menu/" + name + ".txt";
                byte[] menuBytes = Files.readAllBytes(Paths.get(menuPath));
                menuStrings.put(name, new String(menuBytes));
            } catch (IOException e) {
                System.err.println("Error reading menu content: " + e.getMessage());
                return false;
            }
        }
        System.out.println(menuStrings.get(name));
        return true;
    }

    private static boolean gererOptionMenuPrincipal(String option) {
        switch (option) {
            case "1" -> chargerPlats();
            case "2" -> sauvegarderPlats();
            case "3" -> nouvelleCommande();
            case "4" -> afficherCommandes();
            case "5" -> {
                System.out.println("Vous voulez quitter !");
                return true;
            }
            default -> System.out.println("Option invalide. SVP choisir une option valide.");
        }
        return false;
    }

    private static void chargerPlats() {
        System.out.println("Nom du fichier (par défaut : plat2.json) : ");
        String nomFichier = scanner.nextLine();
        if (nomFichier.trim().isEmpty()) {
            nomFichier = "plats2.json";
        }
        try {
            gestionnairePlats.charger(nomFichier);
        } catch (IOException e) {
            System.out.println("impossible de charger le fichier : " + e.getMessage());
        }
    }

    private static void sauvegarderPlats() {
        System.out.println("Nom du fichier : ");
        String nomFichier = scanner.nextLine();
        try {
            gestionnairePlats.sauvegarder(nomFichier);
        } catch (IOException e) {
            System.out.println("impossible de sauvegarder le fichier : " + e.getMessage());
        }
    }

    private static void nouvelleCommande() {
        boolean done = false;
        Commande commande = new Commande();

        while (!done) {
            if (!commande.estVide()) {
                System.out.println("\nNouvelle commande");
                System.out.println(commande);
            }
            if (afficherMenu("nouvelleCommande")) {
                String option = scanner.nextLine();
                done = gererOptionMenuNouvelleCommande(option, commande);
            } else {
                done = true;
            }
        }
    }

    private static void afficherCommandes() {
        System.out.println(restaurant);
    }

    private static boolean gererOptionMenuNouvelleCommande(String option, Commande commande) {
        switch (option) {
            case "1" -> System.out.println(gestionnairePlats);
            case "2" -> rechercherPlat();
            case "3" -> ajouterPlat(commande);
            case "4" -> {
                completerCommande(commande);
                return true;
            }
            case "5" -> {
                System.out.println("Commande annulée");
                return true;
            }
            default -> System.out.println("Option invalide. SVP choisir une option valide.");
        }
        return false;
    }

    private static void rechercherPlat() {
        boolean done = false;

        while (!done) {
            if (afficherMenu("rechercherPlat")) {
                String option = scanner.nextLine();
                done = gererOptionMenuRechercherPlat(option);
            } else {
                done = true;
            }
        }
    }

    private static void ajouterPlat(Commande commande) {
        System.out.println("Index du plat à ajouter : ");
        try {
            int index = scanner.nextInt();
            commande.ajouterPlat(gestionnairePlats.getPlat(index));
        } catch (InputMismatchException | IndexOutOfBoundsException e) {
            System.out.println("Index invalide, impossible d'ajouter le plat !");
        } finally {
            scanner.nextLine();
        }
    }

    private static void completerCommande(Commande commande) {
        if (commande.estVide()) {
            System.out.println("Commande vide, impossible de compléter");
        } else {
            restaurant.ajouterCommande(commande);
            System.out.println("Commande complétée");
            System.out.println(commande);
        }
    }

    private static boolean gererOptionMenuRechercherPlat(String option) {
        switch (option) {
            case "1" -> rechercherParNom();
            case "2" -> rechercherParPrix();
            case "3" -> {
                return true;
            }
            default -> System.out.println("Option invalide. SVP choisir une option valide.");
        }
        return false;
    }

    private static void rechercherParNom() {
        System.out.println("Chaîne à rechercher : ");

        String critere = scanner.nextLine();
        Map<Integer, Plat> resultats = gestionnairePlats.rechercherParNom(critere);
        afficherResultatsRecherche(resultats);
    }

    private static void rechercherParPrix() {
        System.out.println("Entrer < ou > comme premier caractère, suivi du prix.");
        System.out.println("Prix à rechercher : ");
        String critere = scanner.nextLine();

        char operateur = critere.charAt(0);
        if (operateur != '<' && operateur != '>') {
            System.out.println("Opérateur invalide. SVP entrer < ou > comme opérateur.");
            return;
        }

        try {
            double prix = Double.parseDouble(critere.substring(1));
            Map<Integer, Plat> resultats = gestionnairePlats.rechercherParPrix(prix, operateur == '<');
            afficherResultatsRecherche(resultats);
        } catch (NumberFormatException e) {
            System.out.println("Prix invalide !");
        }
    }

    private static void afficherResultatsRecherche(Map<Integer, Plat> resultats) {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre de plats = ").append(resultats.size()).append(",\n");
        sb.append("plats = \n");

        int i = 1;
        for (Integer index_plats : resultats.keySet()) {
            sb.append("  ").append(index_plats).append(": ").append(resultats.get(index_plats));
            if (i < resultats.size()) {
                sb.append(",\n");
            }
            i++;
        }
        sb.append("\n");
        System.out.println(sb);
    }
}