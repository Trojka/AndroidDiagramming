package be.trojkasoftware.android.diagramming;

import be.trojkasoftware.android.diagramming.DiagramDesignerConnection.ConnectorArea;

public class HitDefinition
{
	public CommandWidget command = null;
	public int nodeId = -1;
	public ConnectionPoint connectionPoint = null;
	public int connectionId = -1;
	public DiagramDesignerConnection.ConnectorArea connectorArea = ConnectorArea.Middle;
	
	public boolean IsSame(HitDefinition hitDefinition)
	{
		return (command == hitDefinition.command
				&& nodeId == hitDefinition.nodeId
				&& connectionPoint == hitDefinition.connectionPoint
						&& connectionId == hitDefinition.connectionId
				&& connectorArea == hitDefinition.connectorArea
				);
	}
	
	public boolean isOnCommand()
	{
		return this.command != null && this.nodeId < 0 && this.connectionPoint == null && this.connectionId < 0;
	}
	
	public boolean isOnNode()
	{
		return this.command == null && this.nodeId >= 0 && this.connectionPoint == null && this.connectionId < 0;
	}
	
	public boolean isOnConnectionPoint()
	{
		return this.command == null && this.nodeId >= 0 && this.connectionPoint != null && this.connectionId < 0;
	}
	
	public boolean isOnConnection()
	{
		return this.command == null && this.nodeId < 0 && this.connectionPoint == null && this.connectionId >= 0;
	}
}
