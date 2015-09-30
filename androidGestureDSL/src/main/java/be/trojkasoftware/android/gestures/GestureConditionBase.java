package be.trojkasoftware.android.gestures;

public abstract class GestureConditionBase<T> implements IGestureCondition {

	public GestureConditionBase(T base) {
		touchBase = base;
	}

	public T getTouchBase()
	{
		return touchBase;
	}
	
	private T touchBase;
}
