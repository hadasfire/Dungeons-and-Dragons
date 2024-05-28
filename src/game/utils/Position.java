package game.utils;
import java.math.*;
public class Position{
    private int x;
    private int y;
    private Position up;
    private Position down;
    private Position right;
    private Position left;
    public Position(int x, int y){
        this.x=x;
        this.y=y;
    }
    public void initialize(){
        this.up=new Position(this.x, this.y-1);
        this.down=new Position(this.x, this.y+1);
        this.right=new Position(this.x+1, this.y);
        this.left=new Position(this.x-1, this.y);
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public Position getUp() {
        return this.up;
    }
    public Position getDown() {
        return this.down;
    }
    public Position getLeft() {
        return this.left;
    }
    public Position getRight() {
        return this.right;
    }
    public int compareTo(Position pos){
        if(this.y>pos.getY()){
            return 1;
        }
        else if(this.y==pos.getY()){
            if(this.x==pos.getX())
                return 0;
            if(this.x>pos.getX())
                return 1;
            if(this.x<pos.getX())
                return -1;
        }
        return -1;
    }
    public double range(Position pos){
        double output=Math.pow((this.x-pos.getX()),2)+Math.pow((this.y-pos.getY()),2);
        return Math.sqrt(output);
    }
    public boolean inRange(Position position, int range){
        if(range(position)<range){
            return true;
        }
        return false;
    }
}
