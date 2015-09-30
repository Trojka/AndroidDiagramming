package be.trojkasoftware.android.diagramming.gestures.actions;

import java.util.List;

import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.HitDefinition;
import be.trojkasoftware.android.diagramming.commands.IActOnSelection;

import be.trojkasoftware.android.gestures.GestureActionBase;
import be.trojkasoftware.android.gestures.GestureEvent;
import be.trojkasoftware.android.gestures.TouchGesture;

public class DoSelectedCommandGestureAction extends GestureActionBase<DiagramDesignerView> {

	public DoSelectedCommandGestureAction(DiagramDesignerView view) {
		super(view);
	}
	
	@Override
	public void executeAction(GestureEvent motion, TouchGesture gesture) {
		List<HitDefinition> hitResult = getTouchedView().hitTest(motion.getPosition());
		if( hitResult.size() == 1 && hitResult.get(0).command != null)
		{
			if(hitResult.get(0).command.getCommand() instanceof IActOnSelection)
			{
				if(getTouchedView().getSelection() == null || getTouchedView().getSelection().size() == 0 )
				{
					return;
				}
				
				IActOnSelection command = (IActOnSelection)hitResult.get(0).command.getCommand();
				command.setSelection(getTouchedView().getSelection());
			}
			hitResult.get(0).command.getCommand().Execute();
		}

	}
}
