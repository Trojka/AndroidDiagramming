package be.trojkasoftware.android.sample.diagramming.sampledata;

import java.util.ArrayList;
import java.util.List;

public class CustomGraphDataStructure {
	
	public CustomGraphDataStructure()
	{
		nodeList = new ArrayList<NodeType>();
		
		NodeType node1 = new NodeType("A", 100, 100);
		AddNode(node1);
		
		NodeType node2 = new NodeType("B", 300, 100);
		//AddNode(node2);
		
		NodeType node3 = new NodeType("C", 200, 300);
		AddNode(node3);
		
		connectionList = new ArrayList<Connection>();
		
		AddConnection(node1, 1, node3, 0);
		//AddConnection(node2, 1, node3, 0);
	}
	
	public List<NodeType> getNodes()
	{
		return nodeList;
	}
	
	public void AddNode(NodeType newNode)
	{
		nodeList.add(newNode);
	}
	
	public void RemoveNode(NodeType newNode)
	{
		nodeList.remove(newNode);
		
		List<Connection> connectionsToRemoveList = new ArrayList<Connection>();
		for(Connection connection : connectionList)
		{
			if(connection.sourceNode == newNode
					|| connection.targetNode == newNode)
			{
				connectionsToRemoveList.add(connection);
			}
		}
		
		connectionList.removeAll(connectionsToRemoveList);
	}
	
	public void AddConnection(NodeType sourceNode, int sourcePoint, NodeType targetNode, int targetPoint)
	{
        if(sourceNode == targetNode)
            return;

		Connection connection = new Connection();
		connection.sourceNode = sourceNode;
		connection.sourcePoint = sourcePoint;
		connection.targetNode = targetNode;
		connection.targetPoint = targetPoint;
		
		connectionList.add(connection);
	}
	
	public void DeleteConnection(Connection connection)
	{
		connectionList.remove(connection);
	}
	
	public void DeleteConnection(NodeType sourceNode, int sourcePoint, NodeType targetNode, int targetPoint)
	{
		for(Connection connection  : connectionList)
		{
			if(connection.sourceNode == sourceNode
				&& connection.sourcePoint == sourcePoint
				&&  connection.targetNode == targetNode
				&& connection.targetPoint == targetPoint)
			{
				connectionList.remove(connection);
				return;
			}
		}		
		
	}
	
	public List<Connection> getConnections()
	{
		return connectionList;
	}
	
	public class Connection
	{
		public NodeType sourceNode;
		public int sourcePoint;
		public NodeType targetNode;
		public int targetPoint;
	}
	
	private List<NodeType> nodeList;
	private List<Connection> connectionList;
}
