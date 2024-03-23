package com.thrinod.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thrinod.IntegrationTest;
import com.thrinod.domain.TickOption;
import com.thrinod.repository.TickOptionRepository;
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
 * Integration tests for the {@link TickOptionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TickOptionResourceIT {

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

    private static final String ENTITY_API_URL = "/api/tick-options";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TickOptionRepository tickOptionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTickOptionMockMvc;

    private TickOption tickOption;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TickOption createEntity(EntityManager em) {
        TickOption tickOption = new TickOption()
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
        return tickOption;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TickOption createUpdatedEntity(EntityManager em) {
        TickOption tickOption = new TickOption()
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
        return tickOption;
    }

    @BeforeEach
    public void initTest() {
        tickOption = createEntity(em);
    }

    @Test
    @Transactional
    void createTickOption() throws Exception {
        int databaseSizeBeforeCreate = tickOptionRepository.findAll().size();
        // Create the TickOption
        restTickOptionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tickOption))
            )
            .andExpect(status().isCreated());

        // Validate the TickOption in the database
        List<TickOption> tickOptionList = tickOptionRepository.findAll();
        assertThat(tickOptionList).hasSize(databaseSizeBeforeCreate + 1);
        TickOption testTickOption = tickOptionList.get(tickOptionList.size() - 1);
        assertThat(testTickOption.getInstrumentKey()).isEqualTo(DEFAULT_INSTRUMENT_KEY);
        assertThat(testTickOption.getExchangeToken()).isEqualTo(DEFAULT_EXCHANGE_TOKEN);
        assertThat(testTickOption.getTradingSymbol()).isEqualTo(DEFAULT_TRADING_SYMBOL);
        assertThat(testTickOption.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTickOption.getLastPrice()).isEqualTo(DEFAULT_LAST_PRICE);
        assertThat(testTickOption.getExpiry()).isEqualTo(DEFAULT_EXPIRY);
        assertThat(testTickOption.getStrike()).isEqualTo(DEFAULT_STRIKE);
        assertThat(testTickOption.getTickSize()).isEqualTo(DEFAULT_TICK_SIZE);
        assertThat(testTickOption.getLotSize()).isEqualTo(DEFAULT_LOT_SIZE);
        assertThat(testTickOption.getInstrumentType()).isEqualTo(DEFAULT_INSTRUMENT_TYPE);
        assertThat(testTickOption.getOptionType()).isEqualTo(DEFAULT_OPTION_TYPE);
        assertThat(testTickOption.getExchange()).isEqualTo(DEFAULT_EXCHANGE);
    }

    @Test
    @Transactional
    void createTickOptionWithExistingId() throws Exception {
        // Create the TickOption with an existing ID
        tickOption.setId(1L);

        int databaseSizeBeforeCreate = tickOptionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTickOptionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tickOption))
            )
            .andExpect(status().isBadRequest());

        // Validate the TickOption in the database
        List<TickOption> tickOptionList = tickOptionRepository.findAll();
        assertThat(tickOptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTickOptions() throws Exception {
        // Initialize the database
        tickOptionRepository.saveAndFlush(tickOption);

        // Get all the tickOptionList
        restTickOptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tickOption.getId().intValue())))
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
    void getTickOption() throws Exception {
        // Initialize the database
        tickOptionRepository.saveAndFlush(tickOption);

        // Get the tickOption
        restTickOptionMockMvc
            .perform(get(ENTITY_API_URL_ID, tickOption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tickOption.getId().intValue()))
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
    void getNonExistingTickOption() throws Exception {
        // Get the tickOption
        restTickOptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTickOption() throws Exception {
        // Initialize the database
        tickOptionRepository.saveAndFlush(tickOption);

        int databaseSizeBeforeUpdate = tickOptionRepository.findAll().size();

        // Update the tickOption
        TickOption updatedTickOption = tickOptionRepository.findById(tickOption.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTickOption are not directly saved in db
        em.detach(updatedTickOption);
        updatedTickOption
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

        restTickOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTickOption.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTickOption))
            )
            .andExpect(status().isOk());

        // Validate the TickOption in the database
        List<TickOption> tickOptionList = tickOptionRepository.findAll();
        assertThat(tickOptionList).hasSize(databaseSizeBeforeUpdate);
        TickOption testTickOption = tickOptionList.get(tickOptionList.size() - 1);
        assertThat(testTickOption.getInstrumentKey()).isEqualTo(UPDATED_INSTRUMENT_KEY);
        assertThat(testTickOption.getExchangeToken()).isEqualTo(UPDATED_EXCHANGE_TOKEN);
        assertThat(testTickOption.getTradingSymbol()).isEqualTo(UPDATED_TRADING_SYMBOL);
        assertThat(testTickOption.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTickOption.getLastPrice()).isEqualTo(UPDATED_LAST_PRICE);
        assertThat(testTickOption.getExpiry()).isEqualTo(UPDATED_EXPIRY);
        assertThat(testTickOption.getStrike()).isEqualTo(UPDATED_STRIKE);
        assertThat(testTickOption.getTickSize()).isEqualTo(UPDATED_TICK_SIZE);
        assertThat(testTickOption.getLotSize()).isEqualTo(UPDATED_LOT_SIZE);
        assertThat(testTickOption.getInstrumentType()).isEqualTo(UPDATED_INSTRUMENT_TYPE);
        assertThat(testTickOption.getOptionType()).isEqualTo(UPDATED_OPTION_TYPE);
        assertThat(testTickOption.getExchange()).isEqualTo(UPDATED_EXCHANGE);
    }

    @Test
    @Transactional
    void putNonExistingTickOption() throws Exception {
        int databaseSizeBeforeUpdate = tickOptionRepository.findAll().size();
        tickOption.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTickOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tickOption.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tickOption))
            )
            .andExpect(status().isBadRequest());

        // Validate the TickOption in the database
        List<TickOption> tickOptionList = tickOptionRepository.findAll();
        assertThat(tickOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTickOption() throws Exception {
        int databaseSizeBeforeUpdate = tickOptionRepository.findAll().size();
        tickOption.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTickOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tickOption))
            )
            .andExpect(status().isBadRequest());

        // Validate the TickOption in the database
        List<TickOption> tickOptionList = tickOptionRepository.findAll();
        assertThat(tickOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTickOption() throws Exception {
        int databaseSizeBeforeUpdate = tickOptionRepository.findAll().size();
        tickOption.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTickOptionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tickOption))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TickOption in the database
        List<TickOption> tickOptionList = tickOptionRepository.findAll();
        assertThat(tickOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTickOptionWithPatch() throws Exception {
        // Initialize the database
        tickOptionRepository.saveAndFlush(tickOption);

        int databaseSizeBeforeUpdate = tickOptionRepository.findAll().size();

        // Update the tickOption using partial update
        TickOption partialUpdatedTickOption = new TickOption();
        partialUpdatedTickOption.setId(tickOption.getId());

        partialUpdatedTickOption
            .tradingSymbol(UPDATED_TRADING_SYMBOL)
            .strike(UPDATED_STRIKE)
            .tickSize(UPDATED_TICK_SIZE)
            .instrumentType(UPDATED_INSTRUMENT_TYPE)
            .optionType(UPDATED_OPTION_TYPE)
            .exchange(UPDATED_EXCHANGE);

        restTickOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTickOption.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTickOption))
            )
            .andExpect(status().isOk());

        // Validate the TickOption in the database
        List<TickOption> tickOptionList = tickOptionRepository.findAll();
        assertThat(tickOptionList).hasSize(databaseSizeBeforeUpdate);
        TickOption testTickOption = tickOptionList.get(tickOptionList.size() - 1);
        assertThat(testTickOption.getInstrumentKey()).isEqualTo(DEFAULT_INSTRUMENT_KEY);
        assertThat(testTickOption.getExchangeToken()).isEqualTo(DEFAULT_EXCHANGE_TOKEN);
        assertThat(testTickOption.getTradingSymbol()).isEqualTo(UPDATED_TRADING_SYMBOL);
        assertThat(testTickOption.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTickOption.getLastPrice()).isEqualTo(DEFAULT_LAST_PRICE);
        assertThat(testTickOption.getExpiry()).isEqualTo(DEFAULT_EXPIRY);
        assertThat(testTickOption.getStrike()).isEqualTo(UPDATED_STRIKE);
        assertThat(testTickOption.getTickSize()).isEqualTo(UPDATED_TICK_SIZE);
        assertThat(testTickOption.getLotSize()).isEqualTo(DEFAULT_LOT_SIZE);
        assertThat(testTickOption.getInstrumentType()).isEqualTo(UPDATED_INSTRUMENT_TYPE);
        assertThat(testTickOption.getOptionType()).isEqualTo(UPDATED_OPTION_TYPE);
        assertThat(testTickOption.getExchange()).isEqualTo(UPDATED_EXCHANGE);
    }

    @Test
    @Transactional
    void fullUpdateTickOptionWithPatch() throws Exception {
        // Initialize the database
        tickOptionRepository.saveAndFlush(tickOption);

        int databaseSizeBeforeUpdate = tickOptionRepository.findAll().size();

        // Update the tickOption using partial update
        TickOption partialUpdatedTickOption = new TickOption();
        partialUpdatedTickOption.setId(tickOption.getId());

        partialUpdatedTickOption
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

        restTickOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTickOption.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTickOption))
            )
            .andExpect(status().isOk());

        // Validate the TickOption in the database
        List<TickOption> tickOptionList = tickOptionRepository.findAll();
        assertThat(tickOptionList).hasSize(databaseSizeBeforeUpdate);
        TickOption testTickOption = tickOptionList.get(tickOptionList.size() - 1);
        assertThat(testTickOption.getInstrumentKey()).isEqualTo(UPDATED_INSTRUMENT_KEY);
        assertThat(testTickOption.getExchangeToken()).isEqualTo(UPDATED_EXCHANGE_TOKEN);
        assertThat(testTickOption.getTradingSymbol()).isEqualTo(UPDATED_TRADING_SYMBOL);
        assertThat(testTickOption.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTickOption.getLastPrice()).isEqualTo(UPDATED_LAST_PRICE);
        assertThat(testTickOption.getExpiry()).isEqualTo(UPDATED_EXPIRY);
        assertThat(testTickOption.getStrike()).isEqualTo(UPDATED_STRIKE);
        assertThat(testTickOption.getTickSize()).isEqualTo(UPDATED_TICK_SIZE);
        assertThat(testTickOption.getLotSize()).isEqualTo(UPDATED_LOT_SIZE);
        assertThat(testTickOption.getInstrumentType()).isEqualTo(UPDATED_INSTRUMENT_TYPE);
        assertThat(testTickOption.getOptionType()).isEqualTo(UPDATED_OPTION_TYPE);
        assertThat(testTickOption.getExchange()).isEqualTo(UPDATED_EXCHANGE);
    }

    @Test
    @Transactional
    void patchNonExistingTickOption() throws Exception {
        int databaseSizeBeforeUpdate = tickOptionRepository.findAll().size();
        tickOption.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTickOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tickOption.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tickOption))
            )
            .andExpect(status().isBadRequest());

        // Validate the TickOption in the database
        List<TickOption> tickOptionList = tickOptionRepository.findAll();
        assertThat(tickOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTickOption() throws Exception {
        int databaseSizeBeforeUpdate = tickOptionRepository.findAll().size();
        tickOption.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTickOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tickOption))
            )
            .andExpect(status().isBadRequest());

        // Validate the TickOption in the database
        List<TickOption> tickOptionList = tickOptionRepository.findAll();
        assertThat(tickOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTickOption() throws Exception {
        int databaseSizeBeforeUpdate = tickOptionRepository.findAll().size();
        tickOption.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTickOptionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tickOption))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TickOption in the database
        List<TickOption> tickOptionList = tickOptionRepository.findAll();
        assertThat(tickOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTickOption() throws Exception {
        // Initialize the database
        tickOptionRepository.saveAndFlush(tickOption);

        int databaseSizeBeforeDelete = tickOptionRepository.findAll().size();

        // Delete the tickOption
        restTickOptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, tickOption.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TickOption> tickOptionList = tickOptionRepository.findAll();
        assertThat(tickOptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
