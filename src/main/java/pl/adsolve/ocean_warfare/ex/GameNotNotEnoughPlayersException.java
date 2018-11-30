package pl.adsolve.ocean_warfare.ex;

public class GameNotNotEnoughPlayersException extends GameException {

    @Override
    public String getMessage() {
        return "Not enough players exception";
    }
}
