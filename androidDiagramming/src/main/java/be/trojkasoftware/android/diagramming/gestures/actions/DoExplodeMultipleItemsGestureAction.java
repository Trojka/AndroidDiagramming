package be.trojkasoftware.android.diagramming.gestures.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import be.trojkasoftware.android.diagramming.DesignerVector;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.HitDefinition;

import be.trojkasoftware.android.ScreenVector;
import be.trojkasoftware.android.gestures.GestureActionBase;
import be.trojkasoftware.android.gestures.GestureEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class DoExplodeMultipleItemsGestureAction extends GestureActionBase<DiagramDesignerView> {

	public DoExplodeMultipleItemsGestureAction(DiagramDesignerView view) {
		super(view);
	}

	@Override
	public void executeAction(GestureEvent motion, TouchGesture gesture) {
		List<HitDefinition> hitResult = getTouchedView().hitTest(motion.getPosition());
		List<HitDefinition> selectedItems = new ArrayList<HitDefinition>();
		for(HitDefinition hitItem:hitResult)
		{
			if(hitItem.isOnNode())
			{
				selectedItems.add(hitItem);
			}
		}

		selectedItemsOriginalPosition = new HashMap<HitDefinition,DesignerVector>();
    	int x = 10;
    	int y = 10;
    	int xOffset = 10;
    	for (HitDefinition item : selectedItems) {
    		selectedItemsOriginalPosition.put(item, getTouchedView().getDesignerItem(item.nodeId).getPosition());
    		
    		DesignerVector newPos = getTouchedView().getDesignerPoint(
    				new ScreenVector(x + getTouchedView().getDesignerItem(item.nodeId).getWidth()/2, 
    						y + getTouchedView().getDesignerItem(item.nodeId).getHeight()/2), true);
    		getTouchedView().getDesignerItem(item.nodeId).setPosition(newPos);
    		x = x + xOffset + getTouchedView().getDesignerItem(item.nodeId).getWidth();
    	}
    	
    	gesture.addContext("MULTISELECT_ITEMS", selectedItemsOriginalPosition);
	}
	
	private HashMap<HitDefinition,DesignerVector> selectedItemsOriginalPosition;
}
