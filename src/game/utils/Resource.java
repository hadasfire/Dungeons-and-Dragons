package game.utils;

public class Resource {
    private int pool;
    private int amount;
    public Resource(int pool, int amount){
        this.pool=pool;
        this.amount=amount;
    }
    public int getPool(){
        return this.pool;
    }
    public int getAmount(){
        return this.amount;
    }
    public void setAmount(int value){this.amount=value;}
    public void increaseAmount(int added){
        if(this.amount+added>=this.pool)
            this.amount=this.pool;
        else {
            this.amount=this.amount+added;
        }
    }
    public boolean reduceAmount(int reduce){
        if(this.amount-reduce<=0) {
            this.amount=0;
            return false;
        }
        else {
            this.amount=this.amount-reduce;
        }
        return true;
    }
    public void addCapacity(int gained){
        this.pool=this.pool+gained;
    }
    public void restore(){
        this.amount=this.pool;
    }
}
