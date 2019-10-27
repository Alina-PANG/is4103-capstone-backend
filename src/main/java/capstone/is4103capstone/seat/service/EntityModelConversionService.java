package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.admin.repository.BusinessUnitRepository;
import capstone.is4103capstone.admin.service.CompanyFunctionService;
import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.admin.service.OfficeService;
import capstone.is4103capstone.admin.service.TeamService;
import capstone.is4103capstone.entities.*;
import capstone.is4103capstone.entities.seat.*;
import capstone.is4103capstone.general.model.ApprovalTicketModel;
import capstone.is4103capstone.seat.model.EmployeeModel;
import capstone.is4103capstone.seat.model.schedule.EmployeeOfficeWorkingScheduleModel;
import capstone.is4103capstone.seat.model.GroupModel;
import capstone.is4103capstone.seat.model.schedule.ScheduleModel;
import capstone.is4103capstone.seat.model.seat.SeatModelForAllocation;
import capstone.is4103capstone.seat.model.seat.SeatModelWithHighlighting;
import capstone.is4103capstone.seat.model.seatAllocation.SeatAllocationModelForEmployee;
import capstone.is4103capstone.seat.model.seatAllocationRequest.SeatAllocationRequestModel;
import capstone.is4103capstone.seat.model.seatDemandForecast.MonthlySeatForecastModelForTeam;
import capstone.is4103capstone.seat.repository.SeatRequestAdminMatchRepository;
import capstone.is4103capstone.util.enums.ApprovalTypeEnum;
import capstone.is4103capstone.util.enums.ScheduleRecurringBasisEnum;
import capstone.is4103capstone.util.exception.EntityModelConversionException;
import capstone.is4103capstone.util.exception.ScheduleValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EntityModelConversionService {

    @Autowired
    private SeatRequestAdminMatchRepository seatRequestAdminMatchRepository;
    @Autowired
    private BusinessUnitRepository businessUnitRepository;

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private CompanyFunctionService companyFunctionService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private  SeatAllocationRequestService seatAllocationRequestService;


    // ---------------------------------- Seat -----------------------------------

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
        seatModelForAllocation.setSeqNum(seat.getSerialNumber());

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

    // Warning: The conversion does not check whether the start date time of the schedule is a historical date time
    public Schedule convertScheduleModelToSeatAllocationSchedule(ScheduleModel scheduleModel, String allocationType) throws EntityModelConversionException {
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
                        if (scheduleModel.getStartDateTime().after(scheduleModel.getEndDateTime())) {
                            throw new EntityModelConversionException("Conversion from Schedule Model to Schedule failed: start date time cannot be after end date time!");
                        }
                        schedule.setEndDateTime(scheduleModel.getEndDateTime());
                    }
                } else if (allocationType.equals("SHARED")) {
                    // May be permanent or temporary
                    if (scheduleModel.getEndDateTime() != null) {
                        if (scheduleModel.getStartDateTime().after(scheduleModel.getEndDateTime())) {
                            throw new EntityModelConversionException("Conversion from Schedule Model to Schedule failed: start date time cannot be after end date time!");
                        }
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
                            schedule.setRecurringEndTime(scheduleModel.getRecurringEndTime());
                        }
                    } else if (schedule.getRecurringBasis().equals(ScheduleRecurringBasisEnum.EVERYDAY)) {
                        if (scheduleModel.getRecurringStartTime() != null && scheduleModel.getRecurringEndTime() != null) {
                            if (scheduleModel.getRecurringStartTime().isAfter(scheduleModel.getRecurringEndTime())) {
                                throw new EntityModelConversionException("Conversion from Schedule Model to Schedule failed: invalid recurring start time and end time!");
                            }
                            schedule.setRecurringStartTime(scheduleModel.getRecurringStartTime());
                            schedule.setRecurringEndTime(scheduleModel.getRecurringEndTime());
                        } else {
                            throw new EntityModelConversionException("Conversion from Schedule Model to Schedule failed: both recurring start time and end time are required!");
                        }
                    }

                } else if (allocationType.equals("HOTDESK")) {
                    if (scheduleModel.getEndDateTime() != null) {
                        if (scheduleModel.getStartDateTime().after(scheduleModel.getEndDateTime())) {
                            throw new EntityModelConversionException("Conversion from Schedule Model to Schedule failed: start date time cannot be after end date time!");
                        }
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

    public ScheduleModel convertScheduleEntityToModel(Schedule schedule) {
        ScheduleModel scheduleModel = new ScheduleModel();
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
        return scheduleModel;
    }

    public EmployeeOfficeWorkingSchedule convertNewEmployeeOfficeWorkingScheduleToEntity(EmployeeOfficeWorkingScheduleModel employeeOfficeWorkingScheduleModel)
            throws EntityModelConversionException{
        EmployeeOfficeWorkingSchedule employeeOfficeWorkingSchedule = new EmployeeOfficeWorkingSchedule();

        if (employeeOfficeWorkingScheduleModel.getEmployee() == null) {
            throw new EntityModelConversionException("Incomplete input: employee info is missing!");
        }
        EmployeeModel employeeModel = employeeOfficeWorkingScheduleModel.getEmployee();
        if (employeeModel.getId() == null || employeeModel.getId().trim().length() == 0) {
            throw new EntityModelConversionException("Incomplete input: employee info is missing!");
        }
        Employee employee = employeeService.retrieveEmployeeById(employeeModel.getId());
        employeeOfficeWorkingSchedule.setEmployee(employee);

        if (employeeOfficeWorkingScheduleModel.getOffice() == null) {
            throw new EntityModelConversionException("Incomplete input: office info is missing!");
        }
        GroupModel officeModel = employeeOfficeWorkingScheduleModel.getOffice();
        if (officeModel.getId() == null || officeModel.getId().trim().length() == 0) {
            throw new EntityModelConversionException("Incomplete input: office info is missing!");
        }
        try {
            Office office = officeService.getOfficeEntityByUuid(officeModel.getId());
            employeeOfficeWorkingSchedule.setOffice(office);

            if (employeeOfficeWorkingScheduleModel.getSchedules() == null || employeeOfficeWorkingScheduleModel.getSchedules().size() == 0) {
                throw new EntityModelConversionException("Incomplete input: schedule info is missing!");
            }

            List<Schedule> schedules = new ArrayList<>();
            for (ScheduleModel scheduleModel :
                    employeeOfficeWorkingScheduleModel.getSchedules()) {
                schedules.add(convertNewScheduleModelToEmployeeOfficeWorkingSchedule(scheduleModel, employee.getEmployeeType().toString()));
            }
            employeeOfficeWorkingSchedule.setSchedules(schedules);

            return employeeOfficeWorkingSchedule;
        } catch (EntityModelConversionException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new EntityModelConversionException(ex.getMessage());
        }

    }

    public Schedule convertNewScheduleModelToEmployeeOfficeWorkingSchedule(ScheduleModel scheduleModel, String employeeType) {
        Schedule schedule = new Schedule();
        //    private boolean isRecurring = false;
        //    private ScheduleRecurringBasisEnum recurringBasis;
        //    @ElementCollection(targetClass=DayOfWeek.class)
        //    private List<DayOfWeek> recurringWeekdays = new ArrayList<>();
        //    @ElementCollection(targetClass=Date.class)
        //    private List<Date> recurringDates = new ArrayList<>();
        //    private LocalTime recurringStartTime;
        //    private LocalTime recurringEndTime;
        //    @Temporal(TemporalType.TIMESTAMP)
        //    private Date startDateTime;
        //    @Temporal(TemporalType.TIMESTAMP)
        //    private Date endDateTime;

        // Check employee type and assign attributes accordingly
        if (scheduleModel.getStartDateTime() == null) {
            throw new EntityModelConversionException("Incomplete input: schedule's start date time is missing!");
        }
        Date startDateTime = scheduleModel.getStartDateTime();
        schedule.setStartDateTime(startDateTime);
        if (employeeType.equals("PERMANENT")) {

            // Do nothing

        } else if (employeeType.equals("TEMPORARY")) {

            if (scheduleModel.getEndDateTime() == null) {
                throw new EntityModelConversionException("Incomplete input: schedule's end date time is missing!");
            }
            Date endDateTime = scheduleModel.getEndDateTime();
            if (!endDateTime.after(startDateTime)) {
                throw new EntityModelConversionException("Invalid input: schedule's end date time must be after the start date time!");
            }
            schedule.setEndDateTime(endDateTime);

        } else if (employeeType.equals("WORKING_FROM_HOME")) {

            if (scheduleModel.getRecurringBasis() == null || scheduleModel.getRecurringBasis().trim().length() == 0) {
                throw new EntityModelConversionException("Incomplete input: schedule's recurring basis is missing!");
            }
            ScheduleRecurringBasisEnum recurringBasisEnum = ScheduleRecurringBasisEnum.valueOf(scheduleModel.getRecurringBasis());
            scheduleService.validateScheduleRecurringBasis(recurringBasisEnum, scheduleModel);
            schedule.setRecurring(true);
            schedule.setRecurringBasis(recurringBasisEnum);

            // May be permanent or temporary
            if (scheduleModel.getEndDateTime() != null) {
                if (scheduleModel.getStartDateTime().after(scheduleModel.getEndDateTime())) {
                    throw new EntityModelConversionException("Invalid input: start date time cannot be after end date time!");
                }
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
                        throw new EntityModelConversionException("Conversion from Schedule Model to Schedule failed: recurring start time must be before the end time!");
                    }
                    schedule.setRecurringStartTime(scheduleModel.getRecurringStartTime());
                    schedule.setRecurringEndTime(scheduleModel.getRecurringEndTime());
                } else {
                    throw new EntityModelConversionException("Incomplete input: either the recurring start time or end time is missing!");
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

        } else {
            throw new EntityModelConversionException("Invalid input: invalid employee type (must be either 'PERMANENT', 'TEMPORARY' or 'WORKING_FROM_HOME')!");
        }

        return  schedule;
    }

    public EmployeeOfficeWorkingScheduleModel convertEmployeeOfficeWorkingScheduleEntityToModel(EmployeeOfficeWorkingSchedule employeeOfficeWorkingSchedule) {
        EmployeeOfficeWorkingScheduleModel employeeOfficeWorkingScheduleModel = new EmployeeOfficeWorkingScheduleModel();
        employeeOfficeWorkingScheduleModel.setId(employeeOfficeWorkingSchedule.getId());

        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setId(employeeOfficeWorkingSchedule.getEmployee().getId());
        employeeModel.setFullName(employeeOfficeWorkingSchedule.getEmployee().getFullName());
        employeeOfficeWorkingScheduleModel.setEmployee(employeeModel);

        GroupModel officeModel = new GroupModel();
        officeModel.setId(employeeOfficeWorkingSchedule.getOffice().getId());
        officeModel.setName(employeeOfficeWorkingSchedule.getOffice().getObjectName());
        officeModel.setCode(employeeOfficeWorkingSchedule.getOffice().getCode());
        employeeOfficeWorkingScheduleModel.setOffice(officeModel);

        List<ScheduleModel> scheduleModels = new ArrayList<>();
        for (Schedule schedule :
                employeeOfficeWorkingSchedule.getSchedules()) {
            scheduleModels.add(convertScheduleEntityToModel(schedule));
        }
        employeeOfficeWorkingScheduleModel.setSchedules(scheduleModels);

        return employeeOfficeWorkingScheduleModel;
    }


    // ---------------------------------- Seat Allocation Request -----------------------------------

    public SeatAllocationRequestModel convertSeatAllocationRequestEntityToModel(SeatAllocationRequest seatAllocationRequest) {
        SeatAllocationRequestModel seatAllocationRequestModel = new SeatAllocationRequestModel();

        seatAllocationRequestModel.setId(seatAllocationRequest.getId());
        seatAllocationRequestModel.setRequestNumber(seatAllocationRequest.getCode());
        seatAllocationRequestModel.setCreatedDateTime(seatAllocationRequest.getCreatedDateTime());

        Team team = seatAllocationRequest.getTeam();
        GroupModel teamModel = new GroupModel();
        teamModel.setId(team.getId());
        teamModel.setCode(team.getCode());
        teamModel.setName(team.getObjectName());

        Office office = team.getOffice();
        GroupModel officeModel = new GroupModel();
        officeModel.setId(office.getId());
        officeModel.setCode(office.getCode());
        officeModel.setName(office.getObjectName());

        seatAllocationRequestModel.setEscalatedHierarchyLevel(seatAllocationRequest.getEscalatedHierarchyLevel().toString());
        seatAllocationRequestModel.setEscalatedHierarchyId(seatAllocationRequest.getEscalatedHierarchyId());
        seatAllocationRequestModel.setTeam(teamModel);
        seatAllocationRequestModel.setOffice(officeModel);

        Employee employeeOfAllocation = seatAllocationRequest.getEmployeeOfAllocation();
        EmployeeModel employeeModelOfAllocation = new EmployeeModel();
        employeeModelOfAllocation.setId(employeeOfAllocation.getId());
        employeeModelOfAllocation.setFullName(employeeOfAllocation.getFullName());
        employeeModelOfAllocation.setUsername(employeeOfAllocation.getUserName());
        seatAllocationRequestModel.setEmployeeOfAllocation(employeeModelOfAllocation);

        List<ScheduleModel> scheduleModels = new ArrayList<>();
        for (Schedule schedule :
                seatAllocationRequest.getSeatAllocationSchedules()) {
            scheduleModels.add(convertScheduleEntityToModel(schedule));
        }
        seatAllocationRequestModel.setSeatAllocationSchedules(scheduleModels);
        seatAllocationRequestModel.setAllocationType(seatAllocationRequest.getAllocationType().toString());

        Employee requester = seatAllocationRequest.getRequester();
        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setId(requester.getId());
        employeeModel.setFullName(requester.getFullName());
        employeeModel.setUsername(requester.getUserName());
        seatAllocationRequestModel.setRequester(employeeModel);

        for (ApprovalForRequest approvalForRequest :
                seatAllocationRequest.getApprovalTickets()) {
            seatAllocationRequestModel.getApprovalTickets().add(convertExistingApprovalTicketEntityToModel(approvalForRequest));
        }

        if (seatAllocationRequest.getCurrentPendingTicket() != null) {
            ApprovalTicketModel currentPendingApprovalTicketModel = convertExistingApprovalTicketEntityToModel(seatAllocationRequest.getCurrentPendingTicket());
            seatAllocationRequestModel.setCurrentPendingTicket(currentPendingApprovalTicketModel);
        }

        seatAllocationRequestModel.setResolved(seatAllocationRequest.isResolved());
        List<SeatAllocationModelForEmployee> seatAllocationModelsForEmployee = new ArrayList<>();
        for (SeatAllocation allocation :
                seatAllocationRequest.getResultedSeatAllocations()) {

            SeatAllocationModelForEmployee allocationModelForEmployee = new SeatAllocationModelForEmployee();
            allocationModelForEmployee.setId(allocation.getId());
            allocationModelForEmployee.setType(allocation.getAllocationType().toString());

            EmployeeModel employeeModel2 = new EmployeeModel(allocation.getEmployee().getId(), allocation.getEmployee().getFullName());
            allocationModelForEmployee.setEmployee(employeeModel2);

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
            seatAllocationModelsForEmployee.add(allocationModelForEmployee);
        }
        seatAllocationRequestModel.setResultedSeatAllocations(seatAllocationModelsForEmployee);

        return seatAllocationRequestModel;
    }

    public ApprovalTicketModel convertExistingApprovalTicketEntityToModel(ApprovalForRequest approvalForRequest) {
        ApprovalTicketModel approvalTicketModel = new ApprovalTicketModel();
        approvalTicketModel.setId(approvalForRequest.getId());
        if (approvalForRequest.getCode() != null) {
            approvalTicketModel.setTicketNumber(approvalForRequest.getCode());
        }
        approvalTicketModel.setApproverComment(approvalForRequest.getCommentByApprover());
        approvalTicketModel.setRequesterComment(approvalForRequest.getCommentByRequester());
        approvalTicketModel.setCreatedDateTime(approvalForRequest.getCreatedDateTime().toString());
        approvalTicketModel.setReviewerUsername(approvalForRequest.getApprover().getUserName());
        approvalTicketModel.setReviewedDateTime(approvalForRequest.getLastModifiedDateTime().toString());
        approvalTicketModel.setTicketResult(approvalForRequest.getApprovalStatus());
        approvalTicketModel.setRequestedItemId(approvalForRequest.getRequestedItemId());
        approvalTicketModel.setRequestType(approvalForRequest.getApprovalType());
        return approvalTicketModel;
    }

    public ApprovalForRequest convertApprovalTicketModelToNewEntity (ApprovalTicketModel approvalTicketModel) {
        ApprovalForRequest approvalForRequest = new ApprovalForRequest();

        ApprovalTypeEnum requestType = approvalTicketModel.getRequestType();
        approvalForRequest.setApprovalType(requestType);
        if (!requestType.equals(ApprovalTypeEnum.SEAT_ALLOCATION)) {
            throw new EntityModelConversionException("Mismatched approval type!");
        }

        if (approvalTicketModel.getReviewerUsername() == null) {
            throw new EntityModelConversionException("Missing reviewer username!");
        }
        String approverUserName = approvalTicketModel.getReviewerUsername();
        Employee approver = employeeService.getEmployeeByUsername(approverUserName);
        approvalForRequest.setApprover(approver);

        if (approvalTicketModel.getRequestedItemId() == null || approvalTicketModel.getRequestedItemId().trim().length() == 0) {
            throw new EntityModelConversionException("Missing request ID!");
        }
        SeatAllocationRequest seatAllocationRequest = seatAllocationRequestService.retrieveSeatAllocationRequestById(approvalTicketModel.getRequestedItemId());
        approvalForRequest.setRequestedItemId(seatAllocationRequest.getId());
        approvalForRequest.setCommentByRequester(approvalTicketModel.getRequesterComment());
        approvalForRequest.setRequester(seatAllocationRequest.getRequester());
        approvalForRequest.setApprovalStatus(approvalTicketModel.getTicketResult());
        return approvalForRequest;
    }



    // ---------------------------------- Seat Demand Forecast -----------------------------------

    public MonthlySeatForecastModelForTeam createMonthlyForecastModelForTeam(Team team, YearMonth yearMonth, Integer inventoryCount,
                                                                             Integer occupancyCount, List<Employee> employeesWhoNeedSeats,
                                                                             List<Employee> employeesWithUnnecessaryAllocations) {

        MonthlySeatForecastModelForTeam monthlySeatForecastModelForTeam = new MonthlySeatForecastModelForTeam();

        GroupModel teamModel = new GroupModel();
        teamModel.setId(team.getId());
        teamModel.setName(team.getObjectName());
        teamModel.setCode(team.getCode());
        monthlySeatForecastModelForTeam.setTeam(teamModel);

        monthlySeatForecastModelForTeam.setYearMonth(yearMonth.toString());

        List<EmployeeModel> employeeModelsWhoNeedSeats = new ArrayList<>();
        for (Employee employee :
                employeesWhoNeedSeats) {
            EmployeeModel employeeModel = new EmployeeModel();
            employeeModel.setId(employee.getId());
            employeeModel.setFullName(employee.getFullName());
            employeeModelsWhoNeedSeats.add(employeeModel);
        }
        monthlySeatForecastModelForTeam.setEmployeesWhoNeedSeats(employeeModelsWhoNeedSeats);

        List<EmployeeModel> employeeModelsWithUnnecessaryAllocations = new ArrayList<>();
        for (Employee employee :
                employeesWithUnnecessaryAllocations) {
            EmployeeModel employeeModel = new EmployeeModel();
            employeeModel.setId(employee.getId());
            employeeModel.setFullName(employee.getFullName());
            employeeModelsWithUnnecessaryAllocations.add(employeeModel);
        }
        monthlySeatForecastModelForTeam.setEmployeesWithUnnecessaryAllocations(employeeModelsWithUnnecessaryAllocations);

        monthlySeatForecastModelForTeam.setInventoryCount(inventoryCount);
        monthlySeatForecastModelForTeam.setNumOfSeatsOccupied(occupancyCount);

        return  monthlySeatForecastModelForTeam;
    }
}
