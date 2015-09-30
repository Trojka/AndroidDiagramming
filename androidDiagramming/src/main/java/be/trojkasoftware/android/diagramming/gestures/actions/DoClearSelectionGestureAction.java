package be.trojkasoftware.android.diagramming.gestures.actions;

import be.trojkasoftware.android.diagramming.DiagramDesignerView;

import be.trojkasoftware.android.gestures.GestureActionBase;
import be.trojkasoftware.android.gestures.GestureEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class DoClearSelectionGestureAction extends GestureActionBase<DiagramDesignerView> {

	public DoClearSelectionGestureAction(DiagramDesignerView view) {
		super(view);
	}
	
	@Override
	public void executeAction(GestureEvent motion, TouchGesture gesture) {

		getTouchedView().clearSelection();
	}
}
