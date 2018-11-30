package pl.adsolve.ocean_warfare.ship;

import com.google.common.collect.Sets;
import pl.adsolve.ocean_warfare.event.ShipEvent;
import pl.adsolve.ocean_warfare.event.ShipHitEvent;
import pl.adsolve.ocean_warfare.event.ShipSunkenEvent;

import java.util.Set;

public class Ship {

    private final Ship.Type type;
    private int mastCount;

    public Ship(Type type) {
        this.type = type;
        this.mastCount = type.masts();
    }

    public Set<ShipEvent> hit() {
        Set<ShipEvent> events = Sets.newHashSet(new ShipHitEvent(type));
        mastCount--;
        if (isSunken()) events.add(new ShipSunkenEvent(type));
        return events;
    }

    public int mastCountInitial() {
        return type.masts();
    }

    public int mastCountHit() {
        return mastCountInitial() - mastCount;
    }

    private boolean isSunken() {
        return mastCount == 0;
    }

    public enum Type {
        ONE_DECKER,
        TWO_DECKER,
        THREE_DECKER,
        FOUR_DECKER;

        public int masts() {
            return ordinal() + 1;
        }
    }

}
