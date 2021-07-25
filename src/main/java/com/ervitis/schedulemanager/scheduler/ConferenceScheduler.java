package com.ervitis.schedulemanager.scheduler;

import com.ervitis.schedulemanager.model.Conference;

import java.time.LocalTime;
import java.util.*;

public class ConferenceScheduler {
    static final LocalTime[] SCHEDULE = {
            LocalTime.of(9, 0),
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            LocalTime.of(16, 0),
            LocalTime.of(17, 0),
    };

    SortedMap<Integer, LinkedList<Conference>> sessions;
    Map<LocalTime, Conference> tracks;

    public ConferenceScheduler() {
        this.sessions = new TreeMap<>(Collections.reverseOrder());
    }

    public void addConference(Conference conference) {
        Integer t = conference.getTime();
        if (this.sessions.get(t) == null) {
            this.sessions.put(t, new LinkedList<>(List.of(conference)));
        } else {
            LinkedList<Conference> els = this.sessions.get(t);
            els.addLast(conference);
        }
    }

    public void calculate() {
        this.tracks = new HashMap<>();
        LocalTime tempo = SCHEDULE[0];

        for (Map.Entry<Integer, LinkedList<Conference>> conferences : this.sessions.entrySet()) {
            if (this.endDayTrack(tempo)) {
                tempo = SCHEDULE[0];
            }

            if (this.isBetweenRestTime(tempo)) {
                tempo = SCHEDULE[2];
            }

            LocalTime t = tempo.plusMinutes(conferences.getKey());
            if (t.equals(SCHEDULE[0]) ||
                    t.isAfter(SCHEDULE[0]) && t.isBefore(SCHEDULE[1]) ||
                    t.equals(SCHEDULE[1]) ||
                    t.equals(SCHEDULE[2]) ||
                    t.isAfter(SCHEDULE[2]) && t.isBefore(SCHEDULE[3])
            ) {
                this.tracks.put(tempo, conferences.getValue().getFirst());
                tempo = tempo.plusMinutes(conferences.getKey());
            } else {
                if (conferences.getKey() == 5 &&
                        t.equals(SCHEDULE[3]) ||
                        t.isAfter(SCHEDULE[3]) && t.isBefore(SCHEDULE[4])
                ) {
                    this.tracks.put(tempo, conferences.getValue().getFirst());
                    tempo = tempo.plusMinutes(conferences.getKey());
                }
            }

        }

    }

    private boolean isBetweenRestTime(LocalTime tempo) {
        return tempo.isAfter(SCHEDULE[1]) && tempo.isBefore(SCHEDULE[2]);
    }

    private boolean endDayTrack(LocalTime t) {
        return SCHEDULE[4].equals(t) || t.isAfter(SCHEDULE[4]);
    }
}
