/*
 *  Copyright (C) 2012 TouK sp. z o.o. s.k.a.
 *  All rights reserved
 */
package pl.touk.tola.gwt.client.widgets;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

public class MessageWindow extends Window {

    protected MessageWindow(String title, String message) {

        this.setHeading(title);
        this.setSize(400, 200);
        this.setLayout(new FitLayout());

        Button button = new Button("Zamknij", new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                hide();
            }
        });

        TextArea messageBox = new TextArea();
        messageBox.setValue(message);
        messageBox.setReadOnly(true);

        this.addButton(button);
        this.add(messageBox);
    }

    public static void show(String title, String message) {
        MessageWindow w = new MessageWindow(title, message);
        w.show();
    }
}
