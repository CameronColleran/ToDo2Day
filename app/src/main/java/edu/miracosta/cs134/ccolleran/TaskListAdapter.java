package edu.miracosta.cs134.ccolleran;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import edu.miracosta.cs134.ccolleran.Model.Task;

public class TaskListAdapter extends ArrayAdapter
{
    private Context mContext;
    private int mResource;
    private List<Task> mAllTasks;


    public TaskListAdapter(@NonNull Context context, int resource, @NonNull List objects)
    {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        mAllTasks = objects;
    }

    // Override (ctrl + o) a method called getView

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResource, null);

        CheckBox isDoneCheckBox = view.findViewById(R.id.isDoneCheckBox);

        Task selectedTask = mAllTasks.get(position);

        isDoneCheckBox.setChecked(selectedTask.ismIsDone()); // mark check mark done if marked as done in database
        isDoneCheckBox.setText(selectedTask.getmDescription()); // set text next to check box


        return view;
    }
}
