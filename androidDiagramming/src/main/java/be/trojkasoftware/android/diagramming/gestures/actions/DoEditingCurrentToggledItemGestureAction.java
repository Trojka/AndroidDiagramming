package be.trojkasoftware.android.diagramming.gestures.actions;

import android.util.Log;
import be.trojkasoftware.android.diagramming.DesignerVector;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;

import be.trojkasoftware.android.ScreenVector;
import be.trojkasoftware.android.gestures.GestureActionBase;
import be.trojkasoftware.android.gestures.GestureEvent;
import be.trojkasoftware.android.gestures.TouchGesture;
import be.trojkasoftware.android.gestures.TouchHandler;

public class DoEditingCurrentToggledItemGestureAction extends GestureActionBase<DiagramDesignerView> {

	public DoEditingCurrentToggledItemGestureAction(DiagramDesignerView view) {
		super(view);
	}
	
	@Override
	public void executeAction(GestureEvent motion, TouchGesture gesture) {
		Log.i("DoEditingCurrentToggledItemGestureAction", "executeAction");
		if(getTouchedView().getSelection() == null || getTouchedView().getSelection().size() != 1 )
		{
			return;
		}
		
		if(getTouchedView().getSelection().get(0).isOnConnectionPoint())
		{
			Log.i("DoEditingCurrentToggledItemGestureAction", "isConnectionPoint");
			if(!gesture.contextExists(DoAddAndSelectConnectorEditorGestureAction.CONNECTOR_EDITOR))
			{
				Log.i("DoEditingCurrentToggledItemGestureAction", "createAndSaveConnectorEditor");
				DoAddAndSelectConnectorEditorGestureAction.createAndSaveConnectorEditor(getTouchedView(), (ScreenVector)gesture.getContext(TouchHandler.getEventId(TouchHandler.ActionDownPos, 1)), gesture);
			}
			Log.i("DoEditingCurrentToggledItemGestureAction", "editConnectionEditor");
			DoEditConnectorEditorGestureAction.editConnectionEditor(getTouchedView(), motion, gesture);
		}
		else
		{
			getTouchedView().editingDesignerItems(getTouchedView().getSelection(), motion.getPosition() /*newPoint*/);
		}
	}
}
