package com.ethan.crmsystem.common;

import com.ethan.crmsystem.infra.domain.User;
import com.ethan.crmsystem.exception.AuthenticationException;
import com.ethan.crmsystem.infra.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class RequestContext {

    @Value("${jwt.header:Authorization}")
    private String header;

    private String prefix;

    private String secret;

    private UserRepository userRepository;

    public User getRequestUser() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        String token = request.getHeader(header);

        if (StringUtils.isEmpty(token) || !token.startsWith(prefix)) {
            return null;
        }

        token = token.substring(prefix.length());

        Claims claims = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        String userId =  claims.getSubject();
        if (userId == null) {
            throw new AuthenticationException();
        }
        Optional<User> user = userRepository.findById(userId);
        return user.get();
    }

    @Value("${jwt.prefix}")
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
