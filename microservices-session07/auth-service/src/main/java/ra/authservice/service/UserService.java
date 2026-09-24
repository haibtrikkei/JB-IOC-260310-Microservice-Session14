package ra.authservice.service;


import ra.authservice.dto.request.UserLogin;
import ra.authservice.dto.request.UserRegister;
import ra.authservice.dto.response.JWTResponse;
import ra.authservice.entity.Users;

import java.util.List;

public interface UserService {
    List<Users>  findAll();
    Users findById(Long id);
    Users insert(Users user);
    Users update(Long userId, Users user);
    void deleteById(Long id);
    Users registerUser(UserRegister  userRegister);
    JWTResponse login(UserLogin userLogin);
    JWTResponse refreshToken(String refreshToken);
}
