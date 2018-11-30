package pl.adsolve.ocean_warfare.ex;

public class CoordinateNotValidException extends GameException {

    @Override
    public String getMessage() {
        return "Coordinate not valie";
    }
}
