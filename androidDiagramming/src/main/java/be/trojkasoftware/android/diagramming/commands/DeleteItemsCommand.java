package be.trojkasoftware.android.diagramming.commands;

import java.util.List;

import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.HitDefinition;

public class DeleteItemsCommand extends DiagramCommand implements IActOnSelection {

	public DeleteItemsCommand(DiagramDesignerView view)
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
		getDesignerView().deleteDesignerItems(selectedItems);
	}

	@Override
	public void setSelection(List<HitDefinition> selection) {
		this.selectedItems = selection;		
	}
	
	List<HitDefinition> selectedItems = null;
}
