package restaurant;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String nom;
    private List<Commande> commandes;

    public Restaurant(String nom) {
        this.nom = nom;
        this.commandes = new ArrayList<>();
    }

    public Commande nouvelleCommande() {
        Commande commande = new Commande();
        commandes.add(commande);
        return commande;
    }
    public void ajouterCommande(Commande commande) {
        commandes.add(commande);
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Commandes du restaurant ").append(nom).append(":\n");
        if (commandes.isEmpty()) {
            builder.append("  Liste de commandes du restaurant vide\n");
        }
        for (int i = 0; i < commandes.size(); i++) {
            builder.append("\nCommande #");
            builder.append(i+1);
            builder.append("\n");
            builder.append(commandes.get(i));
        }
        return builder.toString();
    }
}
