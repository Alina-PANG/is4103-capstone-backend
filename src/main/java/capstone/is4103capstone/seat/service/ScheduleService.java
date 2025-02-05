package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.entities.Office;
import capstone.is4103capstone.entities.Schedule;
import capstone.is4103capstone.entities.Team;
import capstone.is4103capstone.entities.helper.DateHelper;
import capstone.is4103capstone.entities.seat.EmployeeOfficeWorkingSchedule;
import capstone.is4103capstone.entities.seat.SeatAllocation;
import capstone.is4103capstone.seat.model.schedule.ScheduleModel;
import capstone.is4103capstone.seat.repository.EmployeeOfficeWorkingScheduleRepository;
import capstone.is4103capstone.seat.repository.ScheduleRepository;
import capstone.is4103capstone.util.enums.ScheduleRecurringBasisEnum;
import capstone.is4103capstone.util.enums.SeatAllocationTypeEnum;
import capstone.is4103capstone.util.exception.CreateEmployeeOfficeWorkingScheduleException;
import capstone.is4103capstone.util.exception.ScheduleClashException;
import capstone.is4103capstone.util.exception.SeatAllocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.*;

@Service
public class ScheduleService {

    @Autowired
    private EmployeeOfficeWorkingScheduleRepository employeeOfficeWorkingScheduleRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    Comparator<EmployeeOfficeWorkingSchedule> workingScheduleComparatorByReverseChronologicalOrder = new Comparator<EmployeeOfficeWorkingSchedule>() {
        @Override
        public int compare(EmployeeOfficeWorkingSchedule e1, EmployeeOfficeWorkingSchedule e2) {
            Collections.sort(e1.getSchedules());
            Collections.sort(e2.getSchedules());
            return e1.getSchedules().get(0).compareTo(e2.getSchedules().get(0)) * (-1);
        }
    };

    // -------------------------------- Employee Office Working Schedule --------------------------------

    public void createNewEmployeeOfficeWorkingSchedule(EmployeeOfficeWorkingSchedule employeeOfficeWorkingSchedule) {
        // Checking whether the employee belongs to a team that's under the office
        Employee employee = employeeOfficeWorkingSchedule.getEmployee();
        Office office = employeeOfficeWorkingSchedule.getOffice();
        Team team = employee.getTeam();
        if (!team.getOffice().getId().equals(office.getId())) {
            throw new CreateEmployeeOfficeWorkingScheduleException("Create employee office working schedule failed:" +
                    "employee " + employee.getFullName() + "'s team does not work under office " + office.getObjectName() + "!");
        }

        // Check whether there is an existing working schedule of the employee at the office
        Optional<EmployeeOfficeWorkingSchedule> optionalEmployeeOfficeWorkingSchedule = employeeOfficeWorkingScheduleRepository.
                findUndeletedOneByEmployeeIdAndOfficeId(employee.getId(), office.getId());
        if (optionalEmployeeOfficeWorkingSchedule.isPresent()) {
            // Try to see if can merge or not
            // Check schedule clash
            EmployeeOfficeWorkingSchedule existingEmployeeOfficeWorkingSchedule = optionalEmployeeOfficeWorkingSchedule.get();
            for (Schedule existingSchedule :
                    existingEmployeeOfficeWorkingSchedule.getSchedules()) {
                for (Schedule newSchedule:
                        employeeOfficeWorkingSchedule.getSchedules()) {
                    try {
                        checkClashBetweenTwoGeneralSchedules(existingSchedule, newSchedule);
                    } catch (ScheduleClashException ex) {
                        throw new CreateEmployeeOfficeWorkingScheduleException("Create employee office working schedule failed: " +
                                "the employee has an existing working schedule at the office, and there is schedule clash found " +
                                "between existing schedule and the new schedule.");
                    }
                }
            }

            // Merge
            for (Schedule newSchedule:
                    employeeOfficeWorkingSchedule.getSchedules()) {
                newSchedule = scheduleRepository.save(newSchedule);
                existingEmployeeOfficeWorkingSchedule.getSchedules().add(newSchedule);
            }
            employeeOfficeWorkingScheduleRepository.save(existingEmployeeOfficeWorkingSchedule);

        } else {
            for (Schedule schedule :
                    employeeOfficeWorkingSchedule.getSchedules()) {
                scheduleRepository.save(schedule);
            }
            employeeOfficeWorkingScheduleRepository.save(employeeOfficeWorkingSchedule);
        }
    }

