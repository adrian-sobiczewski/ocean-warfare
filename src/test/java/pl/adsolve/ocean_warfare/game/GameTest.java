package pl.adsolve.ocean_warfare.game;

import org.junit.Before;
import org.junit.Test;
import pl.adsolve.ocean_warfare.board.Board;
import pl.adsolve.ocean_warfare.ex.GameNotNotEnoughPlayersException;
import pl.adsolve.ocean_warfare.ex.GameNotPlayerTurnException;
import pl.adsolve.ocean_warfare.ex.GameToManyPlayersException;
import pl.adsolve.ocean_warfare.player.Player;
import pl.adsolve.ocean_warfare.ship.Ship;
import pl.adsolve.ocean_warfare.vo.Coordinate;

import static org.fest.assertions.api.Assertions.assertThat;
import static pl.adsolve.ocean_warfare.board.Board.Direction.HORIZONTAL;
import static pl.adsolve.ocean_warfare.game.Game.Status.*;
import static pl.adsolve.ocean_warfare.game.GameFireResult.Result.HIT;
import static pl.adsolve.ocean_warfare.ship.Ship.Type.TWO_DECKER;
import static pl.adsolve.ocean_warfare.util.Letter.*;

public class GameTest {

    private static final String ID = "ID";
    private static final String TOKEN_1 = "TOKEN1";
    private static final String TOKEN_2 = "TOKEN2";
    private static final String TOKEN_3 = "TOKEN3";

    private Game game;
    private Player playerA;
    private Player playerB;
    private Board boardA;
    private Board boardB;

    @Before
    public void setUp() throws Exception {
        game = new Game(ID);
        playerA = new Player(TOKEN_1);
        playerB = new Player(TOKEN_2);
        boardA = boardA();
        boardB = boardB();
    }

    @Test
    public void add_addedPlayerIsParticipant() {
        // given

        // when
        game.add(playerA, null);

        //then
        assertThat(game.isParticipant(playerA))
                .isTrue();
    }

    @Test
    public void add_addedPlayersAreParticipants() {
        // given

        // when
        game.add(playerA, null);
        game.add(playerB, null);

        //then
        assertThat(game.isParticipant(playerA))
                .isTrue();
        assertThat(game.isParticipant(playerB))
                .isTrue();
    }

    @Test(expected = GameToManyPlayersException.class)
    public void add_addingThirdPlayerThrowsException() {
        // given
        Player player3 = new Player(TOKEN_3);

        // when
        game.add(playerA, null);
        game.add(playerB, null);
        game.add(player3, null);

        // then
        // exception thrown
    }

    @Test
    public void add_lastAddedPlayerTurn() {
        // given
        game.add(playerA, null);
        game.add(playerB, null);

        // when

        // then
        assertThat(game.isTurn(playerA))
                .isFalse();
        assertThat(game.isTurn(playerB))
                .isTrue();

    }

    @Test(expected = GameNotNotEnoughPlayersException.class)
    public void fire_gameParticipatedByOnePlayerThrowsException() {
        // given
        game.add(playerA, null);

        // when
        game.fire(playerA, Coordinate.EMPTY);

        // then
        // exception thrown
    }

    @Test(expected = GameNotPlayerTurnException.class)
    public void fire_notPlayerTurnThrowsException() {
        // given
        game.add(playerA, null);
        game.add(playerB, null);

        // when
        game.fire(playerA, Coordinate.EMPTY);

        // then
        // exception thrown
    }

    @Test
    public void fire_playerB_MissedTurnChanged() {
        // given
        game.add(playerA, boardA);
        game.add(playerB, boardA);

        // when
        game.fire(playerB, Coordinate.of(C, 1)); // miss

        // then
        assertThat(game.isTurn(playerA))
                .isTrue();
        assertThat(game.isTurn(playerB))
                .isFalse();
    }

    @Test
    public void fire_playerB_HitHisTurn() {
        // given
        game.add(playerA, boardA);
        game.add(playerB, boardA);

        // when
        game.fire(playerB, Coordinate.of(A, 1)); // hit

        // then
        assertThat(game.isTurn(playerB))
                .isTrue();
        assertThat(game.isTurn(playerA))
                .isFalse();
    }

    @Test
    public void fire_playerB_Hit_HIT_SUNKEN_FALSE() {
        // given
        game.add(playerA, boardA);
        game.add(playerB, boardA);

        // when
        GameFireResult fire = game.fire(playerB, Coordinate.of(A, 1));// hit

        // then
        assertThat(fire.getResult()).isEqualTo(HIT);
        assertThat(fire.getShipType()).isEqualTo(TWO_DECKER);
        assertThat(fire.getSunken()).isFalse();
    }

    @Test
    public void fire_playerB_HitTwice_HIT_SUNKEN_TRUE() {
        // given
        game.add(playerA, boardA);
        game.add(playerB, boardA);

        // when
        game.fire(playerB, Coordinate.of(A, 1));// hit
        GameFireResult fire2 = game.fire(playerB, Coordinate.of(A, 2));// hit

        // then
        assertThat(fire2.getResult()).isEqualTo(HIT);
        assertThat(fire2.getShipType()).isEqualTo(TWO_DECKER);
        assertThat(fire2.getSunken()).isTrue();
    }

