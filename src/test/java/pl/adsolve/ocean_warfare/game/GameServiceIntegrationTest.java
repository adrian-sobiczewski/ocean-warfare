package pl.adsolve.ocean_warfare.game;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.adsolve.ocean_warfare.IntegrationTest;
import pl.adsolve.ocean_warfare.player.Player;
import pl.adsolve.ocean_warfare.player.PlayerService;

import static org.fest.assertions.api.Assertions.assertThat;
import static pl.adsolve.ocean_warfare.game.Game.Status.*;
import static pl.adsolve.ocean_warfare.game.GameFireResult.Result.HIT;
import static pl.adsolve.ocean_warfare.game.GameFireResult.Result.MISS;
import static pl.adsolve.ocean_warfare.ship.Ship.Type.FOUR_DECKER;
import static pl.adsolve.ocean_warfare.ship.Ship.Type.ONE_DECKER;

public class GameServiceIntegrationTest extends IntegrationTest {

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerService playerService;

    @Test
    public void scenario() {

        // PlayerA creates game
        Player playerA = playerService.create();
        String playerAToken = playerA.getToken();
        Game game = gameService.create(playerA);
        String gameId = game.getId();

        // PlayerA checks status
        GameStatus s1 = gameService.status(gameId, playerAToken);
        assertThat(s1.getGameStatus()).isEqualTo(AWAITING_PLAYERS);
        assertThat(s1.getYourScore()).isEqualTo(0);
        assertThat(s1.getOpponentScore()).isEqualTo(0);

        // PlayerB joins the game
        Player playerB = playerService.create();
        String playerBToken = playerB.getToken();
        gameService.join(gameId, playerB);

        // PlayerB fires (hit one decker sunken turn)
        GameFireResult f1 = gameService.fire(gameId, playerBToken, "A1");
        assertThat(f1.getResult()).isEqualTo(HIT);
        assertThat(f1.getShipType()).isEqualTo(ONE_DECKER);
        assertThat(f1.getSunken()).isTrue();

        // PlayerB checks status
        GameStatus s2 = gameService.status(gameId, playerBToken);
        assertThat(s2.getGameStatus()).isEqualTo(YOUR_TURN);
        assertThat(s2.getYourScore()).isEqualTo(1);
        assertThat(s2.getOpponentScore()).isEqualTo(0);

        // PlayerA checks status
        GameStatus s3 = gameService.status(gameId, playerAToken);
        assertThat(s3.getGameStatus()).isEqualTo(WAITING_FOR_OPPONENT_MOVE);
        assertThat(s3.getYourScore()).isEqualTo(0);
        assertThat(s3.getOpponentScore()).isEqualTo(1);

        // PlayerB fires (miss)
        GameFireResult f2 = gameService.fire(gameId, playerBToken, "B1");
        assertThat(f2.getResult()).isEqualTo(MISS);
        assertThat(f2.getShipType()).isNull();
        assertThat(f2.getSunken()).isNull();

        // PlayerA fires (hit four decker not sunken his turn)
        GameFireResult f3 = gameService.fire(gameId, playerAToken, "D1");
        assertThat(f3.getResult()).isEqualTo(HIT);
        assertThat(f3.getShipType()).isEqualTo(FOUR_DECKER);
        assertThat(f3.getSunken()).isFalse();

        // War goes on ...
        gameService.fire(gameId, playerAToken, "A1");// A hit
        gameService.fire(gameId, playerAToken, "B1");// A hit
        gameService.fire(gameId, playerAToken, "C1");// A hit
        gameService.fire(gameId, playerAToken, "D8");// A hit
        gameService.fire(gameId, playerAToken, "D9");// A hit
        gameService.fire(gameId, playerAToken, "D10");// A hit
        gameService.fire(gameId, playerAToken, "G9"); // A hit
        gameService.fire(gameId, playerAToken, "G10"); // A hit
        gameService.fire(gameId, playerAToken, "J9");// A miss
        gameService.fire(gameId, playerBToken, "D1");// B hit
        gameService.fire(gameId, playerBToken, "D2");// B hit
        gameService.fire(gameId, playerBToken, "G1");// B hit
        gameService.fire(gameId, playerBToken, "G2");// B hit
        gameService.fire(gameId, playerBToken, "G3");// B hit
        gameService.fire(gameId, playerBToken, "J1");// B hit
        gameService.fire(gameId, playerBToken, "J2");// B hit
        gameService.fire(gameId, playerBToken, "J3");// B hit
        // PlayerB winning the game
        gameService.fire(gameId, playerBToken, "J4");// B hit

        // PlayerA checks status
        GameStatus s4 = gameService.status(gameId, playerAToken);
        assertThat(s4.getGameStatus()).isEqualTo(YOU_LOST);
        assertThat(s4.getYourScore()).isEqualTo(9);
        assertThat(s4.getOpponentScore()).isEqualTo(10);

        // PlayerB checks status
        GameStatus s5 = gameService.status(gameId, playerBToken);
        assertThat(s5.getGameStatus()).isEqualTo(YOU_WON);
        assertThat(s5.getYourScore()).isEqualTo(10);
        assertThat(s5.getOpponentScore()).isEqualTo(9);

    }

}