package com.insigma.vo;

import lombok.Data;

import java.io.*;
import javax.media.jai.remote.*;

import java.awt.image.*;

@Data
public class SomeSerializableClass
        implements Serializable {
    protected transient RenderedImage image;

    // Fields omitted.

    public SomeSerializableClass(RenderedImage image) {
        this.image = image;
    }

    // Methods omitted.

    // Serialization method.
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(new SerializableRenderedImage(image, true));
    }

    // Deserialization method.
    private void readObject(ObjectInputStream in) throws IOException,
            ClassNotFoundException {
        in.defaultReadObject();
        image = (RenderedImage) in.readObject();
    }
}
