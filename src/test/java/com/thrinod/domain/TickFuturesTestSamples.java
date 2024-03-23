package com.thrinod.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TickFuturesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TickFutures getTickFuturesSample1() {
        return new TickFutures()
            .id(1L)
            .instrumentKey("instrumentKey1")
            .exchangeToken("exchangeToken1")
            .tradingSymbol("tradingSymbol1")
            .name("name1")
            .lastPrice("lastPrice1")
            .expiry("expiry1")
            .strike("strike1")
            .tickSize("tickSize1")
            .lotSize("lotSize1")
            .instrumentType("instrumentType1")
            .optionType("optionType1")
            .exchange("exchange1");
    }

    public static TickFutures getTickFuturesSample2() {
        return new TickFutures()
            .id(2L)
            .instrumentKey("instrumentKey2")
            .exchangeToken("exchangeToken2")
            .tradingSymbol("tradingSymbol2")
            .name("name2")
            .lastPrice("lastPrice2")
            .expiry("expiry2")
            .strike("strike2")
            .tickSize("tickSize2")
            .lotSize("lotSize2")
            .instrumentType("instrumentType2")
            .optionType("optionType2")
            .exchange("exchange2");
    }

    public static TickFutures getTickFuturesRandomSampleGenerator() {
        return new TickFutures()
            .id(longCount.incrementAndGet())
            .instrumentKey(UUID.randomUUID().toString())
            .exchangeToken(UUID.randomUUID().toString())
            .tradingSymbol(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .lastPrice(UUID.randomUUID().toString())
            .expiry(UUID.randomUUID().toString())
            .strike(UUID.randomUUID().toString())
            .tickSize(UUID.randomUUID().toString())
            .lotSize(UUID.randomUUID().toString())
            .instrumentType(UUID.randomUUID().toString())
            .optionType(UUID.randomUUID().toString())
            .exchange(UUID.randomUUID().toString());
    }
}
