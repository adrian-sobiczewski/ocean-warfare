package pl.adsolve.ocean_warfare.player;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerRepository {

    private final Map<String, Player> players;


    public void save(Player player) {
        players.put(player.getToken(), player);
    }

    public Optional<Player> get(String token) {
        return Optional.ofNullable(players.get(token));
    }

}
