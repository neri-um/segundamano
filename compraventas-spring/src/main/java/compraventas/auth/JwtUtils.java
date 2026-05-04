package compraventas.auth;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static Map<String, Object> parseClaims(String token) throws IOException {
        String[] parts = token.split("\\.");
        if (parts.length < 2) {
            throw new IllegalArgumentException("JWT malformado");
        }
        String json = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
        return MAPPER.readValue(json, new TypeReference<Map<String, Object>>() {});
    }
}