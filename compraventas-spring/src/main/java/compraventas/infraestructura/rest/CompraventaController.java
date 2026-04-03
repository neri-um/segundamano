package compraventas.infraestructura.rest;

import compraventas.aplicacion.puertos.entrada.IServicioCompraventas;
import compraventas.dominio.modelo.Compraventa;
import compraventas.infraestructura.rest.dto.CompraventaRequestDTO;
import compraventas.infraestructura.rest.dto.CompraventaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/api/compraventas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompraventaController {

    private final IServicioCompraventas servicio;

    @Autowired
    private PagedResourcesAssembler<CompraventaResponseDTO> pagedResourcesAssembler;

    public CompraventaController(IServicioCompraventas servicio) {
        this.servicio = servicio;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> realizarCompraventa(
            @Valid @RequestBody CompraventaRequestDTO request) {

        Compraventa c = servicio.realizarCompraventa(
                request.getIdProducto(), request.getIdComprador());

        URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(c.getId()).toUri();

        return ResponseEntity.created(nuevaURL).build();
    }

    @GetMapping("/comprador/{idComprador}")
    public PagedModel<EntityModel<CompraventaResponseDTO>> getComprasDeUsuario(
            @PathVariable String idComprador,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("fecha").descending());
        Page<CompraventaResponseDTO> resultado = servicio
                .obtenerComprasDeUsuario(idComprador, pageable)
                .map(this::toDTO);

        return pagedResourcesAssembler.toModel(resultado);
    }

    @GetMapping("/vendedor/{idVendedor}")
    public PagedModel<EntityModel<CompraventaResponseDTO>> getVentasDeUsuario(
            @PathVariable String idVendedor,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("fecha").descending());
        Page<CompraventaResponseDTO> resultado = servicio
                .obtenerVentasDeUsuario(idVendedor, pageable)
                .map(this::toDTO);

        return pagedResourcesAssembler.toModel(resultado);
    }

    @GetMapping("/comprador/{idComprador}/vendedor/{idVendedor}")
    public PagedModel<EntityModel<CompraventaResponseDTO>> getCompraventasEntreUsuarios(
            @PathVariable String idComprador,
            @PathVariable String idVendedor,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("fecha").descending());
        Page<CompraventaResponseDTO> resultado = servicio
                .obtenerCompraventasEntreUsuarios(idComprador, idVendedor, pageable)
                .map(this::toDTO);

        return pagedResourcesAssembler.toModel(resultado);
    }

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