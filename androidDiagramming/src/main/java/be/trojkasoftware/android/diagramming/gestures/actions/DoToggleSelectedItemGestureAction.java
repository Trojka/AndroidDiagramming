package be.trojkasoftware.android.diagramming.gestures.actions;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.HitDefinition;

import be.trojkasoftware.android.gestures.GestureActionBase;
import be.trojkasoftware.android.gestures.GestureEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class DoToggleSelectedItemGestureAction  extends GestureActionBase<DiagramDesignerView> {
	
	public final static String CURRENT_SELECTITEM = "CURRENT_SELECTITEM";

	public DoToggleSelectedItemGestureAction(DiagramDesignerView view, String message) {
		super(view);
		
		this.message = message;
	}
	
	@Override
	public void executeAction(GestureEvent motion, TouchGesture gesture) {
		toggleSelection(getTouchedView(), motion, gesture, this.message);
	}
	
	public static void toggleSelection(DiagramDesignerView touchedView, GestureEvent motion, TouchGesture gesture, String message)
	{
		List<HitDefinition> hitResult = new ArrayList<HitDefinition>();
		if(gesture.contextExists(DoSaveSelectionAndSelectFirstAction.CURRENT_SELECTION))
		{
			hitResult = (List<HitDefinition>)gesture.getContext(DoSaveSelectionAndSelectFirstAction.CURRENT_SELECTION);
		}
		int numberOfSelectedItems = hitResult.size();
		Log.i("DoToggleSelectedItem", "get numberOfSelectedItems:" + numberOfSelectedItems);
		
		if(numberOfSelectedItems == 0)
		{
			return;
		}

		int currentSelectedItemmIndex = 0;
		if(!gesture.contextExists(DoToggleSelectedItemGestureAction.CURRENT_SELECTITEM))
		{
			gesture.addContext(DoToggleSelectedItemGestureAction.CURRENT_SELECTITEM, currentSelectedItemmIndex);
		}
		
		currentSelectedItemmIndex = (Integer)gesture.getContext(DoToggleSelectedItemGestureAction.CURRENT_SELECTITEM);
		touchedView.setSelection(hitResult.subList(currentSelectedItemmIndex, currentSelectedItemmIndex+1));
		touchedView.invalidate();
		
		Log.i("DoToggleSelectedItem", "get currentSelectedItemmIndex:" + currentSelectedItemmIndex);
		
		currentSelectedItemmIndex++;
		if(currentSelectedItemmIndex >= numberOfSelectedItems)
			currentSelectedItemmIndex = 0;
		Log.i("DoToggleSelectedItem", "set currentSelectedItemmIndex:" + currentSelectedItemmIndex);
		gesture.setContext(DoToggleSelectedItemGestureAction.CURRENT_SELECTITEM, currentSelectedItemmIndex);
	}
	
	String message;
}
