public class Dice {
    public boolean isInAnimation; 
    public int eyes;
    
    public Dice() {
        eyes = 1;
        isInAnimation = false;
    }
    
    public int roll() {
        eyes = (int)(java.lang.Math.random()*6) + 1;    //sets the dice variable to a random number between 1 and 6
        return eyes; 
    }
}
