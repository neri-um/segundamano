package productos.aplicacion.puertos.salida;

import productos.dominio.modelo.UsuarioSimplificado;

public interface UsuarioPuerto {
    UsuarioSimplificado obtenerUsuario(String token) throws Exception;
}