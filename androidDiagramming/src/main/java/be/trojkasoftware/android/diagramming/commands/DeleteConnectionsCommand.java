package be.trojkasoftware.android.diagramming.commands;

import java.util.List;

import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.HitDefinition;

public class DeleteConnectionsCommand extends DiagramCommand implements IActOnSelection {

	public DeleteConnectionsCommand(DiagramDesignerView view)
	{
		super(view);
	}

	@Override
	public String getCommandId() {
		return null;
	}
	
	public void Execute()
	{
		getDesignerView().clearSelection();
		getDesignerView().deleteDesignerConnections(selectedItems);
	}

	@Override
	public void setSelection(List<HitDefinition> selection) {
		this.selectedItems = selection;		
	}
	
	List<HitDefinition> selectedItems = null;
}
