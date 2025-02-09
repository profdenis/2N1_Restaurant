import restaurant.Plat;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuPlats {

    private static final Scanner scanner = new Scanner(System.in);


    public static void menuGestionPlats() {
        boolean done = false;

        while (!done) {
            if (MenuPrincipal.afficherMenu("gestionPlats")) {
                System.out.print("Option : ");
                String option = scanner.nextLine();
                done = gererOptionMenuGestionPlats(option);
            } else {
                done = true;
            }
        }
    }

    private static boolean gererOptionMenuGestionPlats(String option) {
        switch (option) {
            case "1" -> chargerPlats();
            case "2" -> ajouterPlatGestionnairePlats();
            case "3" -> System.out.println(MenuPrincipal.gestionnairePlats);
            case "4" -> sauvegarderPlats();
            case "5" -> {
                System.out.println("Vous voulez quitter !");
                return true;
            }
            default -> System.out.println("Option invalide. SVP choisir une option valide.");
        }
        return false;
    }

    private static void chargerPlats() {
        System.out.print("Nom du fichier (par défaut : plats2.json) : ");
        String nomFichier = scanner.nextLine();
        if (nomFichier.trim().isEmpty()) {
            nomFichier = "plats2.json";
        }
        try {
            MenuPrincipal.gestionnairePlats.charger(nomFichier);
        } catch (IOException e) {
            System.out.println("impossible de charger le fichier : " + e.getMessage());
        }
    }

    private static void ajouterPlatGestionnairePlats() {
        System.out.println("Ajouter un plat");
        System.out.print("Nom du plat : ");
        String nom = scanner.nextLine();
        System.out.print("Prix du plat : ");
        try {
            double prix = scanner.nextDouble();
            MenuPrincipal.gestionnairePlats.ajouterPlat(new Plat(nom, prix));
        } catch(InputMismatchException e) {
            System.out.println("Impossible d'ajouter un plat, prix invalide");
        }
        finally {
            scanner.nextLine();
        }
    }

    private static void sauvegarderPlats() {
        System.out.print("Nom du fichier : ");
        String nomFichier = scanner.nextLine();
        try {
            MenuPrincipal.gestionnairePlats.sauvegarder(nomFichier);
        } catch (IOException e) {
            System.out.println("impossible de sauvegarder le fichier : " + e.getMessage());
        }
    }


}
