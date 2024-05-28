package game.tiles.units.enemies;

import game.tiles.actions.Movement;
import game.tiles.units.Unit;
import game.tiles.units.players.Player;
import view.input.InputProvider;
import game.tiles.Tile;

public class Monster extends Enemy {
    private int visionRange;
    public Monster(char enemyTile, String name, int healthCapacity, int attack, int defense, int visionRange, int experience) {
        super(enemyTile, name, healthCapacity, attack, defense, experience);
        this.visionRange = visionRange;
    }
    public void gameTick(Player player) {
        if (player.getPosition().range(this.position) < this.visionRange) {
            int dx = this.getPosition().getX() - player.getPosition().getX();
            int dy = this.getPosition().getY() - player.getPosition().getY();
            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0) {
                    InputProvider inputProvider = InputProvider.Left;
                    Tile tile = Movement.moveEnemy(inputProvider, this);
                    tile.accept(this);
                } else {
                    InputProvider inputProvider = InputProvider.Right;
                    Tile tile = Movement.moveEnemy(inputProvider, this);
                    tile.accept(this);
                }
            } else {
                if (dy > 0) {
                    InputProvider inputProvider = InputProvider.Up;
                    Tile tile = Movement.moveEnemy(inputProvider, this);
                    tile.accept(this);
                } else {
                    InputProvider inputProvider = InputProvider.Down;
                    Tile tile = Movement.moveEnemy(inputProvider, this);
                    tile.accept(this);
                }
            }
        } else {
            int r = (int) (Math.random() * 4 + 1);
            if (r == 1) {
                Tile tile1 = Movement.moveEnemy(InputProvider.Up, this);
                tile1.accept(this);
            } else if (r == 2) {
                Tile tile2 = Movement.moveEnemy(InputProvider.Down, this);
                tile2.accept(this);
            } else if (r == 3) {
                Tile tile3 = Movement.moveEnemy(InputProvider.Left, this);
                tile3.accept(this);
            } else if (r == 4) {
                Tile tile4 = Movement.moveEnemy(InputProvider.Right, this);
                tile4.accept(this);
            }
        }
    }
    public void accept(Player player){
        player.battle(this);
        if(!this.isAlive()){
            this.onDeath(player);
            player.initialize(this.getPosition());
            player.getPosition().initialize();
        }
    }
    public void accept(Enemy enemy){
        //the monster does not move
    }
    public void onDeath(Unit u){
        super.onDeath(u);
    }
    public void onKill(Player player){
        super.onKill(player);
    }
    public void onKill(Enemy enemy){
        //nothing happens
    }
    public String describe() {
        return super.describe() + "\t\tVision Range: " + this.visionRange;
    }
    public Monster copy(){
        return new Monster(this.tile, this.name, this.health.getPool(), this.attack, this.defense, this.visionRange, this.getExperience());
    }
}
