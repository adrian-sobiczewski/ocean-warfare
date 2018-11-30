package pl.adsolve.ocean_warfare.ex;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LetterNotFoundException extends GameException {

    private final String value;

    @Override
    public String getMessage() {
        return String.format("There is no Letter found for value: %s", value);
    }
}
