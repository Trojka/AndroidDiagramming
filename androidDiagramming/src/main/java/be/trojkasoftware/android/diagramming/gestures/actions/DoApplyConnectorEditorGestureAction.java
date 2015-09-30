package be.trojkasoftware.android.diagramming.gestures.actions;

import java.util.List;

import be.trojkasoftware.android.diagramming.ConnectionPoint;
import be.trojkasoftware.android.diagramming.DesignerVector;
import be.trojkasoftware.android.diagramming.DiagramDesignerConnectionEditor;
import be.trojkasoftware.android.diagramming.DiagramDesignerItem;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.Direction;
import be.trojkasoftware.android.diagramming.HitDefinition;

import be.trojkasoftware.android.gestures.GestureActionBase;
import be.trojkasoftware.android.gestures.GestureEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class DoApplyConnectorEditorGestureAction extends GestureActionBase<DiagramDesignerView> {

	public DoApplyConnectorEditorGestureAction(DiagramDesignerView view) {
		super(view);
	}
	
	@Override
	public void executeAction(GestureEvent motion, TouchGesture gesture) {
		applyConnectorEditor(getTouchedView(), motion, gesture);
	}
	
	public static void applyConnectorEditor(DiagramDesignerView view, GestureEvent motion, TouchGesture gesture)
	{
		DesignerVector newPoint = view.getDesignerPoint(motion.getPosition(), true);
		DiagramDesignerConnectionEditor connectionEditor = (DiagramDesignerConnectionEditor)gesture.getContext(DoAddAndSelectConnectorEditorGestureAction.CONNECTOR_EDITOR);
		connectionEditor.addConnectorDragPoint(newPoint);
		
		List<HitDefinition> hitItems = view.hitTest(motion.getPosition());
		HitDefinition connectionPointHit = null;
		for(HitDefinition hitDefinition : hitItems)
		{
			if(hitDefinition.isOnConnectionPoint())
			{
				connectionPointHit = hitDefinition;
			}
		}
		
		if(((connectionPointHit == null)	// no connectionpoint was hit
				|| (connectionPointHit != null	// a connectionpoint was hit
					&& (connectionPointHit.connectionPoint.getDirection() != Direction.InOut	// but it cannot accept incomming connecitons
					 && connectionPointHit.connectionPoint.getDirection() != Direction.In))))
		{
			view.cancelConnectionEditor(connectionEditor);
			return;
		}
		view.cancelConnectionEditor(connectionEditor);

		Integer sourceNodeId = (Integer)gesture.getContext(DoAddAndSelectConnectorEditorGestureAction.CONNECTOR_STARTITEM);
		ConnectionPoint sourceConnectionPoint = (ConnectionPoint)gesture.getContext(DoAddAndSelectConnectorEditorGestureAction.CONNECTOR_STARTCONNECTIONPOINT);
		
		view.addConnection(sourceNodeId, sourceConnectionPoint, 
				connectionPointHit.nodeId, connectionPointHit.connectionPoint);	}
}