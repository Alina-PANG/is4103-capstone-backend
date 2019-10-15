package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.entities.BusinessUnit;
import capstone.is4103capstone.entities.CompanyFunction;
import capstone.is4103capstone.entities.Schedule;
import capstone.is4103capstone.entities.Team;
import capstone.is4103capstone.entities.helper.DateHelper;
import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatAllocation;
import capstone.is4103capstone.seat.model.EmployeeModel;
import capstone.is4103capstone.seat.model.GroupModel;
import capstone.is4103capstone.seat.model.ScheduleModel;
import capstone.is4103capstone.seat.model.seat.SeatModelForAllocation;
import capstone.is4103capstone.seat.model.seat.SeatModelWithHighlighting;
import capstone.is4103capstone.seat.model.seatAllocation.SeatAllocationModelForEmployee;
import capstone.is4103capstone.util.enums.ScheduleRecurringBasisEnum;
import capstone.is4103capstone.util.exception.EntityModelConversionException;
import capstone.is4103capstone.util.exception.ScheduleValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EntityModelConversionService {

    @Autowired
    private ScheduleService scheduleService;

    // ---------------------------------- Seats -----------------------------------

    public List<SeatModelWithHighlighting> convertSeatsToSeatModelsWithHighlightingEmployee(List<Seat> seats, String employeeId) {
        List<SeatModelWithHighlighting> seatModelsForDeallocationViaSeatMap = new ArrayList<>();
        for (Seat seat :
                seats) {
            // Basic info
            SeatModelWithHighlighting seatModelWithHighlighting = new SeatModelWithHighlighting();
            seatModelWithHighlighting.setId(seat.getId());
            seatModelWithHighlighting.setCode(seat.getCode());
            seatModelWithHighlighting.setSerialNumber(seat.getSerialNumber());
            seatModelWithHighlighting.setType(seat.getType().toString());
            seatModelWithHighlighting.setX(seat.getxCoordinate());
            seatModelWithHighlighting.setY(seat.getyCoordinate());
            seatModelWithHighlighting.setUnderOffice(seat.isUnderOffice());
            if (seat.isUnderOffice() && seat.getAdjacentSeatSeqNum() != null) {
                seatModelWithHighlighting.setAdjacentSeatSeqNum(seat.getAdjacentSeatSeqNum());
            }

            // Function, business unit and team
            CompanyFunction function = seat.getFunctionAssigned();
            if (function != null) {
                GroupModel functionModel = new GroupModel();
                functionModel.setId(function.getId());
                functionModel.setName(function.getObjectName());
                functionModel.setCode(function.getCode());
                seatModelWithHighlighting.setFunctionAssigned(functionModel);
            }

            BusinessUnit businessUnit = seat.getBusinessUnitAssigned();
            if (businessUnit != null) {
                GroupModel businessUnitModel = new GroupModel();
                businessUnitModel.setId(businessUnit.getId());
                businessUnitModel.setName(businessUnit.getObjectName());
                businessUnitModel.setCode(businessUnit.getCode());
                seatModelWithHighlighting.setBusinessUnitAssigned(businessUnitModel);
            }

            Team team = seat.getTeamAssigned();
            if (team != null) {
                GroupModel teamModel = new GroupModel();
                teamModel.setId(team.getId());
                teamModel.setName(team.getObjectName());
                teamModel.setCode(team.getCode());
                seatModelWithHighlighting.setTeamAssigned(teamModel);
            }

            // Current occupancy
            SeatAllocationModelForEmployee currentOccupancyModel = new SeatAllocationModelForEmployee();
            SeatAllocation currentOccupancy = seat.getCurrentOccupancy();
            if (currentOccupancy != null) {
                currentOccupancyModel.setId(currentOccupancy.getId());
                currentOccupancyModel.setType(currentOccupancy.getAllocationType().toString());

                EmployeeModel employeeModel = new EmployeeModel(currentOccupancy.getEmployee().getId(), currentOccupancy.getEmployee().getFullName());
                currentOccupancyModel.setEmployee(employeeModel);

                ScheduleModel scheduleModel = new ScheduleModel();
                Schedule schedule = currentOccupancy.getSchedule();
                scheduleModel.setId(schedule.getId());
                scheduleModel.setRecurring(schedule.isRecurring());
                scheduleModel.setStartDateTime(schedule.getStartDateTime());
                if (schedule.getEndDateTime() != null) {
                    scheduleModel.setEndDateTime(schedule.getEndDateTime());
                }
                if (schedule.getRecurringBasis() != null) {
                    scheduleModel.setRecurringBasis(schedule.getRecurringBasis().toString());
                }
                scheduleModel.setRecurringDates(schedule.getRecurringDates());
                List<Integer> recurringWeekdays = new ArrayList<>();
                for (DayOfWeek dayOfWeek :
                        schedule.getRecurringWeekdays()) {
                    recurringWeekdays.add(dayOfWeek.getValue());
                }
                scheduleModel.setRecurringWeekdays(recurringWeekdays);
                if (schedule.getRecurringStartTime() != null) {
                    scheduleModel.setRecurringStartTime(schedule.getRecurringStartTime());
                }
                if (schedule.getRecurringEndTime() != null) {
                    scheduleModel.setRecurringEndTime(schedule.getRecurringEndTime());
                }
                currentOccupancyModel.setSchedule(scheduleModel);
            }
            seatModelWithHighlighting.setCurrentOccupancy(currentOccupancyModel);

            // Active allocations
            for (SeatAllocation allocation :
                    seat.getActiveSeatAllocations()) {
                SeatAllocationModelForEmployee allocationModelForEmployee = new SeatAllocationModelForEmployee();
                allocationModelForEmployee.setId(allocation.getId());
                allocationModelForEmployee.setType(allocation.getAllocationType().toString());

                EmployeeModel employeeModel = new EmployeeModel(allocation.getEmployee().getId(), allocation.getEmployee().getFullName());
                allocationModelForEmployee.setEmployee(employeeModel);

                ScheduleModel scheduleModel = new ScheduleModel();
                Schedule schedule = allocation.getSchedule();
                scheduleModel.setId(schedule.getId());
                scheduleModel.setRecurring(schedule.isRecurring());
                scheduleModel.setStartDateTime(schedule.getStartDateTime());
                if (schedule.getEndDateTime() != null) {
                    scheduleModel.setEndDateTime(schedule.getEndDateTime());
                }
                if (schedule.getRecurringBasis() != null) {
                    scheduleModel.setRecurringBasis(schedule.getRecurringBasis().toString());
                }
                scheduleModel.setRecurringDates(schedule.getRecurringDates());
                List<Integer> recurringWeekdays = new ArrayList<>();
                for (DayOfWeek dayOfWeek :
                        schedule.getRecurringWeekdays()) {
                    recurringWeekdays.add(dayOfWeek.getValue());
                }
                scheduleModel.setRecurringWeekdays(recurringWeekdays);
                if (schedule.getRecurringStartTime() != null) {
                    scheduleModel.setRecurringStartTime(schedule.getRecurringStartTime());
                }
                if (schedule.getRecurringEndTime() != null) {
                    scheduleModel.setRecurringEndTime(schedule.getRecurringEndTime());
                }
                if (allocation.getEmployee().getId().equals(employeeId)) {
                    seatModelWithHighlighting.setNeedHighlight(true);
                }

                allocationModelForEmployee.setSchedule(scheduleModel);
                seatModelWithHighlighting.getAllocations().add(allocationModelForEmployee);
            }

            seatModelsForDeallocationViaSeatMap.add(seatModelWithHighlighting);
        }

        return seatModelsForDeallocationViaSeatMap;
    }

    public SeatModelWithHighlighting convertSeatToSeatModelWithHighlighting(Seat seat, boolean needHighlighting) {
        // Basic info
        SeatModelWithHighlighting seatModelWithHighlighting = new SeatModelWithHighlighting();
        seatModelWithHighlighting.setId(seat.getId());
        seatModelWithHighlighting.setCode(seat.getCode());
        seatModelWithHighlighting.setSerialNumber(seat.getSerialNumber());
        seatModelWithHighlighting.setType(seat.getType().toString());
        seatModelWithHighlighting.setX(seat.getxCoordinate());
        seatModelWithHighlighting.setY(seat.getyCoordinate());
        seatModelWithHighlighting.setUnderOffice(seat.isUnderOffice());
        if (needHighlighting) {
            seatModelWithHighlighting.setNeedHighlight(true);
        } else {
            seatModelWithHighlighting.setNeedHighlight(false);
        }
        if (seat.isUnderOffice() && seat.getAdjacentSeatSeqNum() != null) {
            seatModelWithHighlighting.setAdjacentSeatSeqNum(seat.getAdjacentSeatSeqNum());
        }

        // Function, business unit and team
        CompanyFunction function = seat.getFunctionAssigned();
        if (function != null) {
            GroupModel functionModel = new GroupModel();
            functionModel.setId(function.getId());
            functionModel.setName(function.getObjectName());
            functionModel.setCode(function.getCode());
            seatModelWithHighlighting.setFunctionAssigned(functionModel);
        }

        BusinessUnit businessUnit = seat.getBusinessUnitAssigned();
        if (businessUnit != null) {
            GroupModel businessUnitModel = new GroupModel();
            businessUnitModel.setId(businessUnit.getId());
            businessUnitModel.setName(businessUnit.getObjectName());
            businessUnitModel.setCode(businessUnit.getCode());
            seatModelWithHighlighting.setBusinessUnitAssigned(businessUnitModel);
        }

        Team team = seat.getTeamAssigned();
        if (team != null) {
            GroupModel teamModel = new GroupModel();
            teamModel.setId(team.getId());
            teamModel.setName(team.getObjectName());
            teamModel.setCode(team.getCode());
            seatModelWithHighlighting.setTeamAssigned(teamModel);
        }

        // Current occupancy
        SeatAllocationModelForEmployee currentOccupancyModel = new SeatAllocationModelForEmployee();
        SeatAllocation currentOccupancy = seat.getCurrentOccupancy();
        if (currentOccupancy != null) {
            currentOccupancyModel.setId(currentOccupancy.getId());
            currentOccupancyModel.setType(currentOccupancy.getAllocationType().toString());

            EmployeeModel employeeModel = new EmployeeModel(currentOccupancy.getEmployee().getId(), currentOccupancy.getEmployee().getFullName());
            currentOccupancyModel.setEmployee(employeeModel);

            ScheduleModel scheduleModel = new ScheduleModel();
            Schedule schedule = currentOccupancy.getSchedule();
            scheduleModel.setId(schedule.getId());
            scheduleModel.setRecurring(schedule.isRecurring());
            scheduleModel.setStartDateTime(schedule.getStartDateTime());
            if (schedule.getEndDateTime() != null) {
                scheduleModel.setEndDateTime(schedule.getEndDateTime());
            }
            if (schedule.getRecurringBasis() != null) {
                scheduleModel.setRecurringBasis(schedule.getRecurringBasis().toString());
            }
            scheduleModel.setRecurringDates(schedule.getRecurringDates());
            List<Integer> recurringWeekdays = new ArrayList<>();
            for (DayOfWeek dayOfWeek :
                    schedule.getRecurringWeekdays()) {
                recurringWeekdays.add(dayOfWeek.getValue());
            }
            scheduleModel.setRecurringWeekdays(recurringWeekdays);
            if (schedule.getRecurringStartTime() != null) {
                scheduleModel.setRecurringStartTime(schedule.getRecurringStartTime());
            }
            if (schedule.getRecurringEndTime() != null) {
                scheduleModel.setRecurringEndTime(schedule.getRecurringEndTime());
            }
            currentOccupancyModel.setSchedule(scheduleModel);
        }
        seatModelWithHighlighting.setCurrentOccupancy(currentOccupancyModel);

        // Active allocations
        for (SeatAllocation allocation :
                seat.getActiveSeatAllocations()) {
            SeatAllocationModelForEmployee allocationModelForEmployee = new SeatAllocationModelForEmployee();
            allocationModelForEmployee.setId(allocation.getId());
            allocationModelForEmployee.setType(allocation.getAllocationType().toString());

            EmployeeModel employeeModel = new EmployeeModel(allocation.getEmployee().getId(), allocation.getEmployee().getFullName());
            allocationModelForEmployee.setEmployee(employeeModel);

            ScheduleModel scheduleModel = new ScheduleModel();
            Schedule schedule = allocation.getSchedule();
            scheduleModel.setId(schedule.getId());
            scheduleModel.setRecurring(schedule.isRecurring());
            scheduleModel.setStartDateTime(schedule.getStartDateTime());
            if (schedule.getEndDateTime() != null) {
                scheduleModel.setEndDateTime(schedule.getEndDateTime());
            }
            if (schedule.getRecurringBasis() != null) {
                scheduleModel.setRecurringBasis(schedule.getRecurringBasis().toString());
            }
            scheduleModel.setRecurringDates(schedule.getRecurringDates());
            List<Integer> recurringWeekdays = new ArrayList<>();
            for (DayOfWeek dayOfWeek :
                    schedule.getRecurringWeekdays()) {
                recurringWeekdays.add(dayOfWeek.getValue());
            }
            scheduleModel.setRecurringWeekdays(recurringWeekdays);
            if (schedule.getRecurringStartTime() != null) {
                scheduleModel.setRecurringStartTime(schedule.getRecurringStartTime());
            }
            if (schedule.getRecurringEndTime() != null) {
                scheduleModel.setRecurringEndTime(schedule.getRecurringEndTime());
            }

            allocationModelForEmployee.setSchedule(scheduleModel);
            seatModelWithHighlighting.getAllocations().add(allocationModelForEmployee);
        }

        return seatModelWithHighlighting;
    }

    public SeatModelForAllocation convertSeatToSeatModelForAllocation(Seat seat) {
        SeatModelForAllocation seatModelForAllocation = new SeatModelForAllocation();

        // Basic info
        seatModelForAllocation.setId(seat.getId());
        seatModelForAllocation.setCode(seat.getCode());
        seatModelForAllocation.setType(seat.getType().toString());

        // Function, business unit and team
        CompanyFunction function = seat.getFunctionAssigned();
        if (function != null) {
            GroupModel functionModel = new GroupModel();
            functionModel.setId(function.getId());
            functionModel.setName(function.getObjectName());
            functionModel.setCode(function.getCode());
            seatModelForAllocation.setFunctionAssigned(functionModel);
        }

        BusinessUnit businessUnit = seat.getBusinessUnitAssigned();
        if (businessUnit != null) {
            GroupModel businessUnitModel = new GroupModel();
            businessUnitModel.setId(businessUnit.getId());
            businessUnitModel.setName(businessUnit.getObjectName());
            businessUnitModel.setCode(businessUnit.getCode());
            seatModelForAllocation.setBusinessUnitAssigned(businessUnitModel);
        }

        Team team = seat.getTeamAssigned();
        if (team != null) {
            GroupModel teamModel = new GroupModel();
            teamModel.setId(team.getId());
            teamModel.setName(team.getObjectName());
            teamModel.setCode(team.getCode());
            seatModelForAllocation.setTeamAssigned(teamModel);
        }


        // Current occupancy
        SeatAllocationModelForEmployee currentOccupancyModel = new SeatAllocationModelForEmployee();
        SeatAllocation currentOccupancy = seat.getCurrentOccupancy();
        if (currentOccupancy != null) {
            currentOccupancyModel.setId(currentOccupancy.getId());
            currentOccupancyModel.setType(currentOccupancy.getAllocationType().toString());

            EmployeeModel employeeModel = new EmployeeModel(currentOccupancy.getEmployee().getId(), currentOccupancy.getEmployee().getFullName());
            currentOccupancyModel.setEmployee(employeeModel);

            ScheduleModel scheduleModel = new ScheduleModel();
            Schedule schedule = currentOccupancy.getSchedule();
            scheduleModel.setId(schedule.getId());
            scheduleModel.setRecurring(schedule.isRecurring());
            scheduleModel.setStartDateTime(schedule.getStartDateTime());
            if (schedule.getEndDateTime() != null) {
                scheduleModel.setEndDateTime(schedule.getEndDateTime());
            }
            if (schedule.getRecurringBasis() != null) {
                scheduleModel.setRecurringBasis(schedule.getRecurringBasis().toString());
            }
            scheduleModel.setRecurringDates(schedule.getRecurringDates());
            List<Integer> recurringWeekdays = new ArrayList<>();
            for (DayOfWeek dayOfWeek :
                    schedule.getRecurringWeekdays()) {
                recurringWeekdays.add(dayOfWeek.getValue());
            }
            scheduleModel.setRecurringWeekdays(recurringWeekdays);
            if (schedule.getRecurringStartTime() != null) {
                scheduleModel.setRecurringStartTime(schedule.getRecurringStartTime());
            }
            if (schedule.getRecurringEndTime() != null) {
                scheduleModel.setRecurringEndTime(schedule.getRecurringEndTime());
            }
            currentOccupancyModel.setSchedule(scheduleModel);
        }
        seatModelForAllocation.setCurrentOccupancy(currentOccupancyModel);

        // Active allocations
        for (SeatAllocation allocation :
                seat.getActiveSeatAllocations()) {
            SeatAllocationModelForEmployee allocationModelForEmployee = new SeatAllocationModelForEmployee();
            allocationModelForEmployee.setId(allocation.getId());
            allocationModelForEmployee.setType(allocation.getAllocationType().toString());

            EmployeeModel employeeModel = new EmployeeModel(allocation.getEmployee().getId(), allocation.getEmployee().getFullName());
            allocationModelForEmployee.setEmployee(employeeModel);

            ScheduleModel scheduleModel = new ScheduleModel();
            Schedule schedule = allocation.getSchedule();
            scheduleModel.setId(schedule.getId());
            scheduleModel.setRecurring(schedule.isRecurring());
            scheduleModel.setStartDateTime(schedule.getStartDateTime());
            if (schedule.getEndDateTime() != null) {
                scheduleModel.setEndDateTime(schedule.getEndDateTime());
            }
            if (schedule.getRecurringBasis() != null) {
                scheduleModel.setRecurringBasis(schedule.getRecurringBasis().toString());
            }
            scheduleModel.setRecurringDates(schedule.getRecurringDates());
            List<Integer> recurringWeekdays = new ArrayList<>();
            for (DayOfWeek dayOfWeek :
                    schedule.getRecurringWeekdays()) {
                recurringWeekdays.add(dayOfWeek.getValue());
            }
            scheduleModel.setRecurringWeekdays(recurringWeekdays);
            if (schedule.getRecurringStartTime() != null) {
                scheduleModel.setRecurringStartTime(schedule.getRecurringStartTime());
            }
            if (schedule.getRecurringEndTime() != null) {
                scheduleModel.setRecurringEndTime(schedule.getRecurringEndTime());
            }

            allocationModelForEmployee.setSchedule(scheduleModel);
            seatModelForAllocation.getAllocations().add(allocationModelForEmployee);
        }

        return seatModelForAllocation;
    }



    // ---------------------------------- Schedule -----------------------------------

    public Schedule convertScheduleModelToSchedule(ScheduleModel scheduleModel, String allocationType) throws EntityModelConversionException {
        Schedule schedule = new Schedule();
        if (scheduleModel.getId() != null) {
            schedule.setId(scheduleModel.getId());
        }

        if (scheduleModel.getStartDateTime() != null) {
            schedule.setStartDateTime(scheduleModel.getStartDateTime());
        } else {
            throw new EntityModelConversionException("Conversion from Schedule Model to Schedule failed: start date time is required for a schedule!");
        }

        if (scheduleModel.getRecurringBasis() != null) {
            String recurringBasisString = scheduleModel.getRecurringBasis();
            if (!recurringBasisString.equals("EVERYDAY") && !recurringBasisString.equals("EVERYWEEK")) {
                throw new EntityModelConversionException("Conversion from Schedule Model to Schedule failed: invalid recurring basis!");
            }


            try {
                ScheduleRecurringBasisEnum recurringBasisEnum = ScheduleRecurringBasisEnum.valueOf(scheduleModel.getRecurringBasis());
                scheduleService.validateScheduleRecurringBasis(recurringBasisEnum, scheduleModel);
                schedule.setRecurring(true);
                schedule.setRecurringBasis(recurringBasisEnum);

                if (allocationType.equals("FIXED")) {
                    // May be permanent or temporary
                    if (scheduleModel.getEndDateTime() != null) {
                        schedule.setEndDateTime(scheduleModel.getEndDateTime());
                    }
                } else if (allocationType.equals("SHARED")) {
                    // May be permanent or temporary
                    if (scheduleModel.getEndDateTime() != null) {
                        schedule.setEndDateTime(scheduleModel.getEndDateTime());
                    }

                    if (schedule.getRecurringBasis().equals(ScheduleRecurringBasisEnum.EVERYWEEK)) {
                        if (scheduleModel.getRecurringWeekdays() != null && scheduleModel.getRecurringWeekdays().size() > 0) {
                            for (Integer weekDay :
                                    scheduleModel.getRecurringWeekdays()) {
                                if (weekDay < 1 || weekDay > 7) {
                                    throw new EntityModelConversionException("Conversion from Schedule Model to Schedule failed: invalid recurring weekday of " + weekDay + "!");
                                }
                                schedule.getRecurringWeekdays().add(DayOfWeek.of(weekDay));
                            }
                        }

                        if (scheduleModel.getRecurringStartTime() != null && scheduleModel.getRecurringEndTime() != null) {
                            if (scheduleModel.getRecurringStartTime().isAfter(scheduleModel.getRecurringEndTime())) {
                                throw new EntityModelConversionException("Conversion from Schedule Model to Schedule failed: invalid recurring start time and end time!");
                            }
                            schedule.setRecurringStartTime(scheduleModel.getRecurringStartTime());
                            scheduleModel.setRecurringEndTime(scheduleModel.getRecurringEndTime());
                        }
                    } else if (schedule.getRecurringBasis().equals(ScheduleRecurringBasisEnum.EVERYDAY)) {
                        if (scheduleModel.getRecurringStartTime() != null && scheduleModel.getRecurringEndTime() != null) {
                            if (scheduleModel.getRecurringStartTime().isAfter(scheduleModel.getRecurringEndTime())) {
                                throw new EntityModelConversionException("Conversion from Schedule Model to Schedule failed: invalid recurring start time and end time!");
                            }
                            schedule.setRecurringStartTime(scheduleModel.getRecurringStartTime());
                            scheduleModel.setRecurringEndTime(scheduleModel.getRecurringEndTime());
                        } else {
                            throw new EntityModelConversionException("Conversion from Schedule Model to Schedule failed: both recurring start time and end time are required!");
                        }
                    }

                } else if (allocationType.equals("HOTDESK")) {
                    if (scheduleModel.getEndDateTime() != null) {
                        schedule.setEndDateTime(scheduleModel.getEndDateTime());
                    } else {
                        throw new EntityModelConversionException("Conversion from Schedule Model to Schedule failed: end date time is required!");
                    }
                } else {
                    throw new EntityModelConversionException("Conversion from Schedule Model to Schedule failed: invalid allocation type!");
                }
            } catch (ScheduleValidationException ex) {
                throw new EntityModelConversionException(ex.getMessage());
            }
        }

        return schedule;
    }
}
