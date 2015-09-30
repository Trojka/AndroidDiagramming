package be.trojkasoftware.android.gestures;

public class DoInvalidateGestureGestureAction implements IGestureAction {
	@Override
	public void executeAction(GestureEvent motion, TouchGesture gesture) {
		gesture.invalidate();
	}
}
