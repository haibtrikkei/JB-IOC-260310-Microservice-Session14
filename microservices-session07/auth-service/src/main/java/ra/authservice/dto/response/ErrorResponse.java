package ra.authservice.dto.response;

import java.time.LocalDateTime;

public class ErrorResponse <T>{
    private LocalDateTime timestamp;
    private T error;
    private String path;
}
