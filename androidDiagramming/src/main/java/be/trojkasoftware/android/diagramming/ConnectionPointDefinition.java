package be.trojkasoftware.android.diagramming;

public class ConnectionPointDefinition {
	private int designerItemId;
	private int connectionPointId;
	
	public void setDesignerItemId(int designerItemId)
	{
		this.designerItemId = designerItemId;
	}
	
	public int getDesignerItemId()
	{
		return designerItemId;
	}
	
	public void setConnectionPointId(int connectionPointId)
	{
		this.connectionPointId = connectionPointId;
	}
	
	public int getConnectionPointId()
	{
		return connectionPointId;
	}

}
