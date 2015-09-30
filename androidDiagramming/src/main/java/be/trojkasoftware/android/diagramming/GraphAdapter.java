package be.trojkasoftware.android.diagramming;

import java.util.ArrayList;
import java.util.List;

import be.trojkasoftware.android.data.DataGraphObserver;

public abstract class GraphAdapter {

	public abstract int getNodeCount();
	
	public abstract Object getItem(int nodeId);
	
	public abstract Object getConnectionItem(int connectionId);
	
	public abstract DiagramDesignerItem getNode(int nodeId);
	
	public abstract int getNodeConnectionPointCount(int nodeId);
	
	public abstract ConnectionPoint getNodeConnectionPoint(int nodeId, int connectionPointId);

	public abstract int getConnectionCount();
	
	public abstract DiagramDesignerConnection getConnection(int connectionId);
	
	public void notifyDataSetChanged()
	{
		for(DataGraphObserver observer : dataGraphObserverList)
		{
			observer.GraphChanged();
		}
	}
	
	public void unregisterGraphDataObsever(DataGraphObserver dataGraphObserver)
	{
		if(!dataGraphObserverList.contains(dataGraphObserver))
			return;
		
		dataGraphObserverList.remove(dataGraphObserver);
	}
	
	public void registerGraphDataObsever(DataGraphObserver dataGraphObserver)
	{
		dataGraphObserverList.add(dataGraphObserver);
	}
	
	List<DataGraphObserver> dataGraphObserverList = new ArrayList<DataGraphObserver>();
}
