package pasarela.zuul.auth;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UsuariosRestClient {

	@FormUrlEncoded
	@POST("api/usuarios/auth/login")
	Call<Map<String, Object>> login(@Field("username") String username, @Field("password") String password);
	
	@GET("api/usuarios/github/{login}")
	Call<Map<String, Object>> getUsuarioPorGithub(@Path("login") String login);
}