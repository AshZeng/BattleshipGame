package player;

import java.util.*;

import world.World;

/**
 * Random guess player (task A).
 * Please implement this class.
 *
 * @author Youhan Xia, Jeffrey Chan
 */
public class RandomGuessPlayer implements Player{

    private LinkedList<Guess> allRandomGuesses;
    public World world;
    private final static int NUM_OF_SHIPS = 5;

    private int numRow;
    private int numColumn;

    private HashMap<World.Coordinate, Boolean> board;

    private List<WorldShip> ships;

    @Override
    public void initialisePlayer(World world) {
        // To be implemented.
        this.world = world;
        this.numRow = world.numRow;
        this.numColumn = world.numColumn;
        this.board = new HashMap();
        this.ships = new ArrayList<>(NUM_OF_SHIPS);
        allRandomGuesses = new LinkedList<>();
        this.enumerateGuess(allRandomGuesses);



        //Initialize the board
        this.setWorld();
        this.setShips();

    } // end of initialisePlayer

    private void setWorld(){
        World.Coordinate wc;
        for(int i = 0; i < numRow; i++) {
            for(int j = 0; j < numColumn; j++) {
                wc = new World().new Coordinate();
                wc.column = j;
                wc.row = i;
                //this.availableGuess.add(g);
                board.put(wc, Boolean.FALSE);
            }
        }
    }

    private void setShips(){
        for(World.ShipLocation s : world.shipLocations) {
            ships.add(new WorldShip(s.ship, s.coordinates));
        }
    }

    @Override
    public Answer getAnswer(Guess guess) {
        // To be implemented.
        Answer answer = new Answer();
        World.Coordinate c = new World().new Coordinate();
        c.column = guess.column;
        c.row = guess.row;

        for(WorldShip ws : this.ships) {
            for(World.Coordinate wc : ws.shipLocation.keySet())
            {
                if(c.row == wc.row && c.column == wc.column) {
                    answer.isHit = true;
                    ws.shipLocation.put(wc, Boolean.TRUE);
                    if(ws.isSunk()) {
                        answer.shipSunk = ws.getShip();
                    }
                    break;
                }
            }
        }

        WorldShip delete = null;
        if(answer.shipSunk != null) {
            for(WorldShip ws : this.ships) {
                if(ws.getShip().name().equals(answer.shipSunk.name())) delete = ws;
            }
            if(delete != null)
                this.ships.remove(delete);
        }
        return  answer;
    } // end of getAnswer()

    @Override
    public boolean noRemainingShips() {

        return this.ships.isEmpty();
    } // end of noRemainingShips()

    @Override
    public Guess makeGuess() {
        // To be implemented.

        if(this.allRandomGuesses.isEmpty()) {
            return null;
        }else{
            Random random = new Random();
            int index = random.nextInt(this.allRandomGuesses.size()-1);
            return this.allRandomGuesses.remove(index);
        }
    } // end of makeGuess()

    @Override
    public void update(Guess guess, Answer answer) {
    }

    private void enumerateGuess(LinkedList<Guess> guesses){
        for(int row = 0;row<world.numRow;row++){
            for(int col = 0;col<world.numColumn;col++){
                Guess guess = new Guess();
                guess.row = row;
                guess.column = col;
                guesses.add(guess);
            }
        }
    }
} // end of class RandomGuessPlayer
