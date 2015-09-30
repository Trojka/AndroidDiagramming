package be.trojkasoftware.android.gestures.dsl;

import be.trojkasoftware.android.gestures.CheckRelationDistanceOfRegisteredPointCondition;
import be.trojkasoftware.android.gestures.IGestureCondition;
import be.trojkasoftware.android.gestures.RelationType;
import be.trojkasoftware.android.gestures.TouchHandler;

public class DistanceRangeStartSelector {
	public DistanceRangeStartSelector(RelationType relationType, int distanceRange)
	{
		this.relationType = relationType;
		this.range = distanceRange;
	}
	
	public IGestureCondition fromLastEvent()
	{
		return new CheckRelationDistanceOfRegisteredPointCondition(relationType, range, TouchHandler.LastActionPos);
	}
	
	public IGestureCondition fromTouchDown(int index)
	{
		return new CheckRelationDistanceOfRegisteredPointCondition(relationType, range, TouchHandler.getEventId(TouchHandler.ActionDownPos, index));
	}
	
//	public IGestureCondition fromMove()
//	{
//		return  new CheckRelationDistanceOfRegisteredPointCondition(relationType, range, TouchHandler.ActionMovePos);
//	}
	
	public IGestureCondition fromTouchUp(int index)
	{
		return  new CheckRelationDistanceOfRegisteredPointCondition(relationType, range, TouchHandler.getEventId(TouchHandler.ActionUpPos, index));
	}
	
	private RelationType relationType;
	private int range;
}
