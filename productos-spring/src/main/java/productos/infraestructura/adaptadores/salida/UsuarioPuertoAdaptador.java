package productos.infraestructura.adaptadores.salida;

import org.springframework.stereotype.Component;
import productos.aplicacion.puertos.salida.UsuarioPuerto;
import productos.dominio.modelo.UsuarioSimplificado;
import productos.aplicacion.dto.UsuarioRemotoDTO;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class UsuarioPuertoAdaptador implements UsuarioPuerto {

    private static final String SECRET_KEY = "claveSecretaSuperSegura123"; // misma que usuarios
    private final UsuariosRetrofit usuariosRetrofit;

    public UsuarioPuertoAdaptador() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:8080/usuarios/")   // WAR de usuarios en Jetty
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        this.usuariosRetrofit = retrofit.create(UsuariosRetrofit.class);
    }

    @Override
    public UsuarioSimplificado obtenerUsuario(String token) throws Exception {
        // Extraer el id del subject del JWT
        String tokenSinBearer = token.replace("Bearer ", "").trim();
        Claims claims = Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(tokenSinBearer)
            .getBody();
        String userId = claims.getSubject();

        // Llamar al microservicio usuarios
        UsuarioRemotoDTO dto = usuariosRetrofit
            .getUsuario(userId, token)
            .execute()
            .body();

        if (dto == null) throw new Exception("Usuario no encontrado: " + userId);

        return new UsuarioSimplificado(dto.getId(), dto.getEmail(), dto.getNombre(), dto.getApellidos());
    }
}