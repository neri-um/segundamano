package productos.infraestructura.rest;

import java.net.URI;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import productos.aplicacion.dto.LugarRecogidaDTO;
import productos.aplicacion.dto.ModificarProductoDTO;
import productos.aplicacion.dto.NuevoProductoDTO;
import productos.aplicacion.dto.ProductoDTO;
import productos.aplicacion.puertos.entrada.IServicioCategorias;
import productos.aplicacion.puertos.entrada.IServicioProductos;
import productos.aplicacion.puertos.salida.UsuarioPuerto;
import productos.aplicacion.servicio.ProductoResumen;
import productos.dominio.modelo.Categoria;
import productos.dominio.modelo.Estado;
import productos.dominio.modelo.Producto;
import productos.dominio.modelo.UsuarioSimplificado;

@RestController
@RequestMapping("/productos")
public class ProductosController implements ProductosAPI{

    private IServicioProductos servicio;
    private IServicioCategorias servicioC;

    @Autowired
    private PagedResourcesAssembler<ProductoResumen> pagedResourcesAssemblerResumen;
    @Autowired
    public ProductosController(IServicioProductos servicio, IServicioCategorias servicioC) {
        this.servicio = servicio;
        this.servicioC = servicioC;
    }

    @Autowired
    private PagedResourcesAssembler<ProductoDTO> pagedResourcesAssembler;
    
    @Autowired
    private UsuarioPuerto usuarioPuerto;

    @PostMapping
    public ResponseEntity<Map<String, String>> crearProducto(
            @Valid @RequestBody NuevoProductoDTO dto) throws Exception {

        Estado estado = Estado.valueOf(dto.getEstado());
        Categoria categoria = servicioC.getCategoria(dto.getCategoria());
        UsuarioSimplificado vendedor = usuarioPuerto.obtenerUsuario(dto.getIdVendedor());
        String id = servicio.altaProducto(dto.getTitulo(), dto.getDescripcion(),
                        dto.getPrecio(), estado, categoria, dto.isEnvio(), vendedor);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(url).body(Map.of("id", id));
    }

    // GET /productos/{id}
    @GetMapping("/{id}")
    public EntityModel<ProductoDTO> getProducto(@PathVariable String id) throws Exception {
        Producto p = servicio.getProducto(id);
        ProductoDTO dto = ProductoDTO.fromEntity(p);
        EntityModel<ProductoDTO> model = EntityModel.of(dto);
        model.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(ProductosController.class).getProducto(id))
                .withSelfRel()); //diapositiva 18 springboot
        return model;
    }

    // PATCH /productos/{id}/recogida
    @PatchMapping("/{id}/recogida")
    public ResponseEntity<Void> asignarRecogida(@PathVariable String id, @RequestBody LugarRecogidaDTO dto) throws Exception {
        servicio.asignarRecogida(id, dto.getLatitud(), dto.getLongitud(), dto.getDescripcion());
        return ResponseEntity.noContent().build();
    }

    // PATCH /productos/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<Void> modificarProducto(@PathVariable String id, @RequestBody ModificarProductoDTO dto) throws Exception {
        servicio.modificarProducto(id, dto.getPrecio(), dto.getDescripcion());
        return ResponseEntity.noContent().build();
    }

    // PATCH /productos/{id}/visualizaciones
    @PatchMapping("/{id}/visualizaciones")
    public ResponseEntity<Void> añadirVisualizacion(@PathVariable String id) throws Exception {
        servicio.añadirVisualizaciones(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/historial")
    public PagedModel<EntityModel<ProductoResumen>> historialMes(
            @RequestParam int anyo,
            @RequestParam int mes,
            Pageable pageable) throws Exception {

        Page<ProductoResumen> resultado = servicio.historialDelMes(mes, anyo, pageable);
        return pagedResourcesAssemblerResumen.toModel(resultado);
    }

    @GetMapping
    public PagedModel<EntityModel<ProductoDTO>> buscarProductos(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String descripcion,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Double precioMax,
            Pageable pageable) throws Exception {

        Estado e = (estado != null) ? Estado.valueOf(estado) : null;
        Page<Producto> resultado = servicio.buscarProductos(categoria, descripcion, e, precioMax, pageable);
        Page<ProductoDTO> dtos = resultado.map(ProductoDTO::fromEntity);
        return pagedResourcesAssembler.toModel(dtos);
    }


}