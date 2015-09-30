package be.trojkasoftware.android.diagramming.commands;

import be.trojkasoftware.android.diagramming.DiagramDesignerView;

public class DiagramDesignerCommandHandler {

	public DiagramDesignerCommandHandler(DiagramDesignerView diagramDesignerView)
	{
		this.diagramDesignerView = diagramDesignerView;
	}
	
	public void ExecuteCommand(ICommand command)
	{
		
	}
	
	private DiagramDesignerView diagramDesignerView;
}
