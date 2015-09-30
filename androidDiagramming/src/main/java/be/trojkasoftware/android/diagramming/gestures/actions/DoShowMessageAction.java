package be.trojkasoftware.android.diagramming.gestures.actions;

import android.util.Log;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;

import be.trojkasoftware.android.gestures.GestureActionBase;
import be.trojkasoftware.android.gestures.GestureEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class DoShowMessageAction extends GestureActionBase<DiagramDesignerView> {

	public DoShowMessageAction(DiagramDesignerView view, String message) {
		super(view);
		
		this.message = message;
		i = 0;
	}
	
	@Override
	public void executeAction(GestureEvent motion, TouchGesture gesture) {
		Log.i("ShowMessageAction", "executeAction() was executed");
		getTouchedView().showMessage(message + i);
		i++;
	}
	
	String message;
	int i;
}
