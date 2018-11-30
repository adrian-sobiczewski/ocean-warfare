package pl.adsolve.ocean_warfare.ex;

public class CoordinateOutOfBoardException extends GameException {

    @Override
    public String getMessage() {
        return "Coordinate out of board exception";
    }
}
