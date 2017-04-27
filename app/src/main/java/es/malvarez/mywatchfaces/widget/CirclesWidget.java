package es.malvarez.mywatchfaces.widget;

import android.app.Service;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import com.huami.watch.watchface.util.Util;
import com.ingenic.iwds.slpt.view.arc.SlptPowerArcAnglePicView;
import com.ingenic.iwds.slpt.view.arc.SlptTodayDistanceArcAnglePicView;
import com.ingenic.iwds.slpt.view.arc.SlptTodayStepArcAnglePicView;
import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.sport.SlptPowerNumView;
import com.ingenic.iwds.slpt.view.sport.SlptTodaySportDistanceLView;
import com.ingenic.iwds.slpt.view.sport.SlptTodayStepNumView;

import java.util.Arrays;
import java.util.List;

import es.malvarez.mywatchfaces.R;
import es.malvarez.mywatchfaces.data.Battery;
import es.malvarez.mywatchfaces.data.DataType;
import es.malvarez.mywatchfaces.data.Steps;
import es.malvarez.mywatchfaces.data.TodayDistance;
import es.malvarez.mywatchfaces.resource.ResourceManager;

public class CirclesWidget extends AbstractWidget {

    private final static int MARGIN = 1;

    private Paint ring;
    private Paint circle;
    private Paint textPaint;
    private int backgroundColour;
    private int batteryColour;
    private int stepsColour;
    private int sportColour;
    private int thickness;

    private Battery batteryData;
    private Float batterySweepAngle = null;
    private Drawable batteryIcon;

    private Steps stepsData;
    private Float stepsSweepAngle = null;
    private Drawable stepsIcon;

    private TodayDistance sportData;
    private Float sportSweepAngle = null;
    private Drawable sportIcon;

    private final float startAngleBattery = 30;
    private final float arcSizeBattery = 360 - startAngleBattery - startAngleBattery;

    private final float startAngleSteps = startAngleBattery + 3;
    private final float arcSizeSteps = 360 - startAngleSteps - startAngleSteps;

    private final float startAngleSport = startAngleSteps + 3;
    private final float arcSizeSport = 360 - startAngleSport - startAngleSport;

    private float batteryTextLeft;
    private float batteryTextTop;
    private float stepsTextLeft;
    private float stepsTextTop;
    private float sportTextLeft;
    private float sportTextTop;

