package productos.infraestructura.adaptadores.salida;

import productos.aplicacion.dto.UsuarioRemotoDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UsuariosRetrofit {

    @GET("api/usuarios/{id}/publico")
    Call<UsuarioRemotoDTO> getUsuarioPublico(
        @Path("id") String id
    );
}