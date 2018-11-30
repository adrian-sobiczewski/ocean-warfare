package pl.adsolve.ocean_warfare.util;

import pl.adsolve.ocean_warfare.ex.LetterNotFoundException;

/**
 * One of Coordinate value
 */
public enum Letter {

    A("A"),
    B("B"),
    C("C"),
    D("D"),
    E("E"),
    F("F"),
    G("G"),
    H("H"),
    I("I"),
    J("J");

    String str;

    Letter(String str) {
        this.str = str;
    }

    public static Letter of(String value) {
        for (Letter letter : values())
            if (letter.str.equalsIgnoreCase(value)) return letter;
        throw new LetterNotFoundException(value);
    }

    public Letter next() {
        try {
            return Letter.values()[ordinal() + 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new LetterNotFoundException("");
        }
    }

}
