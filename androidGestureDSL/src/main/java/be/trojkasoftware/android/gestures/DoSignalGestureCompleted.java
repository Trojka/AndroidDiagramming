package be.trojkasoftware.android.gestures;

public class DoSignalGestureCompleted implements IGestureAction {
	@Override
	public void executeAction(GestureEvent motion, TouchGesture gesture) {
		gesture.setAllEventsProcessed();
		if(gesture.contextExists(TouchHandler.TouchHandlerId))
		{
			TouchHandler handler = (TouchHandler)gesture.getContext(TouchHandler.TouchHandlerId);
			handler.tryReset();
		}
	}
}
