package MeshX.HypeLink.utils.geocode.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GeocodeDto {
    @JsonProperty("x")
    private String lon;

    @JsonProperty("y")
    private String lat;

    public Double getLonAsDouble() {
        return lon != null ? Double.parseDouble(lon) : null;
    }

    public Double getLatAsDouble() {
        return lat != null ? Double.parseDouble(lat) : null;
    }
}
