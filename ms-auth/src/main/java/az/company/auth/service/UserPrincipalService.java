package az.company.auth.service;

import az.company.auth.domain.User;
import az.company.auth.model.UserPrincipal;
import az.company.auth.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author MehdiyevCS on 24.08.21
 */
@Service
public class UserPrincipalService implements UserDetailsService {
    private final UserRepository repository;

    public UserPrincipalService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("USER NOT EXIST"));

        return new UserPrincipal(user);
    }
}
