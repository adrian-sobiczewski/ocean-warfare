package pl.adsolve.ocean_warfare.board;

import org.junit.Test;
import pl.adsolve.ocean_warfare.event.ShipEvent;
import pl.adsolve.ocean_warfare.event.ShipHitEvent;
import pl.adsolve.ocean_warfare.event.ShipMissedEvent;
import pl.adsolve.ocean_warfare.event.ShipSunkenEvent;
import pl.adsolve.ocean_warfare.ex.BoardShipOutOfBoardException;
import pl.adsolve.ocean_warfare.ship.Ship;
import pl.adsolve.ocean_warfare.vo.Coordinate;

import java.util.Set;

import static org.fest.assertions.api.Assertions.assertThat;
import static pl.adsolve.ocean_warfare.board.Board.Direction.HORIZONTAL;
import static pl.adsolve.ocean_warfare.board.Board.Direction.VERTICAL;
import static pl.adsolve.ocean_warfare.ship.Ship.Type.*;
import static pl.adsolve.ocean_warfare.util.Letter.*;

public class BoardTest {

    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;

    @Test
    public void place_fourDecker_HORIZONTAL() {
        // given
        Board board = new Board(ONE, FOUR);
        Ship ship = new Ship(FOUR_DECKER);

        // when
        board.place(ship, Coordinate.of(A, ONE), HORIZONTAL);

        //then
        Set<Coordinate> coordinates = board.getCoordinates(ship);
        assertThat(coordinates).containsOnly(
                Coordinate.of(A, ONE),
                Coordinate.of(A, TWO),
                Coordinate.of(A, THREE),
                Coordinate.of(A, FOUR)
        );
    }

    @Test
    public void place_fourDecker_VERTICAL() {
        // given
        Board board = new Board(ONE, FOUR);
        Ship ship = new Ship(FOUR_DECKER);

        // when
        board.place(ship, Coordinate.of(A, ONE), VERTICAL);

        //then
        Set<Coordinate> coordinates = board.getCoordinates(ship);
        assertThat(coordinates).containsOnly(
                Coordinate.of(A, ONE),
                Coordinate.of(B, ONE),
                Coordinate.of(C, ONE),
                Coordinate.of(D, ONE)
        );
    }

    @Test(expected = BoardShipOutOfBoardException.class)
    public void place_fourDecker_HORIZONTAL_outOfBoard() {
        // given
        Board board = new Board(ONE, FOUR);

        // when
        board.place(new Ship(FOUR_DECKER), Coordinate.of(J, ONE), VERTICAL);

        //then
        Set<Coordinate> coordinates = board.getCoordinates(new Ship(FOUR_DECKER));
        assertThat(coordinates).containsOnly(
                Coordinate.of(A, ONE),
                Coordinate.of(B, ONE),
                Coordinate.of(C, ONE),
                Coordinate.of(D, ONE)
        );
    }

    @Test
    public void fire_missed() {
        // given
        Board board = new Board(ONE, TWO);
        board.place(new Ship(TWO_DECKER), Coordinate.of(A, ONE), HORIZONTAL);

        // when
        Set<ShipEvent> results = board.fire(Coordinate.of(B, TWO));

        //then
        assertThat(results).containsOnly(
                new ShipMissedEvent()
        );
    }

    @Test
    public void fire_hit() {
        // given
        Board board = new Board(ONE, TWO);
        board.place(new Ship(TWO_DECKER), Coordinate.of(A, ONE), HORIZONTAL);

        // when
        Set<ShipEvent> results = board.fire(Coordinate.of(A, ONE));

        //then
        assertThat(results).containsOnly(
                new ShipHitEvent(TWO_DECKER)
        );
    }

    @Test
    public void fire_hit_and_sunken() {
        // given
        Board board = new Board(ONE, ONE);
        board.place(new Ship(ONE_DECKER), Coordinate.of(A, ONE), HORIZONTAL);

        // when
        Set<ShipEvent> results = board.fire(Coordinate.of(A, ONE));

        //then
        assertThat(results).containsOnly(
                new ShipHitEvent(ONE_DECKER),
                new ShipSunkenEvent(ONE_DECKER)
        );
    }

    @Test
    public void fire1_hit_and_sunken_then_fire2_same_coordinate_missed() {
        // given
        Board board = new Board(ONE, ONE);
        board.place(new Ship(ONE_DECKER), Coordinate.of(A, ONE), HORIZONTAL);

        // when
        board.fire(Coordinate.of(A, ONE)); // hit
        Set<ShipEvent> results = board.fire(Coordinate.of(A, ONE));

        //then
        assertThat(results).containsOnly(
                new ShipMissedEvent()
        );
    }

    @Test
    public void score_hit() {
        // given
        Board board = new Board(ONE, ONE);
        board.place(new Ship(ONE_DECKER), Coordinate.of(A, ONE), HORIZONTAL);
        board.fire(Coordinate.of(A, ONE)); // hit

        // when
        int hitCount = board.hitCount();

        //then
        assertThat(hitCount)
                .isEqualTo(ONE);
    }

    @Test
    public void score_hit_miss_hit() {
        // given
        Board board = new Board(ONE, TWO);
        board.place(new Ship(TWO_DECKER), Coordinate.of(A, ONE), HORIZONTAL);
        board.fire(Coordinate.of(A, ONE)); // hit
        board.fire(Coordinate.of(A, ONE)); // miss
        board.fire(Coordinate.of(A, TWO)); // hit

        // when
        int hitCount = board.hitCount();

        //then
        assertThat(hitCount)
                .isEqualTo(TWO);
    }

    @Test
    public void score_hit_one_decker_hit_two_decker() {
        // given
        Board board = new Board(ONE, THREE);
        board.place(new Ship(ONE_DECKER), Coordinate.of(A, ONE), HORIZONTAL);
        board.place(new Ship(TWO_DECKER), Coordinate.of(B, ONE), HORIZONTAL);
        board.fire(Coordinate.of(A, ONE)); // hit one decker
        board.fire(Coordinate.of(B, ONE)); // hit two decker

        // when
        int hitCount = board.hitCount();

        //then
        assertThat(hitCount)
                .isEqualTo(TWO);
    }

    @Test
    public void allShipsSunken_oneDeckerHit() {
        // given
        Board board = new Board(ONE, ONE);
        board.place(new Ship(ONE_DECKER), Coordinate.of(A, ONE), HORIZONTAL);
        board.fire(Coordinate.of(A, ONE)); // hit one decker

        // when
        boolean allShipsSunken = board.allShipsSunken();

        //then
        assertThat(allShipsSunken)
                .isTrue();
    }

    @Test
    public void allShipsSunken_twoDeckerHit() {
        // given
        Board board = new Board(ONE, TWO);
        board.place(new Ship(TWO_DECKER), Coordinate.of(A, ONE), HORIZONTAL);
        board.fire(Coordinate.of(A, ONE)); // hit two decker

        // when
        boolean allShipsSunken = board.allShipsSunken();

        //then
        assertThat(allShipsSunken)
                .isFalse();
    }

    @Test
    public void allShipsSunken_twoDeckerHitHit() {
        // given
        Board board = new Board(ONE, TWO);
        board.place(new Ship(TWO_DECKER), Coordinate.of(A, ONE), HORIZONTAL);
        board.fire(Coordinate.of(A, ONE)); // hit two decker
        board.fire(Coordinate.of(A, TWO)); // hit two decker

        // when
        boolean allShipsSunken = board.allShipsSunken();

        //then
        assertThat(allShipsSunken)
                .isTrue();
    }
}