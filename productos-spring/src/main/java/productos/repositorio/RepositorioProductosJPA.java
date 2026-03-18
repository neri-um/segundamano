package productos.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import productos.modelo.Producto;

@Repository
public interface RepositorioProductosJPA extends RepositorioProductos, JpaRepository<Producto, String> {

}