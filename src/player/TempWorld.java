package player;

import ship.*;
import world.World;

import java.util.ArrayList;

public class TempWorld  {
    private World world;
    private boolean isPossible;


    public ArrayList<World.Coordinate> possibleCoordinate;
    public ArrayList<WorldShip> ships;
    public WorldShip airCar;
    public WorldShip cruiser;
    public WorldShip frigate;
    public WorldShip patCra;
    public WorldShip sub;

    public enum cellStatus{HIT,MISS,UNTEST,POSSIBLE};
    public cellStatus[][] statuses = null;
    public int numRow;
    public int numColumn;

    public TempWorld(int numRow,int numColumn,boolean isPossible){
        world = new World();
        possibleCoordinate = new ArrayList<>();
        statuses = new cellStatus[numRow][numColumn];
        this.numRow = numRow;
        this.numColumn = numColumn;
        this.isPossible = isPossible;

        for(int i = 0;i<numRow;i++){
            for(int j = 0;j<numColumn;j++){
                statuses[i][j] = cellStatus.UNTEST;
            }
        }
    }

    public void updateCell(cellStatus cellStatus, int column, int row){
        statuses[row][column] = cellStatus;
        if((cellStatus == TempWorld.cellStatus.HIT)&&(this.isPossible)){
            calculatePossibles(row, column );

        }
    }

    private void calculatePossibles(int row,int column){
        int temp;
        // Check Up
        temp = row + 1;
        if ( temp < numRow )
            checkCellUntested( temp, column );

        // Check Down
        temp = row - 1;
        if ( temp >= 0 )
            checkCellUntested( temp, column );

        // Check Right
        temp = column + 1;
        if ( temp < numColumn )
            checkCellUntested( row, temp );

        // Check Left
        temp = column - 1;
        if ( temp >= 0 )
            checkCellUntested( row, temp );
    }

    private void checkCellUntested( int row, int column )
    {
        // If the cell is untested, it needs to become a "possible"
        if ( statuses[row][column] == cellStatus.UNTEST)
        {
            statuses[row][column] = cellStatus.POSSIBLE;
            World.Coordinate tempCoord = world.new Coordinate();
            tempCoord.row = row;
            tempCoord.column = column;

            possibleCoordinate.add(tempCoord);
        }
        // If the cell is already marked as hit or miss or possible, then it is ignored
    }

    public void resetAllPossibleTargets(){
        for (int j = numRow - 1; j >= 0; j--){
            for (int i = 0; i < numColumn; i++){
                if(statuses[j][i] == cellStatus.POSSIBLE)
                    statuses[j][i] = cellStatus.UNTEST;
            }
        }
    }



}
