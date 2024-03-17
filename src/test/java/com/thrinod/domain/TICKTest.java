package com.thrinod.domain;

import static com.thrinod.domain.TICKTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.thrinod.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TICKTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TICK.class);
        TICK tICK1 = getTICKSample1();
        TICK tICK2 = new TICK();
        assertThat(tICK1).isNotEqualTo(tICK2);

        tICK2.setId(tICK1.getId());
        assertThat(tICK1).isEqualTo(tICK2);

        tICK2 = getTICKSample2();
        assertThat(tICK1).isNotEqualTo(tICK2);
    }
}
