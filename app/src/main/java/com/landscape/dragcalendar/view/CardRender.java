package com.landscape.dragcalendar.view;

import android.view.ViewGroup;

import java.util.Calendar;

/**
 * Created by landscape on 2016/11/30.
 */

public interface CardRender {
    void render(final ViewGroup view1, final Calendar today);
}
