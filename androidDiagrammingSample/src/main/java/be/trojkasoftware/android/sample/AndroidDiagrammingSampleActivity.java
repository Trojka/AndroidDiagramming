package be.trojkasoftware.android.sample;

import java.util.List;

import be.trojkasoftware.android.diagramming.DesignerVector;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.DiagramDesignerView.IDiagramDesignerEvents;
import be.trojkasoftware.android.sample.diagramming.sampledata.CommandPanDownWidget;
import be.trojkasoftware.android.sample.diagramming.sampledata.CommandPanUpWidget;
import be.trojkasoftware.android.sample.diagramming.sampledata.CustomGraphDataStructure;
import be.trojkasoftware.android.sample.diagramming.sampledata.NodeType;
import be.trojkasoftware.android.sample.diagramming.sampledata.CustomGraphDataStructure.Connection;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

public class AndroidDiagrammingSampleActivity extends Activity implements IDiagramDesignerEvents {
	
    private DiagramDesignerView view;
    private CustomGraphDataStructure data;
    private CustomGraphAdapter adapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        view = new DiagramDesignerView(this, SelectDesigerItemListActivity.class);
        view.setBackgroundColor(Color.argb(255, 255, 255, 255));
        view.addCommand(new CommandPanUpWidget(view));
        view.addCommand(new CommandPanDownWidget(view));

        view.setDiagramDesignerEventHandler(this);

        data = new CustomGraphDataStructure();

        adapter = new CustomGraphAdapter(data, this);
        
        view.setAdapter(adapter);
        
        setContentView(view);

    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
    	view.HandleRequestResult(requestCode, resultCode, intent);
    }
    
	@Override
	public void OnAddItem(Object item, DesignerVector newPoint) {
		NodeType newNode = new NodeType(item.toString(), newPoint.x, newPoint.y);
		
		data.AddNode(newNode);
		
		adapter.notifyDataSetChanged();
	}

	@Override
	public void OnEditItems(List<Integer> nodeList, DesignerVector newPoint) {
		
		for(int nodeId : nodeList)
		{
			NodeType node = (NodeType)adapter.getItem(nodeId);
			node.setPos(newPoint.x, newPoint.y);
		}
		
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean OnEditingItems(List<Integer> nodeList, DesignerVector newPoint) {
		return true;
	}

	@Override
	public void OnDeleteItems(List<Integer> nodeList) {
		
		for(int nodeId : nodeList)
		{
			NodeType node = (NodeType)adapter.getItem(nodeId);
			data.RemoveNode(node);
		}
		
		adapter.notifyDataSetChanged();
		
	}

	@Override
	public boolean OnAddingConnection(Integer sourceNodeId, Integer sourceConnectionPointId, Integer targetNodeId,
			Integer targetConnectionPointId) {
		return true;
	}

	@Override
	public void OnAddConnection(Integer sourceNodeId, Integer sourceConnectionPointId, Integer targetNodeId,
			Integer targetConnectionPointId) {
		
		data.AddConnection(
				(NodeType)adapter.getItem(sourceNodeId), sourceConnectionPointId, 
				(NodeType)adapter.getItem(targetNodeId), targetConnectionPointId
				);
		
		adapter.notifyDataSetChanged();
	}

	@Override
	public void OnDeleteConnections(List<Integer> connectionIdList /*Integer sourceNodeId, Integer sourceConnectionPointId,
			Integer targetNodeId, Integer targetConnectionPointId*/) {
		
		for(int connectionId : connectionIdList)
		{
			Connection connection = (Connection)adapter.getConnectionItem(connectionId);
			data.DeleteConnection(connection
					//(NodeType)adapter.getItem(sourceNodeId), sourceConnectionPointId, 
					//(NodeType)adapter.getItem(targetNodeId), targetConnectionPointId
					);
		}
		
		adapter.notifyDataSetChanged();		
	}

	@Override
	public boolean OnEditingConnections(List<Integer> connectionIdList,
			DesignerVector newPoint) {
		return true;
	}

	@Override
	public void OnEditConnection(int connectionId, 
			int newSourceItemId, int newSourceItemConnectionpointId, 
			int newTargetItemId, int newTargetItemConnectionpointId) {
		Connection connection = (Connection)adapter.getConnectionItem(connectionId);
		if(newSourceItemId >= 0)
		{
			connection.sourceNode = (NodeType)adapter.getItem(newSourceItemId);
			connection.sourcePoint = newSourceItemConnectionpointId;
		}
		else if (newTargetItemId >= 0)
		{
			connection.targetNode = (NodeType)adapter.getItem(newTargetItemId);
			connection.targetPoint = newTargetItemConnectionpointId;
		}
		else
		{
			data.DeleteConnection(connection
					//(NodeType)adapter.getItem(sourceNodeId), sourceConnectionPointId, 
					//(NodeType)adapter.getItem(targetNodeId), targetConnectionPointId
					);
		}
		
		adapter.notifyDataSetChanged();		
	}
}