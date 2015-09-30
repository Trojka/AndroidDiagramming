package be.trojkasoftware.android.diagramming.gestures.conditions;

import java.util.List;

import android.util.Log;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.HitDefinition;

import be.trojkasoftware.android.gestures.GestureConditionBase;
import be.trojkasoftware.android.gestures.GestureEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class CheckOnSingleItemGestureCondition extends GestureConditionBase<DiagramDesignerView> {

	public CheckOnSingleItemGestureCondition(DiagramDesignerView view) {
		super(view);
	}
	
	@Override
	public boolean checkCondition(GestureEvent motion, TouchGesture gesture) {
		List<HitDefinition> hitResult = getTouchBase().hitTest(motion.getPosition());
		
		if(hitResult.size() == 1)
		{
			Log.i("CheckOnSingleItemGestureCondition", "is on single thing");
			if(hitResult.get(0).isOnNode())
			{
				Log.i("CheckOnSingleItemGestureCondition", "and single thing is node");
			}
			else
			{
				Log.i("CheckOnSingleItemGestureCondition", "and single thing is not node");
			}
		}
		else
		{
			Log.i("CheckOnSingleItemGestureCondition", "is not on single thing[" + hitResult.size() + "]");
		}
			
		
		boolean isOnSingleItem = hitResult.size() == 1 && hitResult.get(0).isOnNode();
		if(isOnSingleItem)
		{
			Log.i("CheckOnSingleItemGestureCondition", "is on single item");
		}
		else
		{
			Log.i("CheckOnSingleItemGestureCondition", "is not on single item");
		}
		return isOnSingleItem;
	}
}
