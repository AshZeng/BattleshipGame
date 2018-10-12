package player;

import world.World;

import java.util.*;

/**
 * Greedy guess player (task B).
 * Please implement this class.
 *
 * @author Youhan Xia, Jeffrey Chan
 */
public class GreedyGuessPlayer implements Player{

    private static final int ALL_SHIP_COORDINATES = 17;
    private LinkedList<Guess> jumpGuesses;

    public World world;

    public int numRow;
    public int numColumn;

    private List<Guess> hitShot;

    private TempWorld tempWorld;
    @Override
    public void initialisePlayer(World world) {
        // To be implemented.
        this.world = world;
        this.numRow = world.numRow;
        this.numColumn = world.numColumn;
        this.tempWorld = new TempWorld(numRow,numColumn,true);
        this.hitShot = new ArrayList<>();


        jumpGuesses = new LinkedList<>();
        this.enumerateGuesses(jumpGuesses);




        //Initialize the board


    } // end of initialisePlayer

    //Use two others method to help user getAnswer.
    @Override
    public Answer getAnswer(Guess guess) {
        Answer answer = new Answer();
        //check if the guess hits the fleet
        if(isHit(guess, answer)){
            answer.isHit = true;
        }
        else
            answer.isHit = false;
        return answer;
    } // end of getAnswer()

    //Use method to judge is shot is hit a ship.
    private boolean isHit(Guess guess, Answer answer) {
        // Iterate through ships
        for(World.ShipLocation ship : world.shipLocations) {
            // Iterate over the coordinates of each ship
            for(World.Coordinate c: ship.coordinates) {
                if(sameAs(c, guess)){ //guess is a hit
                    hitShot.add(guess);
                    // if ship is sunk altogether
                    if(isSunk(ship))
                        answer.shipSunk = ship.ship;
                    return true;
                }
            }
        }
        return false;
    }

    //Judge the ship sunk or not
    private boolean isSunk(World.ShipLocation ship) {
        boolean shipSunk = true;
        // Iterate over the coordinates of the ship
        for(World.Coordinate c: ship.coordinates) {
            // if at least one part of ship hasn't been hit
            if(notContainedInGuessList(c,hitShot)){
                shipSunk = false;
            }
        }
        return shipSunk;
    }

    @Override
    public boolean noRemainingShips() {
        return hitShot.size() >= ALL_SHIP_COORDINATES;
    } // end of noRemainingShips()


    @Override
    public Guess makeGuess() {
        //Target mode
        if ( tempWorld.possibleCoordinate.size() > 0 )
        {
            Guess g = new Guess();
            World.Coordinate tempCoord = new World().new Coordinate();
            tempCoord = tempWorld.possibleCoordinate.remove(0);

            g.row = tempCoord.row;
            g.column = tempCoord.column;

            Guess tempGuess = new Guess();
            for ( int i = 0;i < jumpGuesses.size();i++ )
            {
                tempGuess = jumpGuesses.get(i);
                if ((tempGuess.row == g.row) && (tempGuess.column == g.column))
                {
                    jumpGuesses.remove(i);
                    i = jumpGuesses.size();
                }
            }
            return g;
        }

        //Hunting Mode
        if(this.jumpGuesses.isEmpty()) {
            return null;
        }else{
            Random random = new Random();
            int index = random.nextInt(this.jumpGuesses.size()-1);
            return this.jumpGuesses.remove(index);
        }



    }

    @Override
    public void update (Guess guess, Answer answer){
        if(answer.isHit)
            tempWorld.updateCell(TempWorld.cellStatus.HIT, guess.row, guess.column);
        else
            tempWorld.updateCell(TempWorld.cellStatus.MISS, guess.row, guess.column);
    } // end of update()

    //JUMP!2!GUESS!
    private void enumerateGuesses(LinkedList<Guess> guesses){
        for(int row = 0;row<world.numRow;row++){
            for(int col = row%2;col<world.numColumn;col += 2) {
                Guess guess = new Guess();
                guess.row = row;
                guess.column = col;
                guesses.add(guess);

            }
        }
    }

    public Guess getGuess(World.Coordinate c){
        Guess g = new Guess();
        g.row = c.row;
        g.column = c.column;
        return g;
    }


    public World.Coordinate getCoorinate(Guess g){
        World.Coordinate c = world.new Coordinate();
        c.row = g.row;
        c.column = g.column;
        return c;
    }


    public boolean notContainedInGuessList(World.Coordinate coordinate, List<Guess> array){
        Guess coordinateGuess = getGuess(coordinate);
        for(Guess g: array){
            if(coordinateGuess.row == g.row && coordinateGuess.column == g.column)
                return false;
        }
        return true;
    }

    public boolean sameAs(World.Coordinate c, Guess guess){
        return c.row == guess.row && c.column == guess.column;
    }
}

// end of class GreedyGuessPlayer
