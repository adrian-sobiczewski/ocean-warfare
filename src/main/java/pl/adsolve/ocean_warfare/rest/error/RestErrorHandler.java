package pl.adsolve.ocean_warfare.rest.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.adsolve.ocean_warfare.ex.GameException;
import pl.adsolve.ocean_warfare.rest.dto.ErrorResponseDTO;

@Slf4j
@ControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = GameException.class)
    public ErrorResponseDTO handleError(RuntimeException ex) {
        log.error(ex.getMessage());
        return new ErrorResponseDTO(ex.getMessage());
    }

}
