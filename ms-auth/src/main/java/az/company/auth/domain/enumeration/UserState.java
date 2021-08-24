package az.company.auth.domain.enumeration;

import java.util.stream.Stream;

/**
 * @author MehdiyevCS on 24.08.21
 */
public enum UserState {
    BLOCKED,
    ACTIVE;

    public static UserState of(String status) {
        return Stream.of(UserState.values())
                .map(UserState::name)
                .filter(s -> s.equalsIgnoreCase(status))
                .map(UserState::valueOf)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
