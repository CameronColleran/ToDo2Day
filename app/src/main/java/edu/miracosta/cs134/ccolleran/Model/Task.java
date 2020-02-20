package edu.miracosta.cs134.ccolleran.Model;

public class Task
{
    private long mId; // database id's are always longs so we can use very large numbers
    private String mDescription;
    private boolean mIsDone;

    public Task(long id, String description, boolean isDone)
    {
        mId = id;
        mDescription = description;
        mIsDone = isDone;
    }

    public Task(String description, boolean isDone)
    {
        this(-1, description, isDone); // using previous constructor, -1 because database automatically assigns
    }

    public Task(String mDescription)
    {
        this(-1, mDescription, false);
    }

    public long getmId()
    {
        return mId;
    }

    public void setmId(long mId)
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
