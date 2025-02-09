package restaurant;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.util.*;

public class GestionnaireCommandes {
    Restaurant restaurant;
    private final Gson gson;

    public GestionnaireCommandes(Restaurant restaurant) {
        this.restaurant = restaurant;
        this.gson = new Gson();
    }

    public List<Commande> getCommandes() {
        return restaurant.getCommandes();
    }

    public Commande getCommande(int index) {
        return restaurant.getCommandes().get(index);
    }

    public void charger(String nomFichier) throws IOException {
        try (Reader reader = new FileReader(nomFichier)) {
            restaurant.setCommandes(gson.fromJson(reader, new TypeToken<List<Commande>>(){}.getType()));
        }
    }

    public void sauvegarder(String nomFichier) throws IOException {
        try (Writer writer = new FileWriter(nomFichier)) {
            gson.toJson(restaurant.getCommandes(), writer);
        }
    }

    public void ajouterCommande(Commande commande) {
        restaurant.getCommandes().add(commande);
    }

    public Map<Integer, Commande> rechercherParTotal(double montant, boolean inferieur) {
        Map<Integer, Commande> resultats = new TreeMap<>();
        for (int i = 0; i < restaurant.getCommandes().size(); i++) {
            Commande commande = restaurant.getCommandes().get(i);
            if (inferieur ?
                commande.calculerTotal() < montant :
                commande.calculerTotal() > montant) {
                resultats.put(i, commande);
            }
        }
        return resultats;
    }
}
