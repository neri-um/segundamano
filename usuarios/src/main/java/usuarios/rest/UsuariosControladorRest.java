package usuarios.rest;

import java.net.URI;
import java.time.LocalDate;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import servicio.FactoriaServicios;
import usuarios.servicio.IServicioUsuarios;

@Path("usuarios")
public class UsuariosControladorRest {

    private IServicioUsuarios servicio = FactoriaServicios.getServicio(IServicioUsuarios.class);

    @Context
    private UriInfo uriInfo;

    @POST
    public Response altaUsuario(
            @FormParam("nombre")    String nombre,
            @FormParam("apellidos") String apellidos,
            @FormParam("email")     String email,
            @FormParam("clave")     String clave,
            @FormParam("fechaNac")  String fechaNac,
            @FormParam("telefono")  String telefono) throws Exception {

    	   LocalDate fecha = LocalDate.parse(fechaNac); 

    	   String id = servicio.altaUsuario(nombre, apellidos, email, clave, fecha, telefono);
    	   URI nuevaURL = uriInfo.getAbsolutePathBuilder().path(id).build();
    	   return Response.created(nuevaURL).build();
    }


    @PUT
    @Path("/{id}")
    public Response modificarUsuario(
            @PathParam("id")        String id,
            @FormParam("nombre")    String nombre,
            @FormParam("apellidos") String apellidos,
            @FormParam("clave")     String clave,
            @FormParam("fechaNac")  String fechaNac,
            @FormParam("telefono")  String telefono) throws Exception {

        LocalDate fecha = LocalDate.parse(fechaNac);
        servicio.modificarUsuario(id, nombre, apellidos, clave, fecha, telefono);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}

