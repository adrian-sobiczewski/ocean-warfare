package pl.adsolve.ocean_warfare.board;

import org.springframework.stereotype.Service;
import pl.adsolve.ocean_warfare.ship.Ship;
import pl.adsolve.ocean_warfare.vo.Coordinate;

import static pl.adsolve.ocean_warfare.board.Board.Direction.HORIZONTAL;
import static pl.adsolve.ocean_warfare.board.Board.Direction.VERTICAL;
import static pl.adsolve.ocean_warfare.ship.Ship.Type.*;
import static pl.adsolve.ocean_warfare.util.Letter.*;

@Service
public class BoardFactory {

    public Board createBoardA() {
        Board board = new Board(4, 10);
        board.place(new Ship(ONE_DECKER), Coordinate.of(A, 1), HORIZONTAL);
        board.place(new Ship(TWO_DECKER), Coordinate.of(D, 1), HORIZONTAL);
        board.place(new Ship(THREE_DECKER), Coordinate.of(G, 1), HORIZONTAL);
        board.place(new Ship(FOUR_DECKER), Coordinate.of(J, 1), HORIZONTAL);
        return board;
    }

    public Board createBoardB() {
        Board board = new Board(4, 10);
        board.place(new Ship(ONE_DECKER), Coordinate.of(J, 10), HORIZONTAL);
        board.place(new Ship(TWO_DECKER), Coordinate.of(G, 9), HORIZONTAL);
        board.place(new Ship(THREE_DECKER), Coordinate.of(D, 8), HORIZONTAL);
        board.place(new Ship(FOUR_DECKER), Coordinate.of(A, 1), VERTICAL);
        return board;
    }

}
