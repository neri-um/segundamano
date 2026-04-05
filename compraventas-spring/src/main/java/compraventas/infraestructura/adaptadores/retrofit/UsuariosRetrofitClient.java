package compraventas.infraestructura.adaptadores.retrofit;

import compraventas.infraestructura.adaptadores.dto.UsuarioDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UsuariosRetrofitClient {
	@GET("api/usuarios/{id}/publico")
    Call<UsuarioDTO> getNombreUsuario(@Path("id") String id);
}