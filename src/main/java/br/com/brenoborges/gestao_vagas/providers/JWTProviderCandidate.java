package br.com.brenoborges.gestao_vagas.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTProviderCandidate {

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    @Autowired
    private JWTProviderTokenDecoded jwtProviderTokenDecoded;

    public DecodedJWT validationToken(String token) {

        try {
            return jwtProviderTokenDecoded.tokenDecoded(token, secretKey);
        } catch (JWTVerificationException e) {
            // e.printStackTrace();
            return null;
        }
    }
}
