package pl.adsolve.ocean_warfare.player;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerFactory {

    private final PlayerTokenGenerator generator;

    public Player create() {
        return new Player(generator.generateToken());
    }
}
