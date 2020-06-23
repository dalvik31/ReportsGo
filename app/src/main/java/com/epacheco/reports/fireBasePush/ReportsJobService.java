package com.epacheco.reports.fireBasePush;

import com.firebase.jobdispatcher.JobService;
import android.util.Log;

public class ReportsJobService  extends JobService {
  private static final String TAG = "MyJobService";


  @Override
  public boolean onStartJob(com.firebase.jobdispatcher.JobParameters job) {
    Log.d(TAG, "Performing long running task in scheduled job");
    // TODO(developer): add long running task here.
    return false;
  }

  @Override
  public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
    return false;
  }
}
