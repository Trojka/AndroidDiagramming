package be.trojkasoftware.android.sample.diagramming.sampledata;

import android.content.Context;

import be.trojkasoftware.android.diagramming.DiagramDesignerItem;
import be.trojkasoftware.android.sample.R;

public class CustomRoundedRectangularItem extends DiagramDesignerItem {

	public CustomRoundedRectangularItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
//	@Override
//	public String getDescription()
//	{
//		return "Custom Rounded rectangle";
//	}
	
	@Override
	public int getDrawableResourceId()
	{
		return R.drawable.custom_roundedrectangle_item;
	}

}
