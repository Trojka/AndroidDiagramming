package be.trojkasoftware.android.gestures;

public class DoTryResetHandlerAction implements IGestureAction {

	public DoTryResetHandlerAction() {
	}

	@Override
	public void executeAction(GestureEvent motion, TouchGesture gesture) {
		if(gesture.contextExists(TouchHandler.TouchHandlerId))
		{
			TouchHandler handler = (TouchHandler)gesture.getContext(TouchHandler.TouchHandlerId);
			handler.tryReset();
		}
	}
}