package be.trojkasoftware.android.diagramming;

import be.trojkasoftware.android.diagramming.R;

public class ConnectionPoint {
	public ConnectionPoint()
	{
	}
	
	public int getConnectionPointDrawableResourceId()
	{
		return R.drawable.diagramdesigner_connectionpoint;
	}
	
	public int getWidth()
	{
		return 20;
	}
	
	public int getHeight()
	{
		return 20;
	}
	
	public void setParent(DiagramDesignerItem parent)
	{
		this.parent = parent;
	}
	
	public DiagramDesignerItem getParent()
	{
		return parent;
	}
	
	public Dock getDock() {
		return dock;
	}
	
	public void setDock(Dock dock) {
		this.dock = dock;
	}
	
	public int getDockIndex() {
		return dockIndex;
	}
	
	public void setDockIndex(int dockIndex) {
		this.dockIndex = dockIndex;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	DiagramDesignerItem parent;
	Dock dock;
	int dockIndex;
	public Direction direction;
}
