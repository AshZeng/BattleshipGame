package player;

import java.util.*;

import ship.Ship;
import world.World;
import world.World.Coordinate;
import world.World.ShipLocation;

/**
 * Random guess player (task A).
 * Please implement this class.
 *
 * @author Youhan Xia, Jeffrey Chan
 */
public class RandomGuessPlayer extends PlayerImp implements Player{

    @Override
    public void initialisePlayer(World world) {
        super.initialisePlayer(world);
    }

    @Override
    public Answer getAnswer(Guess guess) {
        return super.getAnswer(guess);
    }

    @Override
    public boolean noRemainingShips() {
        return super.noRemainingShips();
    }

    @Override
    public Guess makeGuess() {
        // To be implemented.

        if(super.getAvailableGuess().isEmpty()) {
            return null;
        }else{
            Random random = new Random();
            int index = random.nextInt(super.getAvailableGuess().size()-1);
            return super.getAvailableGuess().remove(index);
        }
    } // end of makeGuess()

    @Override
    public void update(Guess guess, Answer answer) {
    }
} // end of class RandomGuessPlayer
