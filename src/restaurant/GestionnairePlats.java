package restaurant;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.*;

public class GestionnairePlats {
    private List<Plat> plats;
    private final Gson gson;

    public GestionnairePlats() {
        this.plats = new ArrayList<>();
        this.gson = new Gson();
    }

    public List<Plat> getPlats() {
        return new ArrayList<>(plats);  // Retourne une copie pour éviter les modifications externes
    }

    public Plat getPlat(int index) {
            return plats.get(index);
    }

    public void charger(String nomFichier) throws IOException {
        try (Reader reader = new FileReader(nomFichier)) {
            plats = gson.fromJson(reader, new TypeToken<List<Plat>>(){}.getType());
        }
    }

    public void sauvegarder(String nomFichier) throws IOException {
        try (Writer writer = new FileWriter(nomFichier)) {
            gson.toJson(plats, writer);
        }
    }

    public void ajouterPlat(Plat plat) {
        plats.add(plat);
    }

    public Map<Integer, Plat> rechercherParNom(String nom) {
        Map<Integer, Plat> resultats = new TreeMap<>();
        for (int i = 0; i < plats.size(); i++) {
            Plat plat = plats.get(i);
            if (plat.getNom().toLowerCase()
                    .contains(nom.toLowerCase())) {
                resultats.put(i, plat);
            }
        }
        return resultats;
    }

    public Map<Integer, Plat> rechercherParPrix(double prix, boolean inferieur) {
        Map<Integer, Plat> resultats = new TreeMap<>();
        for (int i = 0; i < plats.size(); i++) {
            Plat plat = plats.get(i);
            if (inferieur ?
                    plat.getPrix() < prix :
                    plat.getPrix() > prix) {
                resultats.put(i, plat);
            }
        }
        return resultats;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
//        sb.append("GestionnairePlats\n");
        sb.append("Nombre de plats = ").append(plats.size()).append(",\n");
        sb.append("plats = \n");

        for (int i = 0; i < plats.size(); i++) {
            sb.append("  ").append(i).append(": ").append(plats.get(i));
            if (i < plats.size() - 1) {
                sb.append(",\n");
            }
        }

        sb.append("\n");
        return sb.toString();
    }
}