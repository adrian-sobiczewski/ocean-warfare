package pl.adsolve.ocean_warfare.vo;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.adsolve.ocean_warfare.ex.CoordinateNotValidException;
import pl.adsolve.ocean_warfare.ex.CoordinateOutOfBoardException;
import pl.adsolve.ocean_warfare.util.Letter;

import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static pl.adsolve.ocean_warfare.util.Letter.A;

@ToString
@EqualsAndHashCode
public class Coordinate {

    public static Coordinate EMPTY = new Coordinate(A, -1);

    private static final Pattern PATTERN = Pattern.compile("[A-J][1-9]|[A-J]10");
    private static final Letter MAX_Y = Letter.J;
    private static final int MAX_X = 10;

    private final Letter y;
    private final int x;

    private Coordinate(Letter y, int x) {
        this.y = y;
        this.x = x;
    }

    public static Coordinate of(Letter y, int x) {
        checkNotNull(y, "Letter cannot be null");
        checkArgument(x >= 1 && x <= 10, "X should be >= 1 and <= 10");
        return new Coordinate(y, x);
    }

    public static Coordinate of(String value) {
        checkArgument(value != null && !value.isEmpty(), "Coordinate value cannot be null or empty");
        checkArgument(PATTERN.matcher(value).matches(), "Coordinate value must match pattern {A-J}{0-10} ie. A5");
        return new Coordinate(Letter.of(value.substring(0, 1)), Integer.valueOf(value.substring(1)));
    }

    public Coordinate next(Axis axis) {
        switch (axis) {
            case Y:
                if (y == MAX_Y) throw new CoordinateOutOfBoardException();
                return new Coordinate(y.next(), x);
            case X:
                if (x == MAX_X) throw new CoordinateOutOfBoardException();
                return new Coordinate(y, x + 1);
            default:
                throw new CoordinateNotValidException();
        }

    }

    public enum Axis {
        Y, X
    }


}
