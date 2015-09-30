package be.trojkasoftware.android.diagramming.gestures;

import android.util.Log;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.gestures.actions.DoAddAndSelectConnectorEditorGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoApplyConnectorEditorGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoClearSelectionGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoEditConnectorEditorGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoEditCurrentToggledItemAndClearAllegibleForEditingGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoEditToggledItemGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoEditingCurrentToggledItemGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoEditingSelectedItemsGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoHideCurrentContextMenuGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoSaveSelectionAndSelectFirstAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoShowMessageAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoToggleSelectedItemGestureAction;
import be.trojkasoftware.android.diagramming.gestures.conditions.CheckOnConnectionPointAndConnectionExtremePointGestureCondition;
import be.trojkasoftware.android.diagramming.gestures.conditions.CheckOnSingleConnectionGestureCondition;
import be.trojkasoftware.android.diagramming.gestures.conditions.CheckOnUnconnectedSourceConnectionPointGestureCondition;

import be.trojkasoftware.android.gestures.IGestureAction;
import be.trojkasoftware.android.gestures.IGestureCondition;
import be.trojkasoftware.android.gestures.TouchGesture;
import be.trojkasoftware.android.gestures.dsl.GestureBuilder;

public class AddOrEditConnectionGesture extends GestureBuilder<DiagramDesignerView> {

	public AddOrEditConnectionGesture(DiagramDesignerView view) {
		super(view);
	}
	
	public TouchGesture create()
	{
		TouchGesture gesture = new TouchGesture("AddOrEditConnectionGesture");
		
		this.Create(gesture).TouchDown()
				.If(OnConnectionPointAndConnectionExtremePoint())
					.Do2(ClearCurrentSelection(),
						HideCurrentContextMenu(),
						SaveSelectionAndSelectFirstAction(),
						every().seconds(5).Do(ToggleSelectedItem())
						)
			.AndNext()
				.Move()
				.If(within().milliMeters(2).fromTouchDown(1))
					.Do2(nothing())
				.Else()
					.Do3(endCurrentTimer()
						, EditingCurrentToggledItem()
							)
			.AndNext().TouchUp()
				.Do1(endCurrentTimer()
						, EditCurrentToggledItemAndClearAllegibleForEditing()
						)
			.AndFinally(endCurrentTimer() 
						)
		;
		
		return gesture;
	}

	public IGestureCondition OnConnectionPointAndConnectionExtremePoint()
	{
		return new CheckOnConnectionPointAndConnectionExtremePointGestureCondition(getBase());
	}
	
	public IGestureAction ClearCurrentSelection()
	{
		return new DoClearSelectionGestureAction(getBase());
	}
	
	public IGestureAction HideCurrentContextMenu()
	{
		return new DoHideCurrentContextMenuGestureAction(getBase());
	}
	
	public IGestureAction SaveSelectionAndSelectFirstAction()
	{
		return new DoSaveSelectionAndSelectFirstAction(getBase());
	}
	
	public IGestureAction ToggleSelectedItem()
	{
		return new DoToggleSelectedItemGestureAction(getBase(), "AddOrEditConnectionGesture");
	}
	
	public IGestureAction EditingCurrentToggledItem()
	{
		Log.i("AddOrEditConnectionGesture", "EditingCurrentToggledItem");
		return new DoEditingCurrentToggledItemGestureAction(getBase());
	}
	
	public IGestureAction EditCurrentToggledItemAndClearAllegibleForEditing()
	{
		Log.i("AddOrEditConnectionGesture", "EditingCurrentToggledItem");
		return new DoEditCurrentToggledItemAndClearAllegibleForEditingGestureAction(getBase());
	}
	
}
