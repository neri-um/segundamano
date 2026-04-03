package compraventas.infraestructura.adaptadores;

import compraventas.aplicacion.puertos.salida.IRepositorioCompraventas;
import compraventas.dominio.modelo.Compraventa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
interface MongoCompraventaRepo extends MongoRepository<Compraventa, String> {
    Page<Compraventa> findByIdComprador(String idComprador, Pageable pageable);
    Page<Compraventa> findByIdVendedor(String idVendedor, Pageable pageable);
    Page<Compraventa> findByIdCompradorAndIdVendedor(String idComprador, String idVendedor, Pageable pageable);
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
    public Page<Compraventa> buscarPorComprador(String idComprador, Pageable pageable) {
        return repo.findByIdComprador(idComprador, pageable);
    }

    @Override
    public Page<Compraventa> buscarPorVendedor(String idVendedor, Pageable pageable) {
        return repo.findByIdVendedor(idVendedor, pageable);
    }

    @Override
    public Page<Compraventa> buscarPorCompradorYVendedor(String idComprador, String idVendedor, Pageable pageable) {
        return repo.findByIdCompradorAndIdVendedor(idComprador, idVendedor, pageable);
    }
}