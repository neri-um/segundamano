package productos.auth;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Map;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String token = null;
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring("Bearer ".length()).trim();
        }

        if (token == null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token != null) {
            try {
                Map<String, Object> claims = JwtUtils.parseClaims(token);
                Object subObj = claims.get("sub");
                String subject = subObj != null ? String.valueOf(subObj) : "";
                String rolesValue = claims.get("roles") != null
                        ? String.valueOf(claims.get("roles"))
                        : "";
                String[] roles = rolesValue.isEmpty() ? new String[0] : rolesValue.split(",");

                if (subject.isBlank()) {
                    throw new IllegalArgumentException("JWT sin subject");
                }

                ArrayList<GrantedAuthority> authorities = new ArrayList<>();
                for (String rol : roles)
                    authorities.add(new SimpleGrantedAuthority(rol));

                UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(subject, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT no válido");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}