package pasarela.zuul.auth;

public class LoginResponseDTO {

    private String token;
    private String identificador;
    private String nombre;
    private String roles;

    public LoginResponseDTO(String token, String identificador, String nombre, String roles) {
        this.token = token;
        this.identificador = identificador;
        this.nombre = nombre;
        this.roles = roles;
    }

    public String getToken()        { return token; }
    public String getIdentificador(){ return identificador; }
    public String getNombre()       { return nombre; }
    public String getRoles()        { return roles; }
}