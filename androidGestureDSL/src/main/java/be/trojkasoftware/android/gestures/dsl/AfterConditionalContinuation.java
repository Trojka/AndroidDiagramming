package be.trojkasoftware.android.gestures.dsl;

import java.lang.reflect.InvocationTargetException;

import be.trojkasoftware.android.gestures.IGestureAction;
import be.trojkasoftware.android.gestures.IfThenClause;
import be.trojkasoftware.android.gestures.TouchEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class AfterConditionalContinuation<NextGesture> {
	
	public AfterConditionalContinuation(Class<NextGesture> aClass, TouchGesture gstr, TouchEvent vnt, IfThenClause aCondition)
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
	
	public void AndFinally(IGestureAction action)
	{
		gesture.setResetAction(action);
	}
	
//	public AfterConditional<NextGesture> AndIf()
//	{
//		AfterConditional<NextGesture> result = new AfterConditional<NextGesture>(refClass, gesture, event);
//		
//		return result;
//	}
	
	public ActionAfterGestureOrConditionalContinuation<NextGesture> Else()
	{
		ActionAfterGestureOrConditionalContinuation<NextGesture> result = new ActionAfterGestureOrConditionalContinuation<NextGesture>(refClass, gesture, event, condition);
		
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
