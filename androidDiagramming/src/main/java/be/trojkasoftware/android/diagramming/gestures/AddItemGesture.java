package be.trojkasoftware.android.diagramming.gestures;

import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.gestures.actions.DoAddItemGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoClearSelectionGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoHideCurrentContextMenuGestureAction;
import be.trojkasoftware.android.diagramming.gestures.conditions.CheckOnCanvasGestureCondition;

//import com.hfk.android.gestures.CheckDistanceFromTouchDownExceedsCondition;
//import com.hfk.android.gestures.CheckWithinMilliSecondsOfTimingId;
import be.trojkasoftware.android.gestures.DoInvalidateGestureGestureAction;
import be.trojkasoftware.android.gestures.DoMultipleAction;
//import com.hfk.android.gestures.DoRegisterTimingId;
import be.trojkasoftware.android.gestures.IGestureAction;
import be.trojkasoftware.android.gestures.IGestureCondition;
import be.trojkasoftware.android.gestures.IfThenClause;
import be.trojkasoftware.android.gestures.TouchEvent;
import be.trojkasoftware.android.gestures.TouchGesture;
import be.trojkasoftware.android.gestures.dsl.GestureBuilder;

public class AddItemGesture extends GestureBuilder<DiagramDesignerView> {

	public AddItemGesture(DiagramDesignerView view) {
		super(view);
	}
	
	public TouchGesture create()
	{
		TouchGesture gesture = new TouchGesture("AddItemGesture");
		
		this.Create(gesture).TouchDown()
				.If(OnCanvas())
					.Do2(ClearCurrentSelection(),
						HideCurrentContextMenu())
			.AndNext().CanMove()
				.If(within().milliMeters(2).fromTouchDown(1))
			.AndNext().TouchUp()
				.If(within().seconds(1).fromTouchDown(1))
					.Do2(AddItem())
		;
		
		return gesture;
	}
	
	public IGestureCondition OnCanvas()
	{
		return new CheckOnCanvasGestureCondition(getBase());
	}
	
	public IGestureAction ClearCurrentSelection()
	{
		return new DoClearSelectionGestureAction(getBase());
	}
	
	public IGestureAction HideCurrentContextMenu()
	{
		return new DoHideCurrentContextMenuGestureAction(getBase());
	}
	
	public IGestureAction AddItem()
	{
		return new DoAddItemGestureAction(getBase());
	}
	
}
