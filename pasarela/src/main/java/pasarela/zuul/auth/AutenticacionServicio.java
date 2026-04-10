package pasarela.zuul.auth;
import java.util.Map;

import org.springframework.stereotype.Component;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
@Component
public class AutenticacionServicio {

    private final UsuariosRestClient cliente;

    public AutenticacionServicio() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://usuarios:8080/")
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
}