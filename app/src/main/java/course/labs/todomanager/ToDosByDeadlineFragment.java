package course.labs.todomanager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;

import course.labs.todomanager.ToDoItem.Priority;
import course.labs.todomanager.ToDoItem.Status;

public class ToDosByDeadlineFragment extends ListFragment{

	private static final int ADD_TODO_ITEM_REQUEST = 0;
	public static final int ADD_TODO_ITEM_SUCCESS = 1;
	private static final String FILE_NAME = "TodoManagerActivityData.txt";
	private static final String TAG = "Lab-UserInterface";

	// IDs for menu items
	private static final int MENU_DELETE = Menu.FIRST;
	private static final int MENU_DUMP = Menu.FIRST + 1;

	ToDoListAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		footerView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent addItemIntent=new Intent(getActivity(),AddToDoActivity.class);
				startActivityForResult(addItemIntent,ADD_TODO_ITEM_REQUEST);
			}
		});
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mAdapter=((ToDoManagerActivity) getActivity()).mAdapter;
		mAdapter.setCurrentSortOrder(ToDoListAdapter.SortOrder.ByDeadline);

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