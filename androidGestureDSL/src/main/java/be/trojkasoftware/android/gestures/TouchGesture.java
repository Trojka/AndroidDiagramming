package be.trojkasoftware.android.gestures;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class TouchGesture implements IResetable  {

	public TouchGesture(String id) 
	{
		this.id = id;
	}
	
	public String getId()
	{
		return id;
	}
	
	public void addEvent(TouchEvent event)
	{
		TouchEventExecution eventExecution = new TouchEventExecution();
		eventExecution.touchEvent = event;
		eventExecution.isExecuted = false;
		eventList.add(eventExecution);
	}
	
	public TouchEvent getEvent(int index)
	{
		return eventList.get(index).touchEvent;
	}
	
	public int size()
	{
		return eventList.size();
	}
	
	public void reset()
	{		
		for(IResetable resetable:resetableList)
		{
			resetable.reset();
		}
		
		if(onResetAction != null)
		{
			onResetAction.executeAction(null, this);
		}
		
		isValid = true;
		index = 0;
		context = new Hashtable<String, Object>();
	}
	
	public void addResetable(IResetable resetable)
	{
		resetableList.add(resetable);
	}
	
	public void setResetAction(IGestureAction action)
	{
		onResetAction = action;
	}
	
	public void invalidate()
	{
		if(contextExists("TIMER_INSTALLED"))
		{
			ITimer timer = (ITimer)getContext("TIMER_ONE");
			timer.stop();
			removeContext("TIMER_ONE");
		}
		isValid = false;
	}
	
	public boolean isValid()
	{
		return ((index < eventList.size()) && isValid);
	}
	
	public TouchEvent current()
	{
		return eventList.get(index).touchEvent;
	}
	
	public boolean isCurrentExecuted()
	{
		return eventList.get(index).isExecuted;
	}
	
	public void currentIsExecuted()
	{
		eventList.get(index).isExecuted = true;
	}
	
	public void moveNext()
	{
		index++;
	}
	
	public boolean isCompleted()
	{
		if(!isValid)
			return false;
		
		return (index >= eventList.size());
	}
	
	public void setAllEventsProcessed()
	{
		index = eventList.size();
	}
	
	public boolean contextExists(String key)
	{
		return context.containsKey(key);
	}
	
	public void addContext(String key, Object data)
	{
		context.put(key, data);
	}
	
	public void setContext(String key, Object data)
	{
		context.put(key, data);
	}
	
	public void removeContext(String key)
	{
		context.remove(key);
	}
	
	public Object getContext(String key)
	{
		return context.get(key);
	}

	private String id;
	private List<TouchEventExecution> eventList = new ArrayList<TouchEventExecution>();	
	private List<IResetable> resetableList = new ArrayList<IResetable>();	
	private IGestureAction onResetAction = null;
	private boolean isValid = true;
	private int index = 0;
	
	private Hashtable<String, Object> context = new Hashtable<String, Object>();
	
	private class TouchEventExecution
	{
		TouchEvent touchEvent;
		boolean isExecuted;
	}
	
}
