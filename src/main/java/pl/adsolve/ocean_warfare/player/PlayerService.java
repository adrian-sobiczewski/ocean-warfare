package pl.adsolve.ocean_warfare.player;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.adsolve.ocean_warfare.ex.PlayerNotFoundException;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository repository;
    private final PlayerFactory factory;

    public Player get(String token) {
        return repository.get(token)
                .orElseThrow(() -> new PlayerNotFoundException(token));
    }

    public Player create() {
        Player player = factory.create();
        repository.save(player);
        return player;
    }

}
