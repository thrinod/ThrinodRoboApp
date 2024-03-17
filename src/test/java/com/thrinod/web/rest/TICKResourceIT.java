package com.thrinod.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thrinod.IntegrationTest;
import com.thrinod.domain.TICK;
import com.thrinod.repository.TICKRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TICKResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TICKResourceIT {

    private static final String DEFAULT_INSTRUMENT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUMENT_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_EXCHANGE_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_EXCHANGE_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_TRADING_SYMBOL = "AAAAAAAAAA";
    private static final String UPDATED_TRADING_SYMBOL = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_LAST_PRICE = "BBBBBBBBBB";

    private static final String DEFAULT_EXPIRY = "AAAAAAAAAA";
    private static final String UPDATED_EXPIRY = "BBBBBBBBBB";

    private static final String DEFAULT_STRIKE = "AAAAAAAAAA";
    private static final String UPDATED_STRIKE = "BBBBBBBBBB";

    private static final String DEFAULT_TICK_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_TICK_SIZE = "BBBBBBBBBB";

    private static final String DEFAULT_LOT_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_LOT_SIZE = "BBBBBBBBBB";

    private static final String DEFAULT_INSTRUMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUMENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_OPTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_OPTION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EXCHANGE = "AAAAAAAAAA";
    private static final String UPDATED_EXCHANGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ticks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TICKRepository tICKRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTICKMockMvc;

    private TICK tICK;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TICK createEntity(EntityManager em) {
        TICK tICK = new TICK()
            .instrumentKey(DEFAULT_INSTRUMENT_KEY)
            .exchangeToken(DEFAULT_EXCHANGE_TOKEN)
            .tradingSymbol(DEFAULT_TRADING_SYMBOL)
            .name(DEFAULT_NAME)
            .lastPrice(DEFAULT_LAST_PRICE)
            .expiry(DEFAULT_EXPIRY)
            .strike(DEFAULT_STRIKE)
            .tickSize(DEFAULT_TICK_SIZE)
            .lotSize(DEFAULT_LOT_SIZE)
            .instrumentType(DEFAULT_INSTRUMENT_TYPE)
            .optionType(DEFAULT_OPTION_TYPE)
            .exchange(DEFAULT_EXCHANGE);
        return tICK;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TICK createUpdatedEntity(EntityManager em) {
        TICK tICK = new TICK()
            .instrumentKey(UPDATED_INSTRUMENT_KEY)
            .exchangeToken(UPDATED_EXCHANGE_TOKEN)
            .tradingSymbol(UPDATED_TRADING_SYMBOL)
            .name(UPDATED_NAME)
            .lastPrice(UPDATED_LAST_PRICE)
            .expiry(UPDATED_EXPIRY)
            .strike(UPDATED_STRIKE)
            .tickSize(UPDATED_TICK_SIZE)
            .lotSize(UPDATED_LOT_SIZE)
            .instrumentType(UPDATED_INSTRUMENT_TYPE)
            .optionType(UPDATED_OPTION_TYPE)
            .exchange(UPDATED_EXCHANGE);
        return tICK;
    }

    @BeforeEach
    public void initTest() {
        tICK = createEntity(em);
    }

    @Test
    @Transactional
    void createTICK() throws Exception {
        int databaseSizeBeforeCreate = tICKRepository.findAll().size();
        // Create the TICK
        restTICKMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tICK))
            )
            .andExpect(status().isCreated());

        // Validate the TICK in the database
        List<TICK> tICKList = tICKRepository.findAll();
        assertThat(tICKList).hasSize(databaseSizeBeforeCreate + 1);
        TICK testTICK = tICKList.get(tICKList.size() - 1);
        assertThat(testTICK.getInstrumentKey()).isEqualTo(DEFAULT_INSTRUMENT_KEY);
        assertThat(testTICK.getExchangeToken()).isEqualTo(DEFAULT_EXCHANGE_TOKEN);
        assertThat(testTICK.getTradingSymbol()).isEqualTo(DEFAULT_TRADING_SYMBOL);
        assertThat(testTICK.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTICK.getLastPrice()).isEqualTo(DEFAULT_LAST_PRICE);
        assertThat(testTICK.getExpiry()).isEqualTo(DEFAULT_EXPIRY);
        assertThat(testTICK.getStrike()).isEqualTo(DEFAULT_STRIKE);
        assertThat(testTICK.getTickSize()).isEqualTo(DEFAULT_TICK_SIZE);
        assertThat(testTICK.getLotSize()).isEqualTo(DEFAULT_LOT_SIZE);
        assertThat(testTICK.getInstrumentType()).isEqualTo(DEFAULT_INSTRUMENT_TYPE);
        assertThat(testTICK.getOptionType()).isEqualTo(DEFAULT_OPTION_TYPE);
        assertThat(testTICK.getExchange()).isEqualTo(DEFAULT_EXCHANGE);
    }

    @Test
    @Transactional
    void createTICKWithExistingId() throws Exception {
        // Create the TICK with an existing ID
        tICK.setId(1L);

        int databaseSizeBeforeCreate = tICKRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTICKMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tICK))
            )
            .andExpect(status().isBadRequest());

        // Validate the TICK in the database
        List<TICK> tICKList = tICKRepository.findAll();
        assertThat(tICKList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTICKS() throws Exception {
        // Initialize the database
        tICKRepository.saveAndFlush(tICK);

        // Get all the tICKList
        restTICKMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tICK.getId().intValue())))
            .andExpect(jsonPath("$.[*].instrumentKey").value(hasItem(DEFAULT_INSTRUMENT_KEY)))
            .andExpect(jsonPath("$.[*].exchangeToken").value(hasItem(DEFAULT_EXCHANGE_TOKEN)))
            .andExpect(jsonPath("$.[*].tradingSymbol").value(hasItem(DEFAULT_TRADING_SYMBOL)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastPrice").value(hasItem(DEFAULT_LAST_PRICE)))
            .andExpect(jsonPath("$.[*].expiry").value(hasItem(DEFAULT_EXPIRY)))
            .andExpect(jsonPath("$.[*].strike").value(hasItem(DEFAULT_STRIKE)))
            .andExpect(jsonPath("$.[*].tickSize").value(hasItem(DEFAULT_TICK_SIZE)))
            .andExpect(jsonPath("$.[*].lotSize").value(hasItem(DEFAULT_LOT_SIZE)))
            .andExpect(jsonPath("$.[*].instrumentType").value(hasItem(DEFAULT_INSTRUMENT_TYPE)))
            .andExpect(jsonPath("$.[*].optionType").value(hasItem(DEFAULT_OPTION_TYPE)))
            .andExpect(jsonPath("$.[*].exchange").value(hasItem(DEFAULT_EXCHANGE)));
    }

    @Test
    @Transactional
    void getTICK() throws Exception {
        // Initialize the database
        tICKRepository.saveAndFlush(tICK);

        // Get the tICK
        restTICKMockMvc
            .perform(get(ENTITY_API_URL_ID, tICK.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tICK.getId().intValue()))
            .andExpect(jsonPath("$.instrumentKey").value(DEFAULT_INSTRUMENT_KEY))
            .andExpect(jsonPath("$.exchangeToken").value(DEFAULT_EXCHANGE_TOKEN))
            .andExpect(jsonPath("$.tradingSymbol").value(DEFAULT_TRADING_SYMBOL))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.lastPrice").value(DEFAULT_LAST_PRICE))
            .andExpect(jsonPath("$.expiry").value(DEFAULT_EXPIRY))
            .andExpect(jsonPath("$.strike").value(DEFAULT_STRIKE))
            .andExpect(jsonPath("$.tickSize").value(DEFAULT_TICK_SIZE))
            .andExpect(jsonPath("$.lotSize").value(DEFAULT_LOT_SIZE))
            .andExpect(jsonPath("$.instrumentType").value(DEFAULT_INSTRUMENT_TYPE))
            .andExpect(jsonPath("$.optionType").value(DEFAULT_OPTION_TYPE))
            .andExpect(jsonPath("$.exchange").value(DEFAULT_EXCHANGE));
    }

    @Test
    @Transactional
    void getNonExistingTICK() throws Exception {
        // Get the tICK
        restTICKMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTICK() throws Exception {
        // Initialize the database
        tICKRepository.saveAndFlush(tICK);

        int databaseSizeBeforeUpdate = tICKRepository.findAll().size();

        // Update the tICK
        TICK updatedTICK = tICKRepository.findById(tICK.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTICK are not directly saved in db
        em.detach(updatedTICK);
        updatedTICK
            .instrumentKey(UPDATED_INSTRUMENT_KEY)
            .exchangeToken(UPDATED_EXCHANGE_TOKEN)
            .tradingSymbol(UPDATED_TRADING_SYMBOL)
            .name(UPDATED_NAME)
            .lastPrice(UPDATED_LAST_PRICE)
            .expiry(UPDATED_EXPIRY)
            .strike(UPDATED_STRIKE)
            .tickSize(UPDATED_TICK_SIZE)
            .lotSize(UPDATED_LOT_SIZE)
            .instrumentType(UPDATED_INSTRUMENT_TYPE)
            .optionType(UPDATED_OPTION_TYPE)
            .exchange(UPDATED_EXCHANGE);

        restTICKMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTICK.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTICK))
            )
            .andExpect(status().isOk());

        // Validate the TICK in the database
        List<TICK> tICKList = tICKRepository.findAll();
        assertThat(tICKList).hasSize(databaseSizeBeforeUpdate);
        TICK testTICK = tICKList.get(tICKList.size() - 1);
        assertThat(testTICK.getInstrumentKey()).isEqualTo(UPDATED_INSTRUMENT_KEY);
        assertThat(testTICK.getExchangeToken()).isEqualTo(UPDATED_EXCHANGE_TOKEN);
        assertThat(testTICK.getTradingSymbol()).isEqualTo(UPDATED_TRADING_SYMBOL);
        assertThat(testTICK.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTICK.getLastPrice()).isEqualTo(UPDATED_LAST_PRICE);
        assertThat(testTICK.getExpiry()).isEqualTo(UPDATED_EXPIRY);
        assertThat(testTICK.getStrike()).isEqualTo(UPDATED_STRIKE);
        assertThat(testTICK.getTickSize()).isEqualTo(UPDATED_TICK_SIZE);
        assertThat(testTICK.getLotSize()).isEqualTo(UPDATED_LOT_SIZE);
        assertThat(testTICK.getInstrumentType()).isEqualTo(UPDATED_INSTRUMENT_TYPE);
        assertThat(testTICK.getOptionType()).isEqualTo(UPDATED_OPTION_TYPE);
        assertThat(testTICK.getExchange()).isEqualTo(UPDATED_EXCHANGE);
    }

    @Test
    @Transactional
    void putNonExistingTICK() throws Exception {
        int databaseSizeBeforeUpdate = tICKRepository.findAll().size();
        tICK.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTICKMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tICK.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tICK))
            )
            .andExpect(status().isBadRequest());

        // Validate the TICK in the database
        List<TICK> tICKList = tICKRepository.findAll();
        assertThat(tICKList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTICK() throws Exception {
        int databaseSizeBeforeUpdate = tICKRepository.findAll().size();
        tICK.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTICKMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tICK))
            )
            .andExpect(status().isBadRequest());

        // Validate the TICK in the database
        List<TICK> tICKList = tICKRepository.findAll();
        assertThat(tICKList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTICK() throws Exception {
        int databaseSizeBeforeUpdate = tICKRepository.findAll().size();
        tICK.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTICKMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tICK))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TICK in the database
        List<TICK> tICKList = tICKRepository.findAll();
        assertThat(tICKList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTICKWithPatch() throws Exception {
        // Initialize the database
        tICKRepository.saveAndFlush(tICK);

        int databaseSizeBeforeUpdate = tICKRepository.findAll().size();

        // Update the tICK using partial update
        TICK partialUpdatedTICK = new TICK();
        partialUpdatedTICK.setId(tICK.getId());

        partialUpdatedTICK
            .instrumentKey(UPDATED_INSTRUMENT_KEY)
            .exchangeToken(UPDATED_EXCHANGE_TOKEN)
            .tradingSymbol(UPDATED_TRADING_SYMBOL)
            .lastPrice(UPDATED_LAST_PRICE)
            .expiry(UPDATED_EXPIRY)
            .strike(UPDATED_STRIKE)
            .tickSize(UPDATED_TICK_SIZE)
            .lotSize(UPDATED_LOT_SIZE)
            .exchange(UPDATED_EXCHANGE);

        restTICKMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTICK.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTICK))
            )
            .andExpect(status().isOk());

        // Validate the TICK in the database
        List<TICK> tICKList = tICKRepository.findAll();
        assertThat(tICKList).hasSize(databaseSizeBeforeUpdate);
        TICK testTICK = tICKList.get(tICKList.size() - 1);
        assertThat(testTICK.getInstrumentKey()).isEqualTo(UPDATED_INSTRUMENT_KEY);
        assertThat(testTICK.getExchangeToken()).isEqualTo(UPDATED_EXCHANGE_TOKEN);
        assertThat(testTICK.getTradingSymbol()).isEqualTo(UPDATED_TRADING_SYMBOL);
        assertThat(testTICK.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTICK.getLastPrice()).isEqualTo(UPDATED_LAST_PRICE);
        assertThat(testTICK.getExpiry()).isEqualTo(UPDATED_EXPIRY);
        assertThat(testTICK.getStrike()).isEqualTo(UPDATED_STRIKE);
        assertThat(testTICK.getTickSize()).isEqualTo(UPDATED_TICK_SIZE);
        assertThat(testTICK.getLotSize()).isEqualTo(UPDATED_LOT_SIZE);
        assertThat(testTICK.getInstrumentType()).isEqualTo(DEFAULT_INSTRUMENT_TYPE);
        assertThat(testTICK.getOptionType()).isEqualTo(DEFAULT_OPTION_TYPE);
        assertThat(testTICK.getExchange()).isEqualTo(UPDATED_EXCHANGE);
    }

    @Test
    @Transactional
    void fullUpdateTICKWithPatch() throws Exception {
        // Initialize the database
        tICKRepository.saveAndFlush(tICK);

        int databaseSizeBeforeUpdate = tICKRepository.findAll().size();

        // Update the tICK using partial update
        TICK partialUpdatedTICK = new TICK();
        partialUpdatedTICK.setId(tICK.getId());

        partialUpdatedTICK
            .instrumentKey(UPDATED_INSTRUMENT_KEY)
            .exchangeToken(UPDATED_EXCHANGE_TOKEN)
            .tradingSymbol(UPDATED_TRADING_SYMBOL)
            .name(UPDATED_NAME)
            .lastPrice(UPDATED_LAST_PRICE)
            .expiry(UPDATED_EXPIRY)
            .strike(UPDATED_STRIKE)
            .tickSize(UPDATED_TICK_SIZE)
            .lotSize(UPDATED_LOT_SIZE)
            .instrumentType(UPDATED_INSTRUMENT_TYPE)
            .optionType(UPDATED_OPTION_TYPE)
            .exchange(UPDATED_EXCHANGE);

        restTICKMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTICK.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTICK))
            )
            .andExpect(status().isOk());

        // Validate the TICK in the database
        List<TICK> tICKList = tICKRepository.findAll();
        assertThat(tICKList).hasSize(databaseSizeBeforeUpdate);
        TICK testTICK = tICKList.get(tICKList.size() - 1);
        assertThat(testTICK.getInstrumentKey()).isEqualTo(UPDATED_INSTRUMENT_KEY);
        assertThat(testTICK.getExchangeToken()).isEqualTo(UPDATED_EXCHANGE_TOKEN);
        assertThat(testTICK.getTradingSymbol()).isEqualTo(UPDATED_TRADING_SYMBOL);
        assertThat(testTICK.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTICK.getLastPrice()).isEqualTo(UPDATED_LAST_PRICE);
        assertThat(testTICK.getExpiry()).isEqualTo(UPDATED_EXPIRY);
        assertThat(testTICK.getStrike()).isEqualTo(UPDATED_STRIKE);
        assertThat(testTICK.getTickSize()).isEqualTo(UPDATED_TICK_SIZE);
        assertThat(testTICK.getLotSize()).isEqualTo(UPDATED_LOT_SIZE);
        assertThat(testTICK.getInstrumentType()).isEqualTo(UPDATED_INSTRUMENT_TYPE);
        assertThat(testTICK.getOptionType()).isEqualTo(UPDATED_OPTION_TYPE);
        assertThat(testTICK.getExchange()).isEqualTo(UPDATED_EXCHANGE);
    }

    @Test
    @Transactional
    void patchNonExistingTICK() throws Exception {
        int databaseSizeBeforeUpdate = tICKRepository.findAll().size();
        tICK.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTICKMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tICK.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tICK))
            )
            .andExpect(status().isBadRequest());

        // Validate the TICK in the database
        List<TICK> tICKList = tICKRepository.findAll();
        assertThat(tICKList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTICK() throws Exception {
        int databaseSizeBeforeUpdate = tICKRepository.findAll().size();
        tICK.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTICKMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tICK))
            )
            .andExpect(status().isBadRequest());

        // Validate the TICK in the database
        List<TICK> tICKList = tICKRepository.findAll();
        assertThat(tICKList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTICK() throws Exception {
        int databaseSizeBeforeUpdate = tICKRepository.findAll().size();
        tICK.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTICKMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tICK))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TICK in the database
        List<TICK> tICKList = tICKRepository.findAll();
        assertThat(tICKList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTICK() throws Exception {
        // Initialize the database
        tICKRepository.saveAndFlush(tICK);

        int databaseSizeBeforeDelete = tICKRepository.findAll().size();

        // Delete the tICK
        restTICKMockMvc
            .perform(delete(ENTITY_API_URL_ID, tICK.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TICK> tICKList = tICKRepository.findAll();
        assertThat(tICKList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
