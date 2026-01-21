package segundamano.modelo;

import java.util.ArrayList;
import java.util.List;

public enum Estado {
	NUEVO, COMO_NUEVO, BUEN_ESTADO, ACEPTABLE, PARA_PIEZAS_REPARAR;
	
	/**
	 * Verifica si este estado es igual o mejor que el estado dado.
	 * Orden: NUEVO > COMO_NUEVO > BUEN_ESTADO > ACEPTABLE > PARA_PIEZAS_REPARAR
	 * 
	 * @param otro El estado con el que comparar
	 * @return true si este estado es igual o mejor que el otro
	 */
	public boolean esIgualOMejorQue(Estado otro) {
		return this.ordinal() <= otro.ordinal();
	}
	
	/**
	 * Obtiene todos los estados iguales o mejores que este estado.
	 * 
	 * @return Lista de estados iguales o mejores
	 */
	public List<Estado> getEstadosIgualOMejor() {
		Estado[] todosEstados = Estado.values();
		List<Estado> resultado = new ArrayList<>();
		for (int i = 0; i <= this.ordinal(); i++) {
			resultado.add(todosEstados[i]);
		}
		return resultado;
	}
}