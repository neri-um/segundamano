package usuarios.rest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Priority;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.Priorities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtFiltroToken implements ContainerRequestFilter {

    private static final String SECRET_KEY = "1ea4b589e0dc097269b0a67331ad32d130aabb80";

    @Context
    private ResourceInfo resourceInfo;

    @Context
    private HttpServletRequest servletRequest;

    @Override
    public void filter(ContainerRequestContext requestContext) {

        // Null check primero
        if (resourceInfo == null || resourceInfo.getResourceMethod() == null) {
            return;
        }

        // Rutas públicas con @PermitAll no requieren token
        boolean esPublico = resourceInfo.getResourceMethod()
                                .isAnnotationPresent(PermitAll.class)
                            || resourceInfo.getResourceClass()
                                .isAnnotationPresent(PermitAll.class);
        if (esPublico) {
            return;
        }

        // Leer cabecera Authorization
        String authorization = requestContext.getHeaderString("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        String token = authorization.substring("Bearer ".length()).trim();

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            servletRequest.setAttribute("claims", claims);

            // Control de roles con @RolesAllowed
            if (resourceInfo.getResourceMethod().isAnnotationPresent(RolesAllowed.class)) {

                Set<String> rolesUsuario = new HashSet<>(
                    Arrays.asList(claims.get("roles", String.class).split(",")));

                String[] rolesPermitidos = resourceInfo.getResourceMethod()
                        .getAnnotation(RolesAllowed.class).value();

                boolean tieneRol = rolesUsuario.stream()
                        .anyMatch(r -> Arrays.asList(rolesPermitidos).contains(r));

                if (!tieneRol) {
                    requestContext.abortWith(
                        Response.status(Response.Status.FORBIDDEN).build());
                }
            }

        } catch (Exception e) {
            requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}