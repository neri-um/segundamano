package compraventas.infraestructura.rest;

import compraventas.aplicacion.puertos.entrada.IServicioCompraventas;
import compraventas.dominio.modelo.Compraventa;
import compraventas.infraestructura.rest.dto.CompraventaRequestDTO;
import compraventas.infraestructura.rest.dto.CompraventaResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = "/api/compraventas", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Compraventas", description = "Gestión de transacciones entre usuarios")
public class CompraventaController {

    private final IServicioCompraventas servicio;

    public CompraventaController(IServicioCompraventas servicio) {
        this.servicio = servicio;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Realizar una compraventa")
    public ResponseEntity<CompraventaResponseDTO> realizarCompraventa(
            @Valid @RequestBody CompraventaRequestDTO request) {

        Compraventa c = servicio.realizarCompraventa(
                request.getIdProducto(), request.getIdComprador());
        CompraventaResponseDTO dto = toDTO(c);
        dto.add(linkTo(methodOn(CompraventaController.class)
                .getComprasDeUsuario(c.getIdComprador(), Pageable.unpaged())).withRel("compras-comprador"));
        dto.add(linkTo(methodOn(CompraventaController.class)
                .getVentasDeUsuario(c.getIdVendedor(), Pageable.unpaged())).withRel("ventas-vendedor"));
        return ResponseEntity.status(201).body(dto);
    }

    @GetMapping("/comprador/{idComprador}")
    @Operation(summary = "Obtener compras de un usuario")
    public ResponseEntity<PagedModel<EntityModel<CompraventaResponseDTO>>> getComprasDeUsuario(
            @PathVariable String idComprador, Pageable pageable) {

        Page<Compraventa> page = servicio.obtenerComprasDeUsuario(idComprador, pageable);
        return ResponseEntity.ok(assembler.toModel(page, c -> EntityModel.of(toDTO(c))));
    }

    @GetMapping("/vendedor/{idVendedor}")
    @Operation(summary = "Obtener ventas de un usuario")
    public ResponseEntity<PagedModel<EntityModel<CompraventaResponseDTO>>> getVentasDeUsuario(
            @PathVariable String idVendedor, Pageable pageable) {

        List<Compraventa> todas = servicio.obtenerVentasDeUsuario(idVendedor);
        return ResponseEntity.ok(paginar(todas, pageable));
    }

    @GetMapping("/comprador/{idComprador}/vendedor/{idVendedor}")
    @Operation(summary = "Obtener compraventas entre comprador y vendedor")
    public ResponseEntity<PagedModel<EntityModel<CompraventaResponseDTO>>> getCompraventasEntreUsuarios(
            @PathVariable String idComprador,
            @PathVariable String idVendedor,
            Pageable pageable) {

        List<Compraventa> todas = servicio.obtenerCompraventasEntreUsuarios(idComprador, idVendedor);
        return ResponseEntity.ok(paginar(todas, pageable));
    }

    // --- Helpers ---

    private CompraventaResponseDTO toDTO(Compraventa c) {
        CompraventaResponseDTO dto = new CompraventaResponseDTO();
        dto.setId(c.getId());
        dto.setIdProducto(c.getIdProducto());
        dto.setTitulo(c.getTitulo());
        dto.setPrecio(c.getPrecio());
        dto.setRecogida(c.getRecogida());
        dto.setIdVendedor(c.getIdVendedor());
        dto.setNombreVendedor(c.getNombreVendedor());
        dto.setIdComprador(c.getIdComprador());
        dto.setNombreComprador(c.getNombreComprador());
        dto.setFecha(c.getFecha());
        return dto;
    }


}