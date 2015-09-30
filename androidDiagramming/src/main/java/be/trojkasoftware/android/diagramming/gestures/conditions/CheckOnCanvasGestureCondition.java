package be.trojkasoftware.android.diagramming.gestures.conditions;

import android.util.Log;

import java.util.List;

import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.HitDefinition;

import be.trojkasoftware.android.gestures.GestureConditionBase;
import be.trojkasoftware.android.gestures.GestureEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class CheckOnCanvasGestureCondition extends GestureConditionBase<DiagramDesignerView> {

	public CheckOnCanvasGestureCondition(DiagramDesignerView view) {
		super(view);
	}
	
	@Override
	public boolean checkCondition(GestureEvent motion, TouchGesture gesture) {
		List<HitDefinition> hitResult = getTouchBase().hitTest(motion.getPosition());
        int hitItemCount = hitResult.size();

        Log.i("CheckOnCanvasGestureCondition", (hitItemCount == 0)?"is on canvas":"is NOT on canvas");

		return hitItemCount == 0;
	}
}
