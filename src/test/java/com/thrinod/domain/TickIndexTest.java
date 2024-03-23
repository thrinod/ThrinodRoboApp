package com.thrinod.domain;

import static com.thrinod.domain.TickIndexTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.thrinod.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TickIndexTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TickIndex.class);
        TickIndex tickIndex1 = getTickIndexSample1();
        TickIndex tickIndex2 = new TickIndex();
        assertThat(tickIndex1).isNotEqualTo(tickIndex2);

        tickIndex2.setId(tickIndex1.getId());
        assertThat(tickIndex1).isEqualTo(tickIndex2);

        tickIndex2 = getTickIndexSample2();
        assertThat(tickIndex1).isNotEqualTo(tickIndex2);
    }
}
