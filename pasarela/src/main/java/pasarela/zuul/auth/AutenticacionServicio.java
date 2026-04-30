package pasarela.zuul.auth;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
@Component
public class AutenticacionServicio {

    private final UsuariosRestClient cliente;

    public AutenticacionServicio(@Value("${microservicio.usuarios.url}") String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)                   
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        this.cliente = retrofit.create(UsuariosRestClient.class);
    }

    public Map<String, Object> verificarCredenciales(String username, String password) {
        try {
            retrofit2.Response<Map<String, Object>> resp =
                cliente.login(username, password).execute();

            if (resp.isSuccessful() && resp.body() != null) {
                return resp.body();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    
    public Map<String, Object> buscarPorGithubLogin(String githubLogin) {
        try {
            retrofit2.Response<Map<String, Object>> resp =
                cliente.getUsuarioPorGithub(githubLogin).execute();
            if (resp.isSuccessful() && resp.body() != null) return resp.body();
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    
}