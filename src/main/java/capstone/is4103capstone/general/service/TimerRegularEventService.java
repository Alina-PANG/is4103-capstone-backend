package capstone.is4103capstone.general.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TimerRegularEventService {

    //TODO:
    @PostConstruct
    public void createTimerEvents(){
        System.out.println("Run the daily backend checking");
    }
}
