package com.example.apiauth.adapter.out.persistence;

import com.example.apiauth.adapter.out.external.monolith.dto.DriverSyncDto;
import com.example.apiauth.adapter.out.external.monolith.dto.MemberSyncDto;
import com.example.apiauth.adapter.out.external.monolith.dto.PosSyncDto;
import com.example.apiauth.adapter.out.external.monolith.dto.StoreSyncDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Repository
public class InitialSyncRepository {

    private final DataSource writeDataSource;
    private final DataSource readDataSource;

    public InitialSyncRepository(@Qualifier("writeDB") DataSource writeDataSource,
                                  @Qualifier("readDB") DataSource readDataSource) {
        this.writeDataSource = writeDataSource;
        this.readDataSource = readDataSource;
    }

    @Transactional
    public void batchInsertMembers(List<MemberSyncDto> members) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(writeDataSource);

        String sql = """
            INSERT INTO member (id, createdAt, updatedAt, address, email, name, password, phone, refreshToken, region, role)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        jdbcTemplate.batchUpdate(sql, members, 1000, (ps, dto) -> {
            ps.setInt(1, dto.getId());
            ps.setTimestamp(2, dto.getCreatedAt() != null ? Timestamp.valueOf(dto.getCreatedAt()) : null);
            ps.setTimestamp(3, dto.getUpdatedAt() != null ? Timestamp.valueOf(dto.getUpdatedAt()) : null);
            ps.setString(4, dto.getAddress());
            ps.setString(5, dto.getEmail());
            ps.setString(6, dto.getName());
            ps.setString(7, dto.getPassword());
            ps.setString(8, dto.getPhone());
            ps.setString(9, dto.getRefreshToken());
            ps.setString(10, dto.getRegion().name());
            ps.setString(11, dto.getRole().name());
        });

        log.info("Batch inserted {} members", members.size());
    }

    @Transactional
    public void batchInsertStores(List<StoreSyncDto> stores) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(writeDataSource);

        String sql = """
            INSERT INTO store (id, lat, lon, posCount, storeNumber, storeState, member_id)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        jdbcTemplate.batchUpdate(sql, stores, 1000, (ps, dto) -> {
            ps.setInt(1, dto.getId());
            ps.setDouble(2, dto.getLat() != null ? dto.getLat() : 0.0);
            ps.setDouble(3, dto.getLon() != null ? dto.getLon() : 0.0);
            ps.setInt(4, dto.getPosCount() != null ? dto.getPosCount() : 0);
            ps.setString(5, dto.getStoreNumber());
            ps.setObject(6, dto.getStoreState() != null ? dto.getStoreState().ordinal() : null);
            ps.setObject(7, dto.getMemberId());
        });

        log.info("Batch inserted {} stores", stores.size());
    }

    @Transactional
    public void batchInsertPos(List<PosSyncDto> posList) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(writeDataSource);

        String sql = """
            INSERT INTO pos (id, healthCheck, posCode, member_id, store_id)
            VALUES (?, ?, ?, ?, ?)
            """;

        jdbcTemplate.batchUpdate(sql, posList, 1000, (ps, dto) -> {
            ps.setInt(1, dto.getId());
            ps.setBoolean(2, dto.getHealthCheck() != null ? dto.getHealthCheck() : false);
            ps.setString(3, dto.getPosCode());
            ps.setObject(4, dto.getMemberId());
            ps.setObject(5, dto.getStoreId());
        });

        log.info("Batch inserted {} pos", posList.size());
    }

    @Transactional
    public void batchInsertDrivers(List<DriverSyncDto> drivers) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(writeDataSource);

        String sql = """
            INSERT INTO driver (id, carNumber, macAddress, member_id)
            VALUES (?, ?, ?, ?)
            """;

        jdbcTemplate.batchUpdate(sql, drivers, 1000, (ps, dto) -> {
            ps.setInt(1, dto.getId());
            ps.setString(2, dto.getCarNumber());
            ps.setString(3, dto.getMacAddress());
            ps.setObject(4, dto.getMemberId());
        });

        log.info("Batch inserted {} drivers", drivers.size());
    }

    // Read DB 배치 삽입 메서드들

    @Transactional
    public void batchInsertMembersToReadDB(List<MemberSyncDto> members) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(readDataSource);

        String sql = """
            INSERT INTO member (id, createdAt, updatedAt, address, email, name, password, phone, refreshToken, region, role)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        jdbcTemplate.batchUpdate(sql, members, 1000, (ps, dto) -> {
            ps.setInt(1, dto.getId());
            ps.setTimestamp(2, dto.getCreatedAt() != null ? Timestamp.valueOf(dto.getCreatedAt()) : null);
            ps.setTimestamp(3, dto.getUpdatedAt() != null ? Timestamp.valueOf(dto.getUpdatedAt()) : null);
            ps.setString(4, dto.getAddress());
            ps.setString(5, dto.getEmail());
            ps.setString(6, dto.getName());
            ps.setString(7, dto.getPassword());
            ps.setString(8, dto.getPhone());
            ps.setString(9, dto.getRefreshToken());
            ps.setString(10, dto.getRegion().name());
            ps.setString(11, dto.getRole().name());
        });

        log.info("Batch inserted {} members to Read DB", members.size());
    }

    @Transactional
    public void batchInsertStoresToReadDB(List<StoreSyncDto> stores) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(readDataSource);

        String sql = """
            INSERT INTO store (id, lat, lon, posCount, storeNumber, storeState, member_id)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        jdbcTemplate.batchUpdate(sql, stores, 1000, (ps, dto) -> {
            ps.setInt(1, dto.getId());
            ps.setDouble(2, dto.getLat() != null ? dto.getLat() : 0.0);
            ps.setDouble(3, dto.getLon() != null ? dto.getLon() : 0.0);
            ps.setInt(4, dto.getPosCount() != null ? dto.getPosCount() : 0);
            ps.setString(5, dto.getStoreNumber());
            ps.setObject(6, dto.getStoreState() != null ? dto.getStoreState().ordinal() : null);
            ps.setObject(7, dto.getMemberId());
        });

        log.info("Batch inserted {} stores to Read DB", stores.size());
    }

    @Transactional
    public void batchInsertPosToReadDB(List<PosSyncDto> posList) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(readDataSource);

        String sql = """
            INSERT INTO pos (id, healthCheck, posCode, member_id, store_id)
            VALUES (?, ?, ?, ?, ?)
            """;

        jdbcTemplate.batchUpdate(sql, posList, 1000, (ps, dto) -> {
            ps.setInt(1, dto.getId());
            ps.setBoolean(2, dto.getHealthCheck() != null ? dto.getHealthCheck() : false);
            ps.setString(3, dto.getPosCode());
            ps.setObject(4, dto.getMemberId());
            ps.setObject(5, dto.getStoreId());
        });

        log.info("Batch inserted {} pos to Read DB", posList.size());
    }

    @Transactional
    public void batchInsertDriversToReadDB(List<DriverSyncDto> drivers) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(readDataSource);

        String sql = """
            INSERT INTO driver (id, carNumber, macAddress, member_id)
            VALUES (?, ?, ?, ?)
            """;

        jdbcTemplate.batchUpdate(sql, drivers, 1000, (ps, dto) -> {
            ps.setInt(1, dto.getId());
            ps.setString(2, dto.getCarNumber());
            ps.setString(3, dto.getMacAddress());
            ps.setObject(4, dto.getMemberId());
        });

        log.info("Batch inserted {} drivers to Read DB", drivers.size());
    }
}
