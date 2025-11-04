package MeshX.HypeLink.auth.model.dto.res;

import MeshX.HypeLink.auth.model.dto.AuthTokens;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResDto {
    private String email;
    private String name;
    private String role;

    @JsonIgnore
    private AuthTokens authTokens;

    @Builder
    public LoginResDto(String email, String name, String role, AuthTokens authTokens) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.authTokens = authTokens;
    }

    public String getAccessToken() {
        return authTokens.getAccessToken();
    }
}
