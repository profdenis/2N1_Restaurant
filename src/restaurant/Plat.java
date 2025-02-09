package restaurant;

public class Plat {
    private String nom;
    private double prix;

    public Plat(String nom, double prix) {
        this.nom = nom;
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;
    }

    @Override
    public String toString() {
        return String.format("- %s : %.2f$", getNom(), getPrix());
    }
}