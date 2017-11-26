package travelcompare.restapi.api.security;

import com.google.common.base.Verify;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import travelcompare.restapi.data.repository.UserRepository;
import travelcompare.restapi.data.service.UserService;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<travelcompare.restapi.data.model.User> user = userRepository.findFirstByEmailEqualsIgnoreCase(username);

            Verify.verify(user.isPresent());

            return new User(user.get().getEmail(), user.get().getPassword(), Lists.newArrayList());
        } catch(Exception ex) {
            throw new UsernameNotFoundException(username);
        }
    }

}
