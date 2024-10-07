package vn.com.openlab.api.coupon.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponCalculationResponse {
    @JsonProperty("result")
    private Double result;

    // error code
    @JsonProperty("error_message")
    private String errorMessage;
}

