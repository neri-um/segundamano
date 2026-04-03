package compraventas.infraestructura.adaptadores.retrofit;

import compraventas.infraestructura.adaptadores.dto.UsuarioDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UsuariosRetrofitClient {
    // Endpoint público añadido según Nota 1 de la tarea
    @GET("api/usuarios/publico/{id}/nombre")
    Call<UsuarioDTO> getNombreUsuario(@Path("id") String id);
}