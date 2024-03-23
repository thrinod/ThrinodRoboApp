package com.thrinod.domain;

import static com.thrinod.domain.TickOptionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.thrinod.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TickOptionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TickOption.class);
        TickOption tickOption1 = getTickOptionSample1();
        TickOption tickOption2 = new TickOption();
        assertThat(tickOption1).isNotEqualTo(tickOption2);

        tickOption2.setId(tickOption1.getId());
        assertThat(tickOption1).isEqualTo(tickOption2);

        tickOption2 = getTickOptionSample2();
        assertThat(tickOption1).isNotEqualTo(tickOption2);
    }
}
