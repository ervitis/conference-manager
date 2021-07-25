package com.ervitis.schedulemanager.model;

import lombok.Data;

import java.util.List;

@Data
public class Session {
    List<Conference> tracks;
}
