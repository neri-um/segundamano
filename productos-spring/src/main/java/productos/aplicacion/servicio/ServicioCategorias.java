package productos.aplicacion.servicio;

import java.io.File;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import productos.aplicacion.puertos.entrada.IServicioCategorias;
import productos.aplicacion.puertos.salida.IRepositorioCategorias;
import productos.dominio.modelo.Categoria;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

@Service
@Transactional
public class ServicioCategorias implements IServicioCategorias {

	private IRepositorioCategorias repositorio;

	@Autowired
	public ServicioCategorias(IRepositorioCategorias repositorio) {
		this.repositorio = repositorio;
	}

	public Categoria getCategoria(String id) throws RepositorioException, EntidadNoEncontrada {
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		Optional<Categoria> resultado = repositorio.findById(id);
		if (resultado.isPresent() == false)
			throw new EntidadNoEncontrada("No existe categoria con id: " + id);
		else
			return resultado.get();
	}
	
    @Override
    public void cargarJerarquia(String rutaFichero) throws RepositorioException {
        try {
            // 1. Deserializar el XML con JAXB
            JAXBContext context = JAXBContext.newInstance(Categoria.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Categoria raiz = (Categoria) unmarshaller.unmarshal(new File(rutaFichero));

            // 2. Comprobar si la categoría raíz ya existe (por nombre)
            // El enunciado dice: no cargar categorías principales que ya existan
            try {
                // Buscamos entre las raíz si ya existe una con ese nombre
                List<Categoria> raices = repositorio.recuperarCategoriasRaiz();
                for (Categoria c : raices) {
                    if (c.getNombre().equals(raiz.getNombre())) {
                        System.out.println("La categoría raíz '" + raiz.getNombre() + "' ya existe. No se carga.");
                        return;
                    }
                }
            } catch (RepositorioException e) {
                // Si no hay categorías aún, continuamos
            }

            // 3. Guardar la jerarquía completa (CascadeType.ALL persiste subcategorías)
            repositorio.save(raiz);

        } catch (Exception e) {
            throw new RepositorioException("Error al cargar jerarquía desde " + rutaFichero, e);
        }
    }

    @Override
    public void modificarDescripcion(String idCategoria, String descripcion)
            throws RepositorioException, EntidadNoEncontrada {

        if (idCategoria == null || idCategoria.isEmpty())
            throw new IllegalArgumentException("ERROR: id no puede ser null o vacío");
        if (descripcion == null || descripcion.isEmpty())
            throw new IllegalArgumentException("ERROR: descripcion no puede ser null o vacía");

        repositorio.actualizarDescripcion(idCategoria, descripcion);
    }

    @Override
    public List<Categoria> recuperarCategoriasRaiz() throws RepositorioException {
        return repositorio.recuperarCategoriasRaiz();
    }

    @Override
    public List<Categoria> recuperarDescendientes(String idCategoria)
            throws RepositorioException, EntidadNoEncontrada {

        if (idCategoria == null || idCategoria.isEmpty())
            throw new IllegalArgumentException("ERROR: id no puede ser null o vacío");

        return repositorio.recuperarDescendientes(idCategoria);
    }
}
