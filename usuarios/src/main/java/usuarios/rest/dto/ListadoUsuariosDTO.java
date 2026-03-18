package usuarios.rest.dto;

import java.util.List;

public class ListadoUsuariosDTO {

<<<<<<< Mergear
    private List<ResumenConURL> usuarios;

    public ListadoUsuariosDTO(List<ResumenConURL> usuarios) {
        this.usuarios = usuarios;
    }

    public List<ResumenConURL> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<ResumenConURL> usuarios) {
        this.usuarios = usuarios;
    }

    // ── Clase interna: resumen + URL del recurso completo ────────────────────
    public static class ResumenConURL {
        private String url;
        private UsuarioResumenDTO usuario;

        public ResumenConURL(String url, UsuarioResumenDTO usuario) {
            this.url = url;
            this.usuario = usuario;
        }

        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }

        public UsuarioResumenDTO getUsuario() { return usuario; }
        public void setUsuario(UsuarioResumenDTO usuario) { this.usuario = usuario; }
    }
=======
	private List<ResumenConURL> usuarios;

	public ListadoUsuariosDTO() {
	}

	public ListadoUsuariosDTO(List<ResumenConURL> usuarios) {
		this.usuarios = usuarios;
	}

	public List<ResumenConURL> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<ResumenConURL> usuarios) {
		this.usuarios = usuarios;
	}

	public static class ResumenConURL {
		private String url;
		private UsuarioResumenDTO usuario;

		public ResumenConURL() {
		}

		public ResumenConURL(String url, UsuarioResumenDTO usuario) {
			this.url = url;
			this.usuario = usuario;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public UsuarioResumenDTO getUsuario() {
			return usuario;
		}

		public void setUsuario(UsuarioResumenDTO usuario) {
			this.usuario = usuario;
		}
	}
>>>>>>> main
}
