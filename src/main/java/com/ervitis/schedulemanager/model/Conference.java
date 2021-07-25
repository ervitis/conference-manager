package com.ervitis.schedulemanager.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Conference {
    String name;
    Integer time;

    public boolean isLightningTalk() {
        return this.time == 5;
    }
}
