package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.entities.Schedule;
import capstone.is4103capstone.entities.helper.DateHelper;
import capstone.is4103capstone.entities.seat.SeatAllocation;
import capstone.is4103capstone.seat.model.ScheduleModel;
import capstone.is4103capstone.util.enums.ScheduleRecurringBasisEnum;
import capstone.is4103capstone.util.enums.SeatAllocationTypeEnum;
import capstone.is4103capstone.util.exception.ScheduleClashException;
import capstone.is4103capstone.util.exception.SeatAllocationException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Date;

@Service
public class ScheduleService {


    // Check whether there is any clash of allocation schedule
    // - allocation with no occupancy end date: compare the start date of this one and the end date of the new one
    // - allocation with an occupancy end date: compare the whole time frame
    public void checkClashForHotDeskTypeAllocation(Date requiredStartDateTime, Date requiredEndDateTime, SeatAllocation existingSeatAllocation)
            throws ScheduleClashException {
        Schedule allocationSchedule = existingSeatAllocation.getSchedule();
        if (allocationSchedule.getEndDateTime() == null) {
            if (allocationSchedule.getStartDateTime().before(requiredEndDateTime)) {
                throw new ScheduleClashException("Allocation schedule clash with another seat allocation of " +
                        existingSeatAllocation.getAllocationType().toString() + " type!");
            }
        } else {
            if (requiredStartDateTime.before(allocationSchedule.getStartDateTime())) {
                if (!requiredEndDateTime.before(allocationSchedule.getStartDateTime())) {
                    throw new ScheduleClashException("Allocation schedule clash with another seat allocation of " +
                            existingSeatAllocation.getAllocationType().toString() + " type!");
                }
            }
            if (requiredStartDateTime.equals(allocationSchedule.getStartDateTime())) {
                throw new ScheduleClashException("Allocation schedule clash with another seat allocation of " +
                        existingSeatAllocation.getAllocationType().toString() + " type!");
            }
            if (requiredStartDateTime.after(allocationSchedule.getStartDateTime()) && requiredStartDateTime.before(allocationSchedule.getEndDateTime())) {
                throw new ScheduleClashException("Allocation schedule clash with another seat allocation of " +
                        existingSeatAllocation.getAllocationType().toString() + " type!");
            }
        }
    }



    // Check whether there is any clash of allocation schedule
    // For both fixed & shared seats allocation:
    // - allocation with no occupancy end date: directly reject
    // - allocation with an occupancy end date: compare the end date of this one and the start date of the new one
    public void checkClashForPermanentFixedTypeAllocation(Date requiredStartDateTime, SeatAllocation existingSeatAllocation) throws ScheduleClashException {
        Schedule allocationSchedule = existingSeatAllocation.getSchedule();
        if (existingSeatAllocation.getAllocationType() == SeatAllocationTypeEnum.FIXED) {
            if (allocationSchedule.getEndDateTime() == null) {
                throw new ScheduleClashException("Allocation schedule clash with another fixed seat allocation (for a permanent employee)!");
            } else {
                if (allocationSchedule.getEndDateTime().after(requiredStartDateTime)) {
                    throw new ScheduleClashException("Allocation schedule clash with another fixed seat allocation (for a temporary employee)!");
                }
            }
        } else {
            if (allocationSchedule.getEndDateTime() == null) {
                throw new ScheduleClashException("Allocation schedule clash with another shared seat allocation!");
            } else {
                if (allocationSchedule.getEndDateTime().after(requiredStartDateTime)) {
                    throw new ScheduleClashException("Allocation schedule clash with another shared seat allocation (for a working-from-home employee)!");
                }
            }
        }
    }



    // Check whether there is any clash of allocation schedule
    // - allocation with no occupancy end date: compare the start date of this one and the end date of the new one
    // - allocation with an occupancy end date: compare the whole time frame
    public void checkClashForTemporaryFixedTypeAllocation(Date requiredStartDateTime, Date requiredEndDateTime, SeatAllocation existingSeatAllocation)
            throws ScheduleClashException{

        Schedule allocationSchedule = existingSeatAllocation.getSchedule();
        if (allocationSchedule.getEndDateTime() == null) {
            if (allocationSchedule.getStartDateTime().before(requiredEndDateTime)) {
                throw new ScheduleClashException("Allocation schedule clash with another seat allocation of " +
                        existingSeatAllocation.getAllocationType().toString() + " type!");
            }
        } else {
            if (requiredStartDateTime.before(allocationSchedule.getStartDateTime())) {
                if (!requiredEndDateTime.before(allocationSchedule.getStartDateTime())) {
                    throw new ScheduleClashException("Allocation schedule clash with another seat allocation of " +
                            existingSeatAllocation.getAllocationType().toString() + " type!");
                }
            }
            if (requiredStartDateTime.equals(allocationSchedule.getStartDateTime())) {
                throw new ScheduleClashException("Allocation schedule clash with another seat allocation of " +
                        existingSeatAllocation.getAllocationType().toString() + " type!");
            }
            if (requiredStartDateTime.after(allocationSchedule.getStartDateTime()) && requiredEndDateTime.before(allocationSchedule.getEndDateTime())) {
                throw new ScheduleClashException("Allocation schedule clash with another seat allocation of " +
                        existingSeatAllocation.getAllocationType().toString() + " type!");
            }
        }
    }



