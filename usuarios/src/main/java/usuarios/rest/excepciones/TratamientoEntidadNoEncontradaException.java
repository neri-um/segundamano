package usuarios.rest.excepciones;

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import repositorio.EntidadNoEncontrada;
import usuarios.rest.dto.ErrorDTO;

@Provider
public class TratamientoEntidadNoEncontradaException
        implements ExceptionMapper<EntidadNoEncontrada> {
    @Override
    public Response toResponse(EntidadNoEncontrada e) {
        return Response
            .status(Response.Status.NOT_FOUND)
            .entity(new ErrorDTO(e.getMessage()))
            .type(MediaType.APPLICATION_JSON)
            .build();
    }
}
