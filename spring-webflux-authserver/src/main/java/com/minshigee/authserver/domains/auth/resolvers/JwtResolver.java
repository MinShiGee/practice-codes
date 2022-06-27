package com.minshigee.authserver.domains.auth.resolvers;

import com.minshigee.authserver.domains.auth.entities.LoginInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.*;

@Component
public class JwtResolver {

    @Value("${spring.project.jjwt.secretkey}")
    private String secretKey;
    @Value("${spring.project.jjwt.expiration}")
    private String expirationTime;
    @Value("${spring.project.jjwt.tokenname}")
    private String tokenName;
    @Value("${spring.service.root.domain}")
    private String serviceDomains;
    private Key key;

    @PostConstruct
    public void initialize() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Long getUserCodeFromToken(String token) {
        return Long.parseLong(getAllClaimsFromToken(token).getSubject());
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        try {
            final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        }
        catch (Exception e) {
            return true;
        }
    }

    public String generateToken(LoginInfo loginInfo) {
        Map<String, Object> claims = new HashMap<>();

        //List<Role> roles = authInfo.extractAuthorities(); //TODO 권한 부여 코드 작성

        //claims.put("role", roles);
        claims.put("account", loginInfo.getAccountId());
        claims.put("provider", loginInfo.getProvider());
        return doGenerateToken(claims, loginInfo.getAuthInfoId());
    }

    private String doGenerateToken(Map<String, Object> claims, Long authInfoId) {
        Long expirationTimeLong = Long.parseLong(expirationTime); //in second
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(authInfoId))
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    public String resolveToken(ServerHttpRequest request) {
        Assert.notNull(request.getCookies().getFirst(tokenName), "accesstoken Cookie가 비었습니다.");
        return request.getCookies().getFirst(tokenName).getValue();
    }

    public Authentication getAuthentication(String token) {
       /* CustomAuthentication authentication = new CustomAuthentication(
                getUserCodeFromToken(token),
                getAllClaimsFromToken(token),
                token
        );
        authentication.setIsAuthenticated(validateToken(token));

        return authentication;*/
        return null;
    }

    public Boolean isAppropriateRequestForFilter(ServerHttpRequest request) {
        if(!request.getCookies().containsKey(tokenName))
            return false;
        String token = request.getCookies().getFirst(tokenName).getValue();
        return validateToken(token);
    }

    public void addCookieAccessTokenToResponse(ServerHttpResponse res, String token) {
        res.addCookie(makeAddingResponseCookieAccessToken(token));
    }

    protected ResponseCookie makeAddingResponseCookieAccessToken(String token) {
        return ResponseCookie.from(tokenName, token)
                .path("/")
                .domain(serviceDomains)
                .build();
    }

    public ResponseCookie makeDeletingResponseCookieAccessToken() {
        return ResponseCookie.from(tokenName, null)
                .maxAge(0).path("/")
                .build();
    }

}
