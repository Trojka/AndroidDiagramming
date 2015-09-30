package be.trojkasoftware.android.gestures.dsl;

import java.lang.reflect.InvocationTargetException;

import be.trojkasoftware.android.gestures.DoMultipleAction;
import be.trojkasoftware.android.gestures.IGestureAction;
import be.trojkasoftware.android.gestures.TouchGesture;

public class NextGestureOrConditional<NextGesture> {
	
	public NextGestureOrConditional(Class<NextGesture> aClass, TouchGesture gstr)
	{
		refClass = aClass;
		gesture = gstr;
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
	
	public void AndFinally(IGestureAction action1, IGestureAction action2)
	{
		gesture.setResetAction(new DoMultipleAction(action1, action2));
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
}
