package be.trojkasoftware.android.diagramming.gestures;

import android.util.Log;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.gestures.actions.DoClearEligibleForEditingGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoClearSelectionGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoEditSelectedItemsAndClearAllegibleForEditingGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoEditingSelectedItemsGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoHideCurrentContextMenuGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoSelectItemsAndSetEligibleForEditingGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoSelectedCommandGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoShowDesignerContextMenuGestureAction;
import be.trojkasoftware.android.diagramming.gestures.actions.DoShowSelectItemsContextMenuGestureAction;
import be.trojkasoftware.android.diagramming.gestures.conditions.CheckOnSingleCommandCondition;
import be.trojkasoftware.android.diagramming.gestures.conditions.CheckOnSingleItemGestureCondition;

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

public class EditItemOrShowItemContextMenuGesture extends GestureBuilder<DiagramDesignerView> {

	public EditItemOrShowItemContextMenuGesture(DiagramDesignerView view) {
		super(view);
	}
	
	public TouchGesture create()
	{
		TouchGesture gesture = new TouchGesture("EditItemOrShowItemContextMenuGesture");
		
		this.Create(gesture).TouchDown()
				.If(OnSingleItem())
					.Do2(ClearCurrentSelection(),
							HideCurrentContextMenu(),
							after().seconds(5).Do(ShowSelectItemsContextMenu()),
							SelectItemsAndSetAllegibleForEditing())
			.AndNext().Move()
				.If(not(within().milliMeters(2).fromLastEvent()))
					.Do2(EditingSelectedItems(),
							endCurrentTimer(),
							after().seconds(5).Do(ShowSelectItemsContextMenu()))
				.Else()
					.Do3(EditingSelectedItems())
			.AndNext().TouchUp()
				.Do1(endCurrentTimer(),
						EditSelectedItemsAndClearAllegibleForEditing())
			.AndFinally(endCurrentTimer(), 
						ClearAllegibleForEditing())
		;
		
		return gesture;
	}

	public IGestureCondition OnSingleItem()
	{
		return new CheckOnSingleItemGestureCondition(getBase());
	}
	
	public IGestureAction ClearCurrentSelection()
	{
		return new DoClearSelectionGestureAction(getBase());
	}
	
	public IGestureAction EditSelectedItemsAndClearAllegibleForEditing()
	{
		return new DoEditSelectedItemsAndClearAllegibleForEditingGestureAction(getBase());
	}
	
	public IGestureAction ClearAllegibleForEditing()
	{
		return new DoClearEligibleForEditingGestureAction(getBase());
	}
	
	public IGestureAction HideCurrentContextMenu()
	{
		return new DoHideCurrentContextMenuGestureAction(getBase());
	}
	
	public IGestureAction ShowSelectItemsContextMenu()
	{
		return new DoShowSelectItemsContextMenuGestureAction(getBase());
	}
	
	public IGestureAction SelectItemsAndSetAllegibleForEditing()
	{
		return new DoSelectItemsAndSetEligibleForEditingGestureAction(getBase());
	}
	
	public IGestureAction EditingSelectedItems()
	{
		Log.i("EditItemOrShowItemContextMenuGesture", "EditingSelectedItems");
		return new DoEditingSelectedItemsGestureAction(getBase());
	}

}

