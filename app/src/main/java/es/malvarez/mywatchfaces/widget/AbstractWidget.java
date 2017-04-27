package es.malvarez.mywatchfaces.widget;

import android.app.Service;
import android.graphics.drawable.Drawable;

import java.util.Collections;
import java.util.List;

import es.malvarez.mywatchfaces.data.DataType;
import es.malvarez.mywatchfaces.data.MultipleWatchDataListener;

/**
 * Base class for shapes
 */
public abstract class AbstractWidget implements Widget, MultipleWatchDataListener {

    private int x = 0;
    private int y = 0;

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    public void init(Service service) {
        // empty
    }

    @Override
    public List<DataType> getDataTypes() {
        return Collections.emptyList();
    }

    @Override
    public void onDataUpdate(DataType type, Object value) {

    }

    protected void setDrawableBounds(Drawable drawable, float x, float y) {
        drawable.setBounds((int) x, (int) y, (int) x + drawable.getMinimumWidth(), (int) y + drawable.getMinimumHeight());
    }
}
