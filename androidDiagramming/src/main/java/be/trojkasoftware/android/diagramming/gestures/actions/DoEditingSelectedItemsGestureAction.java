package be.trojkasoftware.android.diagramming.gestures.actions;

import android.util.Log;
import be.trojkasoftware.android.diagramming.DesignerVector;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;

import be.trojkasoftware.android.gestures.GestureActionBase;
import be.trojkasoftware.android.gestures.GestureEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class DoEditingSelectedItemsGestureAction extends GestureActionBase<DiagramDesignerView> {

	public DoEditingSelectedItemsGestureAction(DiagramDesignerView view) {
		super(view);
	}
	
	@Override
	public void executeAction(GestureEvent motion, TouchGesture gesture) {
		Log.i("DoEditingSelectedItemsGestureAction", "executeAction");
		if(getTouchedView().getSelection() == null || getTouchedView().getSelection().size() == 0 )
		{
			return;
		}
		
		getTouchedView().editingDesignerItems(getTouchedView().getSelection(), motion.getPosition() /*newPoint*/);
	}
}
