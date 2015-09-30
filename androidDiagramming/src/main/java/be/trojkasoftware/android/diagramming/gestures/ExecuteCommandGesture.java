package be.trojkasoftware.android.diagramming.gestures;

import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.gestures.actions.DoSelectedCommandGestureAction;
import be.trojkasoftware.android.diagramming.gestures.conditions.CheckOnSingleCommandCondition;

//import com.hfk.android.gestures.CheckDistanceFromTouchDownExceedsCondition;
import be.trojkasoftware.android.gestures.CheckNot;
import be.trojkasoftware.android.gestures.DoInvalidateGestureGestureAction;
import be.trojkasoftware.android.gestures.IGestureAction;
import be.trojkasoftware.android.gestures.IGestureCondition;
import be.trojkasoftware.android.gestures.IfThenClause;
import be.trojkasoftware.android.gestures.TouchEvent;
import be.trojkasoftware.android.gestures.TouchGesture;
import be.trojkasoftware.android.gestures.dsl.GestureBuilder;

public class ExecuteCommandGesture extends GestureBuilder<DiagramDesignerView> {

	public ExecuteCommandGesture(DiagramDesignerView view) {
		super(view);
	}
	
	public TouchGesture create()
	{
		TouchGesture gesture = new TouchGesture("ExecuteCommandGesture");
		
		this.Create(gesture).TouchDown()
				.If(OnSingleCommand())
					.Do2(nothing())
			.AndNext().CanMove()
				.If(within().milliMeters(2).fromTouchDown(1))
			.AndNext().TouchUp()
				.Do1(ExecuteSelectedCommand())
		;
		
		return gesture;
	}

	public IGestureCondition OnSingleCommand()
	{
		return new CheckOnSingleCommandCondition(getBase());
	}
	
	public IGestureAction ExecuteSelectedCommand()
	{
		return new DoSelectedCommandGestureAction(getBase());
	}
}

