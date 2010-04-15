/*
* Copyright (c) 2007 TouK
* All rights reserved
*/
package pl.touk.tola.example.gwt.client;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import pl.touk.tola.gwt.client.widgets.MessagesHistoryPanel;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class MessageHistoryExample extends LayoutContainer{

    public MessageHistoryExample() {
        this.setLayoutOnChange(true);

        final MessagesHistoryPanel histPanel = new MessagesHistoryPanel();

        HorizontalPanel buttons = new HorizontalPanel();
        buttons.setSpacing(4);
        buttons.add(new Button("Dodaj komunikat INFO", new SelectionListener<ButtonEvent>() {

            public void componentSelected(ButtonEvent ce) {
                histPanel.addMessage("Komunikat informacyjnye",MessagesHistoryPanel.Type.INFO);
            }
        }));

        buttons.add(new Button("Dodaj komunikat ERROR", new SelectionListener<ButtonEvent>() {

            public void componentSelected(ButtonEvent ce) {
                histPanel.addMessage("Komunikat error",MessagesHistoryPanel.Type.ERROR);
            }
        }));
        buttons.add(new Button("Dodaj komunikat WARN", new SelectionListener<ButtonEvent>() {

            public void componentSelected(ButtonEvent ce) {
                histPanel.addMessage("Komunikat warn",MessagesHistoryPanel.Type.WARNING);
            }
        }));

        this.setLayout(new BorderLayout());

        BorderLayoutData northData = new BorderLayoutData(Style.LayoutRegion.NORTH, 50);
        this.add(buttons, northData);

        BorderLayoutData centerData = new BorderLayoutData(Style.LayoutRegion.CENTER);
        centerData.setMargins(new Margins(2, 2, 2, 2));
        this.add(histPanel, centerData);
    }
}
