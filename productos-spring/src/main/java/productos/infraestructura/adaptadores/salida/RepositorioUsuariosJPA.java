package productos.infraestructura.adaptadores.salida;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import productos.aplicacion.puertos.salida.IRepositorioUsuarios;
import productos.dominio.modelo.UsuarioSimplificado;

@Repository
public interface RepositorioUsuariosJPA 
        extends IRepositorioUsuarios, JpaRepository<UsuarioSimplificado, String> {
}