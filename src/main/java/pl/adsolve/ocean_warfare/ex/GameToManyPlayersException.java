package pl.adsolve.ocean_warfare.ex;

public class GameToManyPlayersException extends GameException {

    @Override
    public String getMessage() {
        return "To many players";
    }
}
