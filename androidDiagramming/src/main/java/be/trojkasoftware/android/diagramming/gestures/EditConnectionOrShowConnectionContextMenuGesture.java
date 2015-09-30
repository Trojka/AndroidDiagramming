package be.trojkasoftware.android.diagramming.gestures;

import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.gestures.actions.DoClearEligibleForEditingGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoClearSelectionGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoEditSelectedConnectionsAndClearAllegibleForEditingGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoEditSelectedItemsAndClearAllegibleForEditingGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoEditingSelectedItemsGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoHideCurrentContextMenuGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoSelectConnectionsAndSetEligibleForEditingGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoSelectItemsAndSetEligibleForEditingGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoShowSelectConnectionsContextMenuGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoShowSelectItemsContextMenuGestureAction;
import be.trojkasoftware.android.diagramming.gestures.conditions.CheckOnSingleConnectionGestureCondition;
import be.trojkasoftware.android.diagramming.gestures.conditions.CheckOnSingleItemGestureCondition;

import be.trojkasoftware.android.gestures.IGestureAction;
import be.trojkasoftware.android.gestures.IGestureCondition;
import be.trojkasoftware.android.gestures.TouchGesture;
import be.trojkasoftware.android.gestures.dsl.GestureBuilder;

public class EditConnectionOrShowConnectionContextMenuGesture extends GestureBuilder<DiagramDesignerView> {

	public EditConnectionOrShowConnectionContextMenuGesture(DiagramDesignerView view) {
		super(view);
	}
	
	public TouchGesture create()
	{
		TouchGesture gesture = new TouchGesture("EditConnectionOrShowConnectionContextMenuGesture");
		
		this.Create(gesture).TouchDown()
				.If(OnSingleConnection())
					.Do2(ClearCurrentSelection(),
							HideCurrentContextMenu(),
							after().seconds(5).Do(ShowSelectConnectionsContextMenu()),
							SelectConnectionsAndSetAllegibleForEditing())
			.AndNext().Move()
				.If(not(within().milliMeters(2).fromLastEvent()))
					.Do2(endCurrentTimer(),
							after().seconds(5).Do(ShowSelectConnectionsContextMenu()))
			.AndNext().TouchUp()
				.Do1(endCurrentTimer(),
						EditAndClearAllegibleForEditing())
			.AndFinally(endCurrentTimer(), ClearAllegibleForEditing())
		;
		
		return gesture;
	}

	public IGestureCondition OnSingleConnection()
	{
		return new CheckOnSingleConnectionGestureCondition(getBase());
	}
	
	public IGestureAction ClearCurrentSelection()
	{
		return new DoClearSelectionGestureAction(getBase());
	}
	
	public IGestureAction HideCurrentContextMenu()
	{
		return new DoHideCurrentContextMenuGestureAction(getBase());
	}
	
	public IGestureAction ShowSelectConnectionsContextMenu()
	{
		return new DoShowSelectConnectionsContextMenuGestureAction(getBase());
	}
	
	public IGestureAction SelectConnectionsAndSetAllegibleForEditing()
	{
		return new DoSelectConnectionsAndSetEligibleForEditingGestureAction(getBase());
	}
	
	public IGestureAction EditAndClearAllegibleForEditing()
	{
		return new DoEditSelectedConnectionsAndClearAllegibleForEditingGestureAction(getBase());
	}
	
	public IGestureAction ClearAllegibleForEditing()
	{
		return new DoClearEligibleForEditingGestureAction(getBase());
	}

}
