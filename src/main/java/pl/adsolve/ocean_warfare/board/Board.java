package pl.adsolve.ocean_warfare.board;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import pl.adsolve.ocean_warfare.event.ShipEvent;
import pl.adsolve.ocean_warfare.event.ShipMissedEvent;
import pl.adsolve.ocean_warfare.ex.BoardShipOutOfBoardException;
import pl.adsolve.ocean_warfare.ex.CoordinateOutOfBoardException;
import pl.adsolve.ocean_warfare.ship.Ship;
import pl.adsolve.ocean_warfare.vo.Coordinate;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {

    private static final Set<Coordinate> EMPTY_COORDINATES = Sets.newHashSet(Coordinate.EMPTY);

    private final Multimap<Ship, Coordinate> ships;
    private final int numOfCoordinates;

    public Board(int numOfShips, int numOfCoordinates) {
        this.ships = HashMultimap.create(numOfShips, numOfCoordinates);
        this.numOfCoordinates = numOfCoordinates;
    }

    public static Coordinate.Axis axis(Direction direction) {
        return direction == Direction.HORIZONTAL ? Coordinate.Axis.X : Coordinate.Axis.Y;
    }

    public void place(Ship ship, Coordinate coordinate, Direction direction) {
        try {
            ships.putAll(ship, coordinates(ship.mastCountInitial(), new HashSet<>(), coordinate, direction));
        } catch (CoordinateOutOfBoardException e) {
            throw new BoardShipOutOfBoardException();
        }
    }

    public Set<ShipEvent> fire(Coordinate coordinate) {
        if (missed(coordinate)) return Sets.newHashSet(new ShipMissedEvent());
        return ships.entries().stream()
                .filter(e -> e.getValue().equals(coordinate))
                .findAny()
                .map(e -> {
                    Ship ship = e.getKey();
                    if (shipLastCoordinate(ship))
                        ships.replaceValues(ship, EMPTY_COORDINATES);
                    else
                        ships.remove(ship, e.getValue());
                    return ship.hit();
                }).orElseThrow(IllegalStateException::new);
    }

    public int hitCount() {
        return ships.entries().stream()
                .map(Map.Entry::getKey)
                .mapToInt(Ship::mastCountHit)
                .sum();
    }

    public boolean allShipsSunken() {
        return hitCount() == numOfCoordinates;
    }

    public Set<Coordinate> getCoordinates(Ship ship) {
        return new HashSet<>(ships.get(ship));
    }

    private boolean shipLastCoordinate(Ship ship) {
        return ships.get(ship).size() == 1;
    }

    private boolean missed(Coordinate coordinate) {
        return !ships.containsValue(coordinate);
    }

    private Set<Coordinate> coordinates(int acc, Set<Coordinate> coordinates, Coordinate coordinate, Direction direction) {
        coordinates.add(coordinate);
        return acc == 1 ? coordinates : coordinates(acc - 1, coordinates, coordinate.next(axis(direction)), direction);
    }

    public enum Direction {
        HORIZONTAL,
        VERTICAL
    }
}
