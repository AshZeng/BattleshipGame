package player;

import java.util.Scanner;
import world.World;

/**
 * Greedy guess player (task B).
 * Please implement this class.
 *
 * @author Youhan Xia, Jeffrey Chan
 */
public class GreedyGuessPlayer extends PlayerImp implements Player{

    int index = -1;
    @Override
    public void initialisePlayer(World world) {
        super.initialisePlayer(world);

} // end of initialisePlayer()

    @Override
    public Answer getAnswer(Guess guess) {
        // To be implemented.
        return super.getAnswer(guess);
        // dummy return
    } // end of getAnswer()


    @Override
    public Guess makeGuess() {
        // To be implemented.
        if(super.getAvailableGuess().isEmpty()){
            return null;
        }else{
            //Hunting Mode
            index += 1;
            if(super.getAnswer(super.getAvailableGuess().get(index)))
            return super.getAvailableGuess().remove(index);



        }
        // dummy return
    } // end of makeGuess()


    @Override
    public void update(Guess guess, Answer answer) {
        // To be implemented.
    } // end of update()


    @Override
    public boolean noRemainingShips() {
        // To be implemented.
        return super.noRemainingShips();
        // dummy return
    } // end of noRemainingShips()

} // end of class GreedyGuessPlayer
