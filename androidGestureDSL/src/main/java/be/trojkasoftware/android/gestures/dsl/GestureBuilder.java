package be.trojkasoftware.android.gestures.dsl;

import be.trojkasoftware.android.gestures.CheckNot;
import be.trojkasoftware.android.gestures.DoInvalidateGestureGestureAction;
import be.trojkasoftware.android.gestures.DoInvalidateRunningTimerAction;
import be.trojkasoftware.android.gestures.DoSignalGestureCompleted;
import be.trojkasoftware.android.gestures.IGestureAction;
import be.trojkasoftware.android.gestures.IGestureCondition;
import be.trojkasoftware.android.gestures.RelationType;
import be.trojkasoftware.android.gestures.TimerType;
import be.trojkasoftware.android.gestures.TouchGesture;

public class GestureBuilder<V> {
	
	public GestureBuilder(V base)
	{
		this.base = base;
	}
	
	public NextGestureAfterCreate Create(TouchGesture gesture)
	{
		return new NextGestureAfterCreate(gesture);
	}
	
	public RangeTypeSelector within()
	{
		RangeTypeSelector result = new RangeTypeSelector(RelationType.Within);
		return result;
	}
	
	public RangeTypeSelector exceed()
	{
		RangeTypeSelector result = new RangeTypeSelector(RelationType.Exceed);
		return result;
	}
	
	public IGestureCondition not(IGestureCondition gestureCondition)
	{
		return new CheckNot(gestureCondition);
	}
	
	public IGestureAction nothing()
	{
		return null;
	}
	
	public TimerTimeConfiguration after()
	{
		return new TimerTimeConfiguration(TimerType.FireOnce);
	}
	
	public TimerTimeConfiguration every()
	{
		return new TimerTimeConfiguration(TimerType.Repeating);
	}
	
	public DoInvalidateRunningTimerAction endCurrentTimer()
	{
		return new DoInvalidateRunningTimerAction();
	}
	
	public DoInvalidateGestureGestureAction invalidateGesture()
	{
		return new DoInvalidateGestureGestureAction();
	}
	
	public DoSignalGestureCompleted gestureIsCompleted()
	{
		return new DoSignalGestureCompleted();
	}
	
	public V getBase()
	{
		return base;
	}
	
	V base;
}
