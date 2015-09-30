package be.trojkasoftware.android.gestures.dsl;

import java.lang.reflect.InvocationTargetException;

import be.trojkasoftware.android.gestures.DoMultipleAction;
import be.trojkasoftware.android.gestures.IGestureAction;
import be.trojkasoftware.android.gestures.IGestureCondition;
import be.trojkasoftware.android.gestures.IfThenClause;
import be.trojkasoftware.android.gestures.TouchEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class AfterConditional<NextGesture> {

	public AfterConditional(Class<NextGesture> aClass, TouchGesture gstr, TouchEvent vnt)
	{
		refClass = aClass;
		gesture = gstr;
		event = vnt;
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
	
	public void AndFinally(IGestureAction action)
	{
		gesture.setResetAction(action);
	}
	
	public AfterConditionalContinuation<NextGesture> Do2(IGestureAction action)
	{
		IfThenClause ifThenClause = event.conditionList.get(event.conditionList.size()-1);
		ifThenClause.setThenAction(action);
		
		AfterConditionalContinuation<NextGesture> result = new AfterConditionalContinuation<NextGesture>(refClass, gesture, event, ifThenClause);
		
		return result;
	}
	
	public AfterConditionalContinuation<NextGesture> Do2(IGestureAction action1, IGestureAction action2)
	{
		return Do2(new DoMultipleAction(action1, action2));
	}
	
	public AfterConditionalContinuation<NextGesture> Do2(IGestureAction action1, IGestureAction action2, IGestureAction action3)
	{
		return Do2(new DoMultipleAction(action1, action2, action3));
	}
	
	public AfterConditionalContinuation<NextGesture> Do2(IGestureAction action1, IGestureAction action2, IGestureAction action3, IGestureAction action4)
	{
		return Do2(new DoMultipleAction(action1, action2, action3, action4));
	}
	
	public AfterConditional<NextGesture> AndIf(IGestureCondition condition)
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
}
