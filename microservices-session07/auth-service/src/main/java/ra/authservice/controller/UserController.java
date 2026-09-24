package ra.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.authservice.dto.request.RefreshTokenRequest;
import ra.authservice.dto.request.UserLogin;
import ra.authservice.dto.request.UserRegister;
import ra.authservice.dto.response.ApiResonse;
import ra.authservice.dto.response.JWTResponse;
import ra.authservice.entity.Users;
import ra.authservice.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Users>> getUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResonse<Users>> register(@RequestBody UserRegister userRegister) {
        return new ResponseEntity<>(new ApiResonse<>(
                true,
                "Đăng ký tài khoản " + userRegister.getUsername() + " thành công",
                userService.registerUser(userRegister),
                null,
                HttpStatus.CREATED
        ), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResonse<JWTResponse>> login(@RequestBody UserLogin userLogin) {
        return new ResponseEntity<>(new ApiResonse<>(
                true,
                "Đăng nhập thành công",
                userService.login(userLogin),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResonse<JWTResponse>> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return new ResponseEntity<>(new ApiResonse<>(
                true,
                "Đã cấp lại access token mới",
                userService.refreshToken(refreshTokenRequest.getRefreshToken()),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }

}
