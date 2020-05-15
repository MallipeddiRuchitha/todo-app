package com.demo.todoapp.service;


import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
@Slf4j
public class TokenAuthenticationService {

    static final long EXPIRATIONTIME = 864_000_000; // 10 days
    //static final String SECRET = "Di11mo_pg3wAhyot7M2aQvYMPgo4rrmYPG_nzekUXMMuhTyKce462nzewcU9lfL0";
    static final String SECRET= "mo4VzPDA6DkSza56y4o5ipZ4TrD24xYY";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    public static void addAuthentication(HttpServletResponse res, String username) {

        String jwt = createToken(username);

        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + jwt);
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()) :
                    null;
        }
        return null;
    }

    public static String createToken(String username) {
        String jwt = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();

        return jwt;
    }
    public static String createJWT(String id, String issuer, String subject, long ttlMillis) {
        byte[] decodedSecret = Base64.getDecoder().decode(SECRET);
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
        //Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
      /*  JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, SECRET)
                ;
*/
        JwtBuilder builder= Jwts.builder()
                .setSubject("admin")
                .setAudience("Solr")
                .signWith(SignatureAlgorithm.HS256,"qwertypassword".getBytes());
        //if it has been specified, let's add the expiration
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();


    }
    public static String createTokens(){
        try {
            String secretkey="cGFzc3dvcmQ=";

            String jwtToken = Jwts.builder()
                    .setSubject("ruchitha")
                    .setAudience("http://localhost:8080/")
                    .setIssuer("https://dev-1ffztey5.auth0.com/")
                    .signWith(SignatureAlgorithm.HS256,secretkey.getBytes()).compact();
//.signWith(SignatureAlgorithm.HS512,decodedSecret).compact();
            log.info(jwtToken);
            return jwtToken;
        } catch (Exception e)
        {
            log.info(e.getMessage());
            return e.getMessage();
        }
    }

}