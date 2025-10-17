package MeshX.HypeLink.auth.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResDto {
    private String name;
    private String role;

    @JsonIgnore
    private AuthTokens authTokens;

    @Builder
    public LoginResDto(String name, String role, AuthTokens authTokens) {
        this.name = name;
        this.role = role;
        this.authTokens = authTokens;
    }

    public String getAccessToken() {
        return authTokens.getAccessToken();
    }
}
