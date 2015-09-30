package be.trojkasoftware.android.diagramming.gestures.actions;

import be.trojkasoftware.android.diagramming.DiagramDesignerView;

import be.trojkasoftware.android.gestures.GestureActionBase;
import be.trojkasoftware.android.gestures.GestureEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class DoStartPanningGestureAction extends GestureActionBase<DiagramDesignerView> {

	public DoStartPanningGestureAction(DiagramDesignerView view) {
		super(view);
	}

	@Override
	public void executeAction(GestureEvent motion, TouchGesture gesture) {
		gesture.addContext("PANNING_LASTPOINT", motion.getPosition());
	}
}
