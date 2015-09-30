package be.trojkasoftware.android.diagramming.gestures.actions;

import android.util.Log;
import be.trojkasoftware.android.diagramming.DesignerVector;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;

import be.trojkasoftware.android.ScreenVector;
import be.trojkasoftware.android.gestures.GestureActionBase;
import be.trojkasoftware.android.gestures.GestureEvent;
import be.trojkasoftware.android.gestures.TouchGesture;
import be.trojkasoftware.android.gestures.TouchHandler;

public class DoEditCurrentToggledItemAndClearAllegibleForEditingGestureAction extends GestureActionBase<DiagramDesignerView> {

	public DoEditCurrentToggledItemAndClearAllegibleForEditingGestureAction(DiagramDesignerView view) {
		super(view);
	}
	
	@Override
	public void executeAction(GestureEvent motion, TouchGesture gesture) {
		Log.i("DoEditCurrentToggledItemAndClearAllegibleForEditingGestureAction", "executeAction");
		if(getTouchedView().getSelection() == null || getTouchedView().getSelection().size() != 1 )
		{
            Log.i("DoEditCurrentToggledItemAndClearAllegibleForEditingGestureAction", "Do nothing");
			return;
		}
		
		if(getTouchedView().getSelection().get(0).isOnConnectionPoint())
		{
			Log.i("DoEditCurrentToggledItemAndClearAllegibleForEditingGestureAction", "isConnectionPoint");
            if(gesture.contextExists(DoAddAndSelectConnectorEditorGestureAction.CONNECTOR_EDITOR)) {
                DoApplyConnectorEditorGestureAction.applyConnectorEditor(getTouchedView(), motion, gesture);
            }
		}
		else
		{
            Log.i("DoEditCurrentToggledItemAndClearAllegibleForEditingGestureAction", "NOT isConnectionPoint");
			getTouchedView().editDesignerItems(getTouchedView().getSelection(), motion.getPosition());
		}

		getTouchedView().clearAdorner();
	}
}
