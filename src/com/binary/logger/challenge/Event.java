package com.binary.logger.challenge;

import java.io.*;

/**
 * BinaryLoggable event implementation.
 */
public class Event implements BinaryLoggable, Serializable {
    private static final long serialVersionUID = 5230549622891722620L;

    private String eventName;
    private Long timestamp;
    private String details;

    /**
     * We have this ability to serialize event but it's not really necessary in this sample project.
     * @return byte array
     * @throws IOException in case of any IO issues
     */
    @Override
    public byte[] toBytes() throws IOException {
        byte[] stream = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos);) {
            oos.writeObject(this);
            oos.flush();
            stream = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream;
    }

    /**
     * We have this ability to deserialize event but it's not really necessary in this sample project.
     * @throws IOException in case of any IO issues
     */
    @Override
    public void fromBytes(byte[] rawBytes) throws IOException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(rawBytes);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            this.eventName = ois.readUTF();
            this.details = ois.readUTF();
            this.timestamp = ois.readLong();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Event Name: " + this.eventName + " | Event details: " + this.details + " | Timestamp: " + this.timestamp;
    }
}
