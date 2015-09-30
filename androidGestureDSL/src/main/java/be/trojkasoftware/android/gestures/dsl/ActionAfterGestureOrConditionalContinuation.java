package be.trojkasoftware.android.gestures.dsl;

import java.lang.reflect.InvocationTargetException;

import be.trojkasoftware.android.gestures.DoMultipleAction;
import be.trojkasoftware.android.gestures.IGestureAction;
import be.trojkasoftware.android.gestures.IGestureCondition;
import be.trojkasoftware.android.gestures.IfThenClause;
import be.trojkasoftware.android.gestures.TouchEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class ActionAfterGestureOrConditionalContinuation<NextGesture>  {
	
	public ActionAfterGestureOrConditionalContinuation(Class<NextGesture> aClass, TouchGesture gstr, TouchEvent vnt, IfThenClause aCondition)
	{
		refClass = aClass;
		gesture = gstr;
		event = vnt;
		condition = aCondition;
	}
	
	public NextGesture AndNext()
	{
		NextGesture result = null;
		try {
			result = getInstance(refClass);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public NextGestureOrConditional<NextGesture> Do3(IGestureAction action)
	{
		condition.setElseAction(action);

		NextGestureOrConditional<NextGesture> result = new NextGestureOrConditional<NextGesture>(refClass, gesture);
		
		return result;
	}
	
	public NextGestureOrConditional<NextGesture> Do3(IGestureAction action1, IGestureAction action2)
	{
		return Do3(new DoMultipleAction(action1, action2));
	}
	
	public NextGestureOrConditional<NextGesture> Do3(IGestureAction action1, IGestureAction action2, IGestureAction action3)
	{
		return Do3(new DoMultipleAction(action1, action2, action3));
	}
	
	public AfterConditional<NextGesture> If(IGestureCondition condition) 
	{
		event.conditionList.add(new IfThenClause(condition));
		
		AfterConditional<NextGesture> result = new AfterConditional<NextGesture>(refClass, gesture, event);
		
		return result;
	}
	
	//http://stackoverflow.com/questions/2434041/instantiating-generics-type-in-java
	//http://stackoverflow.com/questions/234600/can-i-use-class-newinstance-with-constructor-arguments
	private NextGesture getInstance(Class<NextGesture> aClass) throws IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchMethodException
	{
		try {
			return aClass.getDeclaredConstructor(TouchGesture.class).newInstance(gesture);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private Class<NextGesture> refClass;
	private TouchGesture gesture;
	private TouchEvent event;
	private IfThenClause condition;
}
