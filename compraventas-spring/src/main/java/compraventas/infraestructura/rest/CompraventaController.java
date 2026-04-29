package compraventas.infraestructura.rest;

import java.io.IOException;
import java.net.URI;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import compraventas.aplicacion.puertos.entrada.IServicioCompraventas;
import compraventas.dominio.modelo.Compraventa;
import compraventas.infraestructura.rest.dto.CompraventaRequestDTO;
import compraventas.infraestructura.rest.dto.CompraventaResponseDTO;
import repositorio.EntidadNoEncontrada;

@RestController
@RequestMapping(value = "/api/compraventas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompraventaController {

    private final IServicioCompraventas servicio;

    @Autowired
    private PagedResourcesAssembler<CompraventaResponseDTO> pagedResourcesAssembler;

    // Ensamblador que añade el link "self" a cada elemento del listado
    private final RepresentationModelAssembler<CompraventaResponseDTO, EntityModel<CompraventaResponseDTO>> assembler =
        dto -> {
            EntityModel<CompraventaResponseDTO> model = EntityModel.of(dto);
            try {
                model.add(WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(CompraventaController.class)
                        .getCompraventa(dto.getId()))
                    .withSelfRel());
            } catch (EntidadNoEncontrada ignored) { /* linkTo no ejecuta el método real */ }
            return model;
        };

    public CompraventaController(IServicioCompraventas servicio) {
        this.servicio = servicio;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> realizarCompraventa(
            @Valid @RequestBody CompraventaRequestDTO request) throws IOException {

        Compraventa c = servicio.realizarCompraventa(
                request.getIdProducto(), request.getIdComprador());

        URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(c.getId()).toUri();

        return ResponseEntity.created(nuevaURL).build();
    }

    @GetMapping("/comprador/{idComprador}")
    @PreAuthorize("hasAuthority('USUARIO') and #idComprador == authentication.name")
    public PagedModel<EntityModel<CompraventaResponseDTO>> getComprasDeUsuario(
            @PathVariable String idComprador, Pageable pageable) {

        Page<CompraventaResponseDTO> resultado = servicio
                .obtenerComprasDeUsuario(idComprador, pageable)
                .map(this::toDTO);

        return pagedResourcesAssembler.toModel(resultado, assembler);
    }

    @GetMapping("/vendedor/{idVendedor}")
    @PreAuthorize("hasAuthority('USUARIO') and #idVendedor == authentication.name")
    public PagedModel<EntityModel<CompraventaResponseDTO>> getVentasDeUsuario(
            @PathVariable String idVendedor, Pageable pageable) {

        Page<CompraventaResponseDTO> resultado = servicio
                .obtenerVentasDeUsuario(idVendedor, pageable)
                .map(this::toDTO);

        return pagedResourcesAssembler.toModel(resultado, assembler);
    }

    @GetMapping("/comprador/{idComprador}/vendedor/{idVendedor}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public PagedModel<EntityModel<CompraventaResponseDTO>> getCompraventasEntreUsuarios(
            @PathVariable String idComprador,
            @PathVariable String idVendedor, Pageable pageable) {

        Page<CompraventaResponseDTO> resultado = servicio
                .obtenerCompraventasEntreUsuarios(idComprador, idVendedor, pageable)
                .map(this::toDTO);

        return pagedResourcesAssembler.toModel(resultado, assembler);
    }

    @GetMapping("/{id}")
    public EntityModel<CompraventaResponseDTO> getCompraventa(@PathVariable String id)
            throws EntidadNoEncontrada {

        EntityModel<CompraventaResponseDTO> model = EntityModel.of(toDTO(servicio.obtenerCompraventa(id)));
        model.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(CompraventaController.class).getCompraventa(id))
                .withSelfRel());
        return model;
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