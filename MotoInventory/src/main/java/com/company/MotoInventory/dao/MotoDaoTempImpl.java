package com.company.MotoInventory.dao;

import com.company.MotoInventory.repository.Motorcycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MotoDaoTempImpl implements MotoRepository{
    private JdbcTemplate jdbcTemplate;
    //Prepared Statement
    private static final String CREATE_MOTORCYCLE_SQL =
            "INSERT INTO motorcycle(price, vin, make, model, year, color) values(?, ?, ?, ?, ?, ?)";

    private static final String SELECT_MOTORCYCLE_SQL =
            "SELECT * FROM motorcycle WHERE id = ?";

    private static final String SELECT_ALL_MOTORCYCLES_SQL =
            "SELECT * FROM motorcycle";

    private static final String UPDATE_MOTORCYCLE_SQL =
            "UPDATE motorcycle SET price = ?, vin = ?, make = ?, model = ?, year = ?, color = ? WHERE id = ?";

    private static final String DELETE_MOTORCYCLE_SQL =
            "DELETE FROM motorcycle where id = ?";

    @Autowired
    public MotoDaoTempImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public Motorcycle createMotorcycle(Motorcycle motorcycle) {
        jdbcTemplate.update(CREATE_MOTORCYCLE_SQL,
                motorcycle.getPrice(),
                motorcycle.getVin(),
                motorcycle.getMake(),
                motorcycle.getModel(),
                motorcycle.getYear(),
                motorcycle.getColor());
        int id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        motorcycle.setId(id);
        return motorcycle;
    }

    @Override
    public List<Motorcycle> getAllMotorcycle() {
        return jdbcTemplate.query(SELECT_ALL_MOTORCYCLES_SQL, this::mapRowToMotorcycle);
    }

    @Override
    public Motorcycle getMotorcycle(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_MOTORCYCLE_SQL, this::mapRowToMotorcycle, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void updateMotorcycle(Motorcycle motorcycle) {
        jdbcTemplate.update(UPDATE_MOTORCYCLE_SQL,
                motorcycle.getPrice(),
                motorcycle.getVin(),
                motorcycle.getMake(),
                motorcycle.getModel(),
                motorcycle.getYear(),
                motorcycle.getColor(),
                motorcycle.getId());
    }

    @Override
    public void deleteMotorcycle(int id) {
        jdbcTemplate.update(DELETE_MOTORCYCLE_SQL, id);
    }

    private Motorcycle mapRowToMotorcycle(ResultSet rs, int rowNum) throws SQLException {
        Motorcycle motorcycle = new Motorcycle();
        motorcycle.setId(rs.getInt("id"));
        motorcycle.setPrice(rs.getBigDecimal("price"));
        motorcycle.setVin(rs.getString("vin"));
        motorcycle.setMake(rs.getString("make"));
        motorcycle.setModel(rs.getString("model"));
        motorcycle.setYear(rs.getString("year"));
        motorcycle.setColor(rs.getString("color"));

        return motorcycle;
    }
}
