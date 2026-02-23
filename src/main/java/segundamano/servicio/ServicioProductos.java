package segundamano.servicio;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import segundamano.modelo.Categoria;
import segundamano.modelo.Estado;
import segundamano.modelo.LugarRecogida;
import segundamano.modelo.Producto;
import segundamano.modelo.Usuario;
import segundamano.repositorio.RepositorioCategoriasAdHocJPA;
import segundamano.repositorio.RepositorioProductosAdHocJPA;

public class ServicioProductos implements IServicioProductos {

    private RepositorioProductosAdHocJPA repositorioProductos =
            FactoriaRepositorios.getRepositorio(Producto.class);

    private RepositorioCategoriasAdHocJPA repositorioCategorias =
            FactoriaRepositorios.getRepositorio(Categoria.class);

    @Override
    public String altaProducto(String titulo, String descripcion, double precio, Estado estado,
            Categoria categoria, boolean envio, Usuario vendedor) throws RepositorioException {

        if (titulo == null || titulo.isEmpty())
            throw new IllegalArgumentException("ERROR: titulo no puede ser null o vacío");
        if (descripcion == null || descripcion.isEmpty())
            throw new IllegalArgumentException("ERROR: descripcion no puede ser null o vacío");
        if (precio < 0)
            throw new IllegalArgumentException("ERROR: precio no puede ser negativo");
        if (estado == null)
            throw new IllegalArgumentException("ERROR: estado no puede ser null");
        if (categoria == null)
            throw new IllegalArgumentException("ERROR: categoria no puede ser null");
        if (vendedor == null)
            throw new IllegalArgumentException("ERROR: vendedor no puede ser null");

        Producto producto = new Producto(titulo, descripcion, precio, estado, categoria, envio, vendedor);
        producto.setFechaPublicacion(LocalDateTime.now());
        return repositorioProductos.add(producto);  // ← corregido
    }

    @Override
    public void asignarRecogida(String id, double longitud, double latitud, String descripcion)
            throws RepositorioException, EntidadNoEncontrada {

        if (id == null || id.isEmpty())
            throw new IllegalArgumentException("ERROR: ID no puede ser null o vacío");
        if (descripcion == null || descripcion.isEmpty())
            throw new IllegalArgumentException("ERROR: descripcion no puede ser null o vacío");

        Producto p = repositorioProductos.getById(id);  // ← corregido
        LugarRecogida lr = new LugarRecogida(descripcion, longitud, latitud);
        p.setLugar(lr);
        repositorioProductos.update(p);  // ← corregido
    }

    @Override
    public void modificarProducto(String id, double precio, String descripcion)
            throws RepositorioException, EntidadNoEncontrada {

        if (id == null || id.isEmpty())
            throw new IllegalArgumentException("ERROR: ID no puede ser null o vacío");

        Producto p = repositorioProductos.getById(id);  // ← corregido
        if (precio >= 0.0) p.setPrecio(precio);
        if (descripcion != null && !descripcion.trim().isEmpty()) p.setDescripcion(descripcion);
        repositorioProductos.update(p);  // ← corregido
    }

    @Override
    public void añadirVisualizaciones(String id) throws RepositorioException, EntidadNoEncontrada {

        if (id == null || id.isEmpty())
            throw new IllegalArgumentException("ERROR: ID no puede ser null o vacío");

        repositorioProductos.incrementarVisualizaciones(id);  // ← usa el método del repositorio
    }

    @Override
    public List<ProductoResumen> historialDelMes(int mes, int anio) throws RepositorioException {
        YearMonth yearMonth = YearMonth.of(anio, mes);  // ← convierte aquí
        return repositorioProductos.obtenerResumenMensual(yearMonth);
    }

    @Override
    public List<Producto> buscarProductos(String categoriaId, String descripcion,
            Estado estado, Double precioMax) throws RepositorioException, EntidadNoEncontrada {

        List<String> idsCategorias = null;

        // Si hay categoría, obtener ella + todos sus descendientes
        if (categoriaId != null && !categoriaId.isEmpty()) {
            idsCategorias = repositorioCategorias.getIdsCategoriaYDescendientes(categoriaId);
        }

        BigDecimal precio = (precioMax != null) ? BigDecimal.valueOf(precioMax) : null;

        return repositorioProductos.buscarProductosConFiltros(descripcion, idsCategorias, estado, precio);
    }
}
