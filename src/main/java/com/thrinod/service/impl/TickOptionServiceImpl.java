package com.thrinod.service.impl;

import com.thrinod.domain.TickOption;
import com.thrinod.repository.TickOptionRepository;
import com.thrinod.service.TickOptionService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.thrinod.domain.TickOption}.
 */
@Service
@Transactional
public class TickOptionServiceImpl implements TickOptionService {

    private final Logger log = LoggerFactory.getLogger(TickOptionServiceImpl.class);

    private final TickOptionRepository tickOptionRepository;

    public TickOptionServiceImpl(TickOptionRepository tickOptionRepository) {
        this.tickOptionRepository = tickOptionRepository;
    }

    @Override
    public TickOption save(TickOption tickOption) {
        log.debug("Request to save TickOption : {}", tickOption);
        return tickOptionRepository.save(tickOption);
    }

    @Override
    public TickOption update(TickOption tickOption) {
        log.debug("Request to update TickOption : {}", tickOption);
        return tickOptionRepository.save(tickOption);
    }

    @Override
    public Optional<TickOption> partialUpdate(TickOption tickOption) {
        log.debug("Request to partially update TickOption : {}", tickOption);

        return tickOptionRepository
            .findById(tickOption.getId())
            .map(existingTickOption -> {
                if (tickOption.getInstrumentKey() != null) {
                    existingTickOption.setInstrumentKey(tickOption.getInstrumentKey());
                }
                if (tickOption.getExchangeToken() != null) {
                    existingTickOption.setExchangeToken(tickOption.getExchangeToken());
                }
                if (tickOption.getTradingSymbol() != null) {
                    existingTickOption.setTradingSymbol(tickOption.getTradingSymbol());
                }
                if (tickOption.getName() != null) {
                    existingTickOption.setName(tickOption.getName());
                }
                if (tickOption.getLastPrice() != null) {
                    existingTickOption.setLastPrice(tickOption.getLastPrice());
                }
                if (tickOption.getExpiry() != null) {
                    existingTickOption.setExpiry(tickOption.getExpiry());
                }
                if (tickOption.getStrike() != null) {
                    existingTickOption.setStrike(tickOption.getStrike());
                }
                if (tickOption.getTickSize() != null) {
                    existingTickOption.setTickSize(tickOption.getTickSize());
                }
                if (tickOption.getLotSize() != null) {
                    existingTickOption.setLotSize(tickOption.getLotSize());
                }
                if (tickOption.getInstrumentType() != null) {
                    existingTickOption.setInstrumentType(tickOption.getInstrumentType());
                }
                if (tickOption.getOptionType() != null) {
                    existingTickOption.setOptionType(tickOption.getOptionType());
                }
                if (tickOption.getExchange() != null) {
                    existingTickOption.setExchange(tickOption.getExchange());
                }

                return existingTickOption;
            })
            .map(tickOptionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TickOption> findAll(Pageable pageable) {
        log.debug("Request to get all TickOptions");
        return tickOptionRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TickOption> findOne(Long id) {
        log.debug("Request to get TickOption : {}", id);
        return tickOptionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TickOption : {}", id);
        tickOptionRepository.deleteById(id);
    }
}
