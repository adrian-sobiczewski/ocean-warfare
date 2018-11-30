package pl.adsolve.ocean_warfare.game;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameIdGenerator {

    public String generateId() {
        return UUID.randomUUID().toString();
    }

}
