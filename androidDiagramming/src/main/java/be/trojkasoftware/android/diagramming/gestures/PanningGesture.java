package be.trojkasoftware.android.diagramming.gestures;

import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.gestures.actions.DoClearSelectionGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoHideCurrentContextMenuGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoMovePanningGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoShowDesignerContextMenuGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoStartPanningGestureAction;
import be.trojkasoftware.android.diagramming.gestures.conditions.CheckOnCanvasGestureCondition;

//import com.hfk.android.gestures.CheckDistanceFromRegisteredPointExceedsCondition;
import be.trojkasoftware.android.gestures.DoInstallTimer;
import be.trojkasoftware.android.gestures.DoInvalidateGestureGestureAction;
import be.trojkasoftware.android.gestures.DoInvalidateRunningTimerAction;
import be.trojkasoftware.android.gestures.DoMultipleAction;
import be.trojkasoftware.android.gestures.IGestureAction;
import be.trojkasoftware.android.gestures.IGestureCondition;
//import com.hfk.android.gestures.DoRegisterPoint;
import be.trojkasoftware.android.gestures.IfThenClause;
import be.trojkasoftware.android.gestures.TouchEvent;
import be.trojkasoftware.android.gestures.TouchGesture;
import be.trojkasoftware.android.gestures.TouchHandler;
import be.trojkasoftware.android.gestures.dsl.GestureBuilder;

public class PanningGesture extends GestureBuilder<DiagramDesignerView> {

	public PanningGesture(DiagramDesignerView view) {
		super(view);
	}
	
	public TouchGesture create()
	{
		TouchGesture gesture = new TouchGesture("PanningGesture");
		
		this.Create(gesture).TouchDown()
				.If(OnCanvas())
					.Do2(ClearCurrentSelection(),
							HideCurrentContextMenu(),
							after().seconds(5).Do(ShowDesignerContextMenu()),
							StartPanning())
			.AndNext().Move()
				.If(not(within().milliMeters(2).fromLastEvent()))
					.Do2(MovePanning(),
							endCurrentTimer(),
							after().seconds(5).Do(ShowDesignerContextMenu()))
					.Else().Do3(MovePanning())
			.AndNext().TouchUp()
				.Do1(endCurrentTimer())
			.AndFinally(endCurrentTimer())
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
	
	public IGestureAction ShowDesignerContextMenu()
	{
		return new DoShowDesignerContextMenuGestureAction(getBase());
	}
	
	public IGestureAction StartPanning()
	{
		return new DoStartPanningGestureAction(getBase());
	}

	public IGestureAction MovePanning()
	{
		return new DoMovePanningGestureAction(getBase());
	}
	
}
