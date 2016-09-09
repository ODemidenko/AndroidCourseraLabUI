package course.labs.todomanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.Inflater;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.view.LayoutInflater;

public class ToDoListAdapter extends BaseAdapter {

	private final List<ToDoItem> mItems = new ArrayList<ToDoItem>();
	private final Context mContext;

	public enum SortOrder{ByDeadline, ByPriorityDeadline}
	private SortOrder currentSortOrder;

	private static final String TAG = "Lab-UserInterface";

	public ToDoListAdapter(Context context) {

		mContext = context;

	}

	// Add a ToDoItem to the adapter
	// Notify observers that the data set has changed

	public void add(ToDoItem item) {

		mItems.add(item);
		sortData();
		notifyDataSetChanged();

	}


	// Clears the list adapter of all items.

	public void clear() {

		mItems.clear();
		notifyDataSetChanged();

	}

	// Returns the number of ToDoItems

	@Override
	public int getCount() {

		return mItems.size();

	}

	// Retrieve the number of ToDoItems

	@Override
	public Object getItem(int pos) {

		return mItems.get(pos);

	}

	// Get the ID for the ToDoItem
	// In this case it's just the position

	@Override
	public long getItemId(int pos) {

		return pos;

	}

	// Create a View for the ToDoItem at specified position
	// Remember to check whether convertView holds an already allocated View
	// before created a new View.
	// Consider using the ViewHolder pattern to make scrolling more efficient
	// See: http://developer.android.com/training/improving-layouts/smooth-scrolling.html
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// TODO - Get the current ToDoItem
		final ToDoItem toDoItem = mItems.get(position);


		// TODO - Inflate the View for this ToDoItem
		// from todo_item.xml
        RelativeLayout itemLayout = null;
        if (convertView==null) {
             itemLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.todo_item, parent, false);
        }
        else {
            itemLayout=(RelativeLayout) convertView;
        }

		itemLayout.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {

				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setMessage(R.string.delete_confirmation)
						.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								mItems.remove(toDoItem);
								notifyDataSetChanged();
							}
						})
						.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// User cancelled the dialog
							}
						});
				AlertDialog dialog= builder.create();
				dialog.show();
				return true;
			}
		});

        if (toDoItem.getStatus()== ToDoItem.Status.DONE) {
            itemLayout.setBackgroundColor(Color.GREEN);
        }
        else {
            itemLayout.setBackgroundColor(Color.TRANSPARENT);
        }

        // Fill in specific ToDoItem data
		// Remember that the data that goes in this View
		// corresponds to the user interface elements defined
		// in the layout file

		// TODO - Display Title in TextView
		final TextView titleView = (TextView) itemLayout.findViewById(R.id.titleView);
        titleView.setText(toDoItem.getTitle());

		// TODO - Set up Status CheckBox
		final CheckBox statusView = (CheckBox) itemLayout.findViewById(R.id.statusCheckBox);
        statusView.setChecked(toDoItem.getStatus()==ToDoItem.Status.DONE);

		// TODO - Must also set up an OnCheckedChangeListener,
		// which is called when the user toggles the status checkbox

		statusView.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
                        toDoItem.setStatus(isChecked? ToDoItem.Status.DONE: ToDoItem.Status.NOTDONE);
                        notifyDataSetChanged();
					}
				});

		// TODO - Display Priority in a TextView
//		final TextView priorityView = (TextView) itemLayout.findViewById(R.id.priorityView);
//        priorityView.setText(toDoItem.getPriority().toString().toUpperCase());

        final Spinner spinnerSelector = (Spinner) itemLayout.findViewById(R.id.prioritySpinner);
        ArrayAdapter<ToDoItem.Priority> adapter = new ArrayAdapter<ToDoItem.Priority>(mContext, android.R.layout.select_dialog_singlechoice, ToDoItem.Priority.values());
        //That is to see the dropdown list view
        //adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinnerSelector.setAdapter(adapter);



        spinnerSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                toDoItem.setPriority((ToDoItem.Priority) adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // TODO - Display Time and Date.
		// Hint - use ToDoItem.FORMAT.format(toDoItem.getDate()) to get date and
		// time String
		final TextView dateView = (TextView) itemLayout.findViewById(R.id.dateView);
        dateView.setText(ToDoItem.FORMAT.format(toDoItem.getDate()));

		// Return the View you just created
		return itemLayout;

	}

	private void sortData() {
		switch (currentSortOrder) {
			case ByDeadline:
				Collections.sort(mItems, new ToDoItem.DeadlineComparator());
				break;
			case ByPriorityDeadline:
				Collections.sort(mItems, new ToDoItem.PriorityDeadlineComparator());
				break;
		}
	}

	public void setCurrentSortOrder(SortOrder order) {
		currentSortOrder=order;
		sortData();
	}

}
