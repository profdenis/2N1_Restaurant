import restaurant.Commande;
import restaurant.Plat;
import restaurant.Restaurant;

public class Main {
    public static void main(String[] args) {
        // Création d'un nouveau restaurant
        Restaurant leBistro = new Restaurant("Le Bistrot");

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
        System.out.println(leBistro);

        // Paiement de la première commande
        commande1.payer();
    }
}