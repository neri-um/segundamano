package pasarela.zuul.auth;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SecuritySuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private AutenticacionServicio autenticacionServicio;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws IOException {

        DefaultOAuth2User oauth2User = (DefaultOAuth2User) authentication.getPrincipal();
        String githubLogin = (String) oauth2User.getAttributes().get("login");

        // Consultar el microservicio Usuarios
        Map<String, Object> usuario = autenticacionServicio.buscarPorGithubLogin(githubLogin);

        if (usuario == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\":\"Usuario de GitHub no registrado\"}");
            return;
        }

        String id = String.valueOf(usuario.get("id"));
        String nombre = usuario.get("nombre") != null ? String.valueOf(usuario.get("nombre")) : "";
        String apellidos = usuario.get("apellidos") != null ? String.valueOf(usuario.get("apellidos")) : "";
        String nombreCompleto = (nombre + " " + apellidos).trim();

        String roles = usuario.get("roles") != null
            ? String.valueOf(usuario.get("roles"))
            : (Boolean.TRUE.equals(usuario.get("admin")) ? "ADMINISTRADOR" : "USUARIO");

        Map<String, Object> claims = Map.of("sub", id, "name", nombreCompleto, "roles", roles);
        String token = JwtUtils.generateToken(claims);

        Cookie cookie = new Cookie("jwt", token);
        cookie.setMaxAge(3600);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        LoginResponseDTO dto = new LoginResponseDTO(token, id, nombreCompleto, roles);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(dto));
    }
}