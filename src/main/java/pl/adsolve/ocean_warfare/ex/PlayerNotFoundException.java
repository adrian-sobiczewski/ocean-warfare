package pl.adsolve.ocean_warfare.ex;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlayerNotFoundException extends GameException {

    private final String token;

    @Override
    public String getMessage() {
        return String.format("Player not found for token: %s", token);
    }
}
