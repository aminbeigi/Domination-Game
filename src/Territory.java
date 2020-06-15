/**
 * Territory class is responsible for initialising the owner and amount of total armies in each Territory,
 * give each territory an x and y value.
 */
public class Territory {
    private final int row;
    private final int column;
    private Player owner;
    private int armies;

    public Territory(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public void placeArmies(Player owner, int armies) {
        if (this.owner == null && owner != null) {
            owner.setTerritories(owner.getTerritories() + 1);
        }
        if (this.owner != null && owner == null) {
            this.owner.setTerritories(this.owner.getTerritories() - 1);
        }
        this.owner = owner;
        this.armies = this.armies + armies;
    }

    public Player getOwner() {
        return owner;
    }

    public int getArmies() {
        return armies;
    }

    public String toString() {
        return "[" + column + "," + row + "]" + owner + "(" + armies + ")";
    }
}
 
