package be.trojkasoftware.android.gestures;

public class DoInstallTimer implements IGestureAction {

	public DoInstallTimer(IGestureAction action, int timeOut, TimerType timerType) {
		this.timerAction = action;
		this.timerTimeOut = timeOut;
		this.timerType = timerType;
	}

	@Override
	public void executeAction(GestureEvent motion, TouchGesture gesture) {
		ITimer timer = null;
		if (timerType == TimerType.FireOnce)
		{
			timer = new FireOnceTimer(timerAction, motion, gesture, timerTimeOut);
		}
		else if (timerType == TimerType.Repeating)
		{
			timer = new RepeatingTimer(timerAction, motion, gesture, timerTimeOut);
		}
		gesture.addContext("TIMER_ONE", timer);
		timer.start();
	}

	private int timerTimeOut = 0;
	private IGestureAction timerAction = null;
	private TimerType timerType;
}