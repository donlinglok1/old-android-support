package android.support.v4.lang;

import android.widget.Button;

public class NButton {
    private transient Button view;

    public NButton(final Button view) {
	this.view = view;
    }

    public Button setVisibility(final int i) {
	view.setVisibility(i);
	return view;
    }
}
