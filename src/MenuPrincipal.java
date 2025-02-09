import restaurant.GestionnairePlats;
import restaurant.Restaurant;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MenuPrincipal {

    private static final Scanner scanner = new Scanner(System.in);
    static final GestionnairePlats gestionnairePlats = new GestionnairePlats();
    static final Restaurant restaurant = new Restaurant("McDrummond");

    public static void main(String[] args) {
        boolean done = false;

        while (!done) {
            if (afficherMenu("principal")) {
                System.out.print("Option : ");
                String option = scanner.nextLine();
                done = gererOptionMenuPrincipal(option);
            } else {
                done = true;
            }
        }
    }

    private static final Map<String, String> menuStrings = new HashMap<>();

    static boolean afficherMenu(String name) {
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
            case "1" -> MenuPlats.menuGestionPlats();
            case "2" -> MenuCommandes.menuGestionCommandes();
            case "3" -> {
                System.out.println("Vous voulez quitter !");
                return true;
            }
            default -> System.out.println("Option invalide. SVP choisir une option valide.");
        }
        return false;
    }


}