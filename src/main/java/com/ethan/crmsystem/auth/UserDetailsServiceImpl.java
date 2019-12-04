package com.ethan.crmsystem.auth;

import com.ethan.crmsystem.infra.domain.User;
import com.ethan.crmsystem.infra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * Description:
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: SecurityUserDetailsService.java, 2018/9/7 9:55 $
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(s)) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        Optional<User> userOptional = userRepository.findByLoginName(s);

        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        SecurityUser securityUser = new SecurityUser(userOptional.get());

        securityUser.setEnabled(true);
        securityUser.setAccountNonLocked(true);
        securityUser.setAccountNonExpired(true);
        securityUser.setCredentialsNonExpired(true);

        return securityUser;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
