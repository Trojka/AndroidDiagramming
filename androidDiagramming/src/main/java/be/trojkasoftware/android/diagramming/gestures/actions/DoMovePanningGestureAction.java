package be.trojkasoftware.android.diagramming.gestures.actions;

import be.trojkasoftware.android.diagramming.DiagramDesignerView;

import be.trojkasoftware.android.ScreenVector;
import be.trojkasoftware.android.gestures.GestureActionBase;
import be.trojkasoftware.android.gestures.GestureEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class DoMovePanningGestureAction extends GestureActionBase<DiagramDesignerView> {

	public DoMovePanningGestureAction(DiagramDesignerView view) {
		super(view);
	}

	@Override
	public void executeAction(GestureEvent motion, TouchGesture gesture) {
		ScreenVector lastPoint = (ScreenVector)gesture.getContext("PANNING_LASTPOINT");
		
		ScreenVector offset = lastPoint.subtract(motion.getPosition());
		
		ScreenVector currentPanning = getTouchedView().getPanningOffsetset();
		
		getTouchedView().setPanningOffsetset(currentPanning.add(offset));
		
		gesture.addContext("PANNING_LASTPOINT", motion.getPosition());
	}
}
