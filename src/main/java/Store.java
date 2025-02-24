import java.util.Scanner;

public class Store {
    private int elixirPrice = 10; // Prix de l'élixir
    private int elixirHeal = 20;  // PV restaurés par l'élixir

    public void openStore(Player player) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n--- Bienvenue dans la boutique ! ---");
        System.out.println("1. Acheter un Élixir (" + elixirPrice + " pièces d'or)");
        System.out.println("2. Quitter");

        String choix = scanner.nextLine();

        switch (choix) {
            case "1":
                buyElixir(player);
                break;
            case "2":
                System.out.println("À bientôt !");
                break;
            default:
                System.out.println("Choix invalide.");
        }
    }

    private void buyElixir(Player player) {
        if (player.getGold() >= elixirPrice) {
            player.setGold(player.getGold() - elixirPrice);
            player.heal(elixirHeal);
            System.out.println("Tu as acheté un Élixir ! Tu récupères " + elixirHeal + " PV.");
        } else {
            System.out.println("Tu n'as pas assez d'or !");
        }
    }
}
