package be.trojkasoftware.android.diagramming;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DiagramDesignerItem2NodeIndexMap {

	public void putDiagramDesignerItem(DiagramDesignerItem item, int nodeIndex)
	{
		designerItemMap.put(item, nodeIndex);
	}
	
	public boolean containsDesignerItem(DiagramDesignerItem item)
	{
		return designerItemMap.containsKey(item);
	}
	
	public DiagramDesignerItem getDesignerItem(int nodeIndex)
	{
		for(Entry<DiagramDesignerItem, Integer> mapping : designerItemMap.entrySet())
		{
			if(mapping.getValue() == nodeIndex)
			{
				return mapping.getKey();
			}
		}
		
		return null;
	}
	
	public Set<DiagramDesignerItem> getDesignerItems()
	{
		return designerItemMap.keySet();
	}
	
	public int getNodeIndex(DiagramDesignerItem item)
	{
		return designerItemMap.get(item);
	}
	
	public void clear()
	{
		designerItemMap.clear();
	}

    private Map<DiagramDesignerItem, Integer> designerItemMap = new HashMap<DiagramDesignerItem, Integer>();
	
}
