package usuarios.rest.dto;

public class UsuarioCreadoDTO {
	private String id;

	public UsuarioCreadoDTO() {
	}

	public UsuarioCreadoDTO(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
