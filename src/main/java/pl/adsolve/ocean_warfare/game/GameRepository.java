package pl.adsolve.ocean_warfare.game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameRepository {

    private final Map<String, Game> games;

    public void save(Game game) {
        games.put(game.getId(), game);
    }

    public Optional<Game> get(String id) {
        return Optional.ofNullable(games.get(id));
    }


}
