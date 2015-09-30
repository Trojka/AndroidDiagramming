package be.trojkasoftware.android.diagramming.gestures.actions;

import be.trojkasoftware.android.diagramming.DesignerVector;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;

import be.trojkasoftware.android.gestures.GestureActionBase;
import be.trojkasoftware.android.gestures.GestureEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class DoEditToggledItemGestureAction extends GestureActionBase<DiagramDesignerView> {

	public DoEditToggledItemGestureAction(DiagramDesignerView view) {
		super(view);
	}
	
	@Override
	public void executeAction(GestureEvent motion, TouchGesture gesture) {
		if(getTouchedView().getSelection() != null && getTouchedView().getSelection().size() != 0 )
		{
			getTouchedView().editDesignerItems(getTouchedView().getSelection(), motion.getPosition());
		}		

		getTouchedView().clearAdorner();
	}
}
