package be.trojkasoftware.android.gestures;

import android.view.View;

public class IfThenClause {

	public IfThenClause(IGestureCondition condition)
	{
		this.condition = condition;
		this.onTrueAction = null;
		this.onFalseAction = new DoInvalidateGestureGestureAction();
	}

	public void setThenAction(IGestureAction action)
	{
		this.onTrueAction = action;
	}
	
	public void setElseAction(IGestureAction action)
	{
		this.onFalseAction = action;
	}
	
	public boolean Execute(GestureEvent motion, TouchGesture gesture)
	{
		if(condition.checkCondition(motion, gesture))
		{
			if(onTrueAction != null)
			{
				onTrueAction.executeAction(motion, gesture);
			}
			return true;
		}
		else
		{
			if(onFalseAction != null)
			{
				onFalseAction.executeAction(motion, gesture);
			}
			return false;
		}
	}
	
	View view;

	private IGestureCondition condition;
	private IGestureAction onTrueAction;
	private IGestureAction onFalseAction;
}
