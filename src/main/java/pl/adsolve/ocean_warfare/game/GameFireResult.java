package pl.adsolve.ocean_warfare.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.adsolve.ocean_warfare.ship.Ship;

import static pl.adsolve.ocean_warfare.game.GameFireResult.Result.MISS;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameFireResult {

    private GameFireResult.Result result;
    private Ship.Type shipType;
    private Boolean sunken;

    public GameFireResult(Result result) {
        this.result = result;
    }

    public boolean isMissed() {
        return result.equals(MISS);
    }

    public enum Result {
        HIT, MISS
    }
}
