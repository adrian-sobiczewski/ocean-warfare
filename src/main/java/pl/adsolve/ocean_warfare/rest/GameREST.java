package pl.adsolve.ocean_warfare.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import pl.adsolve.ocean_warfare.game.Game;
import pl.adsolve.ocean_warfare.game.GameFireResult;
import pl.adsolve.ocean_warfare.game.GameService;
import pl.adsolve.ocean_warfare.game.GameStatus;
import pl.adsolve.ocean_warfare.player.Player;
import pl.adsolve.ocean_warfare.player.PlayerService;
import pl.adsolve.ocean_warfare.rest.dto.FireRequestDTO;
import pl.adsolve.ocean_warfare.rest.dto.FireResponseDTO;
import pl.adsolve.ocean_warfare.rest.dto.GameCreateResponseDTO;
import pl.adsolve.ocean_warfare.rest.dto.GameStatusResponseDTO;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("game")
@RequiredArgsConstructor
public class GameREST {

    private static final String URL_FORMAT = "http://localhost:%s/game/%s/join";
    private static final String H_SET_AUTH_TOKEN = "Set-Auth-Token";
    private static final String H_AUTH_TOKEN = "Auth-Token";
    private static final String ID = "id";

    private final Environment env;
    private final GameService gameService;
    private final PlayerService playerService;

    @PostMapping(value = "")
    public GameCreateResponseDTO create(
            HttpServletResponse res
    ) {
        Player player = playerService.create();
        Game game = gameService.create(player);
        res.setHeader(H_SET_AUTH_TOKEN, player.getToken());
        return new GameCreateResponseDTO(url(game.getId()));
    }

    @PostMapping(value = "{id}/join")
    public GameStatusResponseDTO join(
            @PathVariable(ID) String gameId,
            HttpServletResponse res
    ) {
        Player player = playerService.create();
        GameStatus status = gameService.join(gameId, player);
        res.setHeader(H_SET_AUTH_TOKEN, player.getToken());
        return GameStatusResponseDTO.of(status);
    }

    @PutMapping(value = "{id}")
    public FireResponseDTO fire(
            @RequestHeader(H_AUTH_TOKEN) String playerToken,
            @PathVariable(ID) String gameId,
            @RequestBody FireRequestDTO fire
    ) {
        GameFireResult result = gameService.fire(gameId, playerToken, fire.getPosition());
        return FireResponseDTO.of(result);
    }

    @GetMapping(value = "{id}")
    public GameStatusResponseDTO status(
            @RequestHeader(H_AUTH_TOKEN) String playerToken,
            @PathVariable(ID) String gameId
    ) {
        GameStatus status = gameService.status(gameId, playerToken);
        return GameStatusResponseDTO.of(status);
    }

    private String url(String gameId) {
        return String.format(URL_FORMAT,
                env.getProperty("local.server.port"),
                gameId
        );
    }

}
