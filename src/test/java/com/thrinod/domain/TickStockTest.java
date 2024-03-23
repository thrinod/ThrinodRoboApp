package com.thrinod.domain;

import static com.thrinod.domain.TickStockTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.thrinod.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TickStockTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TickStock.class);
        TickStock tickStock1 = getTickStockSample1();
        TickStock tickStock2 = new TickStock();
        assertThat(tickStock1).isNotEqualTo(tickStock2);

        tickStock2.setId(tickStock1.getId());
        assertThat(tickStock1).isEqualTo(tickStock2);

        tickStock2 = getTickStockSample2();
        assertThat(tickStock1).isNotEqualTo(tickStock2);
    }
}
