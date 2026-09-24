package ra.authservice.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApiResonse <T>{
    private boolean success;
    private String message;
    private T data;
    private ErrorResponse<T> errors;
    private HttpStatus status;
}
