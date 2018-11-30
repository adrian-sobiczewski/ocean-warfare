package pl.adsolve.ocean_warfare.rest.dto;


import lombok.Data;
import pl.adsolve.ocean_warfare.game.GameFireResult;
import pl.adsolve.ocean_warfare.ship.Ship;

@Data
public class FireResponseDTO {

    private GameFireResult.Result result;
    private Ship.Type shipType;
    private Boolean sunken;

    public static FireResponseDTO of(GameFireResult result) {
        FireResponseDTO dto = new FireResponseDTO();
        dto.setResult(result.getResult());
        dto.setShipType(result.getShipType());
        dto.setSunken(result.getSunken());
        return dto;
    }

}
