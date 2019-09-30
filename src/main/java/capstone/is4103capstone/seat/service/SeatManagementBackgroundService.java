package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.entities.helper.DateHelper;
import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatAllocation;
import capstone.is4103capstone.entities.seat.SeatAllocationInactivationLog;
import capstone.is4103capstone.seat.repository.SeatAllocationInactivationLogRepository;
import capstone.is4103capstone.seat.repository.SeatAllocationRepository;
import capstone.is4103capstone.seat.repository.SeatRepository;
import capstone.is4103capstone.util.enums.ScheduleRecurringBasisEnum;
import capstone.is4103capstone.util.enums.SeatAllocationTypeEnum;
import capstone.is4103capstone.util.enums.SeatTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;

@Scope("singleton")
@Component
public class SeatManagementBackgroundService {
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private SeatAllocationRepository seatAllocationRepository;
    @Autowired
    private SeatAllocationInactivationLogRepository seatAllocationInactivationLogRepository;

    // The pattern is a list of six single space-separated fields: representing second, minute, hour, day, month, weekday.
    // Month and weekday names can be given as the first three letters of the English names.
    // Example patterns:
    // "0 0 * * * *" = the top of every hour of every day.
    // "*/10 * * * * *" = every ten seconds.
    // "0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
    // "0 0 6,19 * * *" = 6:00 AM and 7:00 PM every day.
    // "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30, 10:00 and 10:30 every day.
    // "0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays
    // "0 0 0 25 12 ?" = every Christmas Day at midnight
    // Generation tool at: https://www.freeformatter.com/cron-expression-generator-quartz.html
    // @Scheduled(cron = "* */5 9-18 * * MON-FRI")
    @Scheduled(fixedRate = 5000)
    private void inactivateAllocations() {

        System.out.println("******************** Seat Management Background Service: inactivate allocations ********************");
        List<SeatAllocationInactivationLog> allocationsToInactivate = seatAllocationInactivationLogRepository.getLogsByCurrentTime(new Date());
        for (SeatAllocationInactivationLog allocationToInactivate :
                allocationsToInactivate) {
            if (!allocationToInactivate.isCancelled()) {
                Optional<Seat> optionalSeat = seatRepository.findByActiveSeatAllocationId(allocationToInactivate.getAllocation_id());
                if (optionalSeat.isPresent()) {
                    Seat seat = optionalSeat.get();
                    ListIterator<SeatAllocation> iterator = seat.getActiveSeatAllocations().listIterator();
                    while(iterator.hasNext()) {
                        SeatAllocation activeAllocation = iterator.next();
                        if (activeAllocation.getId().equals(allocationToInactivate.getAllocation_id())) {
                            System.out.println("********** inactivate seat allocation: " + activeAllocation.getId() + " **********");
                            activeAllocation.setActive(false);
                            allocationToInactivate.setDone(true);
                            allocationToInactivate.setCompletion_time(new Date());
                            seat.getInactiveSeatAllocations().add(activeAllocation);
                            iterator.remove();
                            seatAllocationRepository.save(activeAllocation);
                            seatAllocationInactivationLogRepository.save(allocationToInactivate);
                        }
                    }
                    seatRepository.save(seat);
                }
            }
        }
    }

    // @Scheduled(cron = "0 1,31 9-18 * * MON-FRI")
    @Scheduled(fixedRate = 5000)
    private void updateCurrentOccupancy() {

        System.out.println("******************** Seat Management Background Service: update current occupancy ********************");
        List<Seat> seats = seatRepository.findAllUndeleted();
        for (Seat seat :
                seats) {
            List<SeatAllocation> activeAllocations = seat.getActiveSeatAllocations();
            Collections.sort(activeAllocations);
            for (SeatAllocation activeAllocation :
                    activeAllocations) {
                if (activeAllocation.getAllocationType() == SeatAllocationTypeEnum.FIXED) {
                    if (activeAllocation.getSchedule().getStartDateTime().compareTo(new Date()) <= 0 &&
                    seat.getCurrentOccupancy() == null || !seat.getCurrentOccupancy().getId().equals(activeAllocation.getId())) {
                        seat.setCurrentOccupancy(activeAllocation);
                        seat.setType(SeatTypeEnum.FIXED);
                        break;
                    }
                } else { // Shared seat allocation
                    if (activeAllocation.getSchedule().getStartDateTime().compareTo(new Date()) <= 0) {
                        if (activeAllocation.getSchedule().getRecurringBasis() == ScheduleRecurringBasisEnum.EVERYDAY) {
                            if (activeAllocation.getSchedule().getRecurringStartTime().compareTo(LocalDateTime.now().toLocalTime()) <= 0) {
                                seat.setCurrentOccupancy(activeAllocation);
                                seat.setType(SeatTypeEnum.SHARED);
                                break;
                            }
                        } else if (activeAllocation.getSchedule().getRecurringBasis() == ScheduleRecurringBasisEnum.EVERYWEEK) {
                            if (activeAllocation.getSchedule().getRecurringWeekdays().contains(LocalDateTime.now().getDayOfWeek())) {
                                if (activeAllocation.getSchedule().getRecurringStartTime() == null ||
                                        activeAllocation.getSchedule().getRecurringStartTime().compareTo(LocalDateTime.now().toLocalTime()) <= 0) {
                                    seat.setCurrentOccupancy(activeAllocation);
                                    seat.setType(SeatTypeEnum.SHARED);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
