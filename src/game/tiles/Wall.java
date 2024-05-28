package game.tiles;

import game.tiles.units.enemies.Enemy;
import game.tiles.units.players.Player;
import game.utils.Position;

public class Wall extends Tile {
    public static final char wallTile='#';
    public Wall(Position p){
        super(wallTile);
        super.initialize(p);
    }
    public void accept(Player u){
        //nothing happens
    }
    public void accept(Enemy e){
        //nothing happens
    }
}
