package com.thrinod.service.impl;

import com.thrinod.domain.TickFutures;
import com.thrinod.repository.TickFuturesRepository;
import com.thrinod.service.TickFuturesService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.thrinod.domain.TickFutures}.
 */
@Service
@Transactional
public class TickFuturesServiceImpl implements TickFuturesService {

    private final Logger log = LoggerFactory.getLogger(TickFuturesServiceImpl.class);

    private final TickFuturesRepository tickFuturesRepository;

    public TickFuturesServiceImpl(TickFuturesRepository tickFuturesRepository) {
        this.tickFuturesRepository = tickFuturesRepository;
    }

    @Override
    public TickFutures save(TickFutures tickFutures) {
        log.debug("Request to save TickFutures : {}", tickFutures);
        return tickFuturesRepository.save(tickFutures);
    }

    @Override
    public TickFutures update(TickFutures tickFutures) {
        log.debug("Request to update TickFutures : {}", tickFutures);
        return tickFuturesRepository.save(tickFutures);
    }

    @Override
    public Optional<TickFutures> partialUpdate(TickFutures tickFutures) {
        log.debug("Request to partially update TickFutures : {}", tickFutures);

        return tickFuturesRepository
            .findById(tickFutures.getId())
            .map(existingTickFutures -> {
                if (tickFutures.getInstrumentKey() != null) {
                    existingTickFutures.setInstrumentKey(tickFutures.getInstrumentKey());
                }
                if (tickFutures.getExchangeToken() != null) {
                    existingTickFutures.setExchangeToken(tickFutures.getExchangeToken());
                }
                if (tickFutures.getTradingSymbol() != null) {
                    existingTickFutures.setTradingSymbol(tickFutures.getTradingSymbol());
                }
                if (tickFutures.getName() != null) {
                    existingTickFutures.setName(tickFutures.getName());
                }
                if (tickFutures.getLastPrice() != null) {
                    existingTickFutures.setLastPrice(tickFutures.getLastPrice());
                }
                if (tickFutures.getExpiry() != null) {
                    existingTickFutures.setExpiry(tickFutures.getExpiry());
                }
                if (tickFutures.getStrike() != null) {
                    existingTickFutures.setStrike(tickFutures.getStrike());
                }
                if (tickFutures.getTickSize() != null) {
                    existingTickFutures.setTickSize(tickFutures.getTickSize());
                }
                if (tickFutures.getLotSize() != null) {
                    existingTickFutures.setLotSize(tickFutures.getLotSize());
                }
                if (tickFutures.getInstrumentType() != null) {
                    existingTickFutures.setInstrumentType(tickFutures.getInstrumentType());
                }
                if (tickFutures.getOptionType() != null) {
                    existingTickFutures.setOptionType(tickFutures.getOptionType());
                }
                if (tickFutures.getExchange() != null) {
                    existingTickFutures.setExchange(tickFutures.getExchange());
                }

                return existingTickFutures;
            })
            .map(tickFuturesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TickFutures> findAll(Pageable pageable) {
        log.debug("Request to get all TickFutures");
        return tickFuturesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TickFutures> findOne(Long id) {
        log.debug("Request to get TickFutures : {}", id);
        return tickFuturesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TickFutures : {}", id);
        tickFuturesRepository.deleteById(id);
    }
}
