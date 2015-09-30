package be.trojkasoftware.android.diagramming.gestures;

import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.gestures.actions.DoAddAndSelectConnectorEditorGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoApplyConnectorEditorGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoClearSelectionGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoEditConnectorEditorGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoHideCurrentContextMenuGestureAction;
import be.trojkasoftware.android.diagramming.gestures.conditions.CheckOnCanvasGestureCondition;
import be.trojkasoftware.android.diagramming.gestures.conditions.CheckOnUnconnectedSourceConnectionPointGestureCondition;

import be.trojkasoftware.android.gestures.DoInvalidateGestureGestureAction;
import be.trojkasoftware.android.gestures.DoMultipleAction;
import be.trojkasoftware.android.gestures.IGestureAction;
import be.trojkasoftware.android.gestures.IGestureCondition;
import be.trojkasoftware.android.gestures.IfThenClause;
import be.trojkasoftware.android.gestures.TouchEvent;
import be.trojkasoftware.android.gestures.TouchGesture;
import be.trojkasoftware.android.gestures.dsl.GestureBuilder;

public class AddConnectionGesture extends GestureBuilder<DiagramDesignerView> {

	public AddConnectionGesture(DiagramDesignerView view) {
		super(view);
	}
	
	public TouchGesture create()
	{
		TouchGesture gesture = new TouchGesture("AddConnectionGesture");
		
		this.Create(gesture).TouchDown()
				.If(OnUnconnectedSourceConnectionPoint())
					.Do2(ClearCurrentSelection(),
						HideCurrentContextMenu(),
						AddAndSelectConnectorEditor())
			.AndNext().Move()
				.Do1(EditConnectorEditorGestureAction())
			.AndNext().TouchUp()
				.Do1(ApplyConnectorEditor())
		;
		
		return gesture;
	}

	public IGestureCondition OnUnconnectedSourceConnectionPoint()
	{
		return new CheckOnUnconnectedSourceConnectionPointGestureCondition(getBase());
	}
	
	public IGestureAction ClearCurrentSelection()
	{
		return new DoClearSelectionGestureAction(getBase());
	}
	
	public IGestureAction HideCurrentContextMenu()
	{
		return new DoHideCurrentContextMenuGestureAction(getBase());
	}
	
	public IGestureAction AddAndSelectConnectorEditor()
	{
		return new DoAddAndSelectConnectorEditorGestureAction(getBase());
	}
	
	public IGestureAction EditConnectorEditorGestureAction()
	{
		return new DoEditConnectorEditorGestureAction(getBase());
	}
	
	public IGestureAction ApplyConnectorEditor()
	{
		return new DoApplyConnectorEditorGestureAction(getBase());
	}
	
}

