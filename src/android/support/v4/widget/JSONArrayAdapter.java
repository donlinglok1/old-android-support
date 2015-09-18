package android.support.v4.widget;

import net.minidev.json.JSONArray;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class JSONArrayAdapter extends BaseAdapter {
	public final transient Context context;
	public final transient Context baseContext;
	public final transient JSONArray listArray;

	public JSONArrayAdapter(final Context context, final JSONArray listArray) {
		super();
		this.context = context;
		this.listArray = listArray;
		baseContext = context.getApplicationContext();
	}

	@Override
	public final int getCount() {
		return listArray.size();
	}

	@Override
	public final Object getItem(final int position) {
		return listArray.get(position);
	}

	@Override
	public final long getItemId(final int position) {
		return position;
	}

	@Override
	public View getView(final int position, final View convertView,
			final ViewGroup parent) {
		return null;
	}
}
