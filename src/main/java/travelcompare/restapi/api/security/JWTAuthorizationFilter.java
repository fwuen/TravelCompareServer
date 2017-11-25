package travelcompare.restapi.api.security;


import com.google.common.collect.Lists;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import travelcompare.restapi.api.configuration.SecurityConfiguration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if(header == null || !header.startsWith(SecurityConfiguration.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(
            HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization");

        if(token != null) {
            String user = Jwts.parser()
                    .setSigningKey(SecurityConfiguration.SECRET.getBytes())
                    .parseClaimsJws(token.replace(SecurityConfiguration.TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            if(user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, Lists.newArrayList());
            }

            return null;
        }

        return null;
    }

}