package com.binary.logger.challenge;

import java.io.*;
import java.util.Iterator;
import java.util.function.Consumer;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class LoggableIterator<T> implements Iterator<T>, Closeable {

    private File file;
    private ObjectInputStream ois = null;
    private FileInputStream bais = null;
    private BufferedInputStream buf = null;
    private T nextVal = null;

    public LoggableIterator(File file) {
        this.file = file;
    }

    @Override
    public boolean hasNext() {
        if (isNull(ois)) {
            try {
                bais = new FileInputStream(file);
                buf = new BufferedInputStream(bais);
                ois = new ObjectInputStream(buf);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            nextVal = (T) ois.readObject();
            return true;
        } catch (Exception e) {
            try {
                close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public T next() {
        return nextVal;
    }

    @Override
    public void remove() {

    }

    @Override
    public void forEachRemaining(Consumer<? super T> action) {

    }

    @Override
    public void close() throws IOException {
        if (nonNull(ois)) ois.close();
        if (nonNull(bais)) bais.close();
        if (nonNull(buf)) buf.close();
    }
}
