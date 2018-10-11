package player;

import ship.Ship;
import world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class WorldShip{
    private Ship ship;
    HashMap<World.Coordinate, Boolean> shipLocation;

    public WorldShip(Ship ship, List<World.Coordinate> location) {
        this.ship = ship;
        shipLocation = new HashMap();

        for(World.Coordinate c : location) {
            this.shipLocation.put(c, Boolean.FALSE);
        }
    }

    public Ship getShip() {
        return ship;
    }

    public void Hit(World.Coordinate wc) {
        if(this.shipLocation.containsKey(wc)) {
            this.shipLocation.put(wc, Boolean.TRUE);
        }
    }

    public boolean isSunk() {
        boolean isSunk = true;
        for(World.Coordinate wc : shipLocation.keySet()) {
            if(!shipLocation.get(wc).booleanValue()) {
                isSunk = false;
            }
        }
        return isSunk;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || o.getClass() != this.getClass()) return false;

        WorldShip ws = (WorldShip) o;
        return this.ship == ws.ship;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ship);
    }
}