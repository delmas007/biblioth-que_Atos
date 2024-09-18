package ci.digitalacademy.bibliotheque.utils;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;

public class SecurityUtils {
    public static  final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS256;
    public static final String AUTHORITIES_KEY = "auth";
}
