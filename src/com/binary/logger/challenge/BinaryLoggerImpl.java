package com.binary.logger.challenge;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * This is a simple BinaryLogger implementation.
 * @param <T> any loggable event
 */
public class BinaryLoggerImpl<T extends BinaryLoggable> implements BinaryLogger<T> {

    private final String NEW_FILE_NAME_WORDS_SEPARATOR = "_";
    private FileOutputStream fileOutputStream;
    private ObjectOutputStream objectOutputStream;
    private final Long limit;
    private Long fileSize;
    private final String filePath;


    public BinaryLoggerImpl(){
        this.limit = 10000L;
        filePath = null;
    }

    /**
     *
     * @param filePath full path to the log file
     * @param fileSizeLimit file size limit
     * @throws IOException in case of any IO issues
     */
    public BinaryLoggerImpl(String filePath, Long fileSizeLimit) throws IOException {
        File f = new File(filePath);
        if (!f.exists()){
            f.createNewFile();
        }
        this.filePath = filePath;
        this.fileSize = f.length();
        this.limit = fileSizeLimit;

        if (limit < fileSize) {
            f = new File(this.filePath + NEW_FILE_NAME_WORDS_SEPARATOR + System.currentTimeMillis());
            f.createNewFile();
        }

        fileOutputStream = new FileOutputStream(f);
        objectOutputStream = new ObjectOutputStream(fileOutputStream);
    }

    /**
     * Read & deserialize events from log file
     *
     * @param file  a file instance from which to read from
     * @param clazz a class of the type T, clazz should have a public no-arg
     *              constructor
     * @return events
     * @throws IOException in case of any IO issues
     */
    @Override
    public Iterator<T> read(File file, Class<T> clazz) throws IOException {
        return new LoggableIterator<T>(file);
    }

    /**
     * For clothing output streams
     * @throws IOException in case of any IO issues
     */
    @Override
    public void close() throws IOException {
        try {
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Write event to the log.
     *
     * @param loggable an instance of {@link BinaryLoggable} that needs to be
     *                 logged
     * @throws IOException in case of any IO issues
     */
    @Override
    public void write(T loggable) throws IOException {
        byte[] binary = loggable.toBytes();
        try {
            checkLimits(binary);
            objectOutputStream.writeObject(loggable);
            objectOutputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Check file size and create new file if size is higher than limit
     *
     * @param binary new event for adding to the log
     * @throws IOException in case of any IO issues
     */
    private void checkLimits(byte[] binary) throws IOException {
        if (fileSize + binary.length > limit) {
            this.close();
            File f = new File(filePath + NEW_FILE_NAME_WORDS_SEPARATOR + System.currentTimeMillis());
            f.createNewFile();
            fileSize = 0L;
            fileOutputStream = new FileOutputStream(f);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
        }
        fileSize += binary.length;
    }
}
