package com.example.stocktkl.utils;

import com.example.stocktkl.model.RealtimeQuote;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Tan Dang
 * @since 18/11/2023 - 10:16 am
 */
public class RealtimeQuoteRowMapper implements RowMapper<RealtimeQuote> {

    @Override
    public RealtimeQuote mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        RealtimeQuote realtimeQuote = new RealtimeQuote();
        realtimeQuote.setSymbol(resultSet.getString("symbol"));
        realtimeQuote.setCompanyName(resultSet.getString("company_name"));
        realtimeQuote.setIndustry(resultSet.getString("industry"));
        realtimeQuote.setSector(resultSet.getString("sector"));
        realtimeQuote.setPrice(resultSet.getBigDecimal("price"));
        realtimeQuote.setChangeValue(resultSet.getBigDecimal("change_value"));
        realtimeQuote.setPercentChange(resultSet.getBigDecimal("percent_change"));
        realtimeQuote.setVolume(resultSet.getInt("volume"));
        return realtimeQuote;
    }
}

