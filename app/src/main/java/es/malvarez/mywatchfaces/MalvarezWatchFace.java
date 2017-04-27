package es.malvarez.mywatchfaces;

import com.huami.watch.watchface.AbstractSlptClock;

import es.malvarez.mywatchfaces.widget.CirclesWidget;
import es.malvarez.mywatchfaces.widget.HeartRateWidget;
import es.malvarez.mywatchfaces.widget.MalvarezClock;

/**
 * Amazfit watch faces
 */

public class MalvarezWatchFace extends AbstractWatchFace {

    public MalvarezWatchFace() {
        super(
                new MalvarezClock(),
                new CirclesWidget(),
                new HeartRateWidget()
        );
    }

    @Override
    protected Class<? extends AbstractSlptClock> slptClockClass() {
        return MalvarezWatchFaceSplt.class;
    }
}