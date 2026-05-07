package productos.infraestructura.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import productos.aplicacion.dto.CategoriaDTO;
import productos.aplicacion.puertos.entrada.IServicioCategorias;
import productos.dominio.modelo.Categoria;

@RestController
@RequestMapping("/categorias")
public class CategoriasController {

    private final IServicioCategorias servicio;

    public CategoriasController(IServicioCategorias servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<CategoriaDTO> getCategoriasRaiz() throws Exception {
        List<Categoria> categorias = servicio.recuperarCategoriasRaiz();
        return categorias.stream()
            .map(CategoriaDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}/descendientes")
    public List<CategoriaDTO> getDescendientes(@PathVariable String id) throws Exception {
        List<Categoria> categorias = servicio.recuperarDescendientes(id);
        return categorias.stream()
            .map(CategoriaDTO::fromEntity)
            .collect(Collectors.toList());
    }
}
