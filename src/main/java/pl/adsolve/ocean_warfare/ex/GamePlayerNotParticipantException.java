package pl.adsolve.ocean_warfare.ex;

public class GamePlayerNotParticipantException extends GameException {

    @Override
    public String getMessage() {
        return "Player is not game participant";
    }
}
