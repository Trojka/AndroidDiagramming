package be.trojkasoftware.android.diagramming.commands.widgets;

import be.trojkasoftware.android.diagramming.CommandWidget;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.commands.ZoomInCommand;


public class ZoomInCommandWidget extends CommandWidget {
	
	public ZoomInCommandWidget(DiagramDesignerView view) {
		super(view);
		
		this.setCommand(new ZoomInCommand(view));
	}

	protected String getDrawableResourceName()
	{
		return "zoom_in";
	}

}
