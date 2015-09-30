package be.trojkasoftware.android.gestures;

import android.os.Handler;
import android.util.Log;

public class RepeatingTimer implements ITimer {
	private Handler handler;
	private Runnable timerAction;
	
	private IGestureAction timerGestureAction = null;
	
	private int timeOut;
	private boolean isStopped = false;
	
	public RepeatingTimer(IGestureAction action, GestureEvent currentGestureEvent, TouchGesture currentGesture, int timeOut)
	{
		this.timerGestureAction = action;
		this.timeOut = timeOut;
		
		final GestureEvent gestureEvent = currentGestureEvent;
		final TouchGesture gesture = currentGesture;
		
		final ITimer me = this;
		
		this.handler = new Handler();
		this.timerAction = new Runnable()
		{
			public void run() {
				timerGestureAction.executeAction(gestureEvent, gesture);
				Log.i("RepeatingTimer", "calling start()");
				if(isStopped)
					return;
				
				me.start();
			}
		};
	}

	@Override
	public void start() {
		Log.i("RepeatingTimer", "start() posting with timeout:" + this.timeOut);
		this.isStopped = false;
		this.handler.postDelayed(this.timerAction, this.timeOut);
	}

	@Override
	public void stop() {
		Log.i("RepeatingTimer", "stop(), was called");
		if(this.timerAction != null)
		{
			this.handler.removeCallbacks(this.timerAction);
			Log.i("RepeatingTimer", "stop(), callbaks removed");
		}
		this.isStopped = true;
	}
	
}
