package pl.adsolve.ocean_warfare.event;

import lombok.EqualsAndHashCode;
import pl.adsolve.ocean_warfare.game.GameFireResult;

import static pl.adsolve.ocean_warfare.game.GameFireResult.Result.MISS;

@EqualsAndHashCode
public class ShipMissedEvent implements ShipEvent {

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public GameFireResult result() {
        return new GameFireResult(MISS);
    }

}
