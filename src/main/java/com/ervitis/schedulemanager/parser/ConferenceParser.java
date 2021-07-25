package com.ervitis.schedulemanager.parser;

import com.ervitis.schedulemanager.model.Conference;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConferenceParser {

    Pattern pattern = Pattern.compile("^(?<nameConference>.*)\\s(?<time>\\d{1,3}min|lightning)$");
    static final String LIGHTNING = "lightning";

    public Conference parseConferenceLine(String line) {
        Matcher matcher = this.pattern.matcher(line);
        if (!matcher.matches()) {
            return null;
        }

        String time = matcher.group("time");
        if (time.equals(LIGHTNING)) {
            time = "5";
        } else {
            time = time.substring(0, (time.length() - 3));
        }

        return Conference.builder()
                .name(matcher.group("nameConference"))
                .time(Integer.parseInt(time))
                .build();
    }
}
