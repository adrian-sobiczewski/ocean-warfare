package pl.adsolve.ocean_warfare.board;

import org.junit.Before;
import org.junit.Test;

public class BoardFactoryTest {

    private BoardFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new BoardFactory();
    }

    @Test
    public void createBoardA() {
        // given

        // when
        factory.createBoardA();

        // then
        // no exception thrown
    }

    @Test
    public void createBoardB() {
        // given

        // when
        factory.createBoardB();

        // then
        // no exception thrown
    }
}