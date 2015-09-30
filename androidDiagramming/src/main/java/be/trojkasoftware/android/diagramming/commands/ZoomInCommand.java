package be.trojkasoftware.android.diagramming.commands;

import be.trojkasoftware.android.diagramming.DiagramDesignerView;

public class ZoomInCommand extends DiagramCommand {

	public ZoomInCommand(DiagramDesignerView view)
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
		getDesignerView().setZooming(currentZoom * defaultZoomIn);
	}
	
	float defaultZoomIn = 1.1f;
}
