package com.thrinod.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TICK.
 */
@Entity
@Table(name = "tick")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TICK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "instrument_key")
    private String instrumentKey;

    @Column(name = "exchange_token")
    private String exchangeToken;

    @Column(name = "trading_symbol")
    private String tradingSymbol;

    @Column(name = "name")
    private String name;

    @Column(name = "last_price")
    private String lastPrice;

    @Column(name = "expiry")
    private String expiry;

    @Column(name = "strike")
    private String strike;

    @Column(name = "tick_size")
    private String tickSize;

    @Column(name = "lot_size")
    private String lotSize;

    @Column(name = "instrument_type")
    private String instrumentType;

    @Column(name = "option_type")
    private String optionType;

    @Column(name = "exchange")
    private String exchange;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TICK id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstrumentKey() {
        return this.instrumentKey;
    }

    public TICK instrumentKey(String instrumentKey) {
        this.setInstrumentKey(instrumentKey);
        return this;
    }

    public void setInstrumentKey(String instrumentKey) {
        this.instrumentKey = instrumentKey;
    }

    public String getExchangeToken() {
        return this.exchangeToken;
    }

    public TICK exchangeToken(String exchangeToken) {
        this.setExchangeToken(exchangeToken);
        return this;
    }

    public void setExchangeToken(String exchangeToken) {
        this.exchangeToken = exchangeToken;
    }

    public String getTradingSymbol() {
        return this.tradingSymbol;
    }

    public TICK tradingSymbol(String tradingSymbol) {
        this.setTradingSymbol(tradingSymbol);
        return this;
    }

    public void setTradingSymbol(String tradingSymbol) {
        this.tradingSymbol = tradingSymbol;
    }

    public String getName() {
        return this.name;
    }

    public TICK name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastPrice() {
        return this.lastPrice;
    }

    public TICK lastPrice(String lastPrice) {
        this.setLastPrice(lastPrice);
        return this;
    }

    public void setLastPrice(String lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getExpiry() {
        return this.expiry;
    }

    public TICK expiry(String expiry) {
        this.setExpiry(expiry);
        return this;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getStrike() {
        return this.strike;
    }

    public TICK strike(String strike) {
        this.setStrike(strike);
        return this;
    }

    public void setStrike(String strike) {
        this.strike = strike;
    }

    public String getTickSize() {
        return this.tickSize;
    }

    public TICK tickSize(String tickSize) {
        this.setTickSize(tickSize);
        return this;
    }

    public void setTickSize(String tickSize) {
        this.tickSize = tickSize;
    }

    public String getLotSize() {
        return this.lotSize;
    }

    public TICK lotSize(String lotSize) {
        this.setLotSize(lotSize);
        return this;
    }

    public void setLotSize(String lotSize) {
        this.lotSize = lotSize;
    }

    public String getInstrumentType() {
        return this.instrumentType;
    }

    public TICK instrumentType(String instrumentType) {
        this.setInstrumentType(instrumentType);
        return this;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public String getOptionType() {
        return this.optionType;
    }

    public TICK optionType(String optionType) {
        this.setOptionType(optionType);
        return this;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public String getExchange() {
        return this.exchange;
    }

    public TICK exchange(String exchange) {
        this.setExchange(exchange);
        return this;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TICK)) {
            return false;
        }
        return getId() != null && getId().equals(((TICK) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TICK{" +
            "id=" + getId() +
            ", instrumentKey='" + getInstrumentKey() + "'" +
            ", exchangeToken='" + getExchangeToken() + "'" +
            ", tradingSymbol='" + getTradingSymbol() + "'" +
            ", name='" + getName() + "'" +
            ", lastPrice='" + getLastPrice() + "'" +
            ", expiry='" + getExpiry() + "'" +
            ", strike='" + getStrike() + "'" +
            ", tickSize='" + getTickSize() + "'" +
            ", lotSize='" + getLotSize() + "'" +
            ", instrumentType='" + getInstrumentType() + "'" +
            ", optionType='" + getOptionType() + "'" +
            ", exchange='" + getExchange() + "'" +
            "}";
    }
}
