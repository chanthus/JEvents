package com.cs.jevents.sample;

import com.cs.jevents.JEvent;

public class Model {

    // Initialize the event
    public final JEvent<TextChanged> textChanged = JEvent.create(TextChanged.class);

    private String text;

    public Model() {
        text = "Empty...";
    }

    public void setText(String text) {
        this.text = text;

        //Invoke the event
        textChanged.get().textChanged(text);
    }
}
