package be.trojkasoftware.android.diagramming.gestures.actions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import be.trojkasoftware.android.diagramming.DesignerVector;
import be.trojkasoftware.android.diagramming.DiagramDesignerItem;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.IRequestHandlerCallback;

import be.trojkasoftware.android.gestures.GestureActionBase;
import be.trojkasoftware.android.gestures.GestureEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class DoAddItemGestureAction extends GestureActionBase<DiagramDesignerView> {

	public DoAddItemGestureAction(DiagramDesignerView view) {
		super(view);
	}
	
	@Override
	public void executeAction(GestureEvent motion, TouchGesture gesture) {
		final DesignerVector newPoint = getTouchedView().getDesignerPoint(motion.getPosition(), true);

		getTouchedView().ShowSelectItemActivity(
				new IRequestHandlerCallback()
				{
					public void HandleRequestResult(int requestCode, int resultCode, Intent intent)
					{

					    Bundle b = intent.getExtras();
					    if(b.containsKey(DiagramDesignerView.ADD_ITEMSID))
					    {
					    	String selectedItemId = b.getString(DiagramDesignerView.ADD_ITEMSID);
					        
					        getTouchedView().addItem(selectedItemId, newPoint); 					
						}
					}
				});
	}
}
