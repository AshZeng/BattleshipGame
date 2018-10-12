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

    private LinkedList<Guess> jumpGuesses;

    public World world;
    private final static int NUM_OF_SHIPS = 5;

    private int numRow;
    private int numColumn;

    private HashMap<World.Coordinate, Boolean> board;

    private List<WorldShip> ships;

    private TempWorld tempWorld;
    @Override
    public void initialisePlayer(World world) {
        // To be implemented.
        this.world = world;
        this.tempWorld = new TempWorld(world.numRow,world.numColumn,true);
        this.numRow = world.numRow;
        this.numColumn = world.numColumn;
        this.board = new HashMap();
        this.ships = new ArrayList<>(NUM_OF_SHIPS);

        jumpGuesses = new LinkedList<>();
        this.enumerateGuesses(jumpGuesses);




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

        if ( tempWorld.possibleCoordinate.size() > 0 )
        {
            Guess g = new Guess();
            World.Coordinate tempCoord = world.new Coordinate();
            tempCoord = tempWorld.possibleCoordinate.remove(0);

            g.row = tempCoord.row;
            g.column = tempCoord.column;

            /*
             *  Check if this possible target is in the checkBoardGuesses list
             *  This is to avoid duplicate guesses
             */
            Guess tempGuess = new Guess();
            for ( int i = 0; i < jumpGuesses.size(); i++ )
            {
                tempGuess = jumpGuesses.get(i);
                if ( ( tempGuess.row == g.row ) && ( tempGuess.column == g.column ) )
                {
                    jumpGuesses.remove(i);
                    i = jumpGuesses.size();	// TO EXIT LOOP
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
        //Target Mode


    }
    @Override
    public void update (Guess guess, Answer answer){
        if(answer.isHit)
            tempWorld.updateCell (TempWorld.cellStatus.HIT, guess.row, guess.column );
        else
            tempWorld.updateCell ( TempWorld.cellStatus.MISS, guess.row, guess.column );
    } // end of update()


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


} // end of class GreedyGuessPlayer
