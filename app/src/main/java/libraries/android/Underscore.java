package libraries.android;


import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

public class Underscore {

    public static <T> List<T> map(List<T> sourceList, Mapper<T> mapper){
        //sanity checks out of the way
        if(sourceList == null){
            return sourceList;
        }
        //the real code
        List<T> destList = new ArrayList<>();
        for(T row : sourceList){
            destList.add(mapper.map(row));
        }
        return destList;
    }

    public interface Mapper<T>{
        T map(T row);
    }

    public interface Function<InputType,OutputType>{
        OutputType run(InputType input);
    }

    public static abstract class WrapperFunction<InputType,OutputType>{
        public abstract OutputType run(Function<InputType,OutputType> input);
    }



    /** e.g during data validation on the UI, u want to sense when user has stopped changing
     * the input so u can automatically call the validator based on time since input last changed
     * being greater than the minimum threshold before the validator can kick in.
     * So when u try to start the validator and the input is still changing
     * we know the input is still changing bse it changed recently,
     * and we know it changed recently because the duration of time since last change
     * is less than the duration of time before the validator can kick in
     * ie. kickin if duration since last change > a certain number [it has been a while]
     * e.g it has been 3 days and the validator is set to kick in
     * ** we can customize the conditions before the validator kicks in to be a function
     * to compute whether the validator function shd kick in.
     * e.g. imagine a validator that runs if it is over 3 days since something was posted
     * e.g It has been: 3 days since you last posted something
     *
     * - whenever u call the function, you are just making an attempt to call the function or validator
     * -but u can decide what happens if the input has changed recently
     * -When was the validator last run
     * -How long has it been
     * -Has it been long enough
     * -shd the validator kick in
     * -how long to wait
     *
     * ** also useful for preventing accidental double click.
     *
     * There ar many strategies for addressing issue of accidental double click
     * a) ignore the call
     * b) dosomething else
     * c) accept the click
     */

    /** this function can handle delaying, throtttling, run once, run after NCalls, runBeforeNCalls, returning previous result if exists, etc */
    public static abstract class FunctionBaseClass<InputType, ResultType>{
        private ResultType previousResult;
        private long timestampLastCalled;
        private int totalTimesCalled;
        synchronized public final void run(InputType inputType){

            doRun(inputType);
            timestampLastCalled = System.currentTimeMillis();
            totalTimesCalled += 1;

        }

        protected final ResultType previousResult() {
            return previousResult;
        }

        protected final void setPreviousResult(ResultType previousResult) {
            this.previousResult = previousResult;
        }

        public final long timestampLastCalled() {
            return timestampLastCalled;
        }

        public final int totalTimesCalled() {
            return totalTimesCalled;
        }

        protected final long timeSinceLastCall(){
            return System.currentTimeMillis() - timestampLastCalled;
        }

        /** u can check for some conditions and decide what to do e.g ignoreCall, postPoneCall, etc */
        protected abstract void doRun(InputType inputType);

        /** does nothing. Just an expressive way to say something insidee doRun e.g if timeSinceLastCall < 5, ingoreCall(); */
        protected final void ignoreCall(){
        }

        protected final void postPoneCall(long forDurationInMillis){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            }, forDurationInMillis);
        }

        /** just a facny wayt to say postpone, to illustrate than doRun() will be called again */
        protected final void comeBackAfter(long forDurationInMillis){
            postPoneCall(forDurationInMillis);
        }

    }

    /** just added this to handle cases where stored result is Void */
    public static abstract class FunctionRunsOnCondition<InputType> extends FunctionBaseClass<InputType, Void>{

    }

    public static abstract class FunctionRunsOnce<InputType> extends FunctionRunsOnCondition<InputType> {

        @Override
        protected final void doRun(InputType inputType) {
            if(totalTimesCalled() < 1){
                onFirstTime(inputType);
            }
            else{
                onSubsequentCalls(inputType);
            }
        }

        protected abstract void onFirstTime(InputType inputType);
        protected abstract void onSubsequentCalls(InputType inputType);
    }

    public static abstract class FunctionRunsDifferentlyBeforeAndAfterNCalls<InputType> extends FunctionRunsOnCondition<InputType> {

        @Override
        protected final void doRun(InputType inputType) {
            if(totalTimesCalled() >= thresholdNumOfCalls()){
                onReachThreshold(inputType);
            }
            else{
                beforeReachThreshold(inputType);
            }
        }



        protected abstract int thresholdNumOfCalls();
        protected abstract void beforeReachThreshold(InputType inputType);
        protected abstract void onReachThreshold(InputType inputType);
    }
}

