package org.example.apidirect.auth.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Store {
    private Integer id;
    private Double lat;
    private Double lon;
    private Integer posCount;
    private String storeNumber;
    private String storeState;
    private Integer memberId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void increasePosCount() {
        this.posCount++;
    }

    public void decreasePosCount() {
        this.posCount--;
    }

    public void updateStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public void updateLat(Double lat) {
        this.lat = lat;
    }

    public void updateLon(Double lon) {
        this.lon = lon;
    }

    public void updateStoreState(String storeState) {
        this.storeState = storeState;
    }

    public boolean isOpen() {
        return "OPEN".equals(this.storeState);
    }

    public boolean isClosed() {
        return "CLOSED".equals(this.storeState);
    }

    public boolean isTempClosed() {
        return "TEMP_CLOSED".equals(this.storeState);
    }
}
