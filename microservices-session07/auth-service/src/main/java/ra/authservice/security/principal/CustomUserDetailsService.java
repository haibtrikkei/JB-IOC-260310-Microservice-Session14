package ra.authservice.security.principal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ra.authservice.entity.Role;
import ra.authservice.entity.Users;
import ra.authservice.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Tài khoản không tồn tại"));

        return CustomUserDetails.builder()
                .userId(users.getUserId())
                .username(users.getUsername())
                .password(users.getPassword())
                .fullName(users.getFullName())
                .email(users.getEmail())
                .enabled(users.getEnabled())
                .authorities(mapRoleToAuthorities(users.getRoles()))
                .build();
    }

    private Collection<? extends GrantedAuthority> mapRoleToAuthorities(List<Role> roles) {
        return roles.stream().map(role-> new SimpleGrantedAuthority(role.getRoleName().toString())).toList();
    }
}
