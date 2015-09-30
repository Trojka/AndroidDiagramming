package be.trojkasoftware.android.sample.diagramming.sampledata;

import android.content.Context;

import be.trojkasoftware.android.diagramming.DiagramDesignerItem;
import be.trojkasoftware.android.sample.R;

public class CustomOvalItem extends DiagramDesignerItem {

	public CustomOvalItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
//	@Override
//	public String getDescription()
//	{
//		return "Oval";
//	}
	
	@Override
	public int getDrawableResourceId()
	{
		return R.drawable.custom_oval_item;
	}

}
