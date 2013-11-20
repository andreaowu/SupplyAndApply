package com.uem.supplyandapply;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: ItsTexter
 * Date: 11/15/13
 * Time: 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class SupplyPart implements Serializable{

    private String name;
    private int count;

    public SupplyPart(int count, String name) {
        this.count = count;
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeObject(name);
        stream.writeInt(count);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        name = (String) stream.readObject();
        count = stream.readInt();
    }
}
