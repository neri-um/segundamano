package productos.dominio.modelo;

import java.util.ArrayList;
import java.util.List;

public enum Estado {
	NUEVO(5), COMO_NUEVO(4), BUEN_ESTADO(3), ACEPTABLE(2), PARA_PIEZAS(1);

	private final int nivel;
	Estado(int nivel) { this.nivel = nivel; }
	public int getNivel() { return nivel; }
	
}