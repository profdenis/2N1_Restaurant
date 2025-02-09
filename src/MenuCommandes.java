import restaurant.Commande;
import restaurant.Plat;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class MenuCommandes {

    private static final Scanner scanner = new Scanner(System.in);

    public static void menuGestionCommandes() {
        boolean done = false;

        while (!done) {
            if (MenuPrincipal.afficherMenu("gestionCommandes")) {
                System.out.print("Option : ");
                String option = scanner.nextLine();
                done = gererOptionMenuGestionCommandes(option);
            } else {
                done = true;
            }
        }
    }

    private static boolean gererOptionMenuGestionCommandes(String option) {
        switch (option) {
            case "1" -> chargerCommandes();
            case "2" -> menuNouvelleCommande();
            case "3" -> afficherCommandes();
            case "4" -> sauvegarderCommandes();
            case "5" -> {
                System.out.println("Vous voulez quitter !");
                return true;
            }
            default -> System.out.println("Option invalide. SVP choisir une option valide.");
        }
        return false;
    }

    private static void chargerCommandes() {
        System.out.print("Nom du fichier (par défaut : commandes.json) : ");
        String nomFichier = scanner.nextLine();
        if (nomFichier.trim().isEmpty()) {
            nomFichier = "commandes.json";
        }
        try {
            MenuPrincipal.gestionnaireCommandes.charger(nomFichier);
        } catch (IOException e) {
            System.out.println("impossible de charger le fichier : " + e.getMessage());
        }
    }

    private static void menuNouvelleCommande() {
        boolean done = false;
        Commande commande = new Commande();

        while (!done) {
            if (!commande.estVide()) {
                System.out.println("\nNouvelle commande");
                System.out.println(commande);
            }
            if (MenuPrincipal.afficherMenu("nouvelleCommande")) {
                System.out.print("Option : ");
                String option = scanner.nextLine();
                done = gererOptionMenuNouvelleCommande(option, commande);
            } else {
                done = true;
            }
        }
    }

    private static void afficherCommandes() {
        System.out.print(MenuPrincipal.restaurant);
    }

    private static void sauvegarderCommandes() {
        System.out.print("Nom du fichier : ");
        String nomFichier = scanner.nextLine();
        try {
            MenuPrincipal.gestionnaireCommandes.sauvegarder(nomFichier);
        } catch (IOException e) {
            System.out.println("impossible de sauvegarder le fichier : " + e.getMessage());
        }
    }

    private static boolean gererOptionMenuNouvelleCommande(String option, Commande commande) {
        switch (option) {
            case "1" -> System.out.println(MenuPrincipal.gestionnairePlats);
            case "2" -> menuRechercherPlat();
            case "3" -> ajouterPlatCommande(commande);
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

    private static void menuRechercherPlat() {
        boolean done = false;

        while (!done) {
            if (MenuPrincipal.afficherMenu("rechercherPlat")) {
                System.out.print("Option : ");
                String option = scanner.nextLine();
                done = gererOptionMenuRechercherPlat(option);
            } else {
                done = true;
            }
        }
    }

    private static void ajouterPlatCommande(Commande commande) {
        System.out.print("Index du plat à ajouter : ");
        try {
            int index = scanner.nextInt();
            commande.ajouterPlat(MenuPrincipal.gestionnairePlats.getPlat(index));
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
            MenuPrincipal.restaurant.ajouterCommande(commande);
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
        System.out.print("Chaîne à rechercher : ");

        String critere = scanner.nextLine();
        Map<Integer, Plat> resultats = MenuPrincipal.gestionnairePlats.rechercherParNom(critere);
        afficherResultatsRecherche(resultats);
    }

    private static void rechercherParPrix() {
        System.out.println("Entrer < ou > comme premier caractère, suivi du prix.");
        System.out.print("Prix à rechercher : ");
        String critere = scanner.nextLine();

        char operateur = critere.charAt(0);
        if (operateur != '<' && operateur != '>') {
            System.out.println("Opérateur invalide. SVP entrer < ou > comme opérateur.");
            return;
        }

        try {
            double prix = Double.parseDouble(critere.substring(1));
            Map<Integer, Plat> resultats = MenuPrincipal.gestionnairePlats.rechercherParPrix(prix, operateur == '<');
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
        System.out.println(sb);
    }
}
