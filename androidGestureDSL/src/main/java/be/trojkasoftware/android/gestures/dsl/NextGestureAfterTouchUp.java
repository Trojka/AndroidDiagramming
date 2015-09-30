package be.trojkasoftware.android.gestures.dsl;

import be.trojkasoftware.android.gestures.TouchEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class NextGestureAfterTouchUp {
	
	public NextGestureAfterTouchUp(TouchGesture gstr)
	{
		gesture = gstr;
	}
	
	public ActionAfterGestureOrConditional<NextGestureAfterTouchDown> TouchDown()
	{
	    TouchEvent downEvent = new TouchEvent();
	    downEvent.event = TouchEvent.TOUCH_DOWN;
	    
	    gesture.addEvent(downEvent);
		
		ActionAfterGestureOrConditional<NextGestureAfterTouchDown> touchDown = new ActionAfterGestureOrConditional<NextGestureAfterTouchDown>(NextGestureAfterTouchDown.class, gesture, downEvent);
		return touchDown;
	}
	
	TouchGesture gesture;
}
