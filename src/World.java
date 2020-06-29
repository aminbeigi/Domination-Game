import java.util.Scanner;

/**
 * World class will use Player and Territory class to create methods.
 */
public class World {

    public static final Scanner keyboard = new Scanner(System.in);
    private Territory territory1;
    private Territory territory2;
    private Territory territory3;
    private Territory territory4;
    private Player player1;
    private Player player2;

    /*
     * Map of World
     * Territory1[0,0] Territory2[1,0] Territory3[0,1] Territory4[1,1]
     */
    public World() {
        territory1 = new Territory(0, 0);
        territory2 = new Territory(1, 0);
        territory3 = new Territory(0, 1);
        territory4 = new Territory(1, 1);
        player1 = new Player();
        player2 = new Player();
    }

    public void placeArmies(Player player) {
        System.out.println("You have " + player.getUnplacedArmies() + " armies to place.");
        System.out.println(this);

        while (player.getUnplacedArmies() > 0) {
            System.out.print("Select a territory: ");
            int positionX = keyboard.nextInt();
            int positionY = keyboard.nextInt();

            Territory targetTerritory = getTargetTerritory(positionX, positionY);

            if (targetTerritory.getOwner() == null || player == targetTerritory.getOwner()) {
                player.placeArmies(targetTerritory, 1);
            }
            System.out.println(this);
        }
    }

    // helper: get target territory from given x and y position
    private Territory getTargetTerritory(int positionX, int positionY) {
        Territory targetTerritory = null;
        if (positionX == 0 && positionY == 0) {
            targetTerritory = territory1;
        } else if (positionX == 1 && positionY == 0) {
            targetTerritory = territory2;
        } else if (positionX == 0 && positionY == 1) {
            targetTerritory = territory3;
        } else if (positionX == 1 && positionY == 1) {
            targetTerritory = territory4;
        }
        return targetTerritory;
    }

    public void transfer(Player player) {
        System.out.println("Select source/target territories for a transfer.");
        System.out.println(this);
        while (true) {
            System.out.print("Select a territory: ");

            int positionX1 = keyboard.nextInt();

            // if user inputs = -1 exit out of method
            if (positionX1 == -1) {
                return;
            }
            int positionY1 = keyboard.nextInt();

            Territory fromTerritory = getTargetTerritory(positionX1, positionY1);

            // continue if owner of target territory is "null"
            if (fromTerritory.getOwner() == null || player != fromTerritory.getOwner())
                continue;

            System.out.print("Select a territory: ");
            int positionX2 = keyboard.nextInt();
            int positionY2 = keyboard.nextInt();

            Territory toTerritory = getTargetTerritory(positionX2, positionY2);

            // if user input selects same territory to swap
            if (positionX1 == positionX2 && positionY1 == positionY2) {
                continue;
            }

            // if user input wants to transfer to a territory that they already own
            // if user input wants to transfer to a territory owned by "null"
            if (fromTerritory.getOwner() != toTerritory.getOwner()) {
                if (toTerritory.getOwner() != null)
                    continue;
            }

            // performs transfer
            player.placeArmies(fromTerritory, -1);
            player.placeArmies(toTerritory, +1);

            if (fromTerritory.getArmies() == 0) { //check to see if armies is 0 and set owner to null
                fromTerritory.placeArmies(null, 0);
            } else if (toTerritory.getArmies() == 0) {
                toTerritory.placeArmies(null, 0);
            }

            System.out.println(this);
        }
    }

    public void attack(Player player) {
        System.out.println("Select source/target territories for an attack.");
        System.out.println(this);
        Territory fromTerritory = null;

        while (true) {
            System.out.print("Select a territory: ");
            int positionX = keyboard.nextInt();
            // if user inputs = -1 exit out of method
            if (positionX == -1) {
                return;
            }
            int positionY = keyboard.nextInt();

            Territory selectedTerritory = getTargetTerritory(positionX, positionY);

            if (player == selectedTerritory.getOwner()) {
                fromTerritory = selectedTerritory;
                continue;
            }

            if (selectedTerritory.getOwner() == null || fromTerritory == null) {
                continue;
            }

            // if user selects enemy territory
            player.placeArmies(fromTerritory, -1);
            selectedTerritory.getOwner().placeArmies(selectedTerritory, -1);

            if (fromTerritory.getArmies() <= 0) {
                fromTerritory.placeArmies(null, 0);
            }
            if (selectedTerritory.getArmies() <= 0) {
                selectedTerritory.placeArmies(null, 0);
            }

            System.out.println(this);
        }
    }

    public void turn(Player player) {
        System.out.println(player + "'s turn.");

        if (player.getTurn() > 0) {
            System.out.println("Giving " + player.getTerritories() + " new armies to " + player.getName());
            player.setUnplacedArmies(player.getTerritories());
        }
        placeArmies(player);

        if (player.getTurn() > 0) {
            attack(player);
            if (player.getTerritories() > 0) {
                transfer(player);
            }
        }

        player.setTurn(player.getTurn() + 1);
    }

    public void run() {
        Player winner;
        turn(player1);
        turn(player2);
        while (true) {
            turn(player1);
            if (player2.getTerritories() > 0 && player1.getTerritories() > 0) {
                winner = player1;
                break;
            }
            turn(player2);
            if (player1.getTerritories() > 0 && player2.getTerritories() > 0) {
                winner = player2;
                break;
            }
        }

        System.out.println(winner.getName() + " wins!");
    }

    public String toString() {
        return territory1 + " " + territory2 + " " + territory3 + " " + territory4;
    }
}