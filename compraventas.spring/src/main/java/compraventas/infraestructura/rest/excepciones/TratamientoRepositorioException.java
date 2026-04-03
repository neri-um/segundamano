package compraventas.infraestructura.rest.excepciones;

import compraventas.infraestructura.rest.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import repositorio.RepositorioException;

@ControllerAdvice
public class TratamientoRepositorioException {

    @ExceptionHandler(RepositorioException.class)
    public ResponseEntity<ErrorDTO> handle(RepositorioException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorDTO(e.getMessage()));
    }
}