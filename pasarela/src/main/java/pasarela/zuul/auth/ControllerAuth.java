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

        Map<String, Object> claims = autenticacionServicio.verificarCredenciales(username, password);

        if (claims != null) {
            String token = JwtUtils.generateToken(claims);

            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(3600);
            cookie.setPath("/");
            response.addCookie(cookie);

            // Gson deserializa números como Double, usar String.valueOf por seguridad
            String identificador = String.valueOf(claims.get("identificador"));
            String nombre        = String.valueOf(claims.get("nombre"));
            String roles         = String.valueOf(claims.get("roles"));

            return ResponseEntity.ok(new LoginResponseDTO(token, identificador, nombre, roles));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }
}