package compraventas.aplicacion.puertos.entrada;

import compraventas.dominio.modelo.Compraventa;
import java.util.List;

public interface IServicioCompraventas {

    Compraventa realizarCompraventa(String idProducto, String idComprador);

    List<Compraventa> obtenerComprasDeUsuario(String idComprador);

    List<Compraventa> obtenerVentasDeUsuario(String idVendedor);

    List<Compraventa> obtenerCompraventasEntreUsuarios(String idComprador, String idVendedor);
}