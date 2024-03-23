package com.thrinod.service.impl;

import com.thrinod.domain.TickStock;
import com.thrinod.repository.TickStockRepository;
import com.thrinod.service.TickStockService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.thrinod.domain.TickStock}.
 */
@Service
@Transactional
public class TickStockServiceImpl implements TickStockService {

    private final Logger log = LoggerFactory.getLogger(TickStockServiceImpl.class);

    private final TickStockRepository tickStockRepository;

    public TickStockServiceImpl(TickStockRepository tickStockRepository) {
        this.tickStockRepository = tickStockRepository;
    }

    @Override
    public TickStock save(TickStock tickStock) {
        log.debug("Request to save TickStock : {}", tickStock);
        return tickStockRepository.save(tickStock);
    }

    @Override
    public TickStock update(TickStock tickStock) {
        log.debug("Request to update TickStock : {}", tickStock);
        return tickStockRepository.save(tickStock);
    }

    @Override
    public Optional<TickStock> partialUpdate(TickStock tickStock) {
        log.debug("Request to partially update TickStock : {}", tickStock);

        return tickStockRepository
            .findById(tickStock.getId())
            .map(existingTickStock -> {
                if (tickStock.getInstrumentKey() != null) {
                    existingTickStock.setInstrumentKey(tickStock.getInstrumentKey());
                }
                if (tickStock.getExchangeToken() != null) {
                    existingTickStock.setExchangeToken(tickStock.getExchangeToken());
                }
                if (tickStock.getTradingSymbol() != null) {
                    existingTickStock.setTradingSymbol(tickStock.getTradingSymbol());
                }
                if (tickStock.getName() != null) {
                    existingTickStock.setName(tickStock.getName());
                }
                if (tickStock.getLastPrice() != null) {
                    existingTickStock.setLastPrice(tickStock.getLastPrice());
                }
                if (tickStock.getExpiry() != null) {
                    existingTickStock.setExpiry(tickStock.getExpiry());
                }
                if (tickStock.getStrike() != null) {
                    existingTickStock.setStrike(tickStock.getStrike());
                }
                if (tickStock.getTickSize() != null) {
                    existingTickStock.setTickSize(tickStock.getTickSize());
                }
                if (tickStock.getLotSize() != null) {
                    existingTickStock.setLotSize(tickStock.getLotSize());
                }
                if (tickStock.getInstrumentType() != null) {
                    existingTickStock.setInstrumentType(tickStock.getInstrumentType());
                }
                if (tickStock.getOptionType() != null) {
                    existingTickStock.setOptionType(tickStock.getOptionType());
                }
                if (tickStock.getExchange() != null) {
                    existingTickStock.setExchange(tickStock.getExchange());
                }

                return existingTickStock;
            })
            .map(tickStockRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TickStock> findAll(Pageable pageable) {
        log.debug("Request to get all TickStocks");
        return tickStockRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TickStock> findOne(Long id) {
        log.debug("Request to get TickStock : {}", id);
        return tickStockRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TickStock : {}", id);
        tickStockRepository.deleteById(id);
    }
}
