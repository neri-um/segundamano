package segundamano.servicio;

import java.io.IOException;

import segundamano.repositorio.RepositorioCategoriasAdHoc;
import segundamano.repositorio.RepositorioCategoriasAdHocJPA;

public class ServicioCategorias {
	private RepositorioCategoriasAdHoc repositorio;

    public ServicioCategorias() throws IOException {
        this.repositorio = new RepositorioCategoriasAdHocJPA();
    }
}
