package be.trojkasoftware.android.diagramming.gestures.actions;

import be.trojkasoftware.android.diagramming.DesignerVector;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;

import be.trojkasoftware.android.gestures.GestureActionBase;
import be.trojkasoftware.android.gestures.GestureEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class DoEditSelectedConnectionsAndClearAllegibleForEditingGestureAction extends GestureActionBase<DiagramDesignerView> {

	public DoEditSelectedConnectionsAndClearAllegibleForEditingGestureAction(DiagramDesignerView view) {
		super(view);
	}
	
	@Override
	public void executeAction(GestureEvent motion, TouchGesture gesture) {
		getTouchedView().clearAdorner();
	}
}
