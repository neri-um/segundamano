package productos.infraestructura.adaptadores.salida;

<<<<<<< HEAD
import org.springframework.stereotype.Repository;
import productos.aplicacion.puertos.salida.IRepositorioProductos;

@Repository
public interface RepositorioProductosJPA extends IRepositorioProductos {
=======
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import productos.dominio.modelo.Producto;

@Repository
public interface RepositorioProductosJPA extends JpaRepository<Producto, String> {
>>>>>>> aff986665e43008b81af73c9ca6bd4ad369254a7

}