    // Check whether there is any clash of allocation schedule
    // For fixed seat allocation:
    // - for permanent employees (no occupancy end date): compare the start date of this one and the end date of the new one (if have any)
    // - for temporary employees (with occupancy end date): compare the whole time period
    public void checkClashForSharedTypeAllocation(Date requiredStartDateTime, Date requiredEndDateTime, Schedule newAllocationSchedule, SeatAllocation seatAllocation)
                throws ScheduleClashException{
        Schedule allocationSchedule = seatAllocation.getSchedule();
        if (seatAllocation.getAllocationType() == SeatAllocationTypeEnum.FIXED) {

            // Two cases: fixed seat for permanent employees and temporary employees
            if (allocationSchedule.getEndDateTime() == null) { // permanent employees
                if (requiredEndDateTime == null) {
                    // Both have no occupancy end date, reject
                    throw new ScheduleClashException("Schedule clash with another fixed seat allocation for a permanent employee!");
                } else {
                    // Compare the end date of the new one with the start date of the existing one
                    if (!requiredEndDateTime.before(allocationSchedule.getStartDateTime())) {
                        throw new ScheduleClashException("schedule clash with another fixed seat allocation for a permanent employee!");
                    }
                }
            } else { // temporary employees
                if (requiredEndDateTime == null) {
                    // Compare the start date of the new one with the end date of the existing one
                    if (!requiredStartDateTime.after(allocationSchedule.getEndDateTime())) {
                        throw new ScheduleClashException("schedule clash with another fixed seat allocation for a temporary employee!");
                    }
                } else {
                    // Both have a start date and an end date -> Compare the whole time frame
                    if (requiredStartDateTime.before(allocationSchedule.getStartDateTime())) {
                        if (!requiredEndDateTime.before(allocationSchedule.getStartDateTime())) {
                            throw new ScheduleClashException("schedule clash with another seat allocation of " + seatAllocation.getAllocationType().toString() + " type!");
                        }
                    }
                    if (requiredStartDateTime.equals(allocationSchedule.getStartDateTime())) {
                        throw new ScheduleClashException("schedule clash with another seat allocation of " + seatAllocation.getAllocationType().toString() + " type!");
                    }
                    if (requiredStartDateTime.after(allocationSchedule.getStartDateTime()) && requiredStartDateTime.before(allocationSchedule.getEndDateTime())) {
                        throw new ScheduleClashException("schedule clash with another seat allocation of " + seatAllocation.getAllocationType().toString() + " type!");
                    }
                }
            }

        } else { // shared seats

            // The checking can be bypassed if the time frames don't overlap
            if (allocationSchedule.getEndDateTime() != null) { // the existing allocation has an end date
                if (requiredEndDateTime == null) { // the new allocation does not have an end date, sliding on the time scale
                    if (requiredStartDateTime.before(allocationSchedule.getEndDateTime())) {
                        checkSharedSeatsScheduleClash(allocationSchedule, newAllocationSchedule);
                    }
                } else { // both have an end date
                    if (requiredStartDateTime.after(allocationSchedule.getStartDateTime()) && requiredStartDateTime.before(allocationSchedule.getEndDateTime())
                            || requiredEndDateTime.after(allocationSchedule.getStartDateTime()) && requiredEndDateTime.before(allocationSchedule.getEndDateTime())) {
                        checkSharedSeatsScheduleClash(allocationSchedule, newAllocationSchedule);
                    }
                }
            } else { // the existing allocation does not have an end date
                if (requiredEndDateTime == null) {
                    checkSharedSeatsScheduleClash(allocationSchedule, newAllocationSchedule);
                } else {
                    if (requiredEndDateTime.after(allocationSchedule.getStartDateTime())) {
                        checkSharedSeatsScheduleClash(allocationSchedule, newAllocationSchedule);
                    }
                }
            }
        }
    }




