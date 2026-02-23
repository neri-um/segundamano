package segundamano.modelo;

import java.util.ArrayList;
import java.util.List;

public enum Estado {
	NUEVO, COMO_NUEVO, BUEN_ESTADO, ACEPTABLE, PARA_PIEZAS_REPARAR;
	
	public boolean esIgualOMejorQue(Estado otro) {
		return this.ordinal() <= otro.ordinal();
	}
	
	public List<Estado> getEstadosIgualOMejor() {
		Estado[] todosEstados = Estado.values();
		List<Estado> resultado = new ArrayList<>();
		for (int i = 0; i <= this.ordinal(); i++) {
			resultado.add(todosEstados[i]);
		}
		return resultado;
	}
	
}