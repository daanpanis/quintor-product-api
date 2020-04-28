package nl.quintor.dpanis.productapi.security;

import io.jsonwebtoken.*;
import nl.quintor.dpanis.productapi.config.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    private final JwtConfig config;

    @Autowired
    public TokenProvider(JwtConfig config) {
        this.config = config;
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(config.getSigningKey())
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(config.getAuthoritiesKey(), authorities)
                .signWith(SignatureAlgorithm.HS256, config.getSigningKey())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + config.getTokenDurationSeconds().toMillis()))
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    UsernamePasswordAuthenticationToken getAuthentication(String token, Authentication existingAuth, UserDetails userDetails) {
        final JwtParser jwtParser = Jwts.parser().setSigningKey(config.getSigningKey());
        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

        final Claims claims = claimsJws.getBody();

        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(config.getAuthoritiesKey()).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }
}
