package az.company.auth.domain.enumeration;

import java.util.stream.Stream;

/**
 * @author MehdiyevCS on 24.08.21
 */
public enum UserRole {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    private String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static UserRole of(String roleName) {
        return Stream.of(UserRole.values())
                .map(UserRole::getName)
                .filter(s -> s.equals(roleName))
                .map(UserRole::valueOf)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
