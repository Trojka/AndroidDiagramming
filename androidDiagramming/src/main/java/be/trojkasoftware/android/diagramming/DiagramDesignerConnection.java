package be.trojkasoftware.android.diagramming;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class DiagramDesignerConnection implements IDrawable {
	
	private int areaDiameter = 10;
	
	public enum ConnectorArea
	{
		Start,
		End,
		Middle
	}
	
	public ConnectionPointDefinition getStartConnectionPoint()
	{
		return startConnectionPoint;
	}
	
	public void setStartConnectionPoint(ConnectionPointDefinition connectionPoint)
	{
		startConnectionPoint = connectionPoint;
	}

	public void setStartPosition(DesignerVector pos) {
		startPointPosition = pos;
	}
	
	public ConnectionPointDefinition getEndConnectionPoint()
	{
		return endConnectionPoint;
	}
	
	public void setEndConnectionPoint(ConnectionPointDefinition connectionPoint)
	{
		endConnectionPoint = connectionPoint;
	}

	public void setEndPosition(DesignerVector pos) {
		endPointPosition = pos;
	}
	
	public void Register(int connectionId)
	{
		this.connectionId = connectionId;
	}
	
	public int getConnectionId()
	{
		return this.connectionId;
	}
	
	public HitDefinition isHit(DesignerVector point, DesignerVector selectionWindow)
	{
		HitDefinition result = null;
		
		Point startPoint = source.getConnectionPointPosition(startConnectionPoint.getConnectionPointId());
		Point endPoint = target.getConnectionPointPosition(endConnectionPoint.getConnectionPointId());

        int minConnectorX = Math.min(startPoint.x, endPoint.x) - (areaDiameter/2);
        int maxConnectorX = Math.max(startPoint.x, endPoint.x) + (areaDiameter/2);

        if(point.x < minConnectorX || point.x > maxConnectorX)
            return result;

		double yShouldBe = ((point.x - endPoint.x) * (startPoint.y - endPoint.y) / (startPoint.x - endPoint.x)) + endPoint.y;
		
		if(((yShouldBe > 0.9 * (double)point.y)) && ((yShouldBe < 1.1 * (double)point.y)))
		{
			result = new HitDefinition();
			result.connectionId = connectionId;
			
			if((Math.pow(startPoint.x - point.x, 2.0) + Math.pow(startPoint.y - point.y, 2.0)) < (areaDiameter*areaDiameter))
			{
				result.connectorArea = ConnectorArea.Start;
			}
			else if((Math.pow(endPoint.x - point.x, 2.0) + Math.pow(endPoint.y - point.y, 2.0)) < (areaDiameter*areaDiameter))
			{
				result.connectorArea = ConnectorArea.End;
			}
			else
			{
				result.connectorArea = ConnectorArea.Middle;
			}
		}
		
		return result;

	}
	
	public int getColor()
	{
		return color;
	}
	
	public void setColor(int color)
	{
		this.color = color;
	}
	
	public void Draw(Canvas canvas, Point startPoint, Point endPoint)
	{
		Paint paint = new Paint();
		paint.setColor(color);
		paint.setStrokeWidth(2);
		canvas.drawLine(startPoint.x, startPoint.y, 
				endPoint.x, endPoint.y, 
				paint);
		
	}
	
	public final void RegisterDiagramDesignerItems(DiagramDesignerItem source, DiagramDesignerItem target)
	{
		this.source = source;
		this.target = target;
	}
	
	public final void Draw(Canvas canvas)
	{
		Point startPoint = getStartPoint();
		Point endPoint = getEndPoint();
		
		Draw(canvas, startPoint, endPoint);
    }
	
	public Point getStartPoint()
	{
		if(startPointPosition != null)
			return new Point(startPointPosition.x, startPointPosition.y);
		return source.getConnectionPointPosition(startConnectionPoint.getConnectionPointId());
	}
	
	public Point getEndPoint()
	{
		if(endPointPosition != null)
			return new Point(endPointPosition.x, endPointPosition.y);
		return target.getConnectionPointPosition(endConnectionPoint.getConnectionPointId());
	}

	private ConnectionPointDefinition startConnectionPoint;
	private DesignerVector startPointPosition;
	private ConnectionPointDefinition endConnectionPoint;
	private DesignerVector endPointPosition;
	
	private DiagramDesignerItem source;
	private DiagramDesignerItem target;
	
    int connectionId = -1;
    
    int color = Color.BLACK;
	
}
