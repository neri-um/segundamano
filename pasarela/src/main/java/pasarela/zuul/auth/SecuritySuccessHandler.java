package pasarela.zuul.auth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SecuritySuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws IOException {

        DefaultOAuth2User usuario = (DefaultOAuth2User) authentication.getPrincipal();

        Map<String, Object> claims = new HashMap<>();
        String login = (String) usuario.getAttributes().get("login");
        String name  = (String) usuario.getAttributes().get("name");
        claims.put("sub", login);
        claims.put("name", name != null ? name : login);
        claims.put("roles", "USUARIO");

        String token = JwtUtils.generateToken(claims);

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600);
        cookie.setPath("/");
        response.addCookie(cookie);

        // Devolver DTO como JSON en vez de texto plano
        LoginResponseDTO dto = new LoginResponseDTO(
            token,
            login,
            name != null ? name : login,
            "USUARIO"
        );

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(dto));
    }
}
