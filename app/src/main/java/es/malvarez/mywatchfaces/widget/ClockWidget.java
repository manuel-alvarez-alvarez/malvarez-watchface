package es.malvarez.mywatchfaces.widget;

import android.app.Service;

/**
 * Interface for the clock widget.
 */
public interface ClockWidget extends HasSlptViewComponent{

    void init(Service service);
}