    public Optional<EmployeeOfficeWorkingSchedule> retrieveEmployeeOfficeWorkingSchedulesByEmployeeAndOffice(String employeeId, String officeId) {
        Optional<EmployeeOfficeWorkingSchedule> optionalEmployeeOfficeWorkingSchedule = employeeOfficeWorkingScheduleRepository.findUndeletedOneByEmployeeIdAndOfficeId(employeeId, officeId);
        return optionalEmployeeOfficeWorkingSchedule;
    }

    public boolean hasWorkingScheduleDuringYearMonthAtOfficeByEmployeeId(String employeeId, String officeId, YearMonth yearMonth) {
        Optional<EmployeeOfficeWorkingSchedule> optionalEmployeeOfficeWorkingSchedule = employeeOfficeWorkingScheduleRepository.findUndeletedOneByEmployeeIdAndOfficeId(employeeId, officeId);
        if (!optionalEmployeeOfficeWorkingSchedule.isPresent()) {
            return false;
        } else {
            EmployeeOfficeWorkingSchedule employeeOfficeWorkingSchedule = optionalEmployeeOfficeWorkingSchedule.get();
            Collections.sort(employeeOfficeWorkingSchedule.getSchedules());
            for (Schedule schedule :
                    employeeOfficeWorkingSchedule.getSchedules()) {
                if (containYearMonth(schedule, yearMonth)) {
                    return true;
                }
            }
            return false;
        }
    }

    // -------------------------------- Logic Checking --------------------------------

    // ---------------- Check whether a schedule contains a particular year month ----------------

