package be.trojkasoftware.android.sample;

import android.content.Context;

import be.trojkasoftware.android.diagramming.ConnectionPoint;
import be.trojkasoftware.android.diagramming.ConnectionPointDefinition;
import be.trojkasoftware.android.diagramming.DesignerVector;
import be.trojkasoftware.android.diagramming.DiagramDesignerConnection;
import be.trojkasoftware.android.diagramming.DiagramDesignerItem;
import be.trojkasoftware.android.diagramming.Dock;
import be.trojkasoftware.android.diagramming.Direction;
import be.trojkasoftware.android.diagramming.GraphAdapter;
import be.trojkasoftware.android.sample.diagramming.sampledata.ConnectionPointType;
import be.trojkasoftware.android.sample.diagramming.sampledata.CustomGraphDataStructure;
import be.trojkasoftware.android.sample.diagramming.sampledata.CustomOvalConnectionPoint;
import be.trojkasoftware.android.sample.diagramming.sampledata.CustomOvalItem;
import be.trojkasoftware.android.sample.diagramming.sampledata.CustomRoundedRectangularItem;
import be.trojkasoftware.android.sample.diagramming.sampledata.NodeType;

public class CustomGraphAdapter extends GraphAdapter {
	
	private Context context;
	private CustomGraphDataStructure graphData;

	public CustomGraphAdapter(CustomGraphDataStructure graphData, Context context)
	{
		this.graphData = graphData;
		this.context = context;
	}
			
	@Override
	public int getNodeCount() {

		return this.graphData.getNodes().size();
	}

	@Override
	public Object getItem(int nodeId) {

		return this.graphData.getNodes().get(nodeId);
	}

	@Override
	public Object getConnectionItem(int connectionId) {

		return this.graphData.getConnections().get(connectionId);
	}

	@Override
	public DiagramDesignerItem getNode(int nodeId) {
		DiagramDesignerItem returnValue = null;
		
		int x = this.graphData.getNodes().get(nodeId).getPosX();
		int y = this.graphData.getNodes().get(nodeId).getPosY();
		
		if(this.graphData.getNodes().get(nodeId).getNodeName().equals("A"))
		{
			returnValue = new DiagramDesignerItem(this.context);
			returnValue.setPosition(new DesignerVector(x, y));
		}
		else if(this.graphData.getNodes().get(nodeId).getNodeName().equals("B"))
		{
			returnValue = new CustomOvalItem(this.context);
			returnValue.setPosition(new DesignerVector(x, y));
		}
		else if(this.graphData.getNodes().get(nodeId).getNodeName().equals( "C"))
		{
			returnValue = new CustomRoundedRectangularItem(this.context);
			returnValue.setPosition(new DesignerVector(x, y));
		}
		return returnValue;
	}

	@Override
	public int getConnectionCount() {

		return graphData.getConnections().size();
	}

	@Override
	public DiagramDesignerConnection getConnection(int connectionId) {
		DiagramDesignerConnection diagramConnection = new DiagramDesignerConnection();
		
		CustomGraphDataStructure.Connection connection = graphData.getConnections().get(connectionId);
		
		for(int nodeId = 0; nodeId < graphData.getNodes().size(); nodeId++)
		{
			NodeType node = graphData.getNodes().get(nodeId);
			if(connection.sourceNode == node)
			{
				ConnectionPointDefinition connectionPoint = new ConnectionPointDefinition();
				connectionPoint.setDesignerItemId(nodeId);
				connectionPoint.setConnectionPointId(connection.sourcePoint);
				diagramConnection.setStartConnectionPoint(connectionPoint );
			}
			else if (connection.targetNode == node)
			{
				ConnectionPointDefinition connectionPoint = new ConnectionPointDefinition();
				connectionPoint.setDesignerItemId(nodeId);
				connectionPoint.setConnectionPointId(connection.targetPoint);
				diagramConnection.setEndConnectionPoint(connectionPoint );
			}
		}
		
		return diagramConnection;
	}

	@Override
	public int getNodeConnectionPointCount(int nodeId) {
		return this.graphData.getNodes().get(nodeId).getConnectionPonts().size();
	}

	@Override
	public ConnectionPoint getNodeConnectionPoint(int nodeId,
			int connectionPointId) {

		ConnectionPointType point = this.graphData.getNodes().get(nodeId).getConnectionPonts().get(connectionPointId);
		
		ConnectionPoint connectionPoint = null;
		if(this.graphData.getNodes().get(nodeId).getNodeName().equals("B"))
		{
			connectionPoint = new CustomOvalConnectionPoint();
		}
		else
		{
			connectionPoint = new ConnectionPoint();
		}
		
		connectionPoint.setDockIndex(point.getDockIndex());
		if(point.getAcceptIncomming() && !point.getAcceptOutgoing())
		{
			connectionPoint.setDock(Dock.Top);
			connectionPoint.setDirection(Direction.In);
		}
		else if(!point.getAcceptIncomming() && point.getAcceptOutgoing())
		{
			connectionPoint.setDock(Dock.Bottom);
			connectionPoint.setDirection(Direction.Out);
		}
		else if(point.getAcceptIncomming() && point.getAcceptOutgoing())
		{
			connectionPoint.setDock(Dock.Left);
			connectionPoint.setDirection(Direction.InOut);
		}
		
		return connectionPoint;
	}

}
