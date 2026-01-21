package segundamano.servicio;

import java.time.LocalDate;
import java.util.List;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import segundamano.modelo.Categoria;
import segundamano.modelo.Estado;
import segundamano.modelo.Producto;
import segundamano.modelo.Usuario;

public interface IServicioProductos {
/**
 * Alta de un producto con la información de título, descripción, precio, estado, identificador de categoría, envío disponible e identificador del usuario vendedor. La aplicación proporcionará un identificador único al producto que se creará con fecha de publicación la fecha y hora actual. La operación retorna el identificador del nuevo producto creado.

Asignar lugar de recogida. Esta operación recibe como parámetro el identificador de un producto y los datos de longitud, latitud y descripción del lugar de recogida. Como resultado establece el lugar de recogida del producto.

Modificar datos de un producto. Esta operación permite modificar el precio y/o la descripción del producto. Recibe como parámetros el identificador del producto y los valores a 
modificar.

Añadir visualización. Esta operación incrementa el contador de visualizaciones de un producto. Recibe como parámetro el identificador del producto. Como resultado, 
las visualizaciones del producto se incrementan a uno.

Historial del mes. Esta operación recibe un mes y año y devuelve un listado resumen de los productos a la venta durante ese mes ordenados de mayor a menor número de visualizaciones. El resumen de los productos debe indicar el identificador, título, precio, fecha de publicación, nombre de categoría y número de visualizaciones.

Buscar productos a la venta por categoría, descripción, estado y precio. Recibe como parámetros un identificador de categoría, un texto, un estado y un precio máximo, y devuelve un listado de productos que están a la venta en esa categoría o en alguna categoría que sea descendiente de esta (directa o no), cuyo precio sea menor o igual al indicado, cuyo estado sea igual o mejor al especificado y que contenga el texto en su descripción. Todos los parámetros de búsqueda 
son opcionales. El orden de los estados de mejor a peor es: nuevo > como nuevo > buen estado > aceptable > para piezas o reparar.
 * @throws RepositorioException 
 */
	
	String altaProducto(String titulo, String descripcion, double precio, Estado estado, Categoria ategoria,
			boolean envio, Usuario vendedor) throws RepositorioException;
	
	void asignarRecogida(String id, double longitud, double latitud, String descripcion) throws RepositorioException, EntidadNoEncontrada;
	
	void modificarProducto(String id, double precio, String descripcion) throws RepositorioException, EntidadNoEncontrada;
	
	void añadirVisualizaciones(String id) throws RepositorioException, EntidadNoEncontrada;

	List<Producto> buscarProductos(String categoriaId, String descripcion, Estado estado, Double precioMax)
			throws RepositorioException;

	List<ProductoResumen> historialDelMes(int mes, int anio) throws RepositorioException;


}
