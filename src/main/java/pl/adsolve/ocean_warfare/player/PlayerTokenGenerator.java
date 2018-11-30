package pl.adsolve.ocean_warfare.player;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PlayerTokenGenerator {

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

}
