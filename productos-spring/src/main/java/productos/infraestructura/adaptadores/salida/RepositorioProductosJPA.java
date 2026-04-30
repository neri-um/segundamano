package productos.infraestructura.adaptadores.salida;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import productos.aplicacion.puertos.salida.IRepositorioProductos;
import productos.dominio.modelo.Producto;

@Repository
public interface RepositorioProductosJPA
        extends IRepositorioProductos, JpaRepository<Producto, String> {
}