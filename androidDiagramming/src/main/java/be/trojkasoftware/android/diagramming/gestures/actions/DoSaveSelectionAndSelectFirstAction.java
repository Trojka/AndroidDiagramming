package be.trojkasoftware.android.diagramming.gestures.actions;

import java.util.List;

import android.util.Log;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.HitDefinition;

import be.trojkasoftware.android.gestures.GestureActionBase;
import be.trojkasoftware.android.gestures.GestureEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class DoSaveSelectionAndSelectFirstAction  extends GestureActionBase<DiagramDesignerView> {
	
	public final static String CURRENT_SELECTION = "CURRENT_SELECTION";

	public DoSaveSelectionAndSelectFirstAction(DiagramDesignerView view) {
		super(view);
	}
	
	@Override
	public void executeAction(GestureEvent motion, TouchGesture gesture) {
		List<HitDefinition> hitResult = getTouchedView().hitTest(motion.getPosition());
		
		gesture.addContext(CURRENT_SELECTION, hitResult);
		
		DoToggleSelectedItemGestureAction.toggleSelection(getTouchedView(), motion, gesture, "DoSaveSelectionAndSelectFirstAction");

	}

}
