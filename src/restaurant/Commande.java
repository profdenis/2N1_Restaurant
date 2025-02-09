package restaurant;

import java.util.ArrayList;
import java.util.List;

public class Commande {
    private List<Plat> plats;
    private boolean estPayee;

    public Commande() {
        this(new ArrayList<>());
    }

    public Commande(List<Plat> plats) {
        this.plats = plats;
        this.estPayee = false;
    }

    public void ajouterPlat(Plat plat) {
        plats.add(plat);
    }

    public double calculerTotal() {
        return plats.stream()
                .mapToDouble(Plat::getPrix)
                .sum();
    }

    public void payer() {
        estPayee = true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Détails de la commande : ");
        if (!estPayee) {
            builder.append("non ");
        }
        builder.append("payée\n");
        plats.forEach(builder::append);
        builder.append(String.format("Total : %.2f$\n", calculerTotal()));
        return builder.toString();
    }
}