    public void validateScheduleRecurringBasis(ScheduleRecurringBasisEnum recurringBasisEnum, ScheduleModel scheduleModel) throws ScheduleClashException {
        if (recurringBasisEnum == ScheduleRecurringBasisEnum.EVERYDAY) {
            if (scheduleModel.getRecurringStartTime() == null || scheduleModel.getRecurringEndTime() == null) {
                throw new ScheduleClashException("Schedule validation failed: recurring start time or end time must be provided for EveryDay recurring basis!");
            }
        } else if (recurringBasisEnum == ScheduleRecurringBasisEnum.EVERYWEEK) {
            if (scheduleModel.getRecurringWeekdays() == null || scheduleModel.getRecurringWeekdays().size() == 0) {
                throw new ScheduleClashException("Schedule validation failed: there must be at least one recurring weekday provided for EveryWeek recurring basis!");
            } else if  (scheduleModel.getRecurringStartTime() == null || scheduleModel.getRecurringEndTime() == null) {
                throw new ScheduleClashException("Schedule validation failed: recurring start time or end time must be provided for EveryWeek recurring basis!");
            }
        } else {
            if (scheduleModel.getRecurringDates() == null || scheduleModel.getRecurringDates().size() == 0) {
                throw new ScheduleClashException("Schedule validation failed: there must be at least one recurring date provided for EveryYear recurring basis!");
            } else if  (scheduleModel.getRecurringStartTime() == null || scheduleModel.getRecurringEndTime() == null) {
                throw new ScheduleClashException("Schedule validation failed: recurring start time or end time must be provided for EveryYear recurring basis!");
            }
        }
    }


    // Pre-condition:
    // - The two schedules were checked before hand to have overlapping time frame (based on date only)
    private void checkSharedSeatsScheduleClash(Schedule schedule1, Schedule schedule2) throws ScheduleClashException {

        // shared seats -> compare by recurring basis, one by one
        // "EveryDay" (recurringStartTime, recurringEndTime),
        // "EveryWeek" (recurringWeekdays, recurringStartTime, recurringEndTime),

        try {
            if (schedule1.getRecurringBasis() == ScheduleRecurringBasisEnum.EVERYDAY) {
                checkHourlyClashes(schedule1, schedule2);
            } else if (schedule1.getRecurringBasis() == ScheduleRecurringBasisEnum.EVERYWEEK) {
                if (schedule2.getRecurringBasis() == ScheduleRecurringBasisEnum.EVERYDAY) {
                    checkHourlyClashes(schedule1, schedule2);
                } else if (schedule2.getRecurringBasis() == ScheduleRecurringBasisEnum.EVERYWEEK) {
                    for (DayOfWeek dayOfWeek :
                            schedule1.getRecurringWeekdays()) {
                        for (DayOfWeek dayOfWeek2 :
                                schedule2.getRecurringWeekdays()) {
                            if (dayOfWeek.equals(dayOfWeek2)) {
                                checkHourlyClashes(schedule1, schedule2);
                            }
                        }
                    }
                } else {
                    for (DayOfWeek dayOfWeek :
                            schedule1.getRecurringWeekdays()) {
                        for (Date date :
                                schedule2.getRecurringDates()) {
                            if (dayOfWeek == DateHelper.getDayOfWeekFromDate(date)) {
                                checkHourlyClashes(schedule1, schedule2);
                            }
                        }
                    }
                }
            }
        } catch (ScheduleClashException ex) {
            throw new SeatAllocationException(ex.getMessage());
        }
    }


    private void checkHourlyClashes(Schedule schedule1, Schedule schedule2) throws ScheduleClashException {

        System.out.println("******************** Check Hourly Clashes ********************");
        LocalTime startTime1 = schedule1.getRecurringStartTime();
        LocalTime endTime1 = schedule1.getRecurringEndTime();
        LocalTime startTime2 = schedule2.getRecurringStartTime();
        LocalTime endTime2 = schedule2.getRecurringEndTime();

        if (startTime1.compareTo(startTime2) < 0) {
            System.out.println("********** " + startTime1.toString() + " is before " + startTime2.toString() + " **********");
            if (!(endTime1.compareTo(startTime2) < 0)) {
                throw new ScheduleClashException("There is hourly schedule clash!");
            }
        }
        if (startTime1.equals(startTime2)) {
            throw new ScheduleClashException("There is hourly schedule clash!");
        }
        if (startTime1.compareTo(startTime2) > 0 && startTime1.compareTo(endTime2) < 0) {
            throw new ScheduleClashException("There is hourly schedule clash!");
        }
    }

}
