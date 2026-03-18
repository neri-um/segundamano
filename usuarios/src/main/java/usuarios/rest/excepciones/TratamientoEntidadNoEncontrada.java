package usuarios.rest.excepciones;

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;
import repositorio.EntidadNoEncontrada;

@Provider
public class TratamientoEntidadNoEncontrada 
        implements ExceptionMapper<EntidadNoEncontrada> {
    @Override
    public Response toResponse(EntidadNoEncontrada e) {
        return Response
            .status(Response.Status.NOT_FOUND)
            .entity(e.getMessage())
            .build();
    }
}
