package productos.aplicacion.puertos.entrada;


import java.io.IOException;
import productos.eventos.Evento;

public interface IPublicadorEventos {
    void publicarEvento(Evento evento) throws IOException;
}