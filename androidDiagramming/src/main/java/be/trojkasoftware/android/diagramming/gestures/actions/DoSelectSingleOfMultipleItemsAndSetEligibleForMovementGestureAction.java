package be.trojkasoftware.android.diagramming.gestures.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import be.trojkasoftware.android.diagramming.DesignerVector;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.HitDefinition;
import be.trojkasoftware.android.diagramming.adorners.MoveAdorner;

import be.trojkasoftware.android.gestures.GestureActionBase;
import be.trojkasoftware.android.gestures.GestureEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class DoSelectSingleOfMultipleItemsAndSetEligibleForMovementGestureAction extends GestureActionBase<DiagramDesignerView> {

	public DoSelectSingleOfMultipleItemsAndSetEligibleForMovementGestureAction(DiagramDesignerView view) {
		super(view);
	}

	@Override
	public void executeAction(GestureEvent motion, TouchGesture gesture) {
		List<HitDefinition> hitResult = getTouchedView().hitTest(motion.getPosition());
		if( !(hitResult.size() == 1 && hitResult.get(0).nodeId >= 0) )
		{
			return;
		}
		
		@SuppressWarnings("unchecked")
		HashMap<HitDefinition,DesignerVector> selectedItemsOriginalPosition = (HashMap<HitDefinition,DesignerVector>)gesture.getContext("MULTISELECT_ITEMS");
    	for (HitDefinition item : selectedItemsOriginalPosition.keySet()) {
    		if(!item.IsSame(hitResult.get(0))) {
    			DesignerVector originalPosition = selectedItemsOriginalPosition.get(item);
    			getTouchedView().getDesignerItem(item.nodeId).setPosition(originalPosition);
    		}
    		else
    		{
    			getTouchedView().setSelection(hitResult);
    			
    			MoveAdorner moveAdorner = new MoveAdorner(getTouchedView().getContext(), getTouchedView());
    			getTouchedView().setAdorner(moveAdorner, hitResult);
    			
    		}
    	}

	}
}
