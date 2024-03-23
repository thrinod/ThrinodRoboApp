package com.thrinod.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thrinod.IntegrationTest;
import com.thrinod.domain.TickStock;
import com.thrinod.repository.TickStockRepository;
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
 * Integration tests for the {@link TickStockResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TickStockResourceIT {

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

    private static final String ENTITY_API_URL = "/api/tick-stocks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TickStockRepository tickStockRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTickStockMockMvc;

    private TickStock tickStock;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TickStock createEntity(EntityManager em) {
        TickStock tickStock = new TickStock()
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
        return tickStock;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TickStock createUpdatedEntity(EntityManager em) {
        TickStock tickStock = new TickStock()
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
        return tickStock;
    }

    @BeforeEach
    public void initTest() {
        tickStock = createEntity(em);
    }

    @Test
    @Transactional
    void createTickStock() throws Exception {
        int databaseSizeBeforeCreate = tickStockRepository.findAll().size();
        // Create the TickStock
        restTickStockMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tickStock))
            )
            .andExpect(status().isCreated());

        // Validate the TickStock in the database
        List<TickStock> tickStockList = tickStockRepository.findAll();
        assertThat(tickStockList).hasSize(databaseSizeBeforeCreate + 1);
        TickStock testTickStock = tickStockList.get(tickStockList.size() - 1);
        assertThat(testTickStock.getInstrumentKey()).isEqualTo(DEFAULT_INSTRUMENT_KEY);
        assertThat(testTickStock.getExchangeToken()).isEqualTo(DEFAULT_EXCHANGE_TOKEN);
        assertThat(testTickStock.getTradingSymbol()).isEqualTo(DEFAULT_TRADING_SYMBOL);
        assertThat(testTickStock.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTickStock.getLastPrice()).isEqualTo(DEFAULT_LAST_PRICE);
        assertThat(testTickStock.getExpiry()).isEqualTo(DEFAULT_EXPIRY);
        assertThat(testTickStock.getStrike()).isEqualTo(DEFAULT_STRIKE);
        assertThat(testTickStock.getTickSize()).isEqualTo(DEFAULT_TICK_SIZE);
        assertThat(testTickStock.getLotSize()).isEqualTo(DEFAULT_LOT_SIZE);
        assertThat(testTickStock.getInstrumentType()).isEqualTo(DEFAULT_INSTRUMENT_TYPE);
        assertThat(testTickStock.getOptionType()).isEqualTo(DEFAULT_OPTION_TYPE);
        assertThat(testTickStock.getExchange()).isEqualTo(DEFAULT_EXCHANGE);
    }

    @Test
    @Transactional
    void createTickStockWithExistingId() throws Exception {
        // Create the TickStock with an existing ID
        tickStock.setId(1L);

        int databaseSizeBeforeCreate = tickStockRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTickStockMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tickStock))
            )
            .andExpect(status().isBadRequest());

        // Validate the TickStock in the database
        List<TickStock> tickStockList = tickStockRepository.findAll();
        assertThat(tickStockList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTickStocks() throws Exception {
        // Initialize the database
        tickStockRepository.saveAndFlush(tickStock);

        // Get all the tickStockList
        restTickStockMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tickStock.getId().intValue())))
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
    void getTickStock() throws Exception {
        // Initialize the database
        tickStockRepository.saveAndFlush(tickStock);

        // Get the tickStock
        restTickStockMockMvc
            .perform(get(ENTITY_API_URL_ID, tickStock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tickStock.getId().intValue()))
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
    void getNonExistingTickStock() throws Exception {
        // Get the tickStock
        restTickStockMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTickStock() throws Exception {
        // Initialize the database
        tickStockRepository.saveAndFlush(tickStock);

        int databaseSizeBeforeUpdate = tickStockRepository.findAll().size();

        // Update the tickStock
        TickStock updatedTickStock = tickStockRepository.findById(tickStock.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTickStock are not directly saved in db
        em.detach(updatedTickStock);
        updatedTickStock
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

        restTickStockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTickStock.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTickStock))
            )
            .andExpect(status().isOk());

        // Validate the TickStock in the database
        List<TickStock> tickStockList = tickStockRepository.findAll();
        assertThat(tickStockList).hasSize(databaseSizeBeforeUpdate);
        TickStock testTickStock = tickStockList.get(tickStockList.size() - 1);
        assertThat(testTickStock.getInstrumentKey()).isEqualTo(UPDATED_INSTRUMENT_KEY);
        assertThat(testTickStock.getExchangeToken()).isEqualTo(UPDATED_EXCHANGE_TOKEN);
        assertThat(testTickStock.getTradingSymbol()).isEqualTo(UPDATED_TRADING_SYMBOL);
        assertThat(testTickStock.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTickStock.getLastPrice()).isEqualTo(UPDATED_LAST_PRICE);
        assertThat(testTickStock.getExpiry()).isEqualTo(UPDATED_EXPIRY);
        assertThat(testTickStock.getStrike()).isEqualTo(UPDATED_STRIKE);
        assertThat(testTickStock.getTickSize()).isEqualTo(UPDATED_TICK_SIZE);
        assertThat(testTickStock.getLotSize()).isEqualTo(UPDATED_LOT_SIZE);
        assertThat(testTickStock.getInstrumentType()).isEqualTo(UPDATED_INSTRUMENT_TYPE);
        assertThat(testTickStock.getOptionType()).isEqualTo(UPDATED_OPTION_TYPE);
        assertThat(testTickStock.getExchange()).isEqualTo(UPDATED_EXCHANGE);
    }

    @Test
    @Transactional
    void putNonExistingTickStock() throws Exception {
        int databaseSizeBeforeUpdate = tickStockRepository.findAll().size();
        tickStock.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTickStockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tickStock.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tickStock))
            )
            .andExpect(status().isBadRequest());

        // Validate the TickStock in the database
        List<TickStock> tickStockList = tickStockRepository.findAll();
        assertThat(tickStockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTickStock() throws Exception {
        int databaseSizeBeforeUpdate = tickStockRepository.findAll().size();
        tickStock.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTickStockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tickStock))
            )
            .andExpect(status().isBadRequest());

        // Validate the TickStock in the database
        List<TickStock> tickStockList = tickStockRepository.findAll();
        assertThat(tickStockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTickStock() throws Exception {
        int databaseSizeBeforeUpdate = tickStockRepository.findAll().size();
        tickStock.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTickStockMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tickStock))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TickStock in the database
        List<TickStock> tickStockList = tickStockRepository.findAll();
        assertThat(tickStockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTickStockWithPatch() throws Exception {
        // Initialize the database
        tickStockRepository.saveAndFlush(tickStock);

        int databaseSizeBeforeUpdate = tickStockRepository.findAll().size();

        // Update the tickStock using partial update
        TickStock partialUpdatedTickStock = new TickStock();
        partialUpdatedTickStock.setId(tickStock.getId());

        partialUpdatedTickStock
            .instrumentKey(UPDATED_INSTRUMENT_KEY)
            .exchangeToken(UPDATED_EXCHANGE_TOKEN)
            .tradingSymbol(UPDATED_TRADING_SYMBOL)
            .name(UPDATED_NAME)
            .lastPrice(UPDATED_LAST_PRICE)
            .expiry(UPDATED_EXPIRY)
            .tickSize(UPDATED_TICK_SIZE)
            .optionType(UPDATED_OPTION_TYPE);

        restTickStockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTickStock.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTickStock))
            )
            .andExpect(status().isOk());

        // Validate the TickStock in the database
        List<TickStock> tickStockList = tickStockRepository.findAll();
        assertThat(tickStockList).hasSize(databaseSizeBeforeUpdate);
        TickStock testTickStock = tickStockList.get(tickStockList.size() - 1);
        assertThat(testTickStock.getInstrumentKey()).isEqualTo(UPDATED_INSTRUMENT_KEY);
        assertThat(testTickStock.getExchangeToken()).isEqualTo(UPDATED_EXCHANGE_TOKEN);
        assertThat(testTickStock.getTradingSymbol()).isEqualTo(UPDATED_TRADING_SYMBOL);
        assertThat(testTickStock.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTickStock.getLastPrice()).isEqualTo(UPDATED_LAST_PRICE);
        assertThat(testTickStock.getExpiry()).isEqualTo(UPDATED_EXPIRY);
        assertThat(testTickStock.getStrike()).isEqualTo(DEFAULT_STRIKE);
        assertThat(testTickStock.getTickSize()).isEqualTo(UPDATED_TICK_SIZE);
        assertThat(testTickStock.getLotSize()).isEqualTo(DEFAULT_LOT_SIZE);
        assertThat(testTickStock.getInstrumentType()).isEqualTo(DEFAULT_INSTRUMENT_TYPE);
        assertThat(testTickStock.getOptionType()).isEqualTo(UPDATED_OPTION_TYPE);
        assertThat(testTickStock.getExchange()).isEqualTo(DEFAULT_EXCHANGE);
    }

    @Test
    @Transactional
    void fullUpdateTickStockWithPatch() throws Exception {
        // Initialize the database
        tickStockRepository.saveAndFlush(tickStock);

        int databaseSizeBeforeUpdate = tickStockRepository.findAll().size();

        // Update the tickStock using partial update
        TickStock partialUpdatedTickStock = new TickStock();
        partialUpdatedTickStock.setId(tickStock.getId());

        partialUpdatedTickStock
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

        restTickStockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTickStock.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTickStock))
            )
            .andExpect(status().isOk());

        // Validate the TickStock in the database
        List<TickStock> tickStockList = tickStockRepository.findAll();
        assertThat(tickStockList).hasSize(databaseSizeBeforeUpdate);
        TickStock testTickStock = tickStockList.get(tickStockList.size() - 1);
        assertThat(testTickStock.getInstrumentKey()).isEqualTo(UPDATED_INSTRUMENT_KEY);
        assertThat(testTickStock.getExchangeToken()).isEqualTo(UPDATED_EXCHANGE_TOKEN);
        assertThat(testTickStock.getTradingSymbol()).isEqualTo(UPDATED_TRADING_SYMBOL);
        assertThat(testTickStock.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTickStock.getLastPrice()).isEqualTo(UPDATED_LAST_PRICE);
        assertThat(testTickStock.getExpiry()).isEqualTo(UPDATED_EXPIRY);
        assertThat(testTickStock.getStrike()).isEqualTo(UPDATED_STRIKE);
        assertThat(testTickStock.getTickSize()).isEqualTo(UPDATED_TICK_SIZE);
        assertThat(testTickStock.getLotSize()).isEqualTo(UPDATED_LOT_SIZE);
        assertThat(testTickStock.getInstrumentType()).isEqualTo(UPDATED_INSTRUMENT_TYPE);
        assertThat(testTickStock.getOptionType()).isEqualTo(UPDATED_OPTION_TYPE);
        assertThat(testTickStock.getExchange()).isEqualTo(UPDATED_EXCHANGE);
    }

    @Test
    @Transactional
    void patchNonExistingTickStock() throws Exception {
        int databaseSizeBeforeUpdate = tickStockRepository.findAll().size();
        tickStock.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTickStockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tickStock.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tickStock))
            )
            .andExpect(status().isBadRequest());

        // Validate the TickStock in the database
        List<TickStock> tickStockList = tickStockRepository.findAll();
        assertThat(tickStockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTickStock() throws Exception {
        int databaseSizeBeforeUpdate = tickStockRepository.findAll().size();
        tickStock.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTickStockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tickStock))
            )
            .andExpect(status().isBadRequest());

        // Validate the TickStock in the database
        List<TickStock> tickStockList = tickStockRepository.findAll();
        assertThat(tickStockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTickStock() throws Exception {
        int databaseSizeBeforeUpdate = tickStockRepository.findAll().size();
        tickStock.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTickStockMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tickStock))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TickStock in the database
        List<TickStock> tickStockList = tickStockRepository.findAll();
        assertThat(tickStockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTickStock() throws Exception {
        // Initialize the database
        tickStockRepository.saveAndFlush(tickStock);

        int databaseSizeBeforeDelete = tickStockRepository.findAll().size();

        // Delete the tickStock
        restTickStockMockMvc
            .perform(delete(ENTITY_API_URL_ID, tickStock.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TickStock> tickStockList = tickStockRepository.findAll();
        assertThat(tickStockList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
