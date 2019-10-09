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
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;

@Scope("singleton")
@Component
// @Service
public class SeatManagementBackgroundService {
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private SeatAllocationRepository seatAllocationRepository;
    @Autowired
    private SeatAllocationInactivationLogRepository seatAllocationInactivationLogRepository;

    public SeatManagementBackgroundService() {

    }


    // ------------------------ Programmatic timers for system release one presentation only ------------------------

    public Runnable runInactivateAllocations() {

        return new Runnable() {

            public void run() {
                System.out.println("******************** Seat Management Background Service: inactivate allocations at " + new Date().toString() + " ********************");
                // List<SeatAllocationInactivationLog> allocationsToInactivate = seatAllocationInactivationLogRepository.getLogsByCurrentTime(new Date());
                List<SeatAllocationInactivationLog> allocationsToInactivate = seatAllocationInactivationLogRepository.getUndoneLogs();
                for (SeatAllocationInactivationLog allocationToInactivate :
                        allocationsToInactivate) {
                    System.out.println("********** allocation ID: " + allocationToInactivate.getAllocation_id() + " **********");
                    System.out.println("********** inactivate time: " + allocationToInactivate.getInactivate_time().toString() + " **********");
                    System.out.println("********** now: " + new Date().toString() + " **********");
                    if (allocationToInactivate.getInactivate_time().before(new Date())) {
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
                            seat.setType(SeatTypeEnum.HOTDESK);
                            seat.setCurrentOccupancy(null);
                            seatRepository.save(seat);
                        }
                    }
                }
            }
        };
    }

    public Runnable runUpdateCurrentOccupancy() {

        return new Runnable() {

            public void run() {
                System.out.println("******************** Seat Management Background Service: update current occupancy ********************");
                List<Seat> seats = seatRepository.findAllUndeleted();
                for (Seat seat :
                        seats) {
                    System.out.println("********** checking seat: " + seat.getId() + " **********");
                    List<SeatAllocation> activeAllocations = seat.getActiveSeatAllocations();
                    System.out.println("********** number of active allocations: " + activeAllocations.size() + " **********");
                    Collections.sort(activeAllocations);
                    for (SeatAllocation activeAllocation :
                            activeAllocations) {
                        System.out.println("***** checking allocation: " + activeAllocation.getId() + " *****");
                        if (activeAllocation.getAllocationType() == SeatAllocationTypeEnum.FIXED) {
                            if (activeAllocation.getSchedule().getStartDateTime().compareTo(new Date()) <= 0 &&
                                    (seat.getCurrentOccupancy() == null || !seat.getCurrentOccupancy().getId().equals(activeAllocation.getId())) ) {
                                seat.setCurrentOccupancy(activeAllocation);
                                seat.setType(SeatTypeEnum.FIXED);
                                seatRepository.save(seat);
                                break;
                            }
                        } else { // Shared seat allocation
                            if (activeAllocation.getSchedule().getStartDateTime().compareTo(new Date()) <= 0) {
                                if (activeAllocation.getSchedule().getRecurringBasis() == ScheduleRecurringBasisEnum.EVERYDAY) {
                                    if (activeAllocation.getSchedule().getRecurringStartTime().compareTo(LocalDateTime.now().toLocalTime()) <= 0) {
                                        seat.setCurrentOccupancy(activeAllocation);
                                        seat.setType(SeatTypeEnum.SHARED);
                                        seatRepository.save(seat);
                                        break;
                                    }
                                } else if (activeAllocation.getSchedule().getRecurringBasis() == ScheduleRecurringBasisEnum.EVERYWEEK) {
                                    if (activeAllocation.getSchedule().getRecurringWeekdays().contains(LocalDateTime.now().getDayOfWeek())) {
                                        if (activeAllocation.getSchedule().getRecurringStartTime() == null ||
                                                activeAllocation.getSchedule().getRecurringStartTime().compareTo(LocalDateTime.now().toLocalTime()) <= 0) {
                                            seat.setCurrentOccupancy(activeAllocation);
                                            seat.setType(SeatTypeEnum.SHARED);
                                            seatRepository.save(seat);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
    }


    // ------------------------ Automatic timers (@scheduled annotation commented out for system release one presentation only) ------------------------

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
    // @Scheduled(cron = "*/5 * 9-18 * * MON-FRI")
    private void inactivateAllocations() {

        System.out.println("******************** Seat Management Background Service: inactivate allocations at " + new Date().toString() + " ********************");
        // List<SeatAllocationInactivationLog> allocationsToInactivate = seatAllocationInactivationLogRepository.getLogsByCurrentTime(new Date());
        List<SeatAllocationInactivationLog> allocationsToInactivate = seatAllocationInactivationLogRepository.getUndoneLogs();
        for (SeatAllocationInactivationLog allocationToInactivate :
                allocationsToInactivate) {
            System.out.println("********** allocation ID: " + allocationToInactivate.getAllocation_id() + " **********");
            System.out.println("********** inactivate time: " + allocationToInactivate.getInactivate_time().toString() + " **********");
            System.out.println("********** now: " + new Date().toString() + " **********");
            if (allocationToInactivate.getInactivate_time().before(new Date())) {
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
                    seat.setType(SeatTypeEnum.HOTDESK);
                    seatRepository.save(seat);
                }
            }
        }
    }

    // @Scheduled(cron = "*/5 * 9-18 * * MON-FRI")
    private void updateCurrentOccupancy() {

        System.out.println("******************** Seat Management Background Service: update current occupancy ********************");
        List<Seat> seats = seatRepository.findAllUndeleted();
        for (Seat seat :
                seats) {
            System.out.println("********** checking seat: " + seat.getId() + " **********");
            List<SeatAllocation> activeAllocations = seat.getActiveSeatAllocations();
            System.out.println("********** number of active allocations: " + activeAllocations.size() + " **********");
            Collections.sort(activeAllocations);
            for (SeatAllocation activeAllocation :
                    activeAllocations) {
                System.out.println("***** checking allocation: " + activeAllocation.getId() + " *****");
                if (activeAllocation.getAllocationType() == SeatAllocationTypeEnum.FIXED) {
                    if (activeAllocation.getSchedule().getStartDateTime().compareTo(new Date()) <= 0 &&
                            (seat.getCurrentOccupancy() == null || !seat.getCurrentOccupancy().getId().equals(activeAllocation.getId())) ) {
                        seat.setCurrentOccupancy(activeAllocation);
                        seat.setType(SeatTypeEnum.FIXED);
                        seatRepository.save(seat);
                        break;
                    }
                } else if (activeAllocation.getAllocationType() == SeatAllocationTypeEnum.HOTDESK) {
                    if (activeAllocation.getSchedule().getStartDateTime().compareTo(new Date()) <= 0 &&
                            (seat.getCurrentOccupancy() == null || !seat.getCurrentOccupancy().getId().equals(activeAllocation.getId())) ) {
                        seat.setCurrentOccupancy(activeAllocation);
                        seat.setType(SeatTypeEnum.HOTDESK);
                        seatRepository.save(seat);
                        break;
                    }
                } else if (activeAllocation.getAllocationType() == SeatAllocationTypeEnum.SHARED) { // Shared seat allocation
                    if (activeAllocation.getSchedule().getStartDateTime().compareTo(new Date()) <= 0) {
                        if (activeAllocation.getSchedule().getRecurringBasis() == ScheduleRecurringBasisEnum.EVERYDAY) {
                            if (activeAllocation.getSchedule().getRecurringStartTime().compareTo(LocalDateTime.now().toLocalTime()) <= 0) {
                                seat.setCurrentOccupancy(activeAllocation);
                                seat.setType(SeatTypeEnum.SHARED);
                                seatRepository.save(seat);
                                break;
                            }
                        } else if (activeAllocation.getSchedule().getRecurringBasis() == ScheduleRecurringBasisEnum.EVERYWEEK) {
                            if (activeAllocation.getSchedule().getRecurringWeekdays().contains(LocalDateTime.now().getDayOfWeek())) {
                                if (activeAllocation.getSchedule().getRecurringStartTime() == null ||
                                        activeAllocation.getSchedule().getRecurringStartTime().compareTo(LocalDateTime.now().toLocalTime()) <= 0) {
                                    seat.setCurrentOccupancy(activeAllocation);
                                    seat.setType(SeatTypeEnum.SHARED);
                                    seatRepository.save(seat);
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
