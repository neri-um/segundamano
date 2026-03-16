package usuarios.rest;

import java.net.URI;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import servicio.FactoriaServicios;
import usuarios.modelo.Usuario;
import usuarios.rest.dto.ListadoUsuariosDTO;
import usuarios.rest.dto.NuevoUsuarioDTO;
import usuarios.rest.dto.UsuarioCreadoDTO;
import usuarios.rest.dto.UsuarioDTO;
import usuarios.rest.dto.UsuarioResumenDTO;
import usuarios.servicio.IServicioUsuarios;
import io.jsonwebtoken.Claims;


@Path("usuarios")
@Produces(MediaType.APPLICATION_JSON)
public class UsuariosControladorRest {

    private IServicioUsuarios servicio = FactoriaServicios.getServicio(IServicioUsuarios.class);

    @Context private UriInfo uriInfo;
    @Context private HttpServletRequest servletRequest;

    // POST /usuarios
    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public Response altaUsuario(NuevoUsuarioDTO nuevoUsuario) throws Exception {
        LocalDate fecha = LocalDate.parse(nuevoUsuario.getFechaNac());
        String id = servicio.altaUsuario(
            nuevoUsuario.getNombre(), nuevoUsuario.getApellidos(),
            nuevoUsuario.getEmail(), nuevoUsuario.getClave(),
            fecha, nuevoUsuario.getTelefono()
        );
        URI nuevaURL = uriInfo.getAbsolutePathBuilder().path(id).build();
        return Response.created(nuevaURL)
                       .entity(new UsuarioCreadoDTO(id))  
                       .build();
    }

    // GET /usuarios/{id}
    @GET
    @Path("{id}")
    @RolesAllowed("USUARIO")
    public Response getUsuario(@PathParam("id") String id) throws Exception {
        Usuario usuario = servicio.recuperarUsuario(id);
        return Response.ok(UsuarioDTO.fromEntity(usuario)).build();
    }

    // PATCH /usuarios/{id}
    @PATCH
    @Path("{id}")
    @RolesAllowed("USUARIO")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modificarUsuario(
            @PathParam("id") String id,
            NuevoUsuarioDTO datos) throws Exception {

        Claims claims = (Claims) servletRequest.getAttribute("claims");
        if (!claims.getSubject().equals(id)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        LocalDate fecha = (datos.getFechaNac() != null)
                ? LocalDate.parse(datos.getFechaNac()) : null;

        servicio.modificarUsuario(
            id, datos.getNombre(), datos.getApellidos(),
            datos.getClave(), fecha, datos.getTelefono()
        );
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    // GET /usuarios
    @GET
    @RolesAllowed("USUARIO")
    public Response getListadoUsuarios() throws Exception {
        List<Usuario> usuarios = servicio.listarUsuarios();
        List<ListadoUsuariosDTO.ResumenConURL> resultado = new LinkedList<>();
        for (Usuario u : usuarios) {
            URI url = uriInfo.getAbsolutePathBuilder().path(u.getId()).build();
            resultado.add(new ListadoUsuariosDTO.ResumenConURL(
                url.toString(),
                UsuarioResumenDTO.fromEntity(u)   
            ));
        }
        return Response.ok(new ListadoUsuariosDTO(resultado)).build();
    }
}