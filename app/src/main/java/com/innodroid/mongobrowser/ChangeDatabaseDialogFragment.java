package com.innodroid.mongobrowser;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ChangeDatabaseDialogFragment extends DialogFragment {
	private ListView mDatabaseListView;

	private static String ARG_DATABASES = "databases";
	
	public interface Callbacks {
		void onChangeDatabase(String name);
	}
	
	public ChangeDatabaseDialogFragment() {
		super();
	}
	
    static ChangeDatabaseDialogFragment create(ArrayList<String> databases) {
    	ChangeDatabaseDialogFragment fragment = new ChangeDatabaseDialogFragment();
    	Bundle args = new Bundle();
    	args.putStringArrayList(ARG_DATABASES, databases);
    	fragment.setArguments(args);
    	return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_change_database, null);

    	mDatabaseListView = (ListView)view.findViewById(R.id.change_database_list);
    	ArrayList<String> databases = getArguments().getStringArrayList(ARG_DATABASES);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, databases);        
    	mDatabaseListView.setAdapter(adapter);
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
	        .setIcon(android.R.drawable.ic_menu_rotate)
	        .setView(view)
	        .setTitle(R.string.title_change_database)
	        .setNegativeButton(android.R.string.cancel,
	            new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {
	                	dialog.dismiss();
	                }
	            }
	        );

    	final Dialog dialog = builder.create();   	
    	
    	mDatabaseListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				((Callbacks)getTargetFragment()).onChangeDatabase(adapter.getItem(position));
				dialog.dismiss();
			}    		
    	});
    	
    	return dialog;
    }
}
