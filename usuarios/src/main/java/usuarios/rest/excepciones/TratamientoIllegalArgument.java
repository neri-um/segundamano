package usuarios.rest.excepciones;

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;

@Provider
public class TratamientoIllegalArgument 
        implements ExceptionMapper<IllegalArgumentException> {
    @Override
    public Response toResponse(IllegalArgumentException e) {
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(e.getMessage())
            .build();
    }
}
