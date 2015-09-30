package be.trojkasoftware.android.diagramming.gestures.actions;

import java.util.List;

import be.trojkasoftware.android.diagramming.DiagramDesignerConnectionEditor;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.HitDefinition;

import be.trojkasoftware.android.ScreenVector;
import be.trojkasoftware.android.gestures.GestureActionBase;
import be.trojkasoftware.android.gestures.GestureEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class DoAddAndSelectConnectorEditorGestureAction extends GestureActionBase<DiagramDesignerView> {

	public static final String CONNECTOR_STARTITEM = "CONNECTOR_STARTITEM";
	public static final String CONNECTOR_STARTCONNECTIONPOINT = "CONNECTOR_STARTCONNECTIONPOINT";
	public static final String CONNECTOR_EDITOR = "CONNECTOR_EDITOR";
	
	public DoAddAndSelectConnectorEditorGestureAction(DiagramDesignerView view) {
		super(view);
	}
	
	@Override
	public void executeAction(GestureEvent motion, TouchGesture gesture) {
		createAndSaveConnectorEditor(getTouchedView(), motion.getPosition(), gesture);
	}
	
	public static void createAndSaveConnectorEditor(DiagramDesignerView view, ScreenVector position, TouchGesture gesture)
	{
		List<HitDefinition> hitResult = view.hitTest(position);

		DiagramDesignerConnectionEditor connectionEditor = view.getConnectionEditor(hitResult.get(0).nodeId, hitResult.get(0).connectionPoint);
		
		gesture.addContext(CONNECTOR_STARTITEM, hitResult.get(0).nodeId);
		gesture.addContext(CONNECTOR_STARTCONNECTIONPOINT, hitResult.get(0).connectionPoint);
		gesture.addContext(CONNECTOR_EDITOR, connectionEditor);
	}
}
