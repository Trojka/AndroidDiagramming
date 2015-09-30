package be.trojkasoftware.android.diagramming;

import java.util.EventListener;

public interface IItemMovedEventListener extends EventListener {
    public void handleItemMovedEvent(ItemMovedEvent evt);
}