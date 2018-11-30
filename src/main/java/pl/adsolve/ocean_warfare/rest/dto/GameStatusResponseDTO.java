package pl.adsolve.ocean_warfare.rest.dto;

import lombok.Data;
import pl.adsolve.ocean_warfare.game.Game;
import pl.adsolve.ocean_warfare.game.GameStatus;

@Data
public class GameStatusResponseDTO {

    private Game.Status gameStatus;
    private Integer yourScore;
    private Integer opponentScore;

    public static GameStatusResponseDTO of(GameStatus status) {
        GameStatusResponseDTO dto = new GameStatusResponseDTO();
        dto.setGameStatus(status.getGameStatus());
        dto.setYourScore(status.getYourScore());
        dto.setOpponentScore(status.getOpponentScore());
        return dto;
    }

}
