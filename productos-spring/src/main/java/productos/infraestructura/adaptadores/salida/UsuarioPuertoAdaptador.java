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

    private final UsuariosRetrofit usuariosRetrofit;

    public UsuarioPuertoAdaptador() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:8080/")   
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        this.usuariosRetrofit = retrofit.create(UsuariosRetrofit.class);
    }

    @Override
    public UsuarioSimplificado obtenerUsuario(String usuarioId) throws Exception {
        UsuarioRemotoDTO dto = usuariosRetrofit
            .getUsuarioPublico(usuarioId)
            .execute()
            .body();

        if (dto == null) throw new Exception("Usuario no encontrado: " + usuarioId);
        return new UsuarioSimplificado(dto.getId(), dto.getEmail(), dto.getNombre(), dto.getApellidos());
    }
}