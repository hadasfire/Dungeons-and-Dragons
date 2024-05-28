package game.tiles;
import java.lang.Comparable;

import game.tiles.units.enemies.Enemy;
import game.tiles.units.players.Player;
import game.utils.Position;

public abstract class Tile implements Comparable<Tile> {
    protected char tile;
    protected Position position;
    protected Tile(char tile){
        this.tile = tile;
    }
    public void initialize(Position position){
        this.position = position;
    }
    public Position getPosition() {
        return position;
    }
    public abstract void accept(Player player);
    public abstract void accept(Enemy enemy);
    @Override
    public int compareTo(Tile tile) {
        return getPosition().compareTo(tile.getPosition());
    }
    @Override
    public String toString() {
        return String.valueOf(tile);
    }
}
