package libraries.android;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

/** use this class to track activities in the foreground by adding an activity to the list
 * when it resumes and removing it when it is destroyed, such that
 *  */
public class ActivityStatus {
    private static final Map<Class<? extends Activity>, Status> activityStatus = new HashMap<>();

    synchronized public static void setToResumed(Class<? extends Activity> activityClass) {
        activityStatus.put(activityClass, Status.RESUMED);
    }
    synchronized public static void setToStopped(Class<? extends Activity> activityClass) {
        activityStatus.put(activityClass, Status.STOPPED);
    }
    synchronized public static void setToPaused(Class<? extends Activity> activityClass) {
        activityStatus.put(activityClass, Status.PAUSED);
    }
    synchronized public static void setToDestroyed(Class<? extends Activity> activityClass) {
        activityStatus.remove(activityClass);
    }


    synchronized public static Status get(Class<? extends Activity> activityClass) {
        Status status = activityStatus.get(activityClass);
        return status == null ? Status.DESTROYED : status;
    }

    //checks if a particular activity is in the foreground
    synchronized public static boolean isForeground(Class<? extends Activity> activityClass) {
        return isResumed(activityClass);
    }

    synchronized public static boolean isNotForeground(Class<? extends Activity> activityClass) {
        return !isForeground(activityClass);
    }

    //checks if any activity is in the foreground
    synchronized public static boolean isForeground() {

        for(Class<? extends Activity> activityClass : activityStatus.keySet()){
            if(isResumed(activityClass)){
                return true;
            }
        }
        return false;
    }

    synchronized public static boolean isBackground(){
        return !isForeground();
    }


    synchronized public static boolean isResumed(Class<? extends Activity> activityClass) {
        return get(activityClass) == Status.RESUMED;
    }

    synchronized public static boolean isNotResumed(Class<? extends Activity> activityClass) {
        return get(activityClass) != Status.RESUMED;
    }

    synchronized public static boolean isStopped(Class<? extends Activity> activityClass) {
        return get(activityClass) == Status.STOPPED;
    }

    synchronized public static boolean isNotStopped(Class<? extends Activity> activityClass) {
        return get(activityClass) != Status.STOPPED;
    }

    synchronized public static boolean isDestroyed(Class<? extends Activity> activityClass) {
        return get(activityClass) == Status.DESTROYED;
    }

    synchronized public static boolean isNotDestroyed(Class<? extends Activity> activityClass) {
        return get(activityClass) != Status.DESTROYED;
    }

    synchronized public static boolean isPaused(Class<? extends Activity> activityClass) {
        return get(activityClass) == Status.PAUSED;
    }

    synchronized public static boolean isNotPaused(Class<? extends Activity> activityClass) {
        return get(activityClass) != Status.PAUSED;
    }

    public enum Status {
        RESUMED, STOPPED, DESTROYED, PAUSED
    }
}



//we opted for this implementation bse it seems the stop lifecycle event
//is called after starting another activity
//which could cause bugs in the sense that
//if first activity is started, and a second activity lauchned from the first,
//the sequence of Events that will happen is:-
//start 1 -> start 2 -> stop 1
//this means than we end in a state of 'false'
//which can wrongly indicate that our app is in background.
//by tracking the foreground state of each activity separately,
//an activity going in background will not erase the data of other activities
//so to check if the app is in the foreground,
//we just have to check that there exists an activity
//that is in the foreground
//if all activities are in the background,
//then it will return false.