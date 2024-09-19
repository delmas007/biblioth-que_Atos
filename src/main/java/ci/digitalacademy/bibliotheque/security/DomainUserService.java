package ci.digitalacademy.bibliotheque.security;

import ci.digitalacademy.bibliotheque.model.Role;
import ci.digitalacademy.bibliotheque.model.User;
import ci.digitalacademy.bibliotheque.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DomainUserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
//        if (!user.get().isActive()) {
//            throw new IllegalArgumentException("User not active");
//        }

        Role role = user.get().getRole();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + role.getRole());
        List<GrantedAuthority> grantedAuthorities = Collections.singletonList(grantedAuthority);

        return user.map(userRecover -> org.springframework.security.core.userdetails.User.builder()
                .username(userRecover.getUsername())
                .password(userRecover.getPassword())
                .authorities(grantedAuthorities)
                .roles(userRecover.getRole().getRole())
//                .disabled(!userRecover.getActive())
                .build()).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

}
