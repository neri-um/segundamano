package pasarela.zuul.auth;

import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class ControllerAuth {

    @Autowired
    private AutenticacionServicio autenticacionServicio;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletResponse response) {

        Map<String, Object> usuario = autenticacionServicio.verificarCredenciales(username, password);

        if (usuario != null) {
            String identificador = String.valueOf(usuario.get("id"));
            String nombre = usuario.get("nombre") != null ? String.valueOf(usuario.get("nombre")) : "";
            String apellidos = usuario.get("apellidos") != null ? String.valueOf(usuario.get("apellidos")) : "";
            String nombreCompleto = (nombre + " " + apellidos).trim();

            String roles = usuario.get("roles") != null
                    ? String.valueOf(usuario.get("roles"))
                    : (Boolean.TRUE.equals(usuario.get("admin")) ? "ADMINISTRADOR" : "USUARIO");

            Map<String, Object> claims = Map.of(
                    "sub", identificador,
                    "name", nombreCompleto,
                    "roles", roles
            );

            String token = JwtUtils.generateToken(claims);
            Cookie cookie = new Cookie("jwt", token);
            cookie.setMaxAge(3600);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            return ResponseEntity.ok(new LoginResponseDTO(token, identificador, nombreCompleto, roles));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", "");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.noContent().build();
    }
}