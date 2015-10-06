package be.trojkasoftware.android.sample.diagramming.sampledata;

import be.trojkasoftware.android.ScreenVector;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;
import be.trojkasoftware.android.diagramming.commands.DiagramCommand;

/**
 * Created by SergeDesmedt on 6/10/2015.
 */
public class CommandPanUp extends DiagramCommand {

    public CommandPanUp(DiagramDesignerView view)
    {
        super(view);
    }
    @Override
    public String getCommandId() {
        return null;
    }

    public void Execute()
    {
        ScreenVector currentPanning = getDesignerView().getPanningOffsetset();
        currentPanning.y = currentPanning.y + defaultPanning;
        getDesignerView().setPanningOffsetset(currentPanning);
    }

    int defaultPanning = 100;
}
