package edu.miracosta.cs134.ccolleran;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import edu.miracosta.cs134.ccolleran.Model.DBHelper;
import edu.miracosta.cs134.ccolleran.Model.Task;

public class MainActivity extends AppCompatActivity
{
    // Create a reference to the database
    private DBHelper mDB;
    private List<Task> mAllTasks;
    private EditText descriptionEditText;

    private ListView taskListView;
    private TaskListAdapter mTaskListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Wire up the views
        descriptionEditText = findViewById(R.id.taskEditText);
        taskListView = findViewById(R.id.taskListView);

        mDB = new DBHelper(this);
        //mDB.clearAllTasks();

        mAllTasks = mDB.getAllTasks();

        // Instantiate the ListAdapter
        mTaskListAdapter = new TaskListAdapter(this, R.layout.task_item, mAllTasks);
        // Connect the ListView with the ListAdapter
        taskListView.setAdapter(mTaskListAdapter);


        // Let's loop through them and print them to the log (deprecated)

        for (Task task: mAllTasks)
        {
            Log.i("ToDo2Day", task.toString());
        }


        // Let's create some dummy data and add it to the database (deprecated)
        /*
        mDB.addTask(new Task("Wash the dishes"));
        mDB.addTask(new Task("Iron pants"));
        mDB.addTask(new Task("Gradle projects"));
        mDB.addTask(new Task("Shave"));
        mDB.addTask(new Task("Finish thing"));
         */

    }


    public void addTask(View v)
    {
        if (taskListView.getAdapter() == null) // if adapter has been set to null from clearAllTasks
        {
            taskListView.setAdapter(mTaskListAdapter);
        }

        // Extract description from the EditText
        String description = descriptionEditText.getText().toString();

        // id = -1, description is whatever user typed, and isDone = false
        Task newTask = new Task(description);

        // Adding task to the database and getting the newly generated id (primary key)
        long id = mDB.addTask(newTask);
        newTask.setmId(id);

        // Also add the new task to the list
        mAllTasks.add(newTask);

        mTaskListAdapter.notifyDataSetChanged(); // Updating list view/adapter

        descriptionEditText.setText(""); // setting text back to nothing for easier use

    }

    public void clearAllTasks(View v)
    {
        mAllTasks.clear(); // clearing list

        mDB.clearAllTasks(); // clearing sql database
        
        taskListView.setAdapter(null); // clearing taskListView
    }


    public void changeTaskStatus(View v)
    {

        CheckBox isDoneCheckBox = findViewById(R.id.isDoneCheckBox);

        Task clickedTask = (Task) v.getTag(); // extracting the tag

        if (isDoneCheckBox.isChecked())
            clickedTask.setmIsDone(true);
        else
            clickedTask.setmIsDone(false);
        System.out.println("2 " + clickedTask.toString());

        int index = mAllTasks.indexOf(clickedTask); // updating object in local list
        System.out.println(index);
        mAllTasks.set(index, clickedTask);

        mDB.updateTask(clickedTask); // updating database

    }




    // Ctrl + O

    @Override
    protected void onDestroy() // closing connection to database when app is terminated
    {
        super.onDestroy();
        mDB.close();
    }


}