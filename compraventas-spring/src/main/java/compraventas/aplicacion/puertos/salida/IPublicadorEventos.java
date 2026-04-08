package compraventas.aplicacion.puertos.salida;

import java.io.IOException;
import compraventas.eventos.Evento;

public interface IPublicadorEventos {
	void publicarEvento(Evento evento) throws IOException;
}