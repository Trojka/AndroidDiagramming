package be.trojkasoftware.android.sample.diagramming.sampledata;

import java.util.ArrayList;
import java.util.List;

public class NodeType {
	
	private String nodeName;
	private int x;
	private int y;
	private List<ConnectionPointType> connectionPonts = new ArrayList<ConnectionPointType>();
	
	public NodeType(String nodeName, int x, int y)
	{
		this.nodeName = nodeName;
		this.x = x;
		this.y = y;
		
		if(nodeName.equals("A"))
		{
			ConnectionPointType node1Point1 = new ConnectionPointType(true, false, 0);
			this.getConnectionPonts().add(node1Point1);
			ConnectionPointType node1Point2 = new ConnectionPointType(false, true, 0);
			this.getConnectionPonts().add(node1Point2);
		}
		
		if(nodeName.equals("B"))
		{
			ConnectionPointType node1Point1 = new ConnectionPointType(true, true, 0);
			this.getConnectionPonts().add(node1Point1);
			ConnectionPointType node1Point2 = new ConnectionPointType(true, true, 1);
			this.getConnectionPonts().add(node1Point2);
		}
	
		if(nodeName.equals("C"))
		{
			ConnectionPointType node2Point1 = new ConnectionPointType(true, false, 0);
			this.getConnectionPonts().add(node2Point1);
			ConnectionPointType node2Point2 = new ConnectionPointType(false, true, 0);
			this.getConnectionPonts().add(node2Point2);
		}
	}
	
	public void setPos(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public String getNodeName()
	{
		return nodeName;
	}
	
	public int getPosX()
	{
		return x;
	}
	
	public int getPosY()
	{
		return y;
	}
	
	public List<ConnectionPointType> getConnectionPonts()
	{
		return connectionPonts;
	}
}
