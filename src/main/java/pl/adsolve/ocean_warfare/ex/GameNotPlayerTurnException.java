package pl.adsolve.ocean_warfare.ex;

public class GameNotPlayerTurnException extends GameException {

    @Override
    public String getMessage() {
        return "Not your turn. Wait for opponent move";
    }
}
