package pl.adsolve.ocean_warfare.event;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import pl.adsolve.ocean_warfare.game.GameFireResult;
import pl.adsolve.ocean_warfare.ship.Ship;

import static pl.adsolve.ocean_warfare.game.GameFireResult.Result.HIT;

@EqualsAndHashCode
@AllArgsConstructor
public class ShipHitEvent implements ShipEvent {

    private final Ship.Type type;

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public GameFireResult result() {
        return new GameFireResult(HIT, type, false);
    }

}
