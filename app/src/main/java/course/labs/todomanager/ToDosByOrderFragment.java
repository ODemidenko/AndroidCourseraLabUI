package course.labs.todomanager;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Oleg on 09.09.2016.
 */
public class ToDosByOrderFragment extends ListFragment {
	public static final int ADD_TODO_ITEM_SUCCESS = 1;
	private static final int ADD_TODO_ITEM_REQUEST = 0;
	private static final String FILE_NAME = "TodoManagerActivityData.txt";
	private static final String TAG = "Lab-UserInterface";
	// IDs for menu items
	private static final int MENU_DELETE = Menu.FIRST;
	private static final int MENU_DUMP = Menu.FIRST + 1;
	ToDoListAdapter mAdapter;
	ToDoListAdapter.SortOrder mSortOrder;

	public void setSortOrder(ToDoListAdapter.SortOrder sortOrder) {
		this.mSortOrder=sortOrder;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Put divider between ToDoItems and FooterView
		getListView().setFooterDividersEnabled(true);

		// TODO - Inflate footerView for footer_view.xml file
		TextView footerView = null;
		footerView = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.footer_view,getListView(),false);

		// TODO - Add footerView to ListView
		getListView().addFooterView(footerView);

		// TODO - Attach Listener to FooterView
		footerView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent addItemIntent=new Intent(getActivity(),AddToDoActivity.class);
				startActivityForResult(addItemIntent,ADD_TODO_ITEM_REQUEST);
			}
		});
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d(TAG,"hash:"+ this.hashCode());
		super.onActivityCreated(savedInstanceState);

		mAdapter=((ToDoManagerActivity) getActivity()).mAdapter;
		mAdapter.setCurrentSortOrder(mSortOrder);

		// TODO - Attach the adapter to this ListActivity's ListView
		setListAdapter(mAdapter);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		Log.i(TAG,"Entered onActivityResult()");

		// TODO - Check result code and request code
		// if user submitted a new ToDoItem
		// Create a new ToDoItem from the data Intent
		// and then add it to the adapter
		if (requestCode==ADD_TODO_ITEM_REQUEST
				&&resultCode==ADD_TODO_ITEM_SUCCESS) {
			ToDoItem item=new ToDoItem(data);
			mAdapter.add(item);
		}
	}
}
