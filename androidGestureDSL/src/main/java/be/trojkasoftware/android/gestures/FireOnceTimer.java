package be.trojkasoftware.android.gestures;

import android.os.Handler;

public class FireOnceTimer implements ITimer {
	private Handler handler;
	private Runnable timerAction;
	
	private IGestureAction timerGestureAction = null;
	
	private int timeOut;
	
	public FireOnceTimer(IGestureAction action, GestureEvent currentGestureEvent, TouchGesture currentGesture, int timeOut)
	{
		this.timerGestureAction = action;
		this.timeOut = timeOut;
		
		final GestureEvent gestureEvent = currentGestureEvent;
		final TouchGesture gesture = currentGesture;
		
		this.handler = new Handler();
		this.timerAction = new Runnable()
		{
			public void run() {
				timerGestureAction.executeAction(gestureEvent, gesture);
			}
		};
	}

	@Override
	public void start() {
		this.handler.postDelayed(this.timerAction, this.timeOut);
	}

	@Override
	public void stop() {
		if(this.timerAction != null)
		{
			this.handler.removeCallbacks(this.timerAction);
			this.timerAction = null;
		}
	}

}
