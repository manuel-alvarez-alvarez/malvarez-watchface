package es.malvarez.mywatchfaces.widget;

import android.app.Service;

import com.ingenic.iwds.slpt.view.core.SlptLayout;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;

import java.util.List;

/**
 * Widget with the slpt component
 */
public interface HasSlptViewComponent {

    List<SlptViewComponent> buildSlptViewComponent(Service service);
}
