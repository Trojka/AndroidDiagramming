package be.trojkasoftware.android.sample;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import be.trojkasoftware.android.diagramming.DiagramDesignerView;


public class SelectDesigerItemListActivity extends ListActivity{

   
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.designeritem_list);

        fillAvailableDesignerItems();
        
        setListAdapter(getListAdapter());
    }
    
    public ListAdapter getListAdapter()
    {
    	if(m_adapter == null)
    	{
    		m_adapter = new AvailableNodeListAdapter(this, R.layout.designeritem_row, m_items);
    	}
    	
    	return m_adapter;
    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		itemClicked = position;
		
		onBackPressed();
	}

	@Override
	public void onBackPressed() {
	    Intent result = new Intent();

	    String itemSelected = m_items.get(itemClicked);
	    Bundle b = new Bundle();
	    b.putString(DiagramDesignerView.ADD_ITEMSID, itemSelected);

	    result.putExtras(b);

	    setResult(Activity.RESULT_OK, result);
	    
	    super.onBackPressed();
	}
	
	public ArrayList<String> fillAvailableDesignerItems()
	{
//        m_items.add(new DiagramDesignerItem(this.getBaseContext()));
//        m_items.add(new OvalItem(this.getBaseContext()));
//        m_items.add(new RoundedRectangularItem(this.getBaseContext()));
		
		m_items.add("A");
		m_items.add("B");
		m_items.add("C");
		
		return m_items;
	}

    private ArrayList<String> m_items = new ArrayList<String>();
    private ListAdapter m_adapter = null;
    
    private int itemClicked;
}
