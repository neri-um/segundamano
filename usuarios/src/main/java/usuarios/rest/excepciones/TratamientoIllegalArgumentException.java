package usuarios.rest.excepciones;

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import usuarios.rest.dto.ErrorDTO;

@Provider
public class TratamientoIllegalArgumentException
        implements ExceptionMapper<IllegalArgumentException> {
    @Override
    public Response toResponse(IllegalArgumentException e) {
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(new ErrorDTO(e.getMessage()))
            .type(MediaType.APPLICATION_JSON)
            .build();
    }
}
