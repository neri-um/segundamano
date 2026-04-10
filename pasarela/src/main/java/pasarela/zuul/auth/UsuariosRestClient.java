package pasarela.zuul.auth;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UsuariosRestClient {

	@FormUrlEncoded
	@POST("api/usuarios/auth/login")
	Call<Map<String, Object>> login(@Field("username") String username, @Field("password") String password);
}