    @Test
    public void status_onlyPlayerA_PlayerAStatus_AWAITING_PLAYERS() {
        // given
        game.add(playerA, boardA);

        // when
        GameStatus status = game.status(playerA);

        //then
        assertThat(status.getGameStatus()).isEqualTo(AWAITING_PLAYERS);
        assertThat(status.getYourScore()).isEqualTo(0);
        assertThat(status.getOpponentScore()).isEqualTo(0);
    }

    @Test
    public void status_onlyPlayerA_PlayerBStatus_AWAITING_PLAYERS() {
        // given
        game.add(playerA, boardA);

        // when
        GameStatus status = game.status(playerA);

        //then
        assertThat(status.getGameStatus()).isEqualTo(AWAITING_PLAYERS);
        assertThat(status.getYourScore()).isEqualTo(0);
        assertThat(status.getOpponentScore()).isEqualTo(0);
    }

    @Test
    public void status_gameStartPlayerA_WAITING_FOR_OPPONENT_MOVE() {
        // given
        game.add(playerA, boardA);
        game.add(playerB, boardB);

        // when
        GameStatus status = game.status(playerA);

        //then
        assertThat(status.getGameStatus()).isEqualTo(WAITING_FOR_OPPONENT_MOVE);
        assertThat(status.getYourScore()).isEqualTo(0);
        assertThat(status.getOpponentScore()).isEqualTo(0);
    }

    @Test
    public void status_gameStartPlayerB_YOUR_TURN() {
        // given
        game.add(playerA, boardA);
        game.add(playerB, boardB);

        // when
        GameStatus status = game.status(playerB);

        //then
        assertThat(status.getGameStatus()).isEqualTo(YOUR_TURN);
        assertThat(status.getYourScore()).isEqualTo(0);
        assertThat(status.getOpponentScore()).isEqualTo(0);
    }

    @Test
    public void status_PlayerA_WIN_PlayerA_YOU_WON() {
        // given
        game.add(playerA, boardA);
        game.add(playerB, boardB);

        game.fire(playerB, Coordinate.of(B, 1)); // B miss
        game.fire(playerA, Coordinate.of(A, 1)); // A hit
        game.fire(playerA, Coordinate.of(A, 2)); // A hit

        // when
        GameStatus status = game.status(playerA);

        //then
        assertThat(status.getGameStatus()).isEqualTo(YOU_WON);
        assertThat(status.getYourScore()).isEqualTo(2);
        assertThat(status.getOpponentScore()).isEqualTo(0);
    }

    @Test
    public void status_PlayerA_WIN_PlayerB_YOU_LOST() {
        // given
        game.add(playerA, boardA);
        game.add(playerB, boardB);

        game.fire(playerB, Coordinate.of(B, 1)); // B miss
        game.fire(playerA, Coordinate.of(A, 1)); // A hit
        game.fire(playerA, Coordinate.of(A, 2)); // A hit

        // when
        GameStatus status = game.status(playerB);

        //then
        assertThat(status.getGameStatus()).isEqualTo(YOU_LOST);
        assertThat(status.getYourScore()).isEqualTo(0);
        assertThat(status.getOpponentScore()).isEqualTo(2);
    }

    @Test
    public void status_PlayerB_WIN_PlayerA_YOU_LOST() {
        // given
        game.add(playerA, boardA);
        game.add(playerB, boardB);

        game.fire(playerB, Coordinate.of(A, 1)); // B hit
        game.fire(playerB, Coordinate.of(A, 2)); // B hit

        // when
        GameStatus status = game.status(playerA);

        //then
        assertThat(status.getGameStatus()).isEqualTo(YOU_LOST);
        assertThat(status.getYourScore()).isEqualTo(0);
        assertThat(status.getOpponentScore()).isEqualTo(2);
    }

    @Test
    public void status_PlayerB_WIN_PlayerB_YOU_WON() {
        // given
        game.add(playerA, boardA);
        game.add(playerB, boardB);

        game.fire(playerB, Coordinate.of(A, 1)); // B hit
        game.fire(playerB, Coordinate.of(A, 2)); // B hit

        // when
        GameStatus status = game.status(playerB);

        //then
        assertThat(status.getGameStatus()).isEqualTo(YOU_WON);
        assertThat(status.getYourScore()).isEqualTo(2);
        assertThat(status.getOpponentScore()).isEqualTo(0);
    }


    private Board boardA() {
        Board board = new Board(1, 2);
        board.place(new Ship(TWO_DECKER), Coordinate.of(A, 1), HORIZONTAL);
        return board;
    }

    private Board boardB() {
        Board board = new Board(1, 2);
        board.place(new Ship(TWO_DECKER), Coordinate.of(A, 1), HORIZONTAL);
        return board;
    }

}