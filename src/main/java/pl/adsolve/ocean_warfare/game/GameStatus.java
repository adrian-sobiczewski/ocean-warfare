package pl.adsolve.ocean_warfare.game;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GameStatus {

    private final Game.Status gameStatus;
    private final int yourScore;
    private final int opponentScore;

    public GameStatus(Game.Status gameStatus) {
        this.gameStatus = gameStatus;
        this.yourScore = 0;
        this.opponentScore = 0;
    }
}
