package be.trojkasoftware.android.gestures.dsl;

import be.trojkasoftware.android.gestures.DoInstallTimer;
import be.trojkasoftware.android.gestures.DoMultipleAction;
import be.trojkasoftware.android.gestures.IGestureAction;
import be.trojkasoftware.android.gestures.TimerType;

public class TimerActionConfiguration {
	public TimerActionConfiguration(int timeOutValue, TimerType timerType)
	{
		this.timeOut = timeOutValue;
		this.timerType = timerType;
	}
	
	public DoInstallTimer Do(IGestureAction action)
	{
		return new DoInstallTimer(action, timeOut, timerType);
	}
	
	public DoInstallTimer Do(IGestureAction action1, IGestureAction action2)
	{
		return new DoInstallTimer(new DoMultipleAction(action1, action2), timeOut, timerType);
	}
	
	private int timeOut;
	private TimerType timerType;
}
