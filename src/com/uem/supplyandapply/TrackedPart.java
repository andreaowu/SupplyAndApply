package com.uem.supplyandapply;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * User: ItsTexter
 */
public class TrackedPart implements Serializable{

    private String name;
    private int brought;
    private int needed;

    public TrackedPart(String name, int brought, int needed) {
        this.name = name;
        this.brought = brought;
        this.needed = needed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBrought() {
        return brought;
    }

    public void setBrought(int brought) {
        this.brought = brought;
    }

    public int getNeeded() {
        return needed;
    }

    public void setNeeded(int needed) {
        this.needed = needed;
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeObject(name);
        stream.writeInt(brought);
        stream.writeInt(needed);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        name = (String) stream.readObject();
        brought = stream.readInt();
        needed = stream.readInt();
    }
}