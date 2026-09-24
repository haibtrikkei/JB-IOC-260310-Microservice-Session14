package ra.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "refresh-token")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long refreshTokenId;
    private String username;
    @Column(name = "expire_at")
    private Instant expiresAt;
    @Column(name = "refresh_token", nullable = false, unique = true)
    private String refreshToken;
    private Boolean invoke;
}
