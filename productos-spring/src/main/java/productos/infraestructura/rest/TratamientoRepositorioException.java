package productos.infraestructura.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import productos.infraestructura.persistencia.base.RepositorioException;

@ControllerAdvice
public class TratamientoRepositorioException {

    @ExceptionHandler(RepositorioException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RespuestaError handleRepositorioException(RepositorioException e) {
        return new RespuestaError("Internal Server Error", e.getMessage());
    }

    public static class RespuestaError {
        private String estado;
        private String mensaje;
        public RespuestaError(String estado, String mensaje) {
            this.estado = estado;
            this.mensaje = mensaje;
        }
        public String getEstado() { return estado; }
        public String getMensaje() { return mensaje; }
    }
}