package be.trojkasoftware.android.gestures;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.view.MotionEvent;

public class TouchHandler {
	
	public static String TouchHandlerId = "TOUCH_HANDLER";
	
	public static String LastActionPos = "LAST_ACTION_POSITION";
	public static String LastActionTime = "LAST_ACTION_TIME";
	
	public static String ActionDownPos = "ACTION_DOWN_POSITION";
	public static String ActionDownTime = "ACTION_DOWN_TIME";
	
	//public static String ActionMovePos = "ACTION_MOVE_POSITION";
	//public static String ActionMoveTime = "ACTION_MOVE_TIME";
	
	public static String ActionUpPos = "ACTION_UP_POSITION";
	public static String ActionUpTime = "ACTION_UP_TIME";
	
	public TouchHandler()
	{
	}
	
	public void addGesture(TouchGesture gesture)
	{
		gesture.addContext(TouchHandlerId, this);
		gestureList.add(gesture);
	}
	
	public static String getEventId(String dataKey, int index)
	{
		return dataKey + "_" + ((Integer)index).toString();
	}
	
	public void tryReset()
	{
		boolean canReset = true;

		for(TouchGesture eventOrder : gestureList)
		{
			// This can not be done if any gesture is valid but not yet completed
			if(eventOrder.isValid() && !eventOrder.isCompleted())
				canReset = false;
		}
		
		if(canReset)
		{
			for(TouchGesture eventOrder : gestureList)
			{
				eventOrder.reset();
				eventOrder.addContext(TouchHandlerId, this);
				
				touchDownCounter = 0;
				touchUpCounter = 0;
			}
		}
	}

	public void onTouchEvent(MotionEvent androidMotion)   {

		handleEvent(androidMotion);
		
		// Try resetting all the gestures
		tryReset();

	}
	
	public void handleEvent(MotionEvent androidMotion) {
    	int action = androidMotion.getActionMasked();
    	
    	GestureEvent motion = new GestureEvent(androidMotion);
      	
		switch (action) {
    	case MotionEvent.ACTION_DOWN:
    	case MotionEvent.ACTION_POINTER_DOWN:
			// We have a touchdown event
			touchDownCounter++;
    		for(TouchGesture eventOrder : gestureList)
    		{
				// Store some data we can query in our actions and conditions
    			if(eventOrder.contextExists(LastActionPos))
				{
					eventOrder.setContext(LastActionPos, motion.getPosition());
	    			eventOrder.addContext(LastActionTime, motion.getTime());
				}
				else
				{
					eventOrder.addContext(LastActionPos, motion.getPosition());
	    			eventOrder.addContext(LastActionTime, motion.getTime());
				}
    			
    			eventOrder.addContext(TouchHandler.getEventId(ActionDownPos, touchDownCounter), motion.getPosition());
    			eventOrder.addContext(TouchHandler.getEventId(ActionDownTime, touchDownCounter), motion.getTime());
				// If the sequence is still valid and we expect a touchdown event, then process it
	    		if(eventOrder.isValid() && eventOrder.current().event == TouchEvent.TOUCH_DOWN)
	    		{
	    			for(IfThenClause condition: eventOrder.current().conditionList)
	    			{
						// Execute the condition
	    				condition.Execute(motion, eventOrder);
						// Check if our gesture is still valid.
						//	It is possible that the condition invalidated the gesture.
						//	If this happened, there is no need to check any further conditions
	    				if(!eventOrder.isValid())
	    				{
	    					break;
	    				}
	    			}
	    			
					// Signal this part of the sequence as executed
					eventOrder.currentIsExecuted();
					// and move the sequence pointer forward
	    			eventOrder.moveNext();
	    		}
    		}

    		break;
    	case MotionEvent.ACTION_MOVE:
			// We have a move event
    		for(TouchGesture eventOrder : gestureList)
    		{
				// Store some data we can query in our actions and conditions
    			if(eventOrder.contextExists(LastActionPos))
				{
					eventOrder.setContext(LastActionPos, motion.getPosition());
	    			eventOrder.addContext(LastActionTime, motion.getTime());
				}
				else
				{
					eventOrder.addContext(LastActionPos, motion.getPosition());
	    			eventOrder.addContext(LastActionTime, motion.getTime());
				}
				
    			boolean isValid = false;
				// If the sequence is still valid and we expect a move event, then process it
	    		if(eventOrder.isValid() && eventOrder.current().event == TouchEvent.TOUCH_MOVE)
	    		{
	    			isValid = true;
	    			for(IfThenClause condition: eventOrder.current().conditionList)
	    			{
	    				condition.Execute(motion, eventOrder);
	    				if(!eventOrder.isValid())
	    				{
	    					break;
	    				}
	    			}

					eventOrder.currentIsExecuted();
	    			// Do not move to the next event because there will most likely be a series
	    			//	of these move-events and otherwise only one would be accepted
	    			//eventOrder.moveNext();
	    		}

	    		if(!isValid)
	    			eventOrder.invalidate();
    		}

    		break;
    	case MotionEvent.ACTION_UP:
    	case MotionEvent.ACTION_POINTER_UP:
			touchUpCounter++;
    		for(TouchGesture eventOrder : gestureList)
    		{
				// Store some data we can query in our actions and conditions
    			if(eventOrder.contextExists(LastActionPos))
				{
					eventOrder.setContext(LastActionPos, motion.getPosition());
	    			eventOrder.addContext(LastActionTime, motion.getTime());
				}
				else
				{
					eventOrder.addContext(LastActionPos, motion.getPosition());
	    			eventOrder.addContext(LastActionTime, motion.getTime());
				}
    			eventOrder.addContext(TouchHandler.getEventId(ActionUpPos, touchUpCounter), motion.getPosition());
    			eventOrder.addContext(TouchHandler.getEventId(ActionUpTime, touchUpCounter), motion.getTime());
				
				// If the sequence is still valid and we expect a move event
				//	which is not optional and is not executed yet
				// Then our sequence is no longer valid
				if(eventOrder.isValid() && (eventOrder.current().event == TouchEvent.TOUCH_MOVE) 
						&& !eventOrder.current().isOptional && !eventOrder.isCurrentExecuted())
				{
					eventOrder.invalidate();
				}
				// If the sequence is still valid and we expect a move event
				//	which is optional and is executed yet
				// Then move forward in the sequence
				if(eventOrder.isValid() && (eventOrder.current().event == TouchEvent.TOUCH_MOVE) 
						&& (eventOrder.current().isOptional || eventOrder.isCurrentExecuted()))
				{
					eventOrder.moveNext();
				}
				
				// If the sequence is still valid and we expect a touchup event, then process it
	    		if(eventOrder.isValid() && eventOrder.current().event == TouchEvent.TOUCH_UP)
	    		{
	    			for(IfThenClause condition: eventOrder.current().conditionList)
	    			{
	    				condition.Execute(motion, eventOrder);
	    				if(!eventOrder.isValid())
	    				{
	    					break;
	    				}
	    			}
	    			
					eventOrder.currentIsExecuted();
	    			eventOrder.moveNext();
	    		}
    		}

    		break;
    	}		
	}
	
	private List<TouchGesture> gestureList = new ArrayList<TouchGesture>();
	
	private int touchDownCounter = 0;
	private int touchUpCounter = 0;
}
