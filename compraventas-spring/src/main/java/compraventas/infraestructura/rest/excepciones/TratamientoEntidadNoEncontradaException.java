package compraventas.infraestructura.rest.excepciones;

import compraventas.infraestructura.rest.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import repositorio.EntidadNoEncontrada;

@ControllerAdvice
public class TratamientoEntidadNoEncontradaException {

    @ExceptionHandler(EntidadNoEncontrada.class)
    public ResponseEntity<ErrorDTO> handle(EntidadNoEncontrada e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorDTO(e.getMessage()));
    }
}