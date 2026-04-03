package compraventas.infraestructura.adaptadores.retrofit;

import compraventas.aplicacion.puertos.salida.IPuertoUsuarios;
import compraventas.infraestructura.adaptadores.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

@Component
public class AdaptadorUsuarios implements IPuertoUsuarios {

    private final UsuariosRetrofitClient client;

    public AdaptadorUsuarios(@Value("${microservicio.usuarios.url}") String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        this.client = retrofit.create(UsuariosRetrofitClient.class);
    }

    @Override
    public String getNombreUsuario(String idUsuario) {
        try {
            retrofit2.Response<UsuarioDTO> resp = client.getNombreUsuario(idUsuario).execute();
            if (!resp.isSuccessful() || resp.body() == null)
                throw new RuntimeException("Usuario no encontrado: " + idUsuario);
            return resp.body().getNombre();
        } catch (IOException e) {
            throw new RuntimeException("Error al contactar microservicio Usuarios", e);
        }
    }
}