    @Override
    public void init(Service service) {
        this.thickness = (int) service.getResources().getDimension(R.dimen.malvarez_circles_thickness);

        this.backgroundColour = service.getResources().getColor(R.color.malvarez_circles_background);

        this.batteryColour = service.getResources().getColor(R.color.malvarez_circles_battery_colour);
        this.batteryIcon = service.getResources().getDrawable(R.drawable.battery, null);
        this.setDrawableBounds(this.batteryIcon, service.getResources().getDimension(R.dimen.malvarez_battery_icon_left), service.getResources().getDimension(R.dimen.malvarez_battery_icon_top));

        this.stepsColour = service.getResources().getColor(R.color.malvarez_circles_steps_colour);
        this.stepsIcon = service.getResources().getDrawable(R.drawable.steps, null);
        this.setDrawableBounds(this.stepsIcon, service.getResources().getDimension(R.dimen.malvarez_steps_icon_left), service.getResources().getDimension(R.dimen.malvarez_steps_icon_top));

        this.sportColour = service.getResources().getColor(R.color.malvarez_circles_sport_colour);
        this.sportIcon = service.getResources().getDrawable(R.drawable.sport, null);
        this.setDrawableBounds(this.sportIcon, service.getResources().getDimension(R.dimen.malvarez_sport_icon_left), service.getResources().getDimension(R.dimen.malvarez_sport_icon_top));

        this.textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.textPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.BEBAS_NEUE));
        this.textPaint.setTextSize(service.getResources().getDimension(R.dimen.malvarez_circles_font_size));
        this.textPaint.setColor(service.getResources().getColor(R.color.malvarez_time_colour));
        this.textPaint.setTextAlign(Paint.Align.CENTER);

        this.ring = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.ring.setStrokeCap(Paint.Cap.ROUND);
        this.ring.setStyle(Paint.Style.STROKE);
        this.ring.setStrokeWidth(this.thickness);

        this.circle = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.circle.setColor(Color.BLACK);
        this.circle.setStrokeWidth(1f);
        this.circle.setStyle(Paint.Style.STROKE);

        this.batteryTextLeft = service.getResources().getDimension(R.dimen.malvarez_battery_text_left);
        this.batteryTextTop = service.getResources().getDimension(R.dimen.malvarez_battery_text_top);
        this.stepsTextLeft = service.getResources().getDimension(R.dimen.malvarez_steps_text_left);
        this.stepsTextTop = service.getResources().getDimension(R.dimen.malvarez_steps_text_top);
        this.sportTextLeft = service.getResources().getDimension(R.dimen.malvarez_sport_text_left);
        this.sportTextTop = service.getResources().getDimension(R.dimen.malvarez_sport_text_top);
    }

    @Override
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        int count = canvas.save();

        int radius = Math.round(Math.min(width / 2, height / 2)) - this.thickness;

        RectF oval = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        // rotate from 0 to 270 degrees
        canvas.rotate(90, centerX, centerY);

        this.ring.setColor(this.backgroundColour);
        canvas.drawArc(oval, startAngleBattery, arcSizeBattery, false, ring);
        if (batterySweepAngle != null) {
            float px = getPointX(oval, centerX, startAngleBattery, batterySweepAngle);
            float py = getPointY(oval, centerY, startAngleBattery, batterySweepAngle);
            this.ring.setColor(this.batteryColour);
            canvas.drawArc(oval, startAngleBattery, batterySweepAngle, false, ring);
            canvas.drawCircle(px, py, this.thickness / 3f, circle);
            canvas.drawCircle(px, py, this.thickness / 6f, circle);
        }

        oval = nextOval(oval);
        this.ring.setColor(this.backgroundColour);
        canvas.drawArc(oval, startAngleSteps, arcSizeSteps, false, ring);
        if (stepsSweepAngle != null) {
            float px = getPointX(oval, centerX, startAngleSteps, stepsSweepAngle);
            float py = getPointY(oval, centerY, startAngleSteps, stepsSweepAngle);
            this.ring.setColor(this.stepsColour);
            canvas.drawArc(oval, startAngleSteps, stepsSweepAngle, false, ring);
            canvas.drawCircle(px, py, this.thickness / 3f, circle);
            canvas.drawCircle(px, py, this.thickness / 6f, circle);
        }

        oval = nextOval(oval);
        this.ring.setColor(this.backgroundColour);
        canvas.drawArc(oval, startAngleSport, arcSizeSport, false, ring);
        if (sportSweepAngle != null) {
            float px = getPointX(oval, centerX, startAngleSport, sportSweepAngle);
            float py = getPointY(oval, centerY, startAngleSport, sportSweepAngle);
            this.ring.setColor(this.sportColour);
            canvas.drawArc(oval, startAngleSport, sportSweepAngle, false, ring);
            canvas.drawCircle(px, py, this.thickness / 3f, circle);
            canvas.drawCircle(px, py, this.thickness / 6f, circle);
        }

        canvas.restoreToCount(count);

        this.batteryIcon.draw(canvas);
        this.stepsIcon.draw(canvas);
        this.sportIcon.draw(canvas);

        if (this.batteryData != null) {
            String text = String.format("%02d", this.batteryData.getLevel() * 100 / this.batteryData.getScale());
            canvas.drawText(text, batteryTextLeft, batteryTextTop, textPaint);
        }

        if (this.stepsData != null) {
            String text = String.format("%s", this.stepsData.getSteps());
            canvas.drawText(text, stepsTextLeft, stepsTextTop, textPaint);
        }

        if (this.sportData != null) {
            String text = String.format("%s", this.sportData.getDistance());
            canvas.drawText(text, sportTextLeft, sportTextTop, textPaint);
        }

    }

    @Override
    public void onDataUpdate(DataType type, Object value) {
        switch (type) {
            case STEPS:
                onSteps((Steps) value);
                break;
            case BATTERY:
                onBatteryData((Battery) value);
                break;
            case DISTANCE:
                onDistanceData((TodayDistance) value);
                break;
        }
    }

    @Override
    public List<DataType> getDataTypes() {
        return Arrays.asList(DataType.BATTERY, DataType.STEPS, DataType.DISTANCE);
    }


    private void onSteps(Steps steps) {
        this.stepsData = steps;
        if (stepsData == null || stepsData.getTarget() == 0) {
            this.stepsSweepAngle = 0f;
        } else {
            this.stepsSweepAngle = Math.min(arcSizeSteps, arcSizeSteps * (stepsData.getSteps() / (float) stepsData.getTarget()));
        }
    }

    private void onBatteryData(Battery battery) {
        this.batteryData = battery;
        if (batteryData == null) {
            this.batterySweepAngle = 0f;
        } else {
            float scale = batteryData.getLevel() / (float) batteryData.getScale();
            this.batterySweepAngle = Math.min(arcSizeBattery, arcSizeBattery * scale);
        }
    }

    private void onDistanceData(TodayDistance distance) {
        this.sportData = distance;
        if (sportData == null || sportData.getDistance() <= 0) {
            this.sportSweepAngle = 0f;
        } else {
            double scale = sportData.getDistance() / 3.0d;
            this.sportSweepAngle = (float) Math.min(arcSizeSport, arcSizeSport * scale);
        }
    }

    private RectF nextOval(RectF oval) {
        oval.left = oval.left + this.thickness + MARGIN;
        oval.top = oval.top + this.thickness + MARGIN;
        oval.right = oval.right - this.thickness - MARGIN;
        oval.bottom = oval.bottom - this.thickness - MARGIN;
        return oval;
    }

    private float getPointX(RectF oval, float cx, float startAngle, float sweepAngle) {
        float width = oval.right - oval.left;
        return (float) (cx + (width / 2D) * Math.cos((sweepAngle + startAngle) * Math.PI / 180));
    }

    private float getPointY(RectF oval, float cy, float startAngle, float sweepAngle) {
        float height = oval.bottom - oval.top;
        return (float) (cy + (height / 2D) * Math.sin((sweepAngle + startAngle) * Math.PI / 180));
    }

    @Override
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        Typeface timeTypeFace = ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.BEBAS_NEUE);

        SlptLinearLayout power = new SlptLinearLayout();
        power.alignX = 2;
        power.alignY = 2;
        power.add(new SlptPowerNumView());
        power.setTextAttrForAll(
                service.getResources().getDimension(R.dimen.malvarez_circles_font_size_slpt),
                -1,
                timeTypeFace
        );
        power.setStart(
                (int) service.getResources().getDimension(R.dimen.malvarez_battery_text_left_slpt),
                (int) service.getResources().getDimension(R.dimen.malvarez_battery_text_top_slpt));

        SlptLinearLayout steps = new SlptLinearLayout();
        steps.alignX = 2;
        steps.alignY = 2;
        steps.add(new SlptTodayStepNumView());
        steps.setTextAttrForAll(
                service.getResources().getDimension(R.dimen.malvarez_circles_font_size_slpt),
                -1,
                timeTypeFace
        );

        steps.setStart(
                (int) service.getResources().getDimension(R.dimen.malvarez_steps_text_left_slpt),
                (int) service.getResources().getDimension(R.dimen.malvarez_steps_text_top_slpt));

        SlptLinearLayout sport = new SlptLinearLayout();
        sport.add(new SlptTodaySportDistanceLView());
        sport.setTextAttrForAll(
                service.getResources().getDimension(R.dimen.malvarez_circles_font_size_slpt),
                -1,
                timeTypeFace
        );
        sport.setStart(
                (int) service.getResources().getDimension(R.dimen.malvarez_sport_text_left_slpt),
                (int) service.getResources().getDimension(R.dimen.malvarez_sport_text_top_slpt));

        SlptPowerArcAnglePicView powerArcView = new SlptPowerArcAnglePicView();
        powerArcView.setImagePicture(Util.assetToBytes(service, "battery_splt.png"));
        powerArcView.start_angle = (int) startAngleBattery + 180 - 3;
        powerArcView.full_angle = (int) arcSizeBattery + 6;

        SlptTodayStepArcAnglePicView stepArcPicView = new SlptTodayStepArcAnglePicView();
        stepArcPicView.setImagePicture(Util.assetToBytes(service, "steps_splt.png"));
        stepArcPicView.start_angle = (int) startAngleSteps + 180 - 3;
        stepArcPicView.full_angle = (int) arcSizeSteps + 6;


        SlptTodayDistanceArcAnglePicView distanceArcPicView = new SlptTodayDistanceArcAnglePicView();
        distanceArcPicView.setImagePicture(Util.assetToBytes(service, "sports_splt.png"));
        distanceArcPicView.start_angle = (int) startAngleSport + 180 - 3;
        distanceArcPicView.full_angle = (int) arcSizeSport + 6;

        return Arrays.asList(power, steps, sport, powerArcView, stepArcPicView, distanceArcPicView);
    }
}