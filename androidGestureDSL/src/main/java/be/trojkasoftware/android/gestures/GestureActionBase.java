package be.trojkasoftware.android.gestures;

public abstract class GestureActionBase<T> implements IGestureAction {

	public GestureActionBase(T view) {
		touchView = view;
	}

	public T getTouchedView()
	{
		return touchView;
	}
	
	private T touchView;
}