    public boolean containYearMonth(Schedule schedule, YearMonth yearMonth) {
        YearMonth scheduleStartYearMonth = YearMonth.of(DateHelper.getYearFromDate(schedule.getStartDateTime()),
                DateHelper.getMonthFromDate(schedule.getStartDateTime()));
        System.out.println("********* year month to compare with " + yearMonth.toString() + " *********");
        System.out.println("********* schedule start time " + schedule.getStartDateTime().toString() + " *********");
        System.out.println("********* schedule start time year month " + scheduleStartYearMonth.toString() + " *********");
        if (yearMonth.isBefore(scheduleStartYearMonth)) {
            System.out.println("********* year month is before schedule start year month *********");
            return false;
        } else if (yearMonth.equals(scheduleStartYearMonth)) {
            System.out.println("********* year month equals schedule start year month *********");
            return true;
        } else { // after scheduleStartYearMonth
            System.out.println("********* year month is after schedule start year month *********");
            if (schedule.getEndDateTime() != null) {
                YearMonth scheduleEndYearMonth = YearMonth.of(DateHelper.getYearFromDate(schedule.getEndDateTime()),
                        DateHelper.getMonthFromDate(schedule.getEndDateTime()));
                if (!yearMonth.isAfter(scheduleEndYearMonth)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
    }

    // ---------------- Check whether there is any clash of two schedules ----------------

    public void checkClashBetweenTwoGeneralSchedules(Schedule schedule1, Schedule schedule2) throws ScheduleClashException {
        // Check the type (recurring or not)
        if (!schedule1.isRecurring()) {
            checkClashBetweenPermanentOrTemporarySchedules(schedule1, schedule2);
        } else { // schedule 1 recurring
            if (!schedule2.isRecurring()) {
                checkClashBetweenPermanentOrTemporarySchedules(schedule1, schedule2);
            } else {
                // both are recurring
                checkRecurringSchedulesClash(schedule1, schedule2);

                // The checking can be bypassed if the time frames don't overlap
                if (schedule1.getEndDateTime() != null) {
                    if (schedule2.getEndDateTime() == null) {
                        if (!schedule1.getEndDateTime().before(schedule2.getStartDateTime())) {
                            checkRecurringSchedulesClash(schedule1, schedule2);
                        }
                    } else { // both have an end date
                        if (schedule1.getStartDateTime().after(schedule2.getStartDateTime()) && schedule1.getStartDateTime().before(schedule2.getEndDateTime())
                                || schedule1.getEndDateTime().after(schedule2.getStartDateTime()) && schedule1.getEndDateTime().before(schedule2.getEndDateTime())) {
                            checkRecurringSchedulesClash(schedule1, schedule2);
                        }
                    }
                } else {
                    if (schedule2.getEndDateTime() == null) {
                        checkRecurringSchedulesClash(schedule1, schedule2);
                    } else {
                        if (schedule2.getEndDateTime().after(schedule1.getStartDateTime())) {
                            checkRecurringSchedulesClash(schedule1, schedule2);
                        }
                    }
                }
            }
        }
    }

    private void checkClashBetweenPermanentOrTemporarySchedules(Schedule schedule1, Schedule schedule2) throws ScheduleClashException {
        if (schedule1.getEndDateTime() == null) {
            if (schedule2.getEndDateTime() == null) {
                throw new ScheduleClashException("Schedule clash: two schedules both have no end date!");
            } else { // schedule 2 temporary
                if (!schedule2.getEndDateTime().before(schedule1.getStartDateTime())) {
                    throw new ScheduleClashException("Schedule clash found!");
                }
            }
        } else {
            if (schedule2.getEndDateTime() == null) {
                throw new ScheduleClashException("Schedule clash found!");
            } else { // both temporary
                if (schedule1.getStartDateTime().before(schedule2.getStartDateTime())) {
                    if (!schedule1.getEndDateTime().before(schedule2.getStartDateTime())) {
                        throw new ScheduleClashException("Schedule clash found!");
                    }
                } else if (schedule1.getStartDateTime().equals(schedule2.getStartDateTime())){
                    throw new ScheduleClashException("Schedule clash found!");
                } else { // schedule1.getStartDateTime().after(schedule2.getStartDateTime())
                    if (schedule1.getStartDateTime().before(schedule2.getEndDateTime())) {
                        throw new ScheduleClashException("Schedule clash found!");
                    }
                }
            }
        }
    }


    // ---------------- Check whether there is any clash of allocation schedule ----------------

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



    // For both fixed & shared seats allocation:
    // - allocation with no occupancy end date: directly reject
    // - allocation with an occupancy end date: compare the end date of this one and the start date of the new one
    public void checkClashForPermanentFixedTypeAllocation(Date requiredStartDateTime, SeatAllocation existingSeatAllocation) throws ScheduleClashException {
        Schedule allocationSchedule = existingSeatAllocation.getSchedule();
        if (existingSeatAllocation.getAllocationType() == SeatAllocationTypeEnum.FIXED) {
            if (allocationSchedule.getEndDateTime() == null) {
                // Two permanent allocations must clash because both don't have an end date time
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
            if (requiredStartDateTime.after(allocationSchedule.getStartDateTime()) && requiredStartDateTime.before(allocationSchedule.getEndDateTime())) {
                throw new ScheduleClashException("Allocation schedule clash with another seat allocation of " +
                        existingSeatAllocation.getAllocationType().toString() + " type!");
            }
        }
    }



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
                        checkRecurringSchedulesClash(allocationSchedule, newAllocationSchedule);
                    }
                } else { // both have an end date
                    if (requiredStartDateTime.after(allocationSchedule.getStartDateTime()) && requiredStartDateTime.before(allocationSchedule.getEndDateTime())
                            || requiredEndDateTime.after(allocationSchedule.getStartDateTime()) && requiredEndDateTime.before(allocationSchedule.getEndDateTime())) {
                        checkRecurringSchedulesClash(allocationSchedule, newAllocationSchedule);
                    }
                }
            } else { // the existing allocation does not have an end date
                if (requiredEndDateTime == null) {
                    checkRecurringSchedulesClash(allocationSchedule, newAllocationSchedule);
                } else {
                    if (requiredEndDateTime.after(allocationSchedule.getStartDateTime())) {
                        checkRecurringSchedulesClash(allocationSchedule, newAllocationSchedule);
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



    // -------------------------------- Helper methods --------------------------------

    // Pre-condition:
    // - The two schedules were checked before hand to have overlapping time frame (based on date only)
    private void checkRecurringSchedulesClash(Schedule schedule1, Schedule schedule2) throws ScheduleClashException {

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
            if (!(endTime1.compareTo(startTime2) <= 0)) {
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
