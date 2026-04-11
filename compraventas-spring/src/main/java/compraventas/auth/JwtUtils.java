package compraventas.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtUtils {

    private static final String SECRET = "1ea4b589e0dc097269b0a67331ad32d130aabb80";

    public static Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
}