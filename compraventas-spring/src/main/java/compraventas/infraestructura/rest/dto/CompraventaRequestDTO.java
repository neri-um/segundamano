package compraventas.infraestructura.rest.dto;

import javax.validation.constraints.NotBlank;

public class CompraventaRequestDTO {

    @NotBlank(message = "El idProducto es obligatorio")
    private String idProducto;

    @NotBlank(message = "El idComprador es obligatorio")
    private String idComprador;

    public String getIdProducto() { return idProducto; }
    public void setIdProducto(String idProducto) { this.idProducto = idProducto; }
    public String getIdComprador() { return idComprador; }
    public void setIdComprador(String idComprador) { this.idComprador = idComprador; }
}