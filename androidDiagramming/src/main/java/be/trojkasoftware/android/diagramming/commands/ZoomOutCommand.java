package be.trojkasoftware.android.diagramming.commands;

import be.trojkasoftware.android.diagramming.DiagramDesignerView;

public class ZoomOutCommand extends DiagramCommand {

	public ZoomOutCommand(DiagramDesignerView view)
	{
		super(view);
	}

	@Override
	public String getCommandId() {
		return null;
	}
	
	public void Execute()
	{
		float currentZoom = getDesignerView().getZooming();
		getDesignerView().setZooming(currentZoom * defaultZoomOut);
	}
	
	float defaultZoomOut = 0.9f;
}
