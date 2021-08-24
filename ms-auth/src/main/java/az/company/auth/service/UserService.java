package az.company.auth.service;

import az.company.auth.domain.User;
import az.company.auth.domain.enumeration.UserRole;
import az.company.auth.model.CreateUserRequest;
import az.company.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author MehdiyevCS on 24.08.21
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void createUser(CreateUserRequest createUserRequest) {

        var user = User.builder()
                .name(createUserRequest.getName())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .username(createUserRequest.getUsername().toLowerCase())
                .role(UserRole.of(createUserRequest.getRole()))
                .status(createUserRequest.getStatus())
                .build();

        userRepository.save(user);
    }

}
