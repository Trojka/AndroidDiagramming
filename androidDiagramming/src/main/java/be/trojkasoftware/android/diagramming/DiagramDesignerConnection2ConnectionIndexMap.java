package be.trojkasoftware.android.diagramming;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class DiagramDesignerConnection2ConnectionIndexMap {

	public void putDiagramDesignerConnection(DiagramDesignerConnection item, int connectionIndex)
	{
		designerConnectionMap.put(item, connectionIndex);
	}
	
	public boolean containsDesignerConnection(DiagramDesignerConnection item)
	{
		return designerConnectionMap.containsKey(item);
	}
	
	public DiagramDesignerConnection getDesignerConnection(int connectionIndex)
	{
		for(Entry<DiagramDesignerConnection, Integer> mapping : designerConnectionMap.entrySet())
		{
			if(mapping.getValue() == connectionIndex)
			{
				return mapping.getKey();
			}
		}
		
		return null;
	}
	
	public Set<DiagramDesignerConnection> getDesignerConnections()
	{
		return designerConnectionMap.keySet();
	}
	
	public int getConnectionIndex(DiagramDesignerConnection item)
	{
		return designerConnectionMap.get(item);
	}
	
	public void clear()
	{
		designerConnectionMap.clear();
	}

    private Map<DiagramDesignerConnection, Integer> designerConnectionMap = new HashMap<DiagramDesignerConnection, Integer>();
	
}
