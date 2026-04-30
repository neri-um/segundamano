package productos.infraestructura.adaptadores.salida;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import productos.aplicacion.puertos.salida.UsuarioPuerto;
import productos.dominio.modelo.UsuarioSimplificado;
import productos.aplicacion.dto.UsuarioRemotoDTO;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

@Component
public class UsuarioPuertoAdaptador implements UsuarioPuerto {

    private final UsuariosRetrofit usuariosRetrofit;

    public UsuarioPuertoAdaptador(@Value("${microservicio.usuarios.url}") String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.usuariosRetrofit = retrofit.create(UsuariosRetrofit.class);
    }

    @Override
    public UsuarioSimplificado obtenerUsuario(String usuarioId) throws Exception {
        try {
            Response<UsuarioRemotoDTO> response = usuariosRetrofit
                    .getUsuarioPublico(usuarioId)
                    .execute();

            if (!response.isSuccessful() || response.body() == null) {
                throw new RuntimeException("Usuario no encontrado: " + usuarioId);
            }

            UsuarioRemotoDTO dto = response.body();
            return new UsuarioSimplificado(dto.getId(), dto.getEmail(), dto.getNombre(), dto.getApellidos());

        } catch (IOException e) {
            throw new RuntimeException("Error al contactar microservicio Usuarios", e);
        }
    }
}