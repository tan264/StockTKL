package com.example.stocktkl.repository;

import com.example.stocktkl.model.RealtimeQuote;
import com.example.stocktkl.utils.RealtimeQuoteRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Tan Dang
 * @since 17/11/2023 - 10:56 pm
 */
@Repository
public class RealtimeQuoteRepository {

    private final JdbcTemplate jdbcTemplate;

    public RealtimeQuoteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<RealtimeQuote> getRealtimeQuote() {
        String sql = """
                select s.symbol, s.company_name, s.industry, s.sector, q.price, q.change_value, q.percent_change, q.volume
                from quotes as q
                left join stock_tkl.stocks s on s.stock_id = q.stock_id
                where q.time_stamp >= (select max(time_stamp) from quotes where stock_id = q.stock_id)
                """;
        return jdbcTemplate.query(sql, new RealtimeQuoteRowMapper());
    }

}
