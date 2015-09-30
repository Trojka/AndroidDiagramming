package be.trojkasoftware.android.gestures.dsl;

import be.trojkasoftware.android.gestures.TouchEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class NextGestureAfterMove {
	
	public NextGestureAfterMove(TouchGesture gstr)
	{
		gesture = gstr;
	}
	
	public ActionAfterGestureOrConditional<NextGestureAfterTouchUp> TouchUp()
	{
	    TouchEvent upEvent = new TouchEvent();
	    upEvent.event = TouchEvent.TOUCH_UP;
	    
	    gesture.addEvent(upEvent);
		
		ActionAfterGestureOrConditional<NextGestureAfterTouchUp> move = new ActionAfterGestureOrConditional<NextGestureAfterTouchUp>(NextGestureAfterTouchUp.class, gesture, upEvent);
		return move;
	}
	
	TouchGesture gesture;

}
