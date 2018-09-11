package edu.yjyx.student.module.main.api.input;

import java.io.Serializable;
import java.util.Map;

public abstract class BaseInput implements Serializable {
    public Map toMap(String[] keys, Object[] values) {
        throw new UnsupportedOperationException("Stub");
    }


    public abstract Map<String, String> toMap();

}