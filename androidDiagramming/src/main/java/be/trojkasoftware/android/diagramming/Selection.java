package be.trojkasoftware.android.diagramming;

import java.util.List;

import android.graphics.Point;

public class Selection {
	public Point getSelectionPoint()
	{
		return selectionPoint;
	}
	
	public void setSelectionPoint(Point point)
	{
		selectionPoint = point;
	}
	
	public List<HitDefinition> getSelection()
	{
		return selection;
	}
	
	public void setSelection(List<HitDefinition> hititems)
	{
		selection = hititems;
	}
	
	private Point selectionPoint;
	private List<HitDefinition> selection;
}
