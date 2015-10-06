package be.trojkasoftware.android.diagramming;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import be.trojkasoftware.android.diagramming.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class DiagramDesignerItem implements IDrawable {
	
	public DiagramDesignerItem(Context context)
	{
		this.context = context;
	}
	
	public void setPosition(DesignerVector pos)
	{
		position = pos;
		
		boundsAreValid = false;
		
		ItemMovedEvent movedEvent = new ItemMovedEvent(this);
		for(IItemMovedEventListener listener : itemMovedListeners)
		{
			listener.handleItemMovedEvent(movedEvent);
		}
	}
	
	public void Register(int nodeId)
	{
		this.nodeId = nodeId;
	}
	
	public DesignerVector getPosition()
	{
		return position;
	}
	
	public synchronized void addEventListener(IItemMovedEventListener listener)  {
		itemMovedListeners.add(listener);
	}
	
	public synchronized void removeEventListener(IItemMovedEventListener listener)   {
		itemMovedListeners.remove(listener);
	}	

	public Point getConnectionPointPosition(int connectionPointId)
	{
		ConnectionPoint connectionPoint = getConnectionPoint(connectionPointId);
		
		if(connectionPoint != null)
		{
			return getConnectionPointPosition(connectionPoint);				
		}
		
		return null;
	}

	private Point getConnectionPointPosition(ConnectionPoint connectionPoint)
	{
		Rect bounds = getConnectionPointTemplate(connectionPoint).getBounds();
		return new Point((bounds.left + bounds.right) / 2, (bounds.top + bounds.bottom) / 2);
	}
	
	public int getWidth()
	{
		if(mDrawable == null)
		{
			getTemplate();
		}
		return width + maxConnectionPointWidth;
	}
	
	public int getHeight()
	{
		if(mDrawable == null)
		{
			getTemplate();
		}
		return height + maxConnectionPointHeight;
	}
	
	public Rect getBounds()
	{
		if(mDrawable == null)
		{
			getTemplate();
		}
		Rect result = new Rect(position.x - width/2, position.y - height/2, position.x + width/2, position.y + height/2);
		
		if(connectionPointMap != null)
		{
			boolean foundDockedAtTop = false;
			boolean foundDockedAtBottom = false;
			boolean foundDockedAtLeft = false;
			boolean foundDockedAtRight = false;
			
			for(ConnectionPoint connectionPoint : getConnectionPoints()) {
				if (!foundDockedAtTop && connectionPoint.getDock() == Dock.Top) {
					result.top = result.top - maxConnectionPointHeight / 2;
					foundDockedAtTop = true;
				}
				if (!foundDockedAtBottom && connectionPoint.getDock() == Dock.Bottom) {
					result.bottom = result.bottom + maxConnectionPointHeight / 2;
					foundDockedAtBottom = true;
				}
				if (!foundDockedAtLeft && connectionPoint.getDock() == Dock.Left) {
					result.left = result.left - maxConnectionPointWidth / 2;
					foundDockedAtLeft = true;
				}
				if (!foundDockedAtRight && connectionPoint.getDock() == Dock.Right) {
					result.right = result.right + maxConnectionPointWidth / 2;
					foundDockedAtRight = true;
				}
				if(foundDockedAtTop
						&& foundDockedAtBottom
						&& foundDockedAtLeft
						&& foundDockedAtRight) {
					break;
				}
			}
		}
		
		return result;
	}
	
	public void moveBy(DesignerVector distance)
	{
		position.x = position.x + distance.x;
		position.y = position.y + distance.y;
		
		setDrawableBounds();
		
		ItemMovedEvent movedEvent = new ItemMovedEvent(this);
		for(IItemMovedEventListener listener : itemMovedListeners)
		{
			listener.handleItemMovedEvent(movedEvent);
		}
	}
	
	public HitDefinition isHit(DesignerVector point, DesignerVector selectionWindow)
	{
		Rect selectionRect = new Rect(point.x-selectionWindow.x/2, point.y-selectionWindow.x/2, point.x+selectionWindow.x/2, point.y+selectionWindow.x/2);
		HitDefinition result = null;
		Rect bounds = getBounds();
		if (Rect.intersects(bounds, selectionRect))
		{
			result = new HitDefinition();
			result.nodeId = nodeId;
		}
		
    	for (ConnectionPoint connectionPoint : getConnectionPoints()) {
    		bounds = getConnectionPointTemplate(connectionPoint).getBounds();
    		if (Rect.intersects(bounds, selectionRect))
    		{
    			if(result == null)
    			{
    				result = new HitDefinition();
    			}
    			result.nodeId = nodeId;
    			result.connectionPoint = connectionPoint;
    			
    			break;
    		}	    		
    	}	
		
		return result;
	}
	
	public void Draw(Canvas canvas)
	{
		if(!boundsAreValid){
			setDrawableBounds();
			boundsAreValid = true;
		}
		
    	getTemplate().draw(canvas);

    	for (ConnectionPoint connectionPoint : getConnectionPoints()) {
    		getConnectionPointTemplate(connectionPoint).draw(canvas);
    	}	
    }
	
	public Set<ConnectionPoint> getConnectionPoints() {
		
		return connectionPointMap.keySet();
	}

	public int getDrawableResourceId()
	{
		return R.drawable.diagramdesigner_item;
	}
	
	public final void registerConnectionPoint(ConnectionPoint connectionPoint, int connectionPointId)
	{
		connectionPointMap.put(connectionPoint, connectionPointId);
		connectionPoint.setParent(this);
		
		if(maxConnectionPointWidth < connectionPoint.getWidth())
		{
			maxConnectionPointWidth = connectionPoint.getWidth();
		}
		if(maxConnectionPointHeight < connectionPoint.getHeight())
		{
			maxConnectionPointHeight = connectionPoint.getHeight();
		}
	}
	
	public final int getConnectionPointId(ConnectionPoint connectionPoint)
	{
		return connectionPointMap.get(connectionPoint);
	}
	
	public final ConnectionPoint getConnectionPoint(int connectionPointId)
	{
		for(ConnectionPoint connectionPoint : connectionPointMap.keySet())
		{
			if(connectionPointMap.get(connectionPoint) == connectionPointId)
			{
				return connectionPoint;				
			}
		}
		
		return null;
	}
	
	private Drawable getTemplate()
	{
		if(mDrawable == null)
		{
            Resources res = context.getResources();
		    try {
		    	mDrawable = (Drawable) res.getDrawable(getDrawableResourceId());
		    } catch (Exception ex) {
		       Log.e("Error", "Exception loading drawable: " + ex.getMessage());
		    }
		    setTemplateDrawableBounds();
		}
		return mDrawable;
	}
	
	private Drawable getConnectionPointTemplate(ConnectionPoint connectionPoint)
	{
		if(mConnectionPointDrawables == null)
		{
			mConnectionPointDrawables = new HashMap<ConnectionPoint, Drawable>();
		}
		
		if(!mConnectionPointDrawables.containsKey(connectionPoint))
		{
			Drawable connectionPointDrawable = null;
		    try {
		    	Resources res = context.getResources();
		    	connectionPointDrawable = (Drawable) res.getDrawable(connectionPoint.getConnectionPointDrawableResourceId()); //id);
			    mConnectionPointDrawables.put(connectionPoint, connectionPointDrawable);
		    } catch (Exception ex) {
		       Log.e("Error", "Exception loading drawable: " + ex.getMessage());
		    }
		   
		    setConnectionPointTemplateDrawableBounds(connectionPoint);
		}
		
		return mConnectionPointDrawables.get(connectionPoint);
	}
	
	private void setDrawableBounds()
	{
		setTemplateDrawableBounds();
		if(mConnectionPointDrawables != null)
		{
	    	for (ConnectionPoint connectionPoint : mConnectionPointDrawables.keySet()) {
	    		setConnectionPointTemplateDrawableBounds(connectionPoint);
	    	}
		}
	}
	
	private void setTemplateDrawableBounds()
	{
		if(mDrawable != null)
		{
			mDrawable.setBounds(
					position.x - width/2, 
					position.y - height/2, 
					position.x + width/2, 
					position.y + height/2);
		}	
	}

	private int getNumberOfItemsDockedToDock(Dock dock) {
		int result = 0;

		for (ConnectionPoint connectionPoint : connectionPointMap.keySet()) {
    		
    		if(connectionPoint.getDock() == dock) {
    			result++;
    		}
    	}
	
		return result;
	}
	
	private void setConnectionPointTemplateDrawableBounds(ConnectionPoint connectionPoint)
	{
		Drawable connectionPointDrawable = mConnectionPointDrawables.get(connectionPoint);
		//int numberOfConnectionsPointOnDock = getNumberOfItemsDockedToDock(connectionPoint.getDock());
		
		connectionPointDrawable.setBounds(getConnectionPointBounds(connectionPoint));
		return;
	}
	
	public Rect getConnectionPointBounds(ConnectionPoint connectionPoint)
	{
		Rect bounds = new Rect();
		int numberOfConnectionsPointOnDock = getNumberOfItemsDockedToDock(connectionPoint.getDock());
    	if(connectionPoint.getDock() == Dock.Top) {
    		int cpx = position.x - width/2 + (connectionPoint.getDockIndex() + 1) * (width / (numberOfConnectionsPointOnDock + 1)) - maxConnectionPointWidth/2;
    		int cpy = position.y - height/2 - maxConnectionPointHeight/2;
    		bounds = new Rect(
	    			cpx, 
	    			cpy, 
	    			cpx + maxConnectionPointWidth, 
	    			cpy + maxConnectionPointHeight);
    		
    	}		
    	if(connectionPoint.getDock() == Dock.Bottom) {
    		int cpx = position.x - width/2 + (connectionPoint.getDockIndex() + 1) * (width / (numberOfConnectionsPointOnDock + 1)) - maxConnectionPointWidth/2;
    		int cpy = position.y - height/2 + height - maxConnectionPointHeight/2;
    		bounds = new Rect(
	    			cpx, 
	    			cpy, 
	    			cpx + maxConnectionPointWidth, 
	    			cpy + maxConnectionPointHeight);
    		
    	}		
    	if(connectionPoint.getDock() == Dock.Left) {
    		int cpx = position.x - width/2 - maxConnectionPointWidth/2;
    		int cpy = position.y - height/2 + (connectionPoint.getDockIndex() + 1) * (height / (numberOfConnectionsPointOnDock + 1)) - maxConnectionPointHeight/2;
    		bounds = new Rect(
	    			cpx, 
	    			cpy, 
	    			cpx + maxConnectionPointWidth, 
	    			cpy + maxConnectionPointHeight);
    		
    	}	
    	
    	return bounds;
	}
	
	private boolean boundsAreValid = false;
	private Drawable mDrawable;
	private Map<ConnectionPoint, Drawable> mConnectionPointDrawables;
	private Context context;
	DesignerVector position = new DesignerVector(0, 0);
    int width = 100;
    int height = 100;
    int nodeId = -1;
    private List<IItemMovedEventListener> itemMovedListeners = new ArrayList<IItemMovedEventListener>();
    int maxConnectionPointWidth = 0;
    int maxConnectionPointHeight = 0;
    private Map<ConnectionPoint, Integer> connectionPointMap = new HashMap<ConnectionPoint, Integer>();

}
