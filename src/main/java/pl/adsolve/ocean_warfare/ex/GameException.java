package pl.adsolve.ocean_warfare.ex;

public abstract class GameException extends RuntimeException {

    public GameException() {
        super();
    }

    public GameException(String message) {
        super(message);
    }
}
