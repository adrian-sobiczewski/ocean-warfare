package pl.adsolve.ocean_warfare.game;

import pl.adsolve.ocean_warfare.board.Board;
import pl.adsolve.ocean_warfare.event.ShipEvent;
import pl.adsolve.ocean_warfare.ex.GameNotNotEnoughPlayersException;
import pl.adsolve.ocean_warfare.ex.GameNotPlayerTurnException;
import pl.adsolve.ocean_warfare.ex.GamePlayerNotParticipantException;
import pl.adsolve.ocean_warfare.ex.GameToManyPlayersException;
import pl.adsolve.ocean_warfare.player.Player;
import pl.adsolve.ocean_warfare.vo.Coordinate;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;

import static pl.adsolve.ocean_warfare.game.Game.Status.*;

public class Game {

    private static final BinaryOperator<ShipEvent> BY_PRIORITY = (e1, e2) -> e1.priority() > e2.priority() ? e1 : e2;
    private static final int NUMBER_OF_PLAYERS = 2;

    private final String id;
    private final Map<Player, Board> boards;
    private Player turn;

    public Game(String id) {
        this.id = id;
        this.boards = new LinkedHashMap<>(2);
    }

    public void add(Player player, Board board) {
        if (boards.size() >= NUMBER_OF_PLAYERS) throw new GameToManyPlayersException();
        boards.put(player, board);
        turn = player;
    }

    public GameStatus status(Player player) {
        if (awaitingPlayers())
            return new GameStatus(AWAITING_PLAYERS);
        Board boardPlayer = board(player);
        Board boardOpponent = board(opponent(player));
        if (boardOpponent.allShipsSunken())
            return new GameStatus(YOU_WON, boardOpponent.hitCount(), boardPlayer.hitCount());
        if (boardPlayer.allShipsSunken())
            return new GameStatus(YOU_LOST, boardOpponent.hitCount(), boardPlayer.hitCount());
        if (isTurn(player))
            return new GameStatus(YOUR_TURN, boardOpponent.hitCount(), boardPlayer.hitCount());
        else
            return new GameStatus(WAITING_FOR_OPPONENT_MOVE, boardOpponent.hitCount(), boardPlayer.hitCount());
    }

    public GameFireResult fire(Player player, Coordinate coordinate) {
        if (awaitingPlayers()) throw new GameNotNotEnoughPlayersException();
        if (!isParticipant(player)) throw new GamePlayerNotParticipantException();
        if (!isTurn(player)) throw new GameNotPlayerTurnException();
        Set<ShipEvent> events = board(opponent(player)).fire(coordinate);
        return events.stream()
                .reduce(BY_PRIORITY)
                .map(event -> {
                    GameFireResult result = event.result();
                    if (result.isMissed()) turn = opponent(player);
                    return result;
                })
                .orElseThrow(IllegalStateException::new);
    }

    public String getId() {
        return id;
    }

    public boolean isParticipant(Player player) {
        return boards.keySet().contains(player);
    }

    public boolean isTurn(Player player) {
        return turn.equals(player);
    }

    private boolean awaitingPlayers() {
        return boards.size() != NUMBER_OF_PLAYERS;
    }

    private Board board(Player player) {
        return boards.get(player);
    }

    private Player opponent(Player player) {
        return boards.keySet().stream()
                .filter(p -> !p.equals(player))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public enum Status {
        AWAITING_PLAYERS,
        YOUR_TURN,
        WAITING_FOR_OPPONENT_MOVE,
        YOU_WON,
        YOU_LOST
    }
}
