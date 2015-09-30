package be.trojkasoftware.android.gestures.dsl;

import be.trojkasoftware.android.gestures.TimerType;

public class TimerTimeConfiguration {
	
	public TimerTimeConfiguration(TimerType timerType)
	{
		this.timerType = timerType;
	}
	
	public TimerActionConfiguration seconds(int seconds)
	{
		return new TimerActionConfiguration(seconds * 1000, timerType);
	}
	
	private TimerType timerType;
}
