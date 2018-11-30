package pl.adsolve.ocean_warfare.ex;

public class BoardShipOutOfBoardException extends GameException {

    @Override
    public String getMessage() {
        return "Ship out of board";
    }
}
