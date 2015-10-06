package be.trojkasoftware.android.diagramming.commands.widgets;

import be.trojkasoftware.android.diagramming.CommandWidget;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.commands.ZoomOutCommand;


public class ZoomOutCommandWidget extends CommandWidget {
	
	public ZoomOutCommandWidget(DiagramDesignerView view) {
		super(view);
		
		this.setCommand(new ZoomOutCommand(view));
	}

	protected String getDrawableResourceName()
	{
		return "zoom_out";
	}

}
