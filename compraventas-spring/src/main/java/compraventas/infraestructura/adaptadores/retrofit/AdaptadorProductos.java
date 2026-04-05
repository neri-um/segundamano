package compraventas.infraestructura.adaptadores.retrofit;

import compraventas.aplicacion.puertos.salida.IPuertoProductos;
import compraventas.infraestructura.adaptadores.dto.ProductoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

@Component
public class AdaptadorProductos implements IPuertoProductos {

    private final ProductosRetrofitClient client;

    public AdaptadorProductos(@Value("${microservicio.productos.url}") String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        this.client = retrofit.create(ProductosRetrofitClient.class);
    }

    private ProductoDTO fetchProducto(String id) {
        try {
            retrofit2.Response<ProductoDTO> resp = client.getProducto(id).execute();
            if (!resp.isSuccessful() || resp.body() == null)
                throw new RuntimeException("Producto no encontrado: " + id);
            return resp.body();
        } catch (IOException e) {
            throw new RuntimeException("Error al contactar microservicio Productos", e);
        }
    }

    @Override
    public String getIdVendedor(String idProducto) {
        return fetchProducto(idProducto).getIdVendedor(); 
    }

    @Override
    public String getTitulo(String idProducto) {
        return fetchProducto(idProducto).getTitulo();
    }

    @Override
    public double getPrecio(String idProducto) {
        return fetchProducto(idProducto).getPrecio();
    }

    @Override
    public String getRecogida(String idProducto) {
        return fetchProducto(idProducto).getLugarRecogida();
    }
}