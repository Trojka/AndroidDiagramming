package be.trojkasoftware.android.gestures;

public class DoInvalidateRunningTimerAction implements IGestureAction {

	public DoInvalidateRunningTimerAction() {
	}

	@Override
	public void executeAction(GestureEvent motion, TouchGesture gesture) {
		if(gesture.contextExists("TIMER_ONE"))
		{
			ITimer timer = (ITimer)gesture.getContext("TIMER_ONE");
			timer.stop();
		}
	}
}