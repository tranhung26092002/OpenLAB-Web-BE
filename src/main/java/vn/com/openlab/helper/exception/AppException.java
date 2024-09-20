package vn.com.openlab.helper.exception;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class AppException extends RuntimeException {
    private ErrorCode errorCode;
}
