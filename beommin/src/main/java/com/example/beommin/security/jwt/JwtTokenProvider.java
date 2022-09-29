package com.example.beommin.security.jwt;

import com.example.beommin.security.CustomUserDetails;
import com.example.beommin.domain.user.entity.User;
import com.example.beommin.domain.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@PropertySource("classpath:application.yaml")
public class JwtTokenProvider {

  private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

  @Value("${jwt.secretKey}")
  private String secretKey;

  @Value("${jwt.cookieKey}")
  private String cookieKey;

  private final UserRepository userRepository;

  public JwtTokenProvider(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String createAccessToken(Authentication authentication) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + JwtExpiration.ACCESS_TOKEN_EXPIRATION_TIME.getValue());

    CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

    String userId = user.getName();
    String role = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    return Jwts.builder()
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .setSubject(userId)
        .claim("role", role)
        .claim("email", user.getEmail())
        .setIssuedAt(now)
        .setExpiration(validity)
        .compact();
  }

  public void createRefreshToken(Authentication authentication, HttpServletResponse response) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + JwtExpiration.REFRESH_TOKEN_EXPIRATION_TIME.getValue());

    String refreshToken = Jwts.builder()
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .setIssuedAt(now)
        .setExpiration(validity)
        .compact();

    saveRefreshToken(authentication, refreshToken);
  }

  public String generateAccessToken(User user) {
    return generateToken(user, JwtExpiration.ACCESS_TOKEN_EXPIRATION_TIME.getValue());
  }

  public String generateRefreshToken(User user) {
    return null;
  }

  public String getEmailFromJwt(String token) {
    try {
      Object email = extractClaims(token).get("email");
      return String.valueOf(email);
    } catch (ExpiredJwtException e) {
      return String.valueOf(e.getClaims().get("email"));
    }
  }

  public Long getIdFromJwt(String token) {
    Object id = extractClaims(token).get("id");
    return Long.valueOf(String.valueOf(id));
  }

  public String resolveToken(HttpServletRequest request) {
    String header = request.getHeader("Authorization");
    if(StringUtils.hasText(header)) return header;
    return null;
  }

  public Authentication getAuthentication(String token) {

    Claims claims = extractClaims(token);
    String email = getEmailFromJwt(token);

    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get("role").toString().split(","))
            .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    CustomUserDetails userDetails = new CustomUserDetails(Long.valueOf(claims.getSubject()), email, authorities);

    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (ExpiredJwtException e) {
      logger.info("만료된 JWT 토큰입니다.");
    } catch (UnsupportedJwtException e) {
      logger.info("지원되지 않는 JWT 토큰입니다.");
    } catch (IllegalStateException e) {
      logger.info("JWT 토큰이 잘못되었습니다");
    } catch (SignatureException | MalformedJwtException e) {
      logger.info("JWT 토큰이 변조되었습니다.");
    }
    return true;
  }

  private String generateToken(User user, Long expirationTime) {
    Claims claims = Jwts.claims();
    claims.put("id", user.getId());
    claims.put("email", user.getEmail());
    Date now = new Date();

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + expirationTime))
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  private Claims extractClaims(String token) {
    try {
      return Jwts.parser()
          .setSigningKey(secretKey)
          .parseClaimsJws(token)
          .getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }

  private void saveRefreshToken(Authentication authentication, String refreshToken) {

    CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
    Long id = Long.valueOf(user.getName());

    userRepository.updateRefreshToken(id, refreshToken);
  }
}
