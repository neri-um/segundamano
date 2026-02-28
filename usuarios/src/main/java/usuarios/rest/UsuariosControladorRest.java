package usuarios.rest;

import java.net.URI;
import java.time.LocalDate;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import servicio.FactoriaServicios;
import usuarios.modelo.Usuario;
import usuarios.rest.dto.NuevoUsuarioDTO;
import usuarios.rest.dto.UsuarioDTO;
import usuarios.servicio.IServicioUsuarios;

@Path("usuarios")
@Produces(MediaType.APPLICATION_JSON)
public class UsuariosControladorRest {

    private IServicioUsuarios servicio =
        FactoriaServicios.getServicio(IServicioUsuarios.class);

    @Context
    private UriInfo uriInfo;

    // POST /usuarios
    @POST
    public Response altaUsuario(NuevoUsuarioDTO nuevoUsuario) throws Exception {
        LocalDate fecha = LocalDate.parse(nuevoUsuario.getFechaNac());
        String id = servicio.altaUsuario(
        	    nuevoUsuario.getNombre(),
        	    nuevoUsuario.getApellidos(),
        	    nuevoUsuario.getEmail(),
        	    nuevoUsuario.getClave(),
        	    fecha,
        	    nuevoUsuario.getTelefono()
        	);

        URI nuevaURL = uriInfo.getAbsolutePathBuilder().path(id).build();
        return Response.created(nuevaURL).build();
    }

    // GET /usuarios/{id}
    @GET
    @Path("/{id}")
    public UsuarioDTO getUsuario(@PathParam("id") String id) throws Exception {
        Usuario usuario = servicio.recuperarUsuario(id); // pendiente tarea 2
        return UsuarioDTO.fromEntity(usuario);
    }

    // PUT /usuarios/{id}
    @PUT
    @Path("/{id}")
    public Response modificarUsuario(
            @PathParam("id") String id,
            NuevoUsuarioDTO datos) throws Exception {
        LocalDate fecha = LocalDate.parse(datos.getFechaNac());
        servicio.modificarUsuario(
            id,
            datos.getNombre(),
            datos.getApellidos(),
            datos.getClave(),
            fecha,
            datos.getTelefono()
        );
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
