package compraventas.infraestructura.adaptadores;

import compraventas.aplicacion.puertos.salida.IRepositorioCompraventas;
import compraventas.dominio.modelo.Compraventa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Component;

import java.util.List;

@Repository
interface MongoCompraventaRepo extends MongoRepository<Compraventa, String> {
    List<Compraventa> findByIdComprador(String idComprador);
    List<Compraventa> findByIdVendedor(String idVendedor);
    List<Compraventa> findByIdCompradorAndIdVendedor(String idComprador, String idVendedor);
}

@Component
public class RepositorioCompraventasMongo implements IRepositorioCompraventas {

    private final MongoCompraventaRepo repo;

    public RepositorioCompraventasMongo(MongoCompraventaRepo repo) {
        this.repo = repo;
    }

    @Override
    public Compraventa guardar(Compraventa c) {
        return repo.save(c);
    }

    @Override
    public List<Compraventa> buscarPorComprador(String idComprador) {
        return repo.findByIdComprador(idComprador);
    }

    @Override
    public List<Compraventa> buscarPorVendedor(String idVendedor) {
        return repo.findByIdVendedor(idVendedor);
    }

    @Override
    public List<Compraventa> buscarPorCompradorYVendedor(String idComprador, String idVendedor) {
        return repo.findByIdCompradorAndIdVendedor(idComprador, idVendedor);
    }
}