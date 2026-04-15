package com.aurus.server.shared;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import org.springframework.stereotype.Component;

@Component
public class TdsWindowNormalization {

    private Queue<Float> tdsWindow = new LinkedList<>();

    TdsWindowNormalization() {
    }

    public void addTdsToWindow(float tdsValue) {
        if (tdsWindow.size() < 50) {
            tdsWindow.add(tdsValue);
            return;
        }
        tdsWindow.poll();
    }

    public float getMaxValue() {
        return Collections.max(tdsWindow);
    }

    public float getMinValue() {
        return Collections.min(tdsWindow);
    }

    public int getSize() {
        return tdsWindow.size();
    }
}
