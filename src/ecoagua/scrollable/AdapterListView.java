package ecoagua.scrollable;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class AdapterListView<T> extends BaseAdapter{
	private List<T> itens;
	private LayoutInflater mInflater;

    public AdapterListView(Context context, List<T> itens, ListView lv) {
        //Itens do listview
        this.itens = itens;
        //Objeto responsável por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
        ListViewUtils.setListViewHeightBasedOnChildren(lv);
		scroll(lv);
	}

	private void scroll(ListView lv) {
		lv.setOnTouchListener(new OnTouchListener() {
			// Setting on Touch Listener for handling the touch inside
			// ScrollView
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// Disallow the touch request for parent scroll on touch of
				// child view
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});

	}
    
	public List<T> getItens() {
		return itens;
	}

	public LayoutInflater getmInflater() {
		return mInflater;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itens.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return itens.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
