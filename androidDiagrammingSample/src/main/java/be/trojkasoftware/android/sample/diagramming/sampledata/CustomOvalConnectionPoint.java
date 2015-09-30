package be.trojkasoftware.android.sample.diagramming.sampledata;

import be.trojkasoftware.android.diagramming.ConnectionPoint;
import be.trojkasoftware.android.sample.R;

public class CustomOvalConnectionPoint extends ConnectionPoint {
	public CustomOvalConnectionPoint()
	{
		super();
	}
	
	public int getConnectionPointDrawableResourceId()
	{
		return R.drawable.custom_oval_connectionpoint;
	}

}
