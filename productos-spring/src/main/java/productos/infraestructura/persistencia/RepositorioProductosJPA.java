package productos.infraestructura.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import productos.aplicacion.output.IRepositorioProductos;
import productos.dominio.modelo.Producto;

@Repository
public interface RepositorioProductosJPA extends IRepositorioProductos, JpaRepository<Producto, String> {

}