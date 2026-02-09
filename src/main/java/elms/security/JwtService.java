package elms.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value; // -> Make sure this is the SPRING import, not Lombok!
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    // 1. INJECT VARIABLES FROM APPLICATION.PROPERTIES
    @Value("${security.jwt.secret-key}") //Make Sure this Value is not Lombok annotation
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    //2.Generate the Token
    public String generateToken(UserDetails userDetails){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("role", userDetails.getAuthorities()) //When the user logs in, the Frontend decodes the token. If it sees "role": "MANAGER", it shows the "Approve Leave" button. If it sees "EMPLOYEE", it hides it.
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSignInKey() , SignatureAlgorithm.HS256)
                //This is HS256 algorithm. Cannot read a "String"[Test].
                //They only understand Bytes.The Problem: SECRET_KEY is just text.
                .compact();
    }
                                                                                                                            /*
                                                                                                                                orElse we can use .signWith(
                                                                                                                                                Keys.hmacShaKeyFor(SECRET_KEY.getBytes()),
                                                                                                                                                SignatureAlgorithm.HS256
                                                                                                                                        )
                                                                                                                                This is Better for Normal Simple Projects
                                                                                                                             */

    //This convert the String to Byte and also allow
    // ----------Industry standard. Allows for much more complex, un-typeable keys---------------
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //3.Exact UserName
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }


    public <G> G extractClaim(String token, Function<Claims, G> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

                                                                                                                    /*
                                                                                                                    <G> ->Generic Type Parameter[It tells Java: I don't know what type of data we are returning yet. It could be anything. Let's call this mystery type 'G'.]
                                                                                                                    G   ->This method will return a value of type 'G'.
                                                                                                                    Function<Claims, G >                    Input : Claims (The map of data: {sub: "John", exp: 12345} and Output: G (The specific piece you want).
                                                                                                                                |    |
                                                                                                                                v    v
                                                                                                                    Function<Input, Output>
                                                                                                                     */
    //5.THE GOGGLES.[Open the Box]
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()                         //Start the inspection tool.
                .setSigningKey(getSignInKey())              //Load the Secret Key to verify the signature (Checking: "Is this fake?").
                .build()                                    //Finish setting up the tool.
                .parseClaimsJws(token)                      //Unlock and verify the specific token string.If the token was tampered with (hacked) or expired, this line explodes (throws an Exception).
                .getBody();                                 //Extract the Data (Payload) so we can use it.
    }

    //4.Logic Important
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token); // Use your "exactUsername" method here
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Helper: Check if today is after the expiration date
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Helper: Get the expiration date from the token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
