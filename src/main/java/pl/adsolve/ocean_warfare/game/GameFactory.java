package pl.adsolve.ocean_warfare.game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.adsolve.ocean_warfare.board.BoardFactory;
import pl.adsolve.ocean_warfare.player.Player;

@Service
@RequiredArgsConstructor
public class GameFactory {

    private final GameIdGenerator generator;
    private final BoardFactory boardFactory;

    public Game create(Player player) {
        Game game = new Game(generator.generateId());
        game.add(player, boardFactory.createBoardA());
        return game;
    }

}
