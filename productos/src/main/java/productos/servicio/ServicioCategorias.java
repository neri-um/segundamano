package productos.servicio;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import productos.modelo.Categoria;
import productos.repositorio.RepositorioCategoriasAdHocJPA;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;

public class ServicioCategorias implements IServicioCategorias {

    private RepositorioCategoriasAdHocJPA repositorio =
            FactoriaRepositorios.getRepositorio(Categoria.class);

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
            repositorio.add(raiz);

        } catch (RepositorioException e) {
            throw e;
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
