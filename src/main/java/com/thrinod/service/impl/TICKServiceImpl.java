package com.thrinod.service.impl;

import com.thrinod.domain.TICK;
import com.thrinod.repository.TICKRepository;
import com.thrinod.service.TICKService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.thrinod.domain.TICK}.
 */
@Service
@Transactional
public class TICKServiceImpl implements TICKService {

    private final Logger log = LoggerFactory.getLogger(TICKServiceImpl.class);

    private final TICKRepository tICKRepository;

    public TICKServiceImpl(TICKRepository tICKRepository) {
        this.tICKRepository = tICKRepository;
    }

    @Override
    public TICK save(TICK tICK) {
        log.debug("Request to save TICK : {}", tICK);
        return tICKRepository.save(tICK);
    }

    @Override
    public TICK update(TICK tICK) {
        log.debug("Request to update TICK : {}", tICK);
        return tICKRepository.save(tICK);
    }

    @Override
    public Optional<TICK> partialUpdate(TICK tICK) {
        log.debug("Request to partially update TICK : {}", tICK);

        return tICKRepository
            .findById(tICK.getId())
            .map(existingTICK -> {
                if (tICK.getInstrumentKey() != null) {
                    existingTICK.setInstrumentKey(tICK.getInstrumentKey());
                }
                if (tICK.getExchangeToken() != null) {
                    existingTICK.setExchangeToken(tICK.getExchangeToken());
                }
                if (tICK.getTradingSymbol() != null) {
                    existingTICK.setTradingSymbol(tICK.getTradingSymbol());
                }
                if (tICK.getName() != null) {
                    existingTICK.setName(tICK.getName());
                }
                if (tICK.getLastPrice() != null) {
                    existingTICK.setLastPrice(tICK.getLastPrice());
                }
                if (tICK.getExpiry() != null) {
                    existingTICK.setExpiry(tICK.getExpiry());
                }
                if (tICK.getStrike() != null) {
                    existingTICK.setStrike(tICK.getStrike());
                }
                if (tICK.getTickSize() != null) {
                    existingTICK.setTickSize(tICK.getTickSize());
                }
                if (tICK.getLotSize() != null) {
                    existingTICK.setLotSize(tICK.getLotSize());
                }
                if (tICK.getInstrumentType() != null) {
                    existingTICK.setInstrumentType(tICK.getInstrumentType());
                }
                if (tICK.getOptionType() != null) {
                    existingTICK.setOptionType(tICK.getOptionType());
                }
                if (tICK.getExchange() != null) {
                    existingTICK.setExchange(tICK.getExchange());
                }

                return existingTICK;
            })
            .map(tICKRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TICK> findAll(Pageable pageable) {
        log.debug("Request to get all TICKS");
        return tICKRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TICK> findOne(Long id) {
        log.debug("Request to get TICK : {}", id);
        return tICKRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TICK : {}", id);
        tICKRepository.deleteById(id);
    }
}
