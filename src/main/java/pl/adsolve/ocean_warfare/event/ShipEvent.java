package pl.adsolve.ocean_warfare.event;

import pl.adsolve.ocean_warfare.game.GameFireResult;

/**
 * Marker interface
 */
public interface ShipEvent {
    int priority();

    GameFireResult result();
}
