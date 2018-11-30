package pl.adsolve.ocean_warfare.game;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.adsolve.ocean_warfare.board.BoardFactory;
import pl.adsolve.ocean_warfare.ex.GameNotFoundException;
import pl.adsolve.ocean_warfare.player.Player;
import pl.adsolve.ocean_warfare.player.PlayerService;
import pl.adsolve.ocean_warfare.vo.Coordinate;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final GameFactory gameFactory;
    private final PlayerService playerService;
    private final BoardFactory boardFactory;


    public Game create(Player player) {
        Game game = gameFactory.create(player);
        save(game);
        return game;
    }

    public GameStatus join(String gameId, Player player) {
        Game game = get(gameId);
        game.add(player, boardFactory.createBoardB());
        log.info("GAME[{}] - Player [{}] joins game {}", gameId, player.getToken(), gameId);
        return game.status(player);
    }

    public GameStatus status(String gameId, String playerToken) {
        Game game = get(gameId);
        Player player = playerService.get(playerToken);
        GameStatus status = game.status(player);
        log.info("GAME[{}] - Player [{}] checks status: {}", gameId, player.getToken(), status);
        return status;
    }

    public GameFireResult fire(String gameId, String playerToken, String coordinate) {
        Game game = get(gameId);
        Player player = playerService.get(playerToken);
        GameFireResult result = game.fire(player, Coordinate.of(coordinate));
        log.info("GAME[{}] - Player [{}] fires at: {} with result: ", gameId, player.getToken(), coordinate, result);
        return result;
    }

    public void save(Game game) {
        gameRepository.save(game);
    }

    public Game get(String id) {
        return gameRepository.get(id)
                .orElseThrow(() -> new GameNotFoundException(id));
    }

}
