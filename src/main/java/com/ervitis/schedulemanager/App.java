package com.ervitis.schedulemanager;

import com.ervitis.schedulemanager.parser.ConferenceParser;
import com.ervitis.schedulemanager.resource.ConferenceResourceManagerBuilder;
import com.ervitis.schedulemanager.scheduler.ConferenceScheduler;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class App {
    private final static Logger log = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {

        ConferenceResourceManagerBuilder.ConferenceIterator conferenceIterator = null;
        ConferenceParser conferenceParser = new ConferenceParser();
        ConferenceScheduler conferenceScheduler = new ConferenceScheduler();

        try {
            conferenceIterator = new ConferenceResourceManagerBuilder()
                    .withParser(conferenceParser)
                    .withPath(getFileAbsPath())
                    .build();
        } catch (URISyntaxException | IOException exception) {
            log.severe("Exception reading file: " + exception.getMessage());
            System.exit(1);
        }

        if (conferenceIterator == null) {
            log.info("Something happened and could not continue");
            System.exit(1);
        }

        while (conferenceIterator.hasNext()) {
            conferenceScheduler.addConference(conferenceIterator.next());
        }
        conferenceScheduler.calculate();
    }

    private static String getFileAbsPath() throws URISyntaxException {
        URL res = App.class.getClassLoader().getResource("conferences.txt");
        if (res == null) {
            return null;
        }
        File f = Paths.get(res.toURI()).toFile();
        return f.getAbsolutePath();
    }
}
