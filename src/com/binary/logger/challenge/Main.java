package com.binary.logger.challenge;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * This class is used for testing purposes.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length >= 1) {
            String command = args[0];
            if (command.equalsIgnoreCase("write")) {
                System.out.println("Writing event operation.");
                performWriteOperation(args);
            } else if (command.equalsIgnoreCase("read")) {
                System.out.println("reading events operation.");
                readEventsFromFile(args[1]);
            } else {
                System.out.println("Unsupported operation. Please set 'read' or 'write' to the fist argument.");
            }
        } else {
            System.out.println("More arguments required.");
        }
    }

    /**
     * Read events and write to the console their details.
     *
     * @param pathToFile path to the file for reading events
     * @throws IOException in case of any IO issues
     */
    private static void readEventsFromFile(String pathToFile) throws IOException {
        BinaryLogger<Event> logger = new BinaryLoggerImpl<>();
        Iterator<Event> iterator = logger.read(new File(pathToFile), Event.class);
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    /**
     * Write events to the binary log file.
     *
     * @param args There are 5 arguments supported.
     *             1) type of operation "write"
     *             2) path to file
     *             3) how many events would you like to generate. It's used for testing. Default 10(not required)
     *             4) Event name value(not required)
     *             5) Event details value(not required)
     *             6) File size limit. Once it's higher than limit then new file will be created for other events.(not required)
     * @throws IOException in case of any IO issues
     */
    private static void performWriteOperation(String[] args) throws IOException {
        String pathToFile = args[1];
        Integer multiplyEvent = args.length >= 3 ? Integer.parseInt(args[2]) : 10;// For generating test data events.

        String eventName = args.length >= 4 ? args[3] : "Test Default Event";
        String details = args.length >= 5 ? args[4] : "Test Details";
        long fileSizeLimit = args.length >= 6 ? Long.parseLong(args[5]) : 100000L;

        List<Event> events = generateMockEvents(eventName, details, multiplyEvent);
        try (BinaryLogger<Event> logger = new BinaryLoggerImpl<>
                (pathToFile, fileSizeLimit)) {
            for (Event event : events) {
                logger.write(event);
            }
        }
    }

    /**
     * Generate desired number of mock events.
     *
     * @param eventName any event name
     * @param details   any random phrase
     * @param multiply  how many events would you like to generate
     * @return {@link List<Event>} generated events
     */
    public static List<Event> generateMockEvents(String eventName, String details, Integer multiply) {
        List<Event> events = new ArrayList<>();
        for (int i = 0; i < multiply; i++) {
            events.add(toEvent(i, eventName, details));
        }
        return events;
    }

    /**
     * Convert event details to {@link Event} object.
     *
     * @param i         is used for having different names in event details, just for better testing
     * @param eventName any event name
     * @param details   any random phrase
     * @return {@link Event}
     */
    public static Event toEvent(Integer i, String eventName, String details) {
        Event e = new Event();
        e.setDetails(i + details);
        e.setEventName(i + eventName);
        e.setTimestamp(new Date().getTime());
        return e;
    }
}
