import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Dice {
    public boolean isInAnimation; 
    private ThreadLocalRandom random = ThreadLocalRandom.current();
    public int boostedChanceLvl;

    public Dice() {
        isInAnimation = false;
    }
    
    public int roll(int boostedChance) {
        int eyes=1;
        if (boostedChance == 1) {    //default roll function --> no boost
            eyes = random.nextInt(1,7);    //sets the dice variable to a random number between 1 and 6
            
        } else if (boostedChance == 2) {    //happens if all pieces are still on their start position
            eyes = random.nextInt(3,7);    //sets the dice variable to a random number between 1 and 6
        }
        return eyes;
    }
}
