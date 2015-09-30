package be.trojkasoftware.android.diagramming.gestures.conditions;

import java.util.List;

import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.Direction;
import be.trojkasoftware.android.diagramming.HitDefinition;

import be.trojkasoftware.android.gestures.GestureConditionBase;
import be.trojkasoftware.android.gestures.GestureEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class CheckOnUnconnectedSourceConnectionPointGestureCondition extends GestureConditionBase<DiagramDesignerView> {

	public CheckOnUnconnectedSourceConnectionPointGestureCondition(DiagramDesignerView view) {
		super(view);
	}
	
	@Override
	public boolean checkCondition(GestureEvent motion, TouchGesture gesture) {
		List<HitDefinition> hitResult = getTouchBase().hitTest(motion.getPosition());
		return hitResult.size() == 1 
				&& hitResult.get(0).isOnConnectionPoint()
				&& (hitResult.get(0).connectionPoint.getDirection() == Direction.InOut
				 || hitResult.get(0).connectionPoint.getDirection() == Direction.Out);
	}
}
