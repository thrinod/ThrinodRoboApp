package com.thrinod.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thrinod.IntegrationTest;
import com.thrinod.domain.TickIndex;
import com.thrinod.repository.TickIndexRepository;
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
 * Integration tests for the {@link TickIndexResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TickIndexResourceIT {

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

    private static final String ENTITY_API_URL = "/api/tick-indices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TickIndexRepository tickIndexRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTickIndexMockMvc;

    private TickIndex tickIndex;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TickIndex createEntity(EntityManager em) {
        TickIndex tickIndex = new TickIndex()
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
        return tickIndex;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TickIndex createUpdatedEntity(EntityManager em) {
        TickIndex tickIndex = new TickIndex()
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
        return tickIndex;
    }

    @BeforeEach
    public void initTest() {
        tickIndex = createEntity(em);
    }

    @Test
    @Transactional
    void createTickIndex() throws Exception {
        int databaseSizeBeforeCreate = tickIndexRepository.findAll().size();
        // Create the TickIndex
        restTickIndexMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tickIndex))
            )
            .andExpect(status().isCreated());

        // Validate the TickIndex in the database
        List<TickIndex> tickIndexList = tickIndexRepository.findAll();
        assertThat(tickIndexList).hasSize(databaseSizeBeforeCreate + 1);
        TickIndex testTickIndex = tickIndexList.get(tickIndexList.size() - 1);
        assertThat(testTickIndex.getInstrumentKey()).isEqualTo(DEFAULT_INSTRUMENT_KEY);
        assertThat(testTickIndex.getExchangeToken()).isEqualTo(DEFAULT_EXCHANGE_TOKEN);
        assertThat(testTickIndex.getTradingSymbol()).isEqualTo(DEFAULT_TRADING_SYMBOL);
        assertThat(testTickIndex.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTickIndex.getLastPrice()).isEqualTo(DEFAULT_LAST_PRICE);
        assertThat(testTickIndex.getExpiry()).isEqualTo(DEFAULT_EXPIRY);
        assertThat(testTickIndex.getStrike()).isEqualTo(DEFAULT_STRIKE);
        assertThat(testTickIndex.getTickSize()).isEqualTo(DEFAULT_TICK_SIZE);
        assertThat(testTickIndex.getLotSize()).isEqualTo(DEFAULT_LOT_SIZE);
        assertThat(testTickIndex.getInstrumentType()).isEqualTo(DEFAULT_INSTRUMENT_TYPE);
        assertThat(testTickIndex.getOptionType()).isEqualTo(DEFAULT_OPTION_TYPE);
        assertThat(testTickIndex.getExchange()).isEqualTo(DEFAULT_EXCHANGE);
    }

    @Test
    @Transactional
    void createTickIndexWithExistingId() throws Exception {
        // Create the TickIndex with an existing ID
        tickIndex.setId(1L);

        int databaseSizeBeforeCreate = tickIndexRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTickIndexMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tickIndex))
            )
            .andExpect(status().isBadRequest());

        // Validate the TickIndex in the database
        List<TickIndex> tickIndexList = tickIndexRepository.findAll();
        assertThat(tickIndexList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTickIndices() throws Exception {
        // Initialize the database
        tickIndexRepository.saveAndFlush(tickIndex);

        // Get all the tickIndexList
        restTickIndexMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tickIndex.getId().intValue())))
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
    void getTickIndex() throws Exception {
        // Initialize the database
        tickIndexRepository.saveAndFlush(tickIndex);

        // Get the tickIndex
        restTickIndexMockMvc
            .perform(get(ENTITY_API_URL_ID, tickIndex.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tickIndex.getId().intValue()))
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
    void getNonExistingTickIndex() throws Exception {
        // Get the tickIndex
        restTickIndexMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTickIndex() throws Exception {
        // Initialize the database
        tickIndexRepository.saveAndFlush(tickIndex);

        int databaseSizeBeforeUpdate = tickIndexRepository.findAll().size();

        // Update the tickIndex
        TickIndex updatedTickIndex = tickIndexRepository.findById(tickIndex.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTickIndex are not directly saved in db
        em.detach(updatedTickIndex);
        updatedTickIndex
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

        restTickIndexMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTickIndex.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTickIndex))
            )
            .andExpect(status().isOk());

        // Validate the TickIndex in the database
        List<TickIndex> tickIndexList = tickIndexRepository.findAll();
        assertThat(tickIndexList).hasSize(databaseSizeBeforeUpdate);
        TickIndex testTickIndex = tickIndexList.get(tickIndexList.size() - 1);
        assertThat(testTickIndex.getInstrumentKey()).isEqualTo(UPDATED_INSTRUMENT_KEY);
        assertThat(testTickIndex.getExchangeToken()).isEqualTo(UPDATED_EXCHANGE_TOKEN);
        assertThat(testTickIndex.getTradingSymbol()).isEqualTo(UPDATED_TRADING_SYMBOL);
        assertThat(testTickIndex.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTickIndex.getLastPrice()).isEqualTo(UPDATED_LAST_PRICE);
        assertThat(testTickIndex.getExpiry()).isEqualTo(UPDATED_EXPIRY);
        assertThat(testTickIndex.getStrike()).isEqualTo(UPDATED_STRIKE);
        assertThat(testTickIndex.getTickSize()).isEqualTo(UPDATED_TICK_SIZE);
        assertThat(testTickIndex.getLotSize()).isEqualTo(UPDATED_LOT_SIZE);
        assertThat(testTickIndex.getInstrumentType()).isEqualTo(UPDATED_INSTRUMENT_TYPE);
        assertThat(testTickIndex.getOptionType()).isEqualTo(UPDATED_OPTION_TYPE);
        assertThat(testTickIndex.getExchange()).isEqualTo(UPDATED_EXCHANGE);
    }

    @Test
    @Transactional
    void putNonExistingTickIndex() throws Exception {
        int databaseSizeBeforeUpdate = tickIndexRepository.findAll().size();
        tickIndex.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTickIndexMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tickIndex.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tickIndex))
            )
            .andExpect(status().isBadRequest());

        // Validate the TickIndex in the database
        List<TickIndex> tickIndexList = tickIndexRepository.findAll();
        assertThat(tickIndexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTickIndex() throws Exception {
        int databaseSizeBeforeUpdate = tickIndexRepository.findAll().size();
        tickIndex.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTickIndexMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tickIndex))
            )
            .andExpect(status().isBadRequest());

        // Validate the TickIndex in the database
        List<TickIndex> tickIndexList = tickIndexRepository.findAll();
        assertThat(tickIndexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTickIndex() throws Exception {
        int databaseSizeBeforeUpdate = tickIndexRepository.findAll().size();
        tickIndex.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTickIndexMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tickIndex))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TickIndex in the database
        List<TickIndex> tickIndexList = tickIndexRepository.findAll();
        assertThat(tickIndexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTickIndexWithPatch() throws Exception {
        // Initialize the database
        tickIndexRepository.saveAndFlush(tickIndex);

        int databaseSizeBeforeUpdate = tickIndexRepository.findAll().size();

        // Update the tickIndex using partial update
        TickIndex partialUpdatedTickIndex = new TickIndex();
        partialUpdatedTickIndex.setId(tickIndex.getId());

        partialUpdatedTickIndex
            .instrumentKey(UPDATED_INSTRUMENT_KEY)
            .exchangeToken(UPDATED_EXCHANGE_TOKEN)
            .name(UPDATED_NAME)
            .lastPrice(UPDATED_LAST_PRICE)
            .expiry(UPDATED_EXPIRY)
            .lotSize(UPDATED_LOT_SIZE)
            .optionType(UPDATED_OPTION_TYPE)
            .exchange(UPDATED_EXCHANGE);

        restTickIndexMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTickIndex.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTickIndex))
            )
            .andExpect(status().isOk());

        // Validate the TickIndex in the database
        List<TickIndex> tickIndexList = tickIndexRepository.findAll();
        assertThat(tickIndexList).hasSize(databaseSizeBeforeUpdate);
        TickIndex testTickIndex = tickIndexList.get(tickIndexList.size() - 1);
        assertThat(testTickIndex.getInstrumentKey()).isEqualTo(UPDATED_INSTRUMENT_KEY);
        assertThat(testTickIndex.getExchangeToken()).isEqualTo(UPDATED_EXCHANGE_TOKEN);
        assertThat(testTickIndex.getTradingSymbol()).isEqualTo(DEFAULT_TRADING_SYMBOL);
        assertThat(testTickIndex.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTickIndex.getLastPrice()).isEqualTo(UPDATED_LAST_PRICE);
        assertThat(testTickIndex.getExpiry()).isEqualTo(UPDATED_EXPIRY);
        assertThat(testTickIndex.getStrike()).isEqualTo(DEFAULT_STRIKE);
        assertThat(testTickIndex.getTickSize()).isEqualTo(DEFAULT_TICK_SIZE);
        assertThat(testTickIndex.getLotSize()).isEqualTo(UPDATED_LOT_SIZE);
        assertThat(testTickIndex.getInstrumentType()).isEqualTo(DEFAULT_INSTRUMENT_TYPE);
        assertThat(testTickIndex.getOptionType()).isEqualTo(UPDATED_OPTION_TYPE);
        assertThat(testTickIndex.getExchange()).isEqualTo(UPDATED_EXCHANGE);
    }

    @Test
    @Transactional
    void fullUpdateTickIndexWithPatch() throws Exception {
        // Initialize the database
        tickIndexRepository.saveAndFlush(tickIndex);

        int databaseSizeBeforeUpdate = tickIndexRepository.findAll().size();

        // Update the tickIndex using partial update
        TickIndex partialUpdatedTickIndex = new TickIndex();
        partialUpdatedTickIndex.setId(tickIndex.getId());

        partialUpdatedTickIndex
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

        restTickIndexMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTickIndex.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTickIndex))
            )
            .andExpect(status().isOk());

        // Validate the TickIndex in the database
        List<TickIndex> tickIndexList = tickIndexRepository.findAll();
        assertThat(tickIndexList).hasSize(databaseSizeBeforeUpdate);
        TickIndex testTickIndex = tickIndexList.get(tickIndexList.size() - 1);
        assertThat(testTickIndex.getInstrumentKey()).isEqualTo(UPDATED_INSTRUMENT_KEY);
        assertThat(testTickIndex.getExchangeToken()).isEqualTo(UPDATED_EXCHANGE_TOKEN);
        assertThat(testTickIndex.getTradingSymbol()).isEqualTo(UPDATED_TRADING_SYMBOL);
        assertThat(testTickIndex.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTickIndex.getLastPrice()).isEqualTo(UPDATED_LAST_PRICE);
        assertThat(testTickIndex.getExpiry()).isEqualTo(UPDATED_EXPIRY);
        assertThat(testTickIndex.getStrike()).isEqualTo(UPDATED_STRIKE);
        assertThat(testTickIndex.getTickSize()).isEqualTo(UPDATED_TICK_SIZE);
        assertThat(testTickIndex.getLotSize()).isEqualTo(UPDATED_LOT_SIZE);
        assertThat(testTickIndex.getInstrumentType()).isEqualTo(UPDATED_INSTRUMENT_TYPE);
        assertThat(testTickIndex.getOptionType()).isEqualTo(UPDATED_OPTION_TYPE);
        assertThat(testTickIndex.getExchange()).isEqualTo(UPDATED_EXCHANGE);
    }

    @Test
    @Transactional
    void patchNonExistingTickIndex() throws Exception {
        int databaseSizeBeforeUpdate = tickIndexRepository.findAll().size();
        tickIndex.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTickIndexMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tickIndex.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tickIndex))
            )
            .andExpect(status().isBadRequest());

        // Validate the TickIndex in the database
        List<TickIndex> tickIndexList = tickIndexRepository.findAll();
        assertThat(tickIndexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTickIndex() throws Exception {
        int databaseSizeBeforeUpdate = tickIndexRepository.findAll().size();
        tickIndex.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTickIndexMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tickIndex))
            )
            .andExpect(status().isBadRequest());

        // Validate the TickIndex in the database
        List<TickIndex> tickIndexList = tickIndexRepository.findAll();
        assertThat(tickIndexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTickIndex() throws Exception {
        int databaseSizeBeforeUpdate = tickIndexRepository.findAll().size();
        tickIndex.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTickIndexMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tickIndex))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TickIndex in the database
        List<TickIndex> tickIndexList = tickIndexRepository.findAll();
        assertThat(tickIndexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTickIndex() throws Exception {
        // Initialize the database
        tickIndexRepository.saveAndFlush(tickIndex);

        int databaseSizeBeforeDelete = tickIndexRepository.findAll().size();

        // Delete the tickIndex
        restTickIndexMockMvc
            .perform(delete(ENTITY_API_URL_ID, tickIndex.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TickIndex> tickIndexList = tickIndexRepository.findAll();
        assertThat(tickIndexList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
