package ra.authservice.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.authservice.entity.Role;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRegister {
    private String username;
    private String password;
    private String fullName;
    private Boolean gender;
    private LocalDate birthday;
    private String address;
    private String email;
    private String phone;
    private List<Role> roles;
}
