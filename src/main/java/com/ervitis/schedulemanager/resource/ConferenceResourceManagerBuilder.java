package com.ervitis.schedulemanager.resource;

import com.ervitis.schedulemanager.model.Conference;
import com.ervitis.schedulemanager.parser.ConferenceParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConferenceResourceManagerBuilder {

    String path;

    ConferenceParser conferenceParser;

    public ConferenceResourceManagerBuilder withPath(String path) throws IOException {
        if (path == null) {
            throw new IOException("Path of the file is null");
        }
        this.path = path;
        return this;
    }

    public ConferenceResourceManagerBuilder withParser(ConferenceParser conferenceParser) {
        this.conferenceParser = conferenceParser;
        return this;
    }

    public ConferenceIterator build() throws IOException {
        List<String> lines = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(this.path))) {
            for(String line; (line = br.readLine()) != null;) {
                lines.add(line);
            }
        }
        return new ConferenceIterator(lines.toArray(new String[0]));
    }

    public class ConferenceIterator implements Iterator<Conference> {
        int index = 0;
        String []lines;

        public ConferenceIterator(String[] lines) {
            this.lines = lines;
        }

        public boolean hasNext() {
            return index < this.lines.length;
        }

        public Conference next() {
            if (!this.hasNext()) {
                return null;
            }

            Conference c = conferenceParser.parseConferenceLine(this.lines[index++]);
            return c;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
