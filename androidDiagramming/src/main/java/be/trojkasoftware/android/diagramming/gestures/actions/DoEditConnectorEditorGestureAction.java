package be.trojkasoftware.android.diagramming.gestures.actions;

import java.util.List;

import android.util.Log;
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

public class DoEditConnectorEditorGestureAction extends GestureActionBase<DiagramDesignerView> {

	public DoEditConnectorEditorGestureAction(DiagramDesignerView view) {
		super(view);
	}
	
	@Override
	public void executeAction(GestureEvent motion, TouchGesture gesture) {
		editConnectionEditor(getTouchedView(), motion, gesture);
	}
	
	public static void editConnectionEditor(DiagramDesignerView view, GestureEvent motion, TouchGesture gesture) {
		DesignerVector newPoint = view.getDesignerPoint(motion.getPosition(), true);

		if(!gesture.contextExists(DoAddAndSelectConnectorEditorGestureAction.CONNECTOR_EDITOR))
		{
			Log.i("DoEditConnectorEditorGestureAction", "context CONNECTOR_EDITOR does not exist");
			return;
		}
		
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
		if(connectionPointHit!= null)
		{
			if(connectionPointHit.connectionPoint.direction == Direction.In
					|| connectionPointHit.connectionPoint.direction == Direction.InOut)
			{
				if(!gesture.contextExists(DoAddAndSelectConnectorEditorGestureAction.CONNECTOR_STARTITEM))
				{
					Log.i("DoEditConnectorEditorGestureAction", "context CONNECTOR_STARTITEM does not exist");
				}
				Integer sourceNodeId = (Integer)gesture.getContext("CONNECTOR_STARTITEM");
				if(!gesture.contextExists(DoAddAndSelectConnectorEditorGestureAction.CONNECTOR_STARTCONNECTIONPOINT))
				{
					Log.i("DoEditConnectorEditorGestureAction", "context CONNECTOR_STARTCONNECTIONPOINT does not exist");
				}
				ConnectionPoint sourceConnectionPoint = (ConnectionPoint)gesture.getContext("CONNECTOR_STARTCONNECTIONPOINT");
				if(view.addingConnection(sourceNodeId, sourceConnectionPoint, 
						connectionPointHit.nodeId, connectionPointHit.connectionPoint))
				{
					connectionEditor.setCanConnect(DiagramDesignerConnectionEditor.CanConnectEnum.OnConnectorCanConnect);
				}
				else
				{
					connectionEditor.setCanConnect(DiagramDesignerConnectionEditor.CanConnectEnum.OnConnectorCanNotConnect);
				}				
			}
			else
			{
				connectionEditor.setCanConnect(DiagramDesignerConnectionEditor.CanConnectEnum.OnConnectorCanNotConnect);
			}
		}
		else
		{
			connectionEditor.setCanConnect(DiagramDesignerConnectionEditor.CanConnectEnum.NotOnConnector);
		}
	}
}
