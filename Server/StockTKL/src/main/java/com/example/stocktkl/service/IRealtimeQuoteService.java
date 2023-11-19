package com.example.stocktkl.service;

import com.example.stocktkl.model.RealtimeQuote;

import java.util.List;

/**
 * @author Tan Dang
 * @since 18/11/2023 - 9:56 am
 */
public interface IRealtimeQuoteService {
    List<RealtimeQuote> getQuotes();
}
