package es.malvarez.mywatchfaces.widget;

import android.app.Service;
import android.graphics.Canvas;

import es.malvarez.mywatchfaces.AbstractWatchFace;

/**
 * Abstract clock
 */
public abstract class AnalogClockWidget implements ClockWidget {

    @Override
    public void init(Service service) {
        // empty
    }

    public abstract void onDrawAnalog(Canvas canvas, float width, float height, float centerX, float centerY, float secRot, float minRot, float hrRot);
}
