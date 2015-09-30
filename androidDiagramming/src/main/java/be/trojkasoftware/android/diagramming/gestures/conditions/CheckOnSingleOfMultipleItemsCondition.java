package be.trojkasoftware.android.diagramming.gestures.conditions;

import java.util.List;
import java.util.Map;

import android.graphics.Point;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.HitDefinition;

import be.trojkasoftware.android.gestures.GestureConditionBase;
import be.trojkasoftware.android.gestures.GestureEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class CheckOnSingleOfMultipleItemsCondition extends GestureConditionBase<DiagramDesignerView> {

	public CheckOnSingleOfMultipleItemsCondition(DiagramDesignerView view) {
		super(view);
	}
	
	@Override
	public boolean checkCondition(GestureEvent motion, TouchGesture gesture) {
		List<HitDefinition> hitResult = getTouchBase().hitTest(motion.getPosition());
		if( !(hitResult.size() == 1 && hitResult.get(0).isOnNode()) )
		{
			return false;
		}
		
		@SuppressWarnings("unchecked")
		Map<HitDefinition,Point> selectedItemsOriginalPosition = (Map<HitDefinition,Point>)gesture.getContext("MULTISELECT_ITEMS");
		for(HitDefinition hit:selectedItemsOriginalPosition.keySet())
		{
			if(hit.IsSame(hitResult.get(0)))
			{
				return true;
			}
		}
		
		return false;
	}
}
