package be.trojkasoftware.android.gestures.dsl;

import be.trojkasoftware.android.gestures.GestureEvent;
import be.trojkasoftware.android.gestures.IGestureCondition;
import be.trojkasoftware.android.gestures.IfThenClause;
import be.trojkasoftware.android.gestures.TouchEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class NextGestureAfterTouchDown {
	
	public NextGestureAfterTouchDown(TouchGesture gstr)
	{
		gesture = gstr;
	}
	
	public ActionAfterGestureOrConditional<NextGestureAfterMove> Move()
	{
	    TouchEvent moveEvent = new TouchEvent();
	    moveEvent.event = TouchEvent.TOUCH_MOVE;
	    moveEvent.isOptional = false;
	    
	    gesture.addEvent(moveEvent);
		
		ActionAfterGestureOrConditional<NextGestureAfterMove> result = new ActionAfterGestureOrConditional<NextGestureAfterMove>(NextGestureAfterMove.class, gesture, moveEvent);		
		return result;
	}
	
	public ActionAfterGestureOrConditional<NextGestureAfterMove> CanMove()
	{
	    TouchEvent moveEvent = new TouchEvent();
	    moveEvent.event = TouchEvent.TOUCH_MOVE;
	    moveEvent.isOptional = true;
	    
	    gesture.addEvent(moveEvent);
		
		ActionAfterGestureOrConditional<NextGestureAfterMove> result = new ActionAfterGestureOrConditional<NextGestureAfterMove>(NextGestureAfterMove.class, gesture, moveEvent);		
		return result;
	}
	
	public ActionAfterGestureOrConditional<NextGestureAfterTouchUp> TouchUp()
	{
		int size = gesture.size();
		if(size == 0)
		{
			// If no other events exist, 
			//	we insert a TOUCH_DOWN which is always accepted. You can not have a TOUCH_UP without having had a TOUCH_DOWN
		    TouchEvent upEvent = new TouchEvent();
		    upEvent.event = TouchEvent.TOUCH_DOWN;
			IfThenClause condition = new IfThenClause(
					new IGestureCondition()
					{
						public boolean checkCondition(GestureEvent motion, TouchGesture gesture)
						{
							return true;
						}
					}
			);
			upEvent.conditionList.add(condition);
		    gesture.addEvent(upEvent);
			
		}
		
	    TouchEvent upEvent = new TouchEvent();
	    upEvent.event = TouchEvent.TOUCH_UP;
	    
	    gesture.addEvent(upEvent);
		
		ActionAfterGestureOrConditional<NextGestureAfterTouchUp> move = new ActionAfterGestureOrConditional<NextGestureAfterTouchUp>(NextGestureAfterTouchUp.class, gesture, upEvent);
		return move;
	}
	
	TouchGesture gesture;

}
