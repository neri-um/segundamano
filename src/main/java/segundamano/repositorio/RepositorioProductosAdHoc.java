package segundamano.repositorio;

import repositorio.RepositorioString;
import segundamano.modelo.Producto;

/*
 * Esta interfaz extiende la definición genérica con operaciones *ad hoc* de consulta.
 * 
 * Es una interfaz concreta para una entidad (Producto).
 */
public interface RepositorioProductosAdHoc extends RepositorioString<Producto> {

	/*
	 * Ejemplo de implementación por defecto (útil para un repositorio en memoria).
	 
	public default List<Encuesta> getByActivas() throws RepositorioException {
		LocalDateTime ahora = LocalDateTime.now();
		return getAll().stream().filter(encuesta -> encuesta.getApertura().isBefore(ahora) &&
				encuesta.getCierre().isAfter(ahora)).collect(Collectors.toList());
	}
	
	public List<Encuesta> getBySinVotos() throws RepositorioException, EntidadNoEncontrada ;
	
	public List<Encuesta> getByNumeroOpcionesMayorQue(int numero)  throws RepositorioException, EntidadNoEncontrada ;
	
	// ...
	public List<Encuesta> getByVotante(String nombre);
	public List<Object[]> getOpcionesPorVotos() throws RepositorioException;
	*/
}