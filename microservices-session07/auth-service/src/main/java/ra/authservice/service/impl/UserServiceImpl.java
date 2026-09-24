package ra.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.authservice.dto.request.UserLogin;
import ra.authservice.dto.request.UserRegister;
import ra.authservice.dto.response.JWTResponse;
import ra.authservice.entity.RefreshToken;
import ra.authservice.entity.Users;
import ra.authservice.repository.RefreshTokenRepository;
import ra.authservice.repository.UserRepository;
import ra.authservice.security.jwt.JWTProvider;
import ra.authservice.security.principal.CustomUserDetails;
import ra.authservice.service.UserService;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserDetailsService userDetailsService;

    @Value("${jwt-refresh-expire}")
    private long jwtRefreshExpire;


    @Override
    public JWTResponse refreshToken(String refreshToken) {
        RefreshToken objRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new NoSuchElementException("Refresh token không tồn tại"));

        if(objRefreshToken.getInvoke()){
            throw new RuntimeException("Refresh token đã bị thu hồi");
        }

        if(objRefreshToken.getExpiresAt().isBefore(Instant.now())){
            throw new RuntimeException("Refresh token đã hết hạn");
        }

        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(objRefreshToken.getUsername());

        String accessToken = jwtProvider.generateToken(userDetails);
        return JWTResponse.builder()
                .username(userDetails.getUsername())
                .fullName(userDetails.getFullName())
                .email(userDetails.getEmail())
                .enabled(userDetails.isEnabled())
                .authorities(userDetails.getAuthorities())
                .accessToken(accessToken)
                .refreshToken(null)
                .build();
    }

    @Override
    public JWTResponse login(UserLogin userLogin) {
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword()));
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            String accessToken = jwtProvider.generateToken(userDetails);

            //Tạo ra refresh-token
            RefreshToken refreshToken = RefreshToken.builder()
                    .username(userDetails.getUsername())
                    .expiresAt(Instant.now().plusMillis(jwtRefreshExpire))
                    .refreshToken(UUID.randomUUID().toString())
                    .invoke(false)
                    .build();
            //save vao database
            refreshTokenRepository.save(refreshToken);

            return JWTResponse.builder()
                    .username(userDetails.getUsername())
                    .fullName(userDetails.getFullName())
                    .email(userDetails.getEmail())
                    .enabled(userDetails.isEnabled())
                    .authorities(userDetails.getAuthorities())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken.getRefreshToken())
                    .build();
        }catch (Exception e){
            log.error("Username hoặc password không đúng ", e.getMessage());
            throw new UsernameNotFoundException("Username hoặc password không đúng "+ e.getMessage());
        }
    }


    @Override
    public Users registerUser(UserRegister userRegister) {
        Users users = Users.builder()
                .username(userRegister.getUsername())
                .password(passwordEncoder.encode(userRegister.getPassword()))
                .email(userRegister.getEmail())
                .phone(userRegister.getPhone())
                .fullName(userRegister.getFullName())
                .roles(userRegister.getRoles())
                .gender(userRegister.getGender())
                .enabled(true)
                .address(userRegister.getAddress())
                .build();
        return userRepository.save(users);
    }




    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Users findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại user " + id));
    }

    @Override
    public Users insert(Users user) {
        return userRepository.save(user);
    }

    @Override
    public Users update(Long userId, Users user) {
        userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Không tồn tại user " + userId));
        user.setUserId(userId);
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại user " + id));
        userRepository.deleteById(id);
    }
}
