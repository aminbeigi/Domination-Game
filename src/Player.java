/**
 * Player class will increment or decrement the armies count by 1.
 * Player class will use Territory class to create methods.
 */
public class Player {
    private static int count = 1;
    private String name;
    private int unplacedArmies = 3;
    private int turn = 0;
    private int territories = 0;

    public Player() {
        System.out.print("Enter player " + count + "'s name: ");
        name = World.keyboard.next();
        ++count;
    }

    public void placeArmies(Territory territory, int armies) {
        unplacedArmies = unplacedArmies - armies;
        territory.placeArmies(this, armies);
    }

    public String getName() {
        return name;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getTerritories() {
        return territories;
    }

    public void setTerritories(int territories) {
        this.territories = territories;
    }

    public int getUnplacedArmies() {
        return unplacedArmies;
    }

    public void setUnplacedArmies(int unplacedArmies) {
        this.unplacedArmies = unplacedArmies;
    }

    public String toString() {
        return name;
    }
}