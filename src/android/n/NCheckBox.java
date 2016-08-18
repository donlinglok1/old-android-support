package android.n;

import android.view.View.OnClickListener;
import android.widget.CheckBox;

public class NCheckBox {
    private transient CheckBox view;

    public NCheckBox(final CheckBox view) {
	this.view = view;
    }

    public NCheckBox setVisibility(final int i) {
	view.setVisibility(i);
	return this;
    }

    public NCheckBox setChecked(final boolean b) {
	view.setChecked(b);
	return this;
    }

    public NCheckBox setOnClickListener(final OnClickListener l) {
	view.setOnClickListener(l);
	return this;
    }
}
