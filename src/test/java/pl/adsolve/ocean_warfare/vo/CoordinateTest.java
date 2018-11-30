package pl.adsolve.ocean_warfare.vo;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static pl.adsolve.ocean_warfare.util.Letter.A;


public class CoordinateTest {

    @Test
    public void of() {
        // given
        String value = "A5";

        // when
        Coordinate coordinate = Coordinate.of(value);

        //then
        assertThat(coordinate).isEqualTo(Coordinate.of(A, 5));
    }
}