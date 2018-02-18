package authentication;

import java.security.Key;

import db.UserDBClient;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;
import util.DateManager;
import util.UUIDManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kylemcgrew on 9/15/17.
 */
public class TokenManager {

    private static Key key = MacProvider.generateKey();

    public static String createJWT(String username) {
        String uuid = UUIDManager.generateUUID();
        String compactJws = Jwts.builder()
                .setSubject(username)
                .setId(uuid)
                .setIssuedAt(DateManager.getCurrentDate())
                .setExpiration(DateManager.getExperationDate())
                .setIssuer("app")
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
        return compactJws;
    }

    public static Boolean verifyJWT(String token) {
        try {
            Jwt jwt = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;

        } catch (SignatureException e) {
            return false;
        }
    }

    public static Claims parseJWT(String jwt) {

        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt).getBody();
        System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());

        return claims;
    }

    public static Boolean verifyUser(String token) {

        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        Date now = new Date();
        if (claims.getExpiration().after(now)) {
            return true;
        }
        return false;
    }
}
