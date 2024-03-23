package com.thrinod.service.impl;

import com.thrinod.domain.TickIndex;
import com.thrinod.repository.TickIndexRepository;
import com.thrinod.service.TickIndexService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.thrinod.domain.TickIndex}.
 */
@Service
@Transactional
public class TickIndexServiceImpl implements TickIndexService {

    private final Logger log = LoggerFactory.getLogger(TickIndexServiceImpl.class);

    private final TickIndexRepository tickIndexRepository;

    public TickIndexServiceImpl(TickIndexRepository tickIndexRepository) {
        this.tickIndexRepository = tickIndexRepository;
    }

    @Override
    public TickIndex save(TickIndex tickIndex) {
        log.debug("Request to save TickIndex : {}", tickIndex);
        return tickIndexRepository.save(tickIndex);
    }

    @Override
    public TickIndex update(TickIndex tickIndex) {
        log.debug("Request to update TickIndex : {}", tickIndex);
        return tickIndexRepository.save(tickIndex);
    }

    @Override
    public Optional<TickIndex> partialUpdate(TickIndex tickIndex) {
        log.debug("Request to partially update TickIndex : {}", tickIndex);

        return tickIndexRepository
            .findById(tickIndex.getId())
            .map(existingTickIndex -> {
                if (tickIndex.getInstrumentKey() != null) {
                    existingTickIndex.setInstrumentKey(tickIndex.getInstrumentKey());
                }
                if (tickIndex.getExchangeToken() != null) {
                    existingTickIndex.setExchangeToken(tickIndex.getExchangeToken());
                }
                if (tickIndex.getTradingSymbol() != null) {
                    existingTickIndex.setTradingSymbol(tickIndex.getTradingSymbol());
                }
                if (tickIndex.getName() != null) {
                    existingTickIndex.setName(tickIndex.getName());
                }
                if (tickIndex.getLastPrice() != null) {
                    existingTickIndex.setLastPrice(tickIndex.getLastPrice());
                }
                if (tickIndex.getExpiry() != null) {
                    existingTickIndex.setExpiry(tickIndex.getExpiry());
                }
                if (tickIndex.getStrike() != null) {
                    existingTickIndex.setStrike(tickIndex.getStrike());
                }
                if (tickIndex.getTickSize() != null) {
                    existingTickIndex.setTickSize(tickIndex.getTickSize());
                }
                if (tickIndex.getLotSize() != null) {
                    existingTickIndex.setLotSize(tickIndex.getLotSize());
                }
                if (tickIndex.getInstrumentType() != null) {
                    existingTickIndex.setInstrumentType(tickIndex.getInstrumentType());
                }
                if (tickIndex.getOptionType() != null) {
                    existingTickIndex.setOptionType(tickIndex.getOptionType());
                }
                if (tickIndex.getExchange() != null) {
                    existingTickIndex.setExchange(tickIndex.getExchange());
                }

                return existingTickIndex;
            })
            .map(tickIndexRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TickIndex> findAll(Pageable pageable) {
        log.debug("Request to get all TickIndices");
        return tickIndexRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TickIndex> findOne(Long id) {
        log.debug("Request to get TickIndex : {}", id);
        return tickIndexRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TickIndex : {}", id);
        tickIndexRepository.deleteById(id);
    }
}
