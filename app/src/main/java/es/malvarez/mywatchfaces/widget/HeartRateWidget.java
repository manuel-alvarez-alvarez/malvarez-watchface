package es.malvarez.mywatchfaces.widget;

import android.app.Service;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;

import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.sport.SlptLastHeartRateView;

import java.util.Collections;
import java.util.List;

import es.malvarez.mywatchfaces.R;
import es.malvarez.mywatchfaces.data.DataType;
import es.malvarez.mywatchfaces.data.HeartRate;
import es.malvarez.mywatchfaces.resource.ResourceManager;

/**
 * Heart rate widget
 */
public class HeartRateWidget extends AbstractWidget {

    private TextPaint textPaint;
    private HeartRate heartRate;

    private float textTop;
    private float textLeft;

    private Drawable heartIcon;

    @Override
    public void init(Service service) {
        this.textLeft = service.getResources().getDimension(R.dimen.malvarez_heart_rate_text_left);
        this.textTop = service.getResources().getDimension(R.dimen.malvarez_heart_rate_text_top);

        this.textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.textPaint.setColor(service.getResources().getColor(R.color.malvarez_time_colour));
        this.textPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.BEBAS_NEUE));
        this.textPaint.setTextSize(service.getResources().getDimension(R.dimen.malvarez_circles_font_size));
        this.textPaint.setTextAlign(Paint.Align.CENTER);

        this.heartIcon = service.getResources().getDrawable(R.drawable.heart, null);
        this.setDrawableBounds(this.heartIcon, service.getResources().getDimension(R.dimen.malvarez_heart_rate_icon_left), service.getResources().getDimension(R.dimen.malvarez_heart_rate_icon_top));
    }

    @Override
    public List<DataType> getDataTypes() {
        return Collections.singletonList(DataType.HEART_RATE);
    }

    @Override
    public void onDataUpdate(DataType type, Object value) {
        this.heartRate = (HeartRate) value;
    }

    @Override
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        String text = heartRate == null || heartRate.getHeartRate() < 25 ? "--" : String.format("%d", heartRate.getHeartRate());
        canvas.drawText(text, textLeft, textTop, textPaint);
        this.heartIcon.draw(canvas);
    }

    @Override
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        SlptLinearLayout heart = new SlptLinearLayout();
        heart.add(new SlptLastHeartRateView());
        heart.setTextAttrForAll(
                service.getResources().getDimension(R.dimen.malvarez_circles_font_size_slpt),
                -1,
                ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.BEBAS_NEUE)
        );
        heart.setStart(
                (int) service.getResources().getDimension(R.dimen.malvarez_heart_rate_text_left_slpt),
                (int) service.getResources().getDimension(R.dimen.malvarez_heart_rate_text_top_slpt));
        return Collections.<SlptViewComponent>singletonList(heart);
    }
}
