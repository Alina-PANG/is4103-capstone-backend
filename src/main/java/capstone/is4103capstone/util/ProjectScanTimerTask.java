package capstone.is4103capstone.util;

import capstone.is4103capstone.finance.requestsMgmt.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.TimerTask;

public class ProjectScanTimerTask extends TimerTask {
    private final static long ONCE_PER_DAY = 1000*60*60*24;
    private final static int TWO_AM = 2;
    private final static int ZERO_MINUTES = 0;

    @Autowired
    ProjectService projectService;

    //TODO:
    @Override
    public void run() {

    }
}
