package be.trojkasoftware.android.gestures.dsl;

import be.trojkasoftware.android.gestures.CheckRelationMilliSecondsOfTimingIdCondition;
import be.trojkasoftware.android.gestures.IGestureCondition;
import be.trojkasoftware.android.gestures.RelationType;
import be.trojkasoftware.android.gestures.TouchHandler;

public class TimeRangeStartSelector {
	public TimeRangeStartSelector(RelationType relationType, int distanceRange)
	{
		this.relationType = relationType;
		this.range = distanceRange;
	}
	
	public IGestureCondition fromTouchDown(int index)
	{
		return new CheckRelationMilliSecondsOfTimingIdCondition(relationType, range, TouchHandler.getEventId(TouchHandler.ActionDownTime, index));
	}
	
//	public IGestureCondition fromMove()
//	{
//		return new CheckRelationMilliSecondsOfTimingIdCondition(relationType, range, TouchHandler.ActionMoveTime);
//	}
	
	public IGestureCondition fromTouchUp(int index)
	{
		return new CheckRelationMilliSecondsOfTimingIdCondition(relationType, range, TouchHandler.getEventId(TouchHandler.ActionUpTime, index));
	}
	
	private RelationType relationType;
	private int range;
}
