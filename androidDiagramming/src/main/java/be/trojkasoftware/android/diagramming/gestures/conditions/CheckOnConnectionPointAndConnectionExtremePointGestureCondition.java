package be.trojkasoftware.android.diagramming.gestures.conditions;

import java.util.List;

import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.Direction;
import be.trojkasoftware.android.diagramming.HitDefinition;
import be.trojkasoftware.android.diagramming.DiagramDesignerConnection.ConnectorArea;

import be.trojkasoftware.android.gestures.GestureConditionBase;
import be.trojkasoftware.android.gestures.GestureEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class CheckOnConnectionPointAndConnectionExtremePointGestureCondition extends GestureConditionBase<DiagramDesignerView> {

	public CheckOnConnectionPointAndConnectionExtremePointGestureCondition(DiagramDesignerView view) {
		super(view);
	}
	
	@Override
	public boolean checkCondition(GestureEvent motion, TouchGesture gesture) {
		List<HitDefinition> hitResult = getTouchBase().hitTest(motion.getPosition());
		boolean twoOrMoreThingsWhereHit = hitResult.size() >= 2;
		if (!twoOrMoreThingsWhereHit)
		{
			return false;
		}
		return ((ResultContainsConnectionPoint(hitResult) && (numverOfConnectionExtremesHit(hitResult) >= 1 ))
				|| (!ResultContainsConnectionPoint(hitResult) && (numverOfConnectionExtremesHit(hitResult) >= 2 ))
				);
	}
	
	private boolean ResultContainsConnectionPoint(List<HitDefinition> hitResult)
	{
		for(HitDefinition hitDefinition : hitResult)
		{
			if(hitDefinition.isOnConnectionPoint())
			{
				return true;
			}
		}
		
		return false;
	}
	
	private int numverOfConnectionExtremesHit(List<HitDefinition> hitResult)
	{
		int hitCount = 0;
		for(HitDefinition hitDefinition : hitResult)
		{
			if(hitDefinition.isOnConnection()
					&& hitDefinition.connectorArea != ConnectorArea.Middle)
			{
				hitCount++;
			}
		}
		
		return hitCount;
		
	}
}
