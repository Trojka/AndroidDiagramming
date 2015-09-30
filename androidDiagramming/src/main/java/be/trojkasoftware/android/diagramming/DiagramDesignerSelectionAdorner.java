package be.trojkasoftware.android.diagramming;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class DiagramDesignerSelectionAdorner implements IDrawable, IItemMovedEventListener {

	public DiagramDesignerSelectionAdorner(Context context, DiagramDesignerView view)
	{
		this.view = view;
	}
	
	public void attach(List<HitDefinition> selection)
	{
		this.selection = selection;
		
		for(HitDefinition hitDefinition : selection)
		{
			Log.i("DiagramDesignerSelectionAdorner", 
					"NodeId[" + hitDefinition.nodeId + "]"
					+ "ConnecionPoint[" + (hitDefinition.connectionPoint == null ? "NULL": "not NULL") + "]"
					+ "ConnecionId[" + hitDefinition.connectionId + "]");
			if(hitDefinition.nodeId != -1 && hitDefinition.connectionPoint == null)
			{
				Log.i("DiagramDesignerSelectionAdorner", "You selected a node");
				DiagramDesignerItem selectedItem = this.view.getDesignerItem(hitDefinition.nodeId);
				selectedItem.addEventListener(this);
				registeredDesignerItems.add(selectedItem);
			}
			if(hitDefinition.nodeId != -1 && hitDefinition.connectionPoint != null)
			{
				Log.i("DiagramDesignerSelectionAdorner", "You selected a connectionpoint");
				registeredConnectionPoints.add(hitDefinition.connectionPoint);
			}
			else if(hitDefinition.connectionId != -1)
			{
				Log.i("DiagramDesignerSelectionAdorner", "You selected a connection");
				DiagramDesignerConnection selectedConnection = this.view.getDesignerConnection(hitDefinition.connectionId);
				registeredDesignerConnections.add(selectedConnection);
			}
			else
			{
				Log.i("DiagramDesignerSelectionAdorner", "You selected a non supported item");
			}
		}
	}
	
	public void detach()
	{
		for(DiagramDesignerItem item : registeredDesignerItems)
		{
			item.removeEventListener(this);
		}
	}

	@Override
	public void Draw(Canvas canvas) {
		Log.i("DiagramDesignerSelectionAdorner", "Draw is called");
	    
		Paint paint = new Paint();

		paint.setColor(android.graphics.Color.BLUE);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);

		Rect itemBounds = null;
		if(isMultipleSelection())
		{
			itemBounds = getBoundingBox();
			Log.i("DiagramDesignerSelectionAdorner", "Bounding box"
					+ "L[" + itemBounds.left + "]"
					+ "R[" + itemBounds.right + "]"
					+ "T[" + itemBounds.top + "]"
					+ "B[" + itemBounds.bottom + "]"
					);
		}
		else
		{
			if(registeredDesignerItems.size() == 1)
			{
				itemBounds = registeredDesignerItems.get(0).getBounds();
			}
			else if(registeredConnectionPoints.size() == 1)
			{
				ConnectionPoint connectionPoint = registeredConnectionPoints.get(0);
				itemBounds = connectionPoint.getParent().getConnectionPointBounds(connectionPoint);
			}
			else if(registeredDesignerConnections.size() == 1)
			{
				DiagramDesignerConnection connection = registeredDesignerConnections.get(0);
				itemBounds = new Rect(connection.getStartPoint().x, connection.getStartPoint().y,
								connection.getEndPoint().x, connection.getEndPoint().y);;
			}
		}
		
		//top line
		canvas.drawLine(itemBounds.left - padding, itemBounds.top - padding, 
				itemBounds.right + padding, itemBounds.top - padding, 
				paint);
		//right line
		canvas.drawLine(itemBounds.right + padding, itemBounds.top - padding, 
				itemBounds.right + padding, itemBounds.bottom + padding, 
				paint);
		//bottom 
		canvas.drawLine(itemBounds.left - padding, itemBounds.bottom + padding, 
				itemBounds.right + padding, itemBounds.bottom + padding, 
				paint);
		//left line
		canvas.drawLine(itemBounds.left - padding, itemBounds.top - padding, 
				itemBounds.left - padding, itemBounds.bottom + padding, 
				paint);
		
	}

	@Override
	public void handleItemMovedEvent(ItemMovedEvent evt) {
		// TODO Auto-generated method stub
		
	}
	
	public Rect getBoundingBox()
	{
		Rect boundingBox = new Rect();
		for(DiagramDesignerItem item : registeredDesignerItems)
		{
			Log.i("DiagramDesignerSelectionAdorner", "getBoundingBox adding items");
			Rect itemBounds = item.getBounds();
			Log.i("DiagramDesignerSelectionAdorner", "getBoundingBox item"
					+ "L[" + itemBounds.left + "]"
					+ "R[" + itemBounds.right + "]"
					+ "T[" + itemBounds.top + "]"
					+ "B[" + itemBounds.bottom + "]"
					);
			boundingBox.union(itemBounds);
		}
		for(DiagramDesignerConnection connection : registeredDesignerConnections)
		{
			Log.i("DiagramDesignerSelectionAdorner", "getBoundingBox adding connections");
			Rect itemBounds = new Rect(connection.getStartPoint().x, connection.getStartPoint().y,
					connection.getEndPoint().x, connection.getEndPoint().y);
			Log.i("DiagramDesignerSelectionAdorner", "getBoundingBox connection"
					+ "L[" + itemBounds.left + "]"
					+ "R[" + itemBounds.right + "]"
					+ "T[" + itemBounds.top + "]"
					+ "B[" + itemBounds.bottom + "]"
					);
			boundingBox.union(itemBounds);
		}
		
		return boundingBox;
	}
	
	private boolean isMultipleSelection()
	{
		return ((registeredDesignerItems.size()
				+ registeredConnectionPoints.size()
				+ registeredDesignerConnections.size()) > 1);
	}

	private int padding = 2;
	
	private DiagramDesignerView view;
	private List<HitDefinition> selection;
	
	private List<DiagramDesignerItem> registeredDesignerItems = new ArrayList<DiagramDesignerItem>();
	private List<ConnectionPoint> registeredConnectionPoints = new ArrayList<ConnectionPoint>();	
	private List<DiagramDesignerConnection> registeredDesignerConnections = new ArrayList<DiagramDesignerConnection>();
}
