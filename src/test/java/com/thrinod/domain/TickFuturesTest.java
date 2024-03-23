package com.thrinod.domain;

import static com.thrinod.domain.TickFuturesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.thrinod.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TickFuturesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TickFutures.class);
        TickFutures tickFutures1 = getTickFuturesSample1();
        TickFutures tickFutures2 = new TickFutures();
        assertThat(tickFutures1).isNotEqualTo(tickFutures2);

        tickFutures2.setId(tickFutures1.getId());
        assertThat(tickFutures1).isEqualTo(tickFutures2);

        tickFutures2 = getTickFuturesSample2();
        assertThat(tickFutures1).isNotEqualTo(tickFutures2);
    }
}
