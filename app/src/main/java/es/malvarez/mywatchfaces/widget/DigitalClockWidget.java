package es.malvarez.mywatchfaces.widget;

import android.app.Service;
import android.graphics.Canvas;

/**
 * Digital clock widget.
 */
public abstract class DigitalClockWidget implements ClockWidget {

    @Override
    public void init(Service service) {
        // empty
    }

    public abstract void onDrawDigital(Canvas canvas, float width, float height, float centerX, float centerY, int seconds, int minutes, int hours, int year, int month, int day, int week, int ampm);
}
