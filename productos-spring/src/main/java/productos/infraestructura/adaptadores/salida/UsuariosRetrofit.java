package productos.infraestructura.adaptadores.salida;

import productos.aplicacion.dto.UsuarioRemotoDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface UsuariosRetrofit {

    @GET("api/usuarios/{id}")
    Call<UsuarioRemotoDTO> getUsuario(
        @Path("id") String id,
        @Header("Authorization") String bearerToken
    );
}