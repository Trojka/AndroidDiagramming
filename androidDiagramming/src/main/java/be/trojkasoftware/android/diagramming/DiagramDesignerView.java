package be.trojkasoftware.android.diagramming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import be.trojkasoftware.android.data.DataGraphObserver;
import be.trojkasoftware.android.diagramming.HitDefinition;
import be.trojkasoftware.android.diagramming.DiagramDesignerConnection.ConnectorArea;
import be.trojkasoftware.android.diagramming.commands.widgets.DeleteSelectedConnectionsCommandWidget;
import be.trojkasoftware.android.diagramming.commands.widgets.DeleteSelectedItemsCommandWidget;
import be.trojkasoftware.android.diagramming.commands.widgets.ZoomInCommandWidget;
import be.trojkasoftware.android.diagramming.commands.widgets.ZoomOutCommandWidget;
import be.trojkasoftware.android.diagramming.gestures.AddConnectionGesture;
import be.trojkasoftware.android.diagramming.gestures.AddItemGesture;
import be.trojkasoftware.android.diagramming.gestures.AddOrEditConnectionGesture;
import be.trojkasoftware.android.diagramming.gestures.EditConnectionOrShowConnectionContextMenuGesture;
import be.trojkasoftware.android.diagramming.gestures.EditItemOrShowItemContextMenuGesture;
import be.trojkasoftware.android.diagramming.gestures.ExecuteCommandGesture;
import be.trojkasoftware.android.diagramming.gestures.PanningGesture;
import be.trojkasoftware.android.diagramming.gestures.SelectSingleFromMultipleGesture;

import be.trojkasoftware.android.ScreenVector;
import be.trojkasoftware.android.WorldVectorMetric;
//import com.hfk.android.diagramming.adorners.MoveAdorner;
//import com.hfk.android.diagramming.adorners.SelectionAdorner;
//import com.hfk.android.diagramming.gestures.AddConnectionGesture;
//import com.hfk.android.diagramming.gestures.AddItemGesture;
//import com.hfk.android.diagramming.gestures.MoveItemOrShowItemContextMenuGesture;
//import com.hfk.android.diagramming.gestures.MultiSelectGesture;
//import com.hfk.android.diagramming.gestures.PanningGesture;
//import com.hfk.android.diagramming.gestures.ExecuteCommandGesture;
import be.trojkasoftware.android.gestures.TouchHandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class DiagramDesignerView extends View implements DataGraphObserver {

	public static final String ADD_ITEMSID = "InsertItems_ItemsId";
	public static final String REMOVE_ITEMSID = "DeleteItems_ItemsId";
	
	public interface IDiagramDesignerEvents
	{
		void OnAddItem(Object item, DesignerVector newPoint);
		
		boolean OnEditingItems(List<Integer> nodeIdList, DesignerVector newPoint);
		void OnEditItems(List<Integer> nodeIdList, DesignerVector newPoint);
		
		void OnDeleteItems(List<Integer> nodeIdList);
		
		boolean OnAddingConnection(Integer sourceNodeId, Integer sourceConnectionPointId,
				Integer targetNodeId, Integer targetConnectionPointId);
		void OnAddConnection(Integer sourceNodeId, Integer sourceConnectionPointId,
				Integer targetNodeId, Integer targetConnectionPointId);

		boolean OnEditingConnections(List<Integer> connectionIdList, DesignerVector newPoint);
	    void OnEditConnection(int connectionId, 
	    		int newSourceItemId, int newSourceItemConnectionpointId, 
	    		int newTargetItemId, int newTargetItemConnectionpointId);
		
		void OnDeleteConnections(List<Integer> connectionIdList);
	}
	
    public DiagramDesignerView(Context context, Class<?> selectDesigerItemListActivityClass) {
	    super(context);
	    
	    this.selectDesigerItemListActivityClass = selectDesigerItemListActivityClass;
	    
	    touchHandler = new TouchHandler();
	    
	    AddItemGesture addItemGesture = new AddItemGesture(this);
	    AddConnectionGesture addConnectionGesture = new AddConnectionGesture(this);
	    AddOrEditConnectionGesture addOrEditConnectionGesture = new AddOrEditConnectionGesture(this);
	    EditItemOrShowItemContextMenuGesture editItemOrShowItemContextMenuGesture = new EditItemOrShowItemContextMenuGesture(this);
	    EditConnectionOrShowConnectionContextMenuGesture editConnectionOrShowConnectionContextMenuGesture = new EditConnectionOrShowConnectionContextMenuGesture(this);
	    PanningGesture panningGesture = new PanningGesture(this);
	    ExecuteCommandGesture executeCommandGesture = new ExecuteCommandGesture(this);
	    SelectSingleFromMultipleGesture multiSelectGesture = new SelectSingleFromMultipleGesture(this);
	    
	    touchHandler.addGesture(addItemGesture.create());
	    touchHandler.addGesture(editItemOrShowItemContextMenuGesture.create());
	    touchHandler.addGesture(editConnectionOrShowConnectionContextMenuGesture.create());
	    touchHandler.addGesture(addConnectionGesture.create());
	    touchHandler.addGesture(addOrEditConnectionGesture.create());
	    touchHandler.addGesture(panningGesture.create());
	    touchHandler.addGesture(executeCommandGesture.create());
	    touchHandler.addGesture(multiSelectGesture.create());

        CommandWidget deleteItemWidget = new DeleteSelectedItemsCommandWidget(this);
        this.addDesignerItemCommand(deleteItemWidget);

        CommandWidget deleteConnectionWidget = new DeleteSelectedConnectionsCommandWidget(this);
        this.addDesignerConnectionCommand(deleteConnectionWidget);
        
        CommandWidget zoomInWidget = new ZoomInCommandWidget(this);
        this.addCommand(zoomInWidget);
        
        CommandWidget zoomOutWidget = new ZoomOutCommandWidget(this);
        this.addCommand(zoomOutWidget);

    }
	
	public void showMessage(String message)
	{
		Toast msg = Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT);
		msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);
		msg.show();		
		Log.i("DiagramDesignerView", "showMessage() was executed");
	}
    
    public void setDiagramDesignerEventHandler(IDiagramDesignerEvents diagramDesignerEvents)
    {
    	this.diagramDesignerEvents = diagramDesignerEvents;
    }
    
    public void setAdapter(GraphAdapter adapter)
    {
    	this.adapter = adapter;
    	this.adapter.registerGraphDataObsever(this);
    }
    
	@Override
	public void GraphChanged() {
		designerItemMapIsValid = false;
		designerConnectionMapIsValid = false;
		this.invalidate();
	}
    
    public void HandleRequestResult(int requestCode, int resultCode, Intent intent)
    {
    	if(requestCode == addItemRequestId && addItemRequestHandlerCallback != null)
    	{
    		addItemRequestHandlerCallback.HandleRequestResult(requestCode, resultCode, intent);
    		addItemRequestHandlerCallback = null;
    	}
    }
    
    public void ShowSelectItemActivity(IRequestHandlerCallback iReguestHandlerCallback)
    {
    	if(this.getContext() instanceof Activity)
    	{
    		addItemRequestHandlerCallback = iReguestHandlerCallback;
    		Activity activity = (Activity)this.getContext();
    		Intent myIntent = new Intent(activity, selectDesigerItemListActivityClass);
    		activity.startActivityForResult(myIntent, addItemRequestId);
    	}
    }
    
    public void showDesignerContextMenu()
    {
    	contextMenuMode = ShowContextMenuMode.Designer;
    	invalidate();
    }
    
    public void showSelectedConnectionContextMenu()
    {
    	contextMenuMode = ShowContextMenuMode.SelectedConnection;
    	invalidate();
    }
    
    public void showSelectedItemContextMenu()
    {
    	contextMenuMode = ShowContextMenuMode.SelectedItem;
    	invalidate();
    }
    
    public void hideContextMenu()
    {
    	contextMenuMode = ShowContextMenuMode.None;
    	invalidate();
    }
    
    public void addCommand(CommandWidget widget)
    {
    	if(designerCommandList.size() == 0)
    	{
    		widget.setPosition(new ScreenVector(
    				initialCommandPosX + widget.getWidth() / 2, 
    				initialCommandPosY + widget.getHeight() / 2));
    	}
    	else
    	{
    		int lastCommandRightPosX = designerCommandList.get(designerCommandList.size() - 1).getBounds().right;
            widget.setPosition(new ScreenVector(
            		lastCommandRightPosX + initialCommandPosX + widget.getWidth() / 2, 
            		initialCommandPosY + widget.getHeight() / 2));
    	}
    	designerCommandList.add(widget);
    }    
    
    public void addDesignerItemCommand(CommandWidget widget)
    {
    	if(designerItemCommandList.size() == 0)
    	{
    		widget.setPosition(new ScreenVector(
    				initialCommandPosX + widget.getWidth() / 2, 
    				initialCommandPosY + widget.getHeight() / 2));
    	}
    	else
    	{
    		int lastCommandRightPosX = designerItemCommandList.get(designerItemCommandList.size() - 1).getBounds().right;
            widget.setPosition(new ScreenVector(
            		lastCommandRightPosX + initialCommandPosX + widget.getWidth() / 2, 
            		initialCommandPosY + widget.getHeight() / 2));
    	}
    	designerItemCommandList.add(widget);
    }    
    
    public void addDesignerConnectionCommand(CommandWidget widget)
    {
    	if(designerConnectionCommandList.size() == 0)
    	{
    		widget.setPosition(new ScreenVector(
    				initialCommandPosX + widget.getWidth() / 2, 
    				initialCommandPosY + widget.getHeight() / 2));
    	}
    	else
    	{
    		int lastCommandRightPosX = designerConnectionCommandList.get(designerConnectionCommandList.size() - 1).getBounds().right;
            widget.setPosition(new ScreenVector(
            		lastCommandRightPosX + initialCommandPosX + widget.getWidth() / 2, 
            		initialCommandPosY + widget.getHeight() / 2));
    	}
    	designerConnectionCommandList.add(widget);
    }    
    
    public void addItem(Object item, DesignerVector newPoint)
    {
    	if(this.diagramDesignerEvents == null)
    	{
    		return;
    	}
    	
    	this.diagramDesignerEvents.OnAddItem(item, newPoint);
    }
    
    public DiagramDesignerConnectionEditor getConnectionEditor(int nodeId, ConnectionPoint startConnectionPoint)
    {
    	this.connectionEditor = new DiagramDesignerConnectionEditor();
    	ConnectionPointDefinition startConnectionPointDef = new ConnectionPointDefinition();
    	startConnectionPointDef.setDesignerItemId(nodeId);
    	startConnectionPointDef.setConnectionPointId(designerItemNodeIndexMap.getDesignerItem(nodeId).getConnectionPointId(startConnectionPoint));
    	this.connectionEditor.setStartConnectionPoint(startConnectionPointDef);
    	
    	return this.connectionEditor;
    }
    
    public boolean addingConnection(int sourceNodeId, ConnectionPoint sourceConnectionPoint, int targetNodeId, ConnectionPoint targetConnectionPoint)
    {
    	if(this.diagramDesignerEvents == null)
    	{
    		return false;
    	}
    	
    	return this.diagramDesignerEvents.OnAddingConnection(
    			sourceNodeId, designerItemNodeIndexMap.getDesignerItem(sourceNodeId).getConnectionPointId(sourceConnectionPoint), 
    			targetNodeId, designerItemNodeIndexMap.getDesignerItem(targetNodeId).getConnectionPointId(targetConnectionPoint));
    }
    
    public boolean addingConnection(DiagramDesignerItem sourceItem, ConnectionPoint sourceConnectionPoint, DiagramDesignerItem targetItem, ConnectionPoint targetConnectionPoint)
    {
    	if(this.diagramDesignerEvents == null)
    	{
    		return false;
    	}
    	
    	return this.diagramDesignerEvents.OnAddingConnection(
    			designerItemNodeIndexMap.getNodeIndex(sourceItem), sourceItem.getConnectionPointId(sourceConnectionPoint), 
    			designerItemNodeIndexMap.getNodeIndex(targetItem), targetItem.getConnectionPointId(targetConnectionPoint));
    }
    
    public void addConnection(int sourceNodeId, ConnectionPoint sourceConnectionPoint, int targetNodeId, ConnectionPoint targetConnectionPoint)
    {
    	if(this.diagramDesignerEvents == null)
    	{
    		return;
    	}
    	
    	this.diagramDesignerEvents.OnAddConnection(
    			sourceNodeId, designerItemNodeIndexMap.getDesignerItem(sourceNodeId).getConnectionPointId(sourceConnectionPoint), 
    			targetNodeId, designerItemNodeIndexMap.getDesignerItem(targetNodeId).getConnectionPointId(targetConnectionPoint));
    }
    
    public void addConnection(DiagramDesignerItem sourceItem, ConnectionPoint sourceConnectionPoint, DiagramDesignerItem targetItem, ConnectionPoint targetConnectionPoint)
    {
    	if(this.diagramDesignerEvents == null)
    	{
    		return;
    	}
    	
    	this.diagramDesignerEvents.OnAddConnection(
    			designerItemNodeIndexMap.getNodeIndex(sourceItem), sourceItem.getConnectionPointId(sourceConnectionPoint), 
    			designerItemNodeIndexMap.getNodeIndex(targetItem), targetItem.getConnectionPointId(targetConnectionPoint));
    }
    
	public void cancelConnectionEditor(DiagramDesignerConnectionEditor connectionEditor) {
		this.connectionEditor = null;		
	}
	
	public void deleteDesignerItems(List<HitDefinition> items)
    {
    	if(this.diagramDesignerEvents == null)
    	{
    		return;
    	}
    	
    	List<Integer> nodeList = new ArrayList<Integer>();
    	for (HitDefinition hitResult : items) {
    		nodeList.add(hitResult.nodeId);
    	}
    	
    	this.diagramDesignerEvents.OnDeleteItems(nodeList);
    }
	
	public void deleteDesignerConnections(List<HitDefinition> items)
    {
    	if(this.diagramDesignerEvents == null)
    	{
    		return;
    	}
    	
    	List<Integer> connectionList = new ArrayList<Integer>();
    	for (HitDefinition hitResult : items) {
    		connectionList.add(hitResult.connectionId);
    	}
    	
    	for(int deleteConnectionId : connectionList)
    	{
    		this.diagramDesignerEvents.OnDeleteConnections(connectionList);
    	}
    }
  
  public void setAdorner(DiagramDesignerSelectionAdorner a, List<HitDefinition> selection)
  {
    if(selection == null)
        return;

    adorner = a;
    adorner.attach(selection);
  }
    
    public void clearAdorner()
    {
    	if(adorner == null)
    		return;
    	
    	adorner.detach();
    	adorner = null;
    }

	public ScreenVector getPanningOffsetset() {
		return panning;		
	}

	public void setPanningOffsetset(ScreenVector panning) {
		this.panning = panning;		
	}
	
	public float getZooming()
	{
		return zoomFactor;
	}
	
	public void setZooming(float zooming)
	{
		zoomFactor = zooming;
	}
	
	public DesignerVector getDesignerPoint(ScreenVector screenPoint, boolean isPosition)
	{
		Matrix matrix;
		if(isPosition)
		{
	    	matrix = getTransformationMatrix();
		}
		else
		{
			matrix = getScalingMatrix();
		}
		
    	Matrix inversMatrix;
    	inversMatrix = new Matrix();
    	matrix.invert(inversMatrix);
    	
    	float[] point = new float[2];
    	point[0] = (float)screenPoint.x;
    	point[1] = (float)screenPoint.y;
    	inversMatrix.mapPoints(point);

    	return new DesignerVector((int)point[0], (int)point[1]);
	}
    
    public boolean onTouchEvent(MotionEvent motion)   { 
    	
    	touchHandler.onTouchEvent(motion);
    	
    	invalidate();
    	return true;  	
    }   
    
    protected void onDraw(Canvas canvas) {
    	
    	// Save the current transformation matrix
        canvas.save();

        // We will draw the graph. So set the transformation resulting from scaling and panning
    	canvas.concat(getTransformationMatrix());
    	
    	if(viewMode == ViewMode.Normal) {
    		
    		for(DiagramDesignerItem item : getDesignerItems())
    		{
    			item.Draw(canvas);
    		}
    		
    		
    		for(DiagramDesignerConnection connection : getDesignerConnections())
    		{
    			connection.Draw(canvas);
    		}
       	
        	if(connectionEditor != null)
        	{
        		connectionEditor.Draw(canvas);
        	}
    	}
    	
		Log.i("DiagramDesignerView", "onDraw");
    	if(selectionAdorner != null)
    	{
    		Log.i("DiagramDesignerView", "onDraw: draw adorner");
    		selectionAdorner.Draw(canvas);
    	}
    	else
    	{
    		Log.i("DiagramDesignerView", "onDraw: no adorner to draw");
    	}
    	
    	if(adorner != null)
    	{
    		adorner.Draw(canvas);
    	}

		// After drawing the graph we restore the transformationmatrix to its original value
        canvas.restore();


        // Now start drawing any command buttons if neccessary
    	if(contextMenuMode == ShowContextMenuMode.Designer)
    	{
	    	for (IDrawable item : designerCommandList) {
	    		item.Draw(canvas);
	    	}
    	}
    	
    	if(contextMenuMode == ShowContextMenuMode.SelectedItem)
    	{
	    	for (IDrawable item : designerItemCommandList) {
	    		item.Draw(canvas);
	    	}
    	}
       	
    	if(contextMenuMode == ShowContextMenuMode.SelectedConnection)
    	{
	    	for (IDrawable item : designerConnectionCommandList) {
	    		item.Draw(canvas);
	    	}
    	}
    	
    }
    
    public List<HitDefinition> hitTest(ScreenVector screenCoord)
    {
		DesignerVector newPoint = getDesignerPoint(screenCoord, true);

		DesignerVector selectionDesigner = getDesignerPoint(new ScreenVector(selectionWindow, this), false);
	
    	List<HitDefinition> selectedItems = new ArrayList<HitDefinition>();

    	if(contextMenuMode == ShowContextMenuMode.Designer)
    	{
	    	// If a command was hit, then this takes precedence over all other
	    	for (CommandWidget item : designerCommandList) {
	    		HitDefinition hitResult = item.isHit(screenCoord, new ScreenVector(selectionWindow, this));
	    		if(hitResult != null) {
	    			selectedItems.add(hitResult);
	    			return selectedItems;
	    		}
	    	}
    	}

    	if(contextMenuMode == ShowContextMenuMode.SelectedItem)
    	{
	    	// If a command was hit, then this takes precedence over all other
	    	for (CommandWidget item : designerItemCommandList) {
	    		HitDefinition hitResult = item.isHit(screenCoord, new ScreenVector(selectionWindow, this));
	    		if(hitResult != null) {
	    			selectedItems.add(hitResult);
	    			return selectedItems;
	    		}
	    	}
    	}

    	if(contextMenuMode == ShowContextMenuMode.SelectedConnection)
    	{
	    	// If a command was hit, then this takes precedence over all other
	    	for (CommandWidget item : designerConnectionCommandList) {
	    		HitDefinition hitResult = item.isHit(screenCoord, new ScreenVector(selectionWindow, this));
	    		if(hitResult != null) {
	    			selectedItems.add(hitResult);
	    			return selectedItems;
	    		}
	    	}
    	}
    	
    	for (DiagramDesignerItem item : getDesignerItems()) {
    		HitDefinition hitResult = item.isHit(newPoint, selectionDesigner);
    		if(hitResult != null) {
    			selectedItems.add(hitResult);
    		}
    	}
    	
    	for (DiagramDesignerConnection connection : getDesignerConnections()) {
    		HitDefinition hitResult = connection.isHit(newPoint, selectionDesigner);
    		if(hitResult != null) {
    			selectedItems.add(hitResult);
    		}
    	}
    	
    	return selectedItems;
    }
    
    public void setSelection(List<HitDefinition> selection)
    {
    	currentSelection = selection;
    	
		selectionAdorner = new DiagramDesignerSelectionAdorner(this.getContext(), this);
		selectionAdorner.attach(currentSelection);
    	
    }
    
    public List<HitDefinition> getSelection()
    {
    	return currentSelection;
    }
    
    public void clearSelection()
    {
    	if(selectionAdorner != null)
    	{
	    	selectionAdorner.detach();
	    	selectionAdorner = null;
    	}
    	currentSelection = null;
    }
    
    public boolean editingDesignerItems(List<HitDefinition> items, ScreenVector pos)
    {
    	if(this.diagramDesignerEvents == null)
    	{
    		return false;
    	}
    	
    	if(items.size() > 1)
    	{
    		// editing multiple items not yet supported
    	}
    	else if(items.size() == 1)
    	{
    		if(items.get(0).isOnNode())
    		{
    	    	List<Integer> nodeList = new ArrayList<Integer>();
    	    	for (HitDefinition hitResult : items) {
    	    		nodeList.add(hitResult.nodeId);
    	    	}
    	    	
    	    	if(this.diagramDesignerEvents.OnEditingItems(nodeList, getDesignerPoint(pos, true)))
    	    	{
    	        	for (HitDefinition hitResult : items) {
    	        		designerItemNodeIndexMap.getDesignerItem(hitResult.nodeId).setPosition(getDesignerPoint(pos, true));
    	        	}    		
    	        	return true;
    	    	}
    		}
    		if(items.get(0).isOnConnection())
    		{
    	    	List<Integer> connectionList = new ArrayList<Integer>();
    	    	for (HitDefinition hitResult : items) {
    	    		connectionList.add(hitResult.connectionId);
    	    	}
    	    	
    			List<HitDefinition> hitItems = this.hitTest(pos);
    			HitDefinition connectionPointHit = null;
    			for(HitDefinition hitDefinition : hitItems)
    			{
    				if(hitDefinition.isOnConnectionPoint())
    				{
    					connectionPointHit = hitDefinition;
    				}
    			}
    			boolean targetIsStartConnectable = false;
    			boolean targetIsEndConnectable = false;
    			if(connectionPointHit!= null)
    			{
    				if(connectionPointHit.connectionPoint.direction == Direction.In
    						|| connectionPointHit.connectionPoint.direction == Direction.InOut)
    				{
    					targetIsEndConnectable = true;
    				}
    				else if(connectionPointHit.connectionPoint.direction == Direction.Out
    						|| connectionPointHit.connectionPoint.direction == Direction.InOut)
    				{
    					targetIsStartConnectable = true;
    				}
    			
    			}
    	    	if(this.diagramDesignerEvents.OnEditingConnections(connectionList, getDesignerPoint(pos, true)))
    	    	{
    	        	for (HitDefinition hitResult : items) {
	        			DiagramDesignerConnection connection = designerConnectionIndexMap.getDesignerConnection(hitResult.connectionId);
    	        		if(hitResult.connectorArea == ConnectorArea.Start)
    	        		{
    	        			if(targetIsStartConnectable)
    	        			{
    	        				connection.setColor(Color.GREEN);
    	        			}
    	        			else
    	        			{
    	        				connection.setColor(Color.RED);
    	        			}
    	        			connection.setStartPosition(getDesignerPoint(pos, true));
    	        		}
    	        		else if(hitResult.connectorArea == ConnectorArea.End)
    	        		{
    	        			if(targetIsEndConnectable)
    	        			{
    	        				connection.setColor(Color.GREEN);
    	        			}
    	        			else
    	        			{
    	        				connection.setColor(Color.RED);
    	        			}
    	        			connection.setEndPosition(getDesignerPoint(pos, true));
    	        		}
    	        	}    		
    	        	return true;
    	    	}    		
    	    }
    	}
    	
    	
    	return false;
    }
    
    public void editDesignerItems(List<HitDefinition> items, ScreenVector pos)
    {
    	if(this.diagramDesignerEvents == null)
    	{
    		return;
    	}
    	
    	if(items.size() > 1)
    	{
    		// editing multiple items not yet supported
    	}
    	else if(items.size() == 1)
    	{
    		if(items.get(0).isOnNode())
    		{
		    	List<Integer> nodeList = new ArrayList<Integer>();
		    	for (HitDefinition hitResult : items) {
		    		nodeList.add(hitResult.nodeId);
		    	}
    	
		    	this.diagramDesignerEvents.OnEditItems(nodeList, getDesignerPoint(pos, true));
    		}
    		if(items.get(0).isOnConnection())
    		{
    	    	List<Integer> connectionList = new ArrayList<Integer>();
    	    	for (HitDefinition hitResult : items) {
    	    		connectionList.add(hitResult.connectionId);
    	    	}
    	    	
    			List<HitDefinition> hitItems = this.hitTest(pos);
    			HitDefinition connectionPointHit = null;
    			for(HitDefinition hitDefinition : hitItems)
    			{
    				if(hitDefinition.isOnConnectionPoint())
    				{
    					connectionPointHit = hitDefinition;
    				}
    			}
    			boolean targetIsStartConnectable = false;
    			boolean targetIsEndConnectable = false;    			
       			if(connectionPointHit != null)
    			{
    				if(connectionPointHit.connectionPoint.direction == Direction.In
    						|| connectionPointHit.connectionPoint.direction == Direction.InOut)
    				{
    					targetIsEndConnectable = true;
    				}
    				else if(connectionPointHit.connectionPoint.direction == Direction.Out
    						|| connectionPointHit.connectionPoint.direction == Direction.InOut)
    				{
    					targetIsStartConnectable = true;
    				}
    			}
    			
	        	for (HitDefinition hitResult : items) {
        			DiagramDesignerConnection connection = designerConnectionIndexMap.getDesignerConnection(hitResult.connectionId);
        			if(connectionPointHit == null)
        			{
        				this.diagramDesignerEvents.OnEditConnection(connection.getConnectionId(),
        						-1, -1, -1, -1);
        			}
        			else
        			{
    	        		if(hitResult.connectorArea == ConnectorArea.Start)
    	        		{
    	        			if(targetIsStartConnectable)
    	        			{
    	        				this.diagramDesignerEvents.OnEditConnection(connection.getConnectionId(),
    	        						connectionPointHit.nodeId, getDesignerItem(connectionPointHit.nodeId).getConnectionPointId(connectionPointHit.connectionPoint), 
    	        						-1, -1);
    	        			}
    	        			else
    	        			{
    	        				this.diagramDesignerEvents.OnEditConnection(connection.getConnectionId(),
    	        						-1, -1, 
    	        						-1, -1);
    	        			}
    	        		}
    	        		else if(hitResult.connectorArea == ConnectorArea.End)
    	        		{
    	        			if(targetIsEndConnectable)
    	        			{
    	        				this.diagramDesignerEvents.OnEditConnection(connection.getConnectionId(),
    	        						-1, -1, 
    	        						connectionPointHit.nodeId, getDesignerItem(connectionPointHit.nodeId).getConnectionPointId(connectionPointHit.connectionPoint));
	   	        			}
    	        			else
    	        			{
    	        				this.diagramDesignerEvents.OnEditConnection(connection.getConnectionId(),
    	        						-1, -1, 
    	        						-1, -1);
    	        			}
    	        		}
        			}
	        	}    		
    		}
    	}
    }
    
    public DiagramDesignerItem getDesignerItem(int nodeIndex)
    {
    	return designerItemNodeIndexMap.getDesignerItem(nodeIndex);
    }
    
    private Set<DiagramDesignerItem> getDesignerItems()
    {
		if(!designerItemMapIsValid)
		{
	    	designerItemNodeIndexMap.clear();
    		for(int itemIndex = 0; itemIndex < adapter.getNodeCount(); itemIndex++)
    		{
    			DiagramDesignerItem item = adapter.getNode(itemIndex);
				// register the node: it's index will be used as an id in for example hittesting
    			item.Register(itemIndex);
    			
    			for(int connectionPointIndex = 0; connectionPointIndex < adapter.getNodeConnectionPointCount(itemIndex); connectionPointIndex++)
    			{
    				ConnectionPoint connectionPoint = adapter.getNodeConnectionPoint(itemIndex, connectionPointIndex);
    				item.registerConnectionPoint(connectionPoint, connectionPointIndex);
    			}
    			
    			designerItemNodeIndexMap.putDiagramDesignerItem(item, itemIndex);
    		}
        	
    		if(selectionAdorner != null)
    		{
    			selectionAdorner = new DiagramDesignerSelectionAdorner(this.getContext(), this);
    			selectionAdorner.attach(currentSelection);
    		}
	    	
    		designerItemMapIsValid = true;
		}
		
		return designerItemNodeIndexMap.getDesignerItems();
    }
    
    public DiagramDesignerConnection getDesignerConnection(int connectionIndex)
    {
    	return designerConnectionIndexMap.getDesignerConnection(connectionIndex);
    }
    
    private Set<DiagramDesignerConnection> getDesignerConnections()
    {
		if(!designerConnectionMapIsValid)
		{
			designerConnectionIndexMap.clear();
    		for(int connectionIndex = 0; connectionIndex < adapter.getConnectionCount(); connectionIndex++)
    		{
    			DiagramDesignerConnection designerConnection = adapter.getConnection(connectionIndex);
    			designerConnection.Register(connectionIndex);
    			
    			designerConnection.RegisterDiagramDesignerItems(
    					designerItemNodeIndexMap.getDesignerItem(designerConnection.getStartConnectionPoint().getDesignerItemId()),
    					designerItemNodeIndexMap.getDesignerItem(designerConnection.getEndConnectionPoint().getDesignerItemId())
    			);
    			designerConnectionIndexMap.putDiagramDesignerConnection(designerConnection, connectionIndex);
    		}

    		designerConnectionMapIsValid = true;
		}
		
		return designerConnectionIndexMap.getDesignerConnections();
    }
    
    private Matrix getTransformationMatrix()
    {
    	Matrix matrix = new Matrix();
    	matrix.postScale(zoomFactor, zoomFactor, this.getWidth() / 2, this.getHeight() / 2);
    	matrix.postTranslate(-1 * panning.x, -1 * panning.y);
    	
    	return matrix;   	
    }
    
    private Matrix getScalingMatrix()
    {
    	Matrix matrix = new Matrix();
    	matrix.postScale(zoomFactor, zoomFactor);
    	
    	return matrix;   	
    }
	
	private enum ShowContextMenuMode
	{
		None,
		Designer,
		SelectedItem, SelectedConnection
	}
    
    private enum ViewMode
    {
    	Normal,
    	MultiSelect
    }
    
    private int addItemRequestId = 0;
    private IRequestHandlerCallback addItemRequestHandlerCallback = null;
    
    TouchHandler touchHandler;
    
	String command;
    
    private WorldVectorMetric selectionWindow = new WorldVectorMetric(2, 0);
    List<HitDefinition> currentSelection;
    
    private ViewMode viewMode = ViewMode.Normal;
    private ShowContextMenuMode contextMenuMode = ShowContextMenuMode.None;
    
    private ScreenVector panning = new ScreenVector(0, 0);
    private float zoomFactor = 1.0f;

    private boolean designerItemMapIsValid = false;
    private DiagramDesignerItem2NodeIndexMap designerItemNodeIndexMap = new DiagramDesignerItem2NodeIndexMap();
    private boolean designerConnectionMapIsValid = false;
    private DiagramDesignerConnection2ConnectionIndexMap designerConnectionIndexMap = new DiagramDesignerConnection2ConnectionIndexMap();
    private List<DiagramDesignerConnection> designerConnectionList = new ArrayList<DiagramDesignerConnection>();
    private int initialCommandPosX = 10;
    private int initialCommandPosY = 10;
    private List<CommandWidget> designerCommandList = new ArrayList<CommandWidget>();
    private List<CommandWidget> designerItemCommandList = new ArrayList<CommandWidget>();
    private List<CommandWidget> designerConnectionCommandList = new ArrayList<CommandWidget>();
    
    private DiagramDesignerConnectionEditor connectionEditor;
    
    private DiagramDesignerSelectionAdorner selectionAdorner;
    private DiagramDesignerSelectionAdorner adorner;
    
    private Class<?> selectDesigerItemListActivityClass;
    
    private IDiagramDesignerEvents diagramDesignerEvents;
    private GraphAdapter adapter;
    
}