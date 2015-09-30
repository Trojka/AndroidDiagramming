package be.trojkasoftware.android.sample.diagramming.sampledata;

public class ConnectionPointType {
	private boolean acceptIncomming = false;
	private boolean acceptOutgoing = false;
	private int dockIndex = 0;
	
	public ConnectionPointType(boolean acceptIncomming, boolean acceptOutgoing, int dockIndex)
	{
		this.acceptIncomming = acceptIncomming;
		this.acceptOutgoing = acceptOutgoing;
		this.dockIndex = dockIndex;
	}
	
	public boolean getAcceptIncomming()
	{
		return acceptIncomming;
	}
	
	public boolean getAcceptOutgoing()
	{
		return acceptOutgoing;
	}
	
	public int getDockIndex()
	{
		return dockIndex;
	}
}
