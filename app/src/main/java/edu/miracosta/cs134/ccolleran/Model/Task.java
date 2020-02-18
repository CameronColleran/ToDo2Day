package edu.miracosta.cs134.ccolleran.Model;

public class Task
{
    private int mId;
    private String mDescription;
    private boolean mIsDone;

    public Task(int id, String description, boolean isDone)
    {
        mId = id;
        mDescription = description;
        mIsDone = isDone;
    }

    public Task(String description, boolean isDone)
    {
        this(-1, description, isDone); // using previous constructor
    }

    public Task(String mDescription)
    {
        this(-1, mDescription, false);
    }

    public int getmId()
    {
        return mId;
    }

    public void setmId(int mId)
    {
        this.mId = mId;
    }

    public String getmDescription()
    {
        return mDescription;
    }

    public void setmDescription(String mDescription)
    {
        this.mDescription = mDescription;
    }

    public boolean ismIsDone()
    {
        return mIsDone;
    }

    public void setmIsDone(boolean mIsDone)
    {
        this.mIsDone = mIsDone;
    }

    @Override
    public String toString() {
        return "Task{" +
                "mId=" + mId +
                ", mDescription='" + mDescription + '\'' +
                ", mIsDone=" + mIsDone +
                '}';
    }
}
