package productos.infraestructura.rest;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import productos.aplicacion.dto.NuevoProductoDTO;
import productos.aplicacion.dto.ProductoDTO;

public interface ProductosAPI {

    @Operation(summary = "Obtener producto", description = "Obtiene un producto por su id")
    @GetMapping("/{id}")
    EntityModel<ProductoDTO> getProducto(@PathVariable String id) throws Exception;

    @Operation(summary = "Buscar productos", description = "Filtra por categoría, descripción, estado y precio")
    @GetMapping
    PagedModel<EntityModel<ProductoDTO>> buscarProductos(
        @RequestParam(required = false) String categoria,
        @RequestParam(required = false) String descripcion,
        @RequestParam(required = false) String estado,
        @RequestParam(required = false) Double precioMax,
        Pageable pageable) throws Exception;

    @Operation(summary = "Crear producto")
    @ApiResponse(responseCode = "201", description = "Producto creado")
    @PostMapping
    ResponseEntity<NuevoProductoDTO> crearProducto(@Valid @RequestBody NuevoProductoDTO dto) throws Exception;
}
