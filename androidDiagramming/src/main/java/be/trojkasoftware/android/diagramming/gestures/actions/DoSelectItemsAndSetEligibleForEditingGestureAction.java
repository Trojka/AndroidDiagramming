package be.trojkasoftware.android.diagramming.gestures.actions;

import java.util.List;

import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.HitDefinition;
import be.trojkasoftware.android.diagramming.adorners.MoveAdorner;

import be.trojkasoftware.android.gestures.GestureActionBase;
import be.trojkasoftware.android.gestures.GestureEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class DoSelectItemsAndSetEligibleForEditingGestureAction extends GestureActionBase<DiagramDesignerView> {

	public DoSelectItemsAndSetEligibleForEditingGestureAction(DiagramDesignerView view) {
		super(view);
	}
	
	@Override
	public void executeAction(GestureEvent motion, TouchGesture gesture) {
		List<HitDefinition> hitResult = getTouchedView().hitTest(motion.getPosition());
		
		getTouchedView().setSelection(hitResult);
	}
}
