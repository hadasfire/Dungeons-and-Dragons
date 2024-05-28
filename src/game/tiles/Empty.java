package game.tiles;

import game.tiles.units.enemies.Enemy;
import game.utils.Position;
import game.tiles.units.players.*;

public class Empty extends Tile {
    public static final char emptyTile='.';
    public Empty(){
        super(emptyTile);
    }
    public void initialize(Position p){
        super.initialize(p);
    }
    public void accept(Player player){
        player.initialize(this.position);
        player.getPosition().initialize();
    }
    public void accept(Enemy enemy){
        enemy.initialize(this.position);
        enemy.getPosition().initialize();
    }
}
