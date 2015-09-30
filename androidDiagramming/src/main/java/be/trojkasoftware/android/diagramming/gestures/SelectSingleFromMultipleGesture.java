package be.trojkasoftware.android.diagramming.gestures;

import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.gestures.actions.DoClearEligibleForEditingGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoClearSelectionGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoEditSelectedItemsAndClearAllegibleForEditingGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoEditingSelectedItemsGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoExplodeMultipleItemsGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoHideCurrentContextMenuGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoSelectSingleOfMultipleItemsAndSetEligibleForMovementGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoSelectedCommandGestureAction;
import be.trojkasoftware.android.diagramming.gestures.conditions.CheckOnMultipleItemGestureCondition;
import be.trojkasoftware.android.diagramming.gestures.conditions.CheckOnSingleCommandCondition;
import be.trojkasoftware.android.diagramming.gestures.conditions.CheckOnSingleOfMultipleItemsCondition;

import be.trojkasoftware.android.gestures.CheckUntilTrueAndAcceptAlways;
import be.trojkasoftware.android.gestures.DoInvalidateGestureGestureAction;
import be.trojkasoftware.android.gestures.DoMultipleAction;
import be.trojkasoftware.android.gestures.IGestureAction;
import be.trojkasoftware.android.gestures.IGestureCondition;
import be.trojkasoftware.android.gestures.IfThenClause;
import be.trojkasoftware.android.gestures.TouchEvent;
import be.trojkasoftware.android.gestures.TouchGesture;
import be.trojkasoftware.android.gestures.TouchHandler;
import be.trojkasoftware.android.gestures.dsl.GestureBuilder;

public class SelectSingleFromMultipleGesture extends GestureBuilder<DiagramDesignerView> {

	public SelectSingleFromMultipleGesture(DiagramDesignerView view) {
		super(view);
	}
	
	public TouchGesture create()
	{
		TouchGesture gesture = new TouchGesture("MultiSelectGesture");
		
		this.Create(gesture).TouchDown()
				.If(OnMultipleItems())
					.Do2(ClearCurrentSelection(),
							HideCurrentContextMenu(),
							ExplodeMultipleItems())
			.AndNext().Move()
				.If(CheckUntilTrueAndAcceptAlways(OnSingleOfMultipleItems()))
					.Do2(SelectSingleOfMultipleItemsAndSetEligibleForMovement(),
							MovingSelectedItems())
				.Else()
					.Do3(nothing())
			.AndNext().TouchUp()
				.Do1(MoveAndClearAllegibleForMovement())
			.AndFinally(ClearAllegibleForMovement())
		;
		
		return gesture;
	}

	public IGestureCondition OnMultipleItems()
	{
		return new CheckOnMultipleItemGestureCondition(getBase());
	}

	public IGestureCondition CheckUntilTrueAndAcceptAlways(IGestureCondition condition)
	{
		return new CheckUntilTrueAndAcceptAlways(condition);
	}

	public IGestureCondition OnSingleOfMultipleItems()
	{
		return new CheckOnSingleOfMultipleItemsCondition(getBase());
	}
	
	public IGestureAction ClearCurrentSelection()
	{
		return new DoClearSelectionGestureAction(getBase());
	}
	
	public IGestureAction MoveAndClearAllegibleForMovement()
	{
		return new DoEditSelectedItemsAndClearAllegibleForEditingGestureAction(getBase());
	}
	
	public IGestureAction ClearAllegibleForMovement()
	{
		return new DoClearEligibleForEditingGestureAction(getBase());
	}
	
	public IGestureAction HideCurrentContextMenu()
	{
		return new DoHideCurrentContextMenuGestureAction(getBase());
	}
	
	public IGestureAction ExplodeMultipleItems()
	{
		return new DoExplodeMultipleItemsGestureAction(getBase());
	}
	
	public IGestureAction SelectSingleOfMultipleItemsAndSetEligibleForMovement()
	{
		return new DoSelectSingleOfMultipleItemsAndSetEligibleForMovementGestureAction(getBase());
	}
	
	public IGestureAction MovingSelectedItems()
	{
		return new DoEditingSelectedItemsGestureAction(getBase());
	}
	
}
