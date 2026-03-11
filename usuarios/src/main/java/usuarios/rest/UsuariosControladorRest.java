package usuarios.rest;

import java.net.URI;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import servicio.FactoriaServicios;
import usuarios.modelo.Usuario;
import usuarios.rest.dto.ListadoUsuariosDTO;
import usuarios.rest.dto.NuevoUsuarioDTO;
import usuarios.rest.dto.UsuarioDTO;
import usuarios.rest.dto.UsuarioResumenDTO;
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
        // 201 Created + Location + id en cuerpo
        return Response.created(nuevaURL)
                       .entity("{\"id\": \"" + id + "\"}")
                       .build();
    }

    // GET /usuarios/{id}
    @GET
    @Path("/{id}")
    public Response getUsuario(@PathParam("id") String id) throws Exception {
        Usuario usuario = servicio.recuperarUsuario(id);
        return Response.ok(UsuarioDTO.fromEntity(usuario)).build();
    }

    // PATCH /usuarios/{id}  (solo campos presentes, sin tocar email ni admin)
    @PATCH
    @Path("{id}")
    public Response modificarUsuario(
            @PathParam("id") String id,
            NuevoUsuarioDTO datos) throws Exception {

        LocalDate fecha = (datos.getFechaNac() != null)
                ? LocalDate.parse(datos.getFechaNac())
                : null;

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

 // GET /usuarios
    @GET
    public Response getListadoUsuarios() throws Exception {
        List<Usuario> usuarios = servicio.listarUsuarios();

        List<ListadoUsuariosDTO.ResumenConURL> resultado = new LinkedList<>();

        for (Usuario u : usuarios) {
            // Construir la URL de cada usuario: GET /usuarios/{id}
            URI url = uriInfo.getAbsolutePathBuilder().path(u.getId()).build();

            UsuarioResumenDTO resumen = new UsuarioResumenDTO(
                u.getId(),
                u.getNombre(),
                u.getEmail()
            );

            resultado.add(new ListadoUsuariosDTO.ResumenConURL(url.toString(), resumen));
        }

        return Response.ok(new ListadoUsuariosDTO(resultado)).build();
    }
}
