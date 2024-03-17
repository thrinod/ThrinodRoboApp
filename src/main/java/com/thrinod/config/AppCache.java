package com.thrinod.config;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class AppCache {

    private String accToken;

    public void setAccToken(String accToken) {
        this.accToken = accToken;
    }

    public String getAccToken() {
        return accToken;
    }
}
