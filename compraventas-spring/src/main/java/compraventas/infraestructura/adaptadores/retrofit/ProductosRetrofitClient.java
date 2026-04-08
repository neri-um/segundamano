package compraventas.infraestructura.adaptadores.retrofit;

import compraventas.infraestructura.adaptadores.dto.ProductoDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductosRetrofitClient {
	@GET("productos/{id}")
    Call<ProductoDTO> getProducto(@Path("id") String id);

    @PUT("productos/{id}/vendido")
    Call<Void> marcarComoVendido(@Path("id") String idProducto);
}