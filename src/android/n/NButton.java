package android.n;

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
