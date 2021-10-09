package sb.ecomm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sb.ecomm.user.User;
import sb.ecomm.user.UserRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user =
                userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));

        return new UserDetailsImpl(user.getId(),
                user.getEmail(), user.getPassword(), getAuthorities(user),
                user.isAccountNonExpired(), user.isAccountNonLocked(),
                user.isCredentialsNonExpired(), user.isEnabled());
    }

    private Collection<GrantedAuthority> getAuthorities(User user) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        user.getRole().getAuthorities().forEach(authority -> authorities.add(new SimpleGrantedAuthority(authority.getAuthority())));
        return authorities;
    }
}
