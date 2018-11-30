package pl.adsolve.ocean_warfare.ex;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GameNotFoundException extends GameException {

    private final String id;

    @Override
    public String getMessage() {
        return String.format("Game not found for id: %s", id);
    }

}
