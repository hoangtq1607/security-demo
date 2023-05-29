package com.example;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationFailureReason;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import org.springframework.jdbc.core.JdbcTemplate;
import reactor.core.publisher.Mono;

import java.util.List;

@Singleton
public class DbLoginAuthenticationProvider implements AuthenticationProvider {

    @Inject
    JdbcTemplate dataSource;

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest,
                                                          AuthenticationRequest<?, ?> authenticationRequest) {
        String email = String.valueOf(authenticationRequest.getIdentity());
        String password = String.valueOf(authenticationRequest.getSecret());
        String SQL = "select * from test where name = ? and password = ?";
        List<String> query = dataSource.query(SQL, ps -> {
            ps.setString(1, email);
            ps.setString(2, password);
        }, (rs, rowNum) -> rs.getString(1));
        if (query.isEmpty()) {
            return Mono.just(AuthenticationResponse.failure(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH));
        }
        return Mono.just(AuthenticationResponse.success(email));
    }
}
