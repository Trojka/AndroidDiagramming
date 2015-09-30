package be.trojkasoftware.android.diagramming;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class DiagramDesignerConnectionEditor {
	public enum CanConnectEnum
	{
		OnConnectorCanConnect,
		OnConnectorCanNotConnect,
		NotOnConnector
	}
	
	public ConnectionPointDefinition getStartConnectionPoint()
	{
		return startConnectionPoint;
	}
	
	public void setStartConnectionPoint(ConnectionPointDefinition connectionPoint)
	{
		startConnectionPoint = connectionPoint;
	}
	
	public ConnectionPointDefinition getEndConnectionPoint()
	{
		return endConnectionPoint;
	}
	
	public void setEndConnectionPoint(ConnectionPointDefinition connectionPoint)
	{
		endConnectionPoint = connectionPoint;
	}
	
	public void addConnectorDragPoint(DesignerVector dragPoint)
	{
		connectorDragPoints.add(dragPoint);
	}
	
	public void setCanConnect(CanConnectEnum canConnect)
	{
		this.canConnect = canConnect;
	}
	
	public void Draw(Canvas canvas)
	{
	
		if(connectorDragPoints != null)
		{
			Paint paint = new Paint();
    		switch(canConnect) {
    		case OnConnectorCanConnect:
				paint.setColor(Color.GREEN);
				break;
    		case OnConnectorCanNotConnect:
	            paint.setColor(Color.RED);    	    			    	    			
				break;
    		case NotOnConnector:
    			paint.setColor(Color.BLACK);    	    			
				break;
    		}
    		paint.setStrokeWidth(2);

    		boolean isFirst = true;
			DesignerVector previousPoint = null;
	    	for(DesignerVector point : connectorDragPoints)
	    	{
	    		if (isFirst)
	    		{
	    			previousPoint = point;
	    			isFirst = false;
	    			continue;
	    		}

	            canvas.drawLine(previousPoint.x, previousPoint.y, point.x, point.y, paint);
	            
	            previousPoint = point;
	    	}
		}

    }

    private CanConnectEnum canConnect = CanConnectEnum.NotOnConnector;

    private ConnectionPointDefinition startConnectionPoint;
	private ConnectionPointDefinition endConnectionPoint;
	private List<DesignerVector> connectorDragPoints = new ArrayList<DesignerVector>();

}
