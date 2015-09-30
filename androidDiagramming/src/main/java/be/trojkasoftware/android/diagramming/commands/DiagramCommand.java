package be.trojkasoftware.android.diagramming.commands;

import be.trojkasoftware.android.diagramming.DiagramDesignerView;

public abstract class DiagramCommand implements ICommand {

	public DiagramCommand(DiagramDesignerView view) {
		diagramDesignerView = view;
	}

	public DiagramDesignerView getDesignerView()
	{
		return diagramDesignerView;
	}

	private DiagramDesignerView diagramDesignerView;
}
