import restaurant.Commande;
import restaurant.GestionnairePlats;
import restaurant.Plat;
import restaurant.Restaurant;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Création d'un nouveau restaurant
        Restaurant leBistro = new Restaurant("Le Bistrot");

        GestionnairePlats gestionnaire = new GestionnairePlats();

        try {
            gestionnaire.charger("plats2.json");
        } catch (IOException e) {
            // Ajout de quelques plats
            gestionnaire.ajouterPlat(new Plat("Pâtes Carbonara", 12.50));
            gestionnaire.ajouterPlat(new Plat("Salade verte", 8.00));
            gestionnaire.ajouterPlat(new Plat("Bœuf Bourguignon", 15.00));
        }

        // Recherche par nom
        List<Plat> platsSalade = gestionnaire.rechercherParNom("salade");
        System.out.println("Plats contenant 'salade' :");
        for (Plat plat : platsSalade) {
            System.out.println(plat);
        }

        // Recherche par prix
        List<Plat> platsChers = gestionnaire.rechercherParPrix(15.00, false);
        System.out.println("\nPlats plus chers que 15$ :");
        for (Plat platsCher : platsChers) {
            System.out.println(platsCher);
        }

        // Sauvegarde dans un fichier
        try {
            gestionnaire.sauvegarder("plats.json");
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }

        // Création d'une première commande
        Commande commande1 = leBistro.nouvelleCommande();
        commande1.ajouterPlat(new Plat("Pâtes Carbonara", 12.50));
        commande1.ajouterPlat(new Plat("Salade verte", 8.00));
        commande1.payer();

        // Création d'une deuxième commande
        Commande commande2 = leBistro.nouvelleCommande();
        commande2.ajouterPlat(new Plat("Bœuf Bourguignon", 15.00));
        commande2.ajouterPlat(new Plat("Frites", 4.50));

        // Affichage des commandes
        System.out.println();
        System.out.println(leBistro);
    }
}