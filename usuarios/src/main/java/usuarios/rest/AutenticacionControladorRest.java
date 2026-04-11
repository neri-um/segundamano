package usuarios.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import servicio.FactoriaServicios;
import usuarios.modelo.Usuario;
import usuarios.servicio.IServicioUsuarios;

@Path("auth")
public class AutenticacionControladorRest {

    private static final String SECRET_KEY = "1ea4b589e0dc097269b0a67331ad32d130aabb80";
    private IServicioUsuarios servicio = FactoriaServicios.getServicio(IServicioUsuarios.class);

    // POST /auth/login  — ruta pública
    @POST
    @Path("login")
    @PermitAll
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(
            @FormParam("username") String username,
            @FormParam("password") String password) throws Exception {

        // Buscar usuario por email
        Usuario usuario = servicio.login(username, password);

        if (usuario == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity("Credenciales inválidas")
                           .build();
        }

        // Generar token JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", usuario.getId());
        claims.put("name", usuario.getNombre());
        claims.put("roles", "USUARIO");
        

        Date caducidad = new Date(System.currentTimeMillis() + 3600 * 1000); // 1 hora

        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setExpiration(caducidad)
                .compact();

        return Response.ok("{\"token\": \"" + token + "\"}").build();
    }
}
