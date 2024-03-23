package com.thrinod.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thrinod.IntegrationTest;
import com.thrinod.domain.TickFutures;
import com.thrinod.repository.TickFuturesRepository;
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
 * Integration tests for the {@link TickFuturesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TickFuturesResourceIT {

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

    private static final String ENTITY_API_URL = "/api/tick-futures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TickFuturesRepository tickFuturesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTickFuturesMockMvc;

    private TickFutures tickFutures;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TickFutures createEntity(EntityManager em) {
        TickFutures tickFutures = new TickFutures()
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
        return tickFutures;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TickFutures createUpdatedEntity(EntityManager em) {
        TickFutures tickFutures = new TickFutures()
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
        return tickFutures;
    }

    @BeforeEach
    public void initTest() {
        tickFutures = createEntity(em);
    }

    @Test
    @Transactional
    void createTickFutures() throws Exception {
        int databaseSizeBeforeCreate = tickFuturesRepository.findAll().size();
        // Create the TickFutures
        restTickFuturesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tickFutures))
            )
            .andExpect(status().isCreated());

        // Validate the TickFutures in the database
        List<TickFutures> tickFuturesList = tickFuturesRepository.findAll();
        assertThat(tickFuturesList).hasSize(databaseSizeBeforeCreate + 1);
        TickFutures testTickFutures = tickFuturesList.get(tickFuturesList.size() - 1);
        assertThat(testTickFutures.getInstrumentKey()).isEqualTo(DEFAULT_INSTRUMENT_KEY);
        assertThat(testTickFutures.getExchangeToken()).isEqualTo(DEFAULT_EXCHANGE_TOKEN);
        assertThat(testTickFutures.getTradingSymbol()).isEqualTo(DEFAULT_TRADING_SYMBOL);
        assertThat(testTickFutures.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTickFutures.getLastPrice()).isEqualTo(DEFAULT_LAST_PRICE);
        assertThat(testTickFutures.getExpiry()).isEqualTo(DEFAULT_EXPIRY);
        assertThat(testTickFutures.getStrike()).isEqualTo(DEFAULT_STRIKE);
        assertThat(testTickFutures.getTickSize()).isEqualTo(DEFAULT_TICK_SIZE);
        assertThat(testTickFutures.getLotSize()).isEqualTo(DEFAULT_LOT_SIZE);
        assertThat(testTickFutures.getInstrumentType()).isEqualTo(DEFAULT_INSTRUMENT_TYPE);
        assertThat(testTickFutures.getOptionType()).isEqualTo(DEFAULT_OPTION_TYPE);
        assertThat(testTickFutures.getExchange()).isEqualTo(DEFAULT_EXCHANGE);
    }

    @Test
    @Transactional
    void createTickFuturesWithExistingId() throws Exception {
        // Create the TickFutures with an existing ID
        tickFutures.setId(1L);

        int databaseSizeBeforeCreate = tickFuturesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTickFuturesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tickFutures))
            )
            .andExpect(status().isBadRequest());

        // Validate the TickFutures in the database
        List<TickFutures> tickFuturesList = tickFuturesRepository.findAll();
        assertThat(tickFuturesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTickFutures() throws Exception {
        // Initialize the database
        tickFuturesRepository.saveAndFlush(tickFutures);

        // Get all the tickFuturesList
        restTickFuturesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tickFutures.getId().intValue())))
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
    void getTickFutures() throws Exception {
        // Initialize the database
        tickFuturesRepository.saveAndFlush(tickFutures);

        // Get the tickFutures
        restTickFuturesMockMvc
            .perform(get(ENTITY_API_URL_ID, tickFutures.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tickFutures.getId().intValue()))
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
    void getNonExistingTickFutures() throws Exception {
        // Get the tickFutures
        restTickFuturesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTickFutures() throws Exception {
        // Initialize the database
        tickFuturesRepository.saveAndFlush(tickFutures);

        int databaseSizeBeforeUpdate = tickFuturesRepository.findAll().size();

        // Update the tickFutures
        TickFutures updatedTickFutures = tickFuturesRepository.findById(tickFutures.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTickFutures are not directly saved in db
        em.detach(updatedTickFutures);
        updatedTickFutures
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

        restTickFuturesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTickFutures.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTickFutures))
            )
            .andExpect(status().isOk());

        // Validate the TickFutures in the database
        List<TickFutures> tickFuturesList = tickFuturesRepository.findAll();
        assertThat(tickFuturesList).hasSize(databaseSizeBeforeUpdate);
        TickFutures testTickFutures = tickFuturesList.get(tickFuturesList.size() - 1);
        assertThat(testTickFutures.getInstrumentKey()).isEqualTo(UPDATED_INSTRUMENT_KEY);
        assertThat(testTickFutures.getExchangeToken()).isEqualTo(UPDATED_EXCHANGE_TOKEN);
        assertThat(testTickFutures.getTradingSymbol()).isEqualTo(UPDATED_TRADING_SYMBOL);
        assertThat(testTickFutures.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTickFutures.getLastPrice()).isEqualTo(UPDATED_LAST_PRICE);
        assertThat(testTickFutures.getExpiry()).isEqualTo(UPDATED_EXPIRY);
        assertThat(testTickFutures.getStrike()).isEqualTo(UPDATED_STRIKE);
        assertThat(testTickFutures.getTickSize()).isEqualTo(UPDATED_TICK_SIZE);
        assertThat(testTickFutures.getLotSize()).isEqualTo(UPDATED_LOT_SIZE);
        assertThat(testTickFutures.getInstrumentType()).isEqualTo(UPDATED_INSTRUMENT_TYPE);
        assertThat(testTickFutures.getOptionType()).isEqualTo(UPDATED_OPTION_TYPE);
        assertThat(testTickFutures.getExchange()).isEqualTo(UPDATED_EXCHANGE);
    }

    @Test
    @Transactional
    void putNonExistingTickFutures() throws Exception {
        int databaseSizeBeforeUpdate = tickFuturesRepository.findAll().size();
        tickFutures.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTickFuturesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tickFutures.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tickFutures))
            )
            .andExpect(status().isBadRequest());

        // Validate the TickFutures in the database
        List<TickFutures> tickFuturesList = tickFuturesRepository.findAll();
        assertThat(tickFuturesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTickFutures() throws Exception {
        int databaseSizeBeforeUpdate = tickFuturesRepository.findAll().size();
        tickFutures.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTickFuturesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tickFutures))
            )
            .andExpect(status().isBadRequest());

        // Validate the TickFutures in the database
        List<TickFutures> tickFuturesList = tickFuturesRepository.findAll();
        assertThat(tickFuturesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTickFutures() throws Exception {
        int databaseSizeBeforeUpdate = tickFuturesRepository.findAll().size();
        tickFutures.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTickFuturesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tickFutures))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TickFutures in the database
        List<TickFutures> tickFuturesList = tickFuturesRepository.findAll();
        assertThat(tickFuturesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTickFuturesWithPatch() throws Exception {
        // Initialize the database
        tickFuturesRepository.saveAndFlush(tickFutures);

        int databaseSizeBeforeUpdate = tickFuturesRepository.findAll().size();

        // Update the tickFutures using partial update
        TickFutures partialUpdatedTickFutures = new TickFutures();
        partialUpdatedTickFutures.setId(tickFutures.getId());

        partialUpdatedTickFutures
            .instrumentKey(UPDATED_INSTRUMENT_KEY)
            .tradingSymbol(UPDATED_TRADING_SYMBOL)
            .lastPrice(UPDATED_LAST_PRICE)
            .expiry(UPDATED_EXPIRY)
            .tickSize(UPDATED_TICK_SIZE)
            .instrumentType(UPDATED_INSTRUMENT_TYPE)
            .exchange(UPDATED_EXCHANGE);

        restTickFuturesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTickFutures.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTickFutures))
            )
            .andExpect(status().isOk());

        // Validate the TickFutures in the database
        List<TickFutures> tickFuturesList = tickFuturesRepository.findAll();
        assertThat(tickFuturesList).hasSize(databaseSizeBeforeUpdate);
        TickFutures testTickFutures = tickFuturesList.get(tickFuturesList.size() - 1);
        assertThat(testTickFutures.getInstrumentKey()).isEqualTo(UPDATED_INSTRUMENT_KEY);
        assertThat(testTickFutures.getExchangeToken()).isEqualTo(DEFAULT_EXCHANGE_TOKEN);
        assertThat(testTickFutures.getTradingSymbol()).isEqualTo(UPDATED_TRADING_SYMBOL);
        assertThat(testTickFutures.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTickFutures.getLastPrice()).isEqualTo(UPDATED_LAST_PRICE);
        assertThat(testTickFutures.getExpiry()).isEqualTo(UPDATED_EXPIRY);
        assertThat(testTickFutures.getStrike()).isEqualTo(DEFAULT_STRIKE);
        assertThat(testTickFutures.getTickSize()).isEqualTo(UPDATED_TICK_SIZE);
        assertThat(testTickFutures.getLotSize()).isEqualTo(DEFAULT_LOT_SIZE);
        assertThat(testTickFutures.getInstrumentType()).isEqualTo(UPDATED_INSTRUMENT_TYPE);
        assertThat(testTickFutures.getOptionType()).isEqualTo(DEFAULT_OPTION_TYPE);
        assertThat(testTickFutures.getExchange()).isEqualTo(UPDATED_EXCHANGE);
    }

    @Test
    @Transactional
    void fullUpdateTickFuturesWithPatch() throws Exception {
        // Initialize the database
        tickFuturesRepository.saveAndFlush(tickFutures);

        int databaseSizeBeforeUpdate = tickFuturesRepository.findAll().size();

        // Update the tickFutures using partial update
        TickFutures partialUpdatedTickFutures = new TickFutures();
        partialUpdatedTickFutures.setId(tickFutures.getId());

        partialUpdatedTickFutures
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

        restTickFuturesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTickFutures.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTickFutures))
            )
            .andExpect(status().isOk());

        // Validate the TickFutures in the database
        List<TickFutures> tickFuturesList = tickFuturesRepository.findAll();
        assertThat(tickFuturesList).hasSize(databaseSizeBeforeUpdate);
        TickFutures testTickFutures = tickFuturesList.get(tickFuturesList.size() - 1);
        assertThat(testTickFutures.getInstrumentKey()).isEqualTo(UPDATED_INSTRUMENT_KEY);
        assertThat(testTickFutures.getExchangeToken()).isEqualTo(UPDATED_EXCHANGE_TOKEN);
        assertThat(testTickFutures.getTradingSymbol()).isEqualTo(UPDATED_TRADING_SYMBOL);
        assertThat(testTickFutures.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTickFutures.getLastPrice()).isEqualTo(UPDATED_LAST_PRICE);
        assertThat(testTickFutures.getExpiry()).isEqualTo(UPDATED_EXPIRY);
        assertThat(testTickFutures.getStrike()).isEqualTo(UPDATED_STRIKE);
        assertThat(testTickFutures.getTickSize()).isEqualTo(UPDATED_TICK_SIZE);
        assertThat(testTickFutures.getLotSize()).isEqualTo(UPDATED_LOT_SIZE);
        assertThat(testTickFutures.getInstrumentType()).isEqualTo(UPDATED_INSTRUMENT_TYPE);
        assertThat(testTickFutures.getOptionType()).isEqualTo(UPDATED_OPTION_TYPE);
        assertThat(testTickFutures.getExchange()).isEqualTo(UPDATED_EXCHANGE);
    }

    @Test
    @Transactional
    void patchNonExistingTickFutures() throws Exception {
        int databaseSizeBeforeUpdate = tickFuturesRepository.findAll().size();
        tickFutures.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTickFuturesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tickFutures.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tickFutures))
            )
            .andExpect(status().isBadRequest());

        // Validate the TickFutures in the database
        List<TickFutures> tickFuturesList = tickFuturesRepository.findAll();
        assertThat(tickFuturesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTickFutures() throws Exception {
        int databaseSizeBeforeUpdate = tickFuturesRepository.findAll().size();
        tickFutures.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTickFuturesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tickFutures))
            )
            .andExpect(status().isBadRequest());

        // Validate the TickFutures in the database
        List<TickFutures> tickFuturesList = tickFuturesRepository.findAll();
        assertThat(tickFuturesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTickFutures() throws Exception {
        int databaseSizeBeforeUpdate = tickFuturesRepository.findAll().size();
        tickFutures.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTickFuturesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tickFutures))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TickFutures in the database
        List<TickFutures> tickFuturesList = tickFuturesRepository.findAll();
        assertThat(tickFuturesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTickFutures() throws Exception {
        // Initialize the database
        tickFuturesRepository.saveAndFlush(tickFutures);

        int databaseSizeBeforeDelete = tickFuturesRepository.findAll().size();

        // Delete the tickFutures
        restTickFuturesMockMvc
            .perform(delete(ENTITY_API_URL_ID, tickFutures.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TickFutures> tickFuturesList = tickFuturesRepository.findAll();
        assertThat(tickFuturesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
