package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.admin.repository.BusinessUnitRepository;
import capstone.is4103capstone.admin.service.CompanyFunctionService;
import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.admin.service.TeamService;
import capstone.is4103capstone.entities.*;
import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatAllocation;
import capstone.is4103capstone.entities.seat.SeatAllocationRequest;
import capstone.is4103capstone.entities.seat.SeatRequestAdminMatch;
import capstone.is4103capstone.general.model.ApprovalTicketModel;
import capstone.is4103capstone.seat.model.EmployeeModel;
import capstone.is4103capstone.seat.model.GroupModel;
import capstone.is4103capstone.seat.model.ScheduleModel;
import capstone.is4103capstone.seat.model.seat.SeatModelForAllocation;
import capstone.is4103capstone.seat.model.seat.SeatModelWithHighlighting;
import capstone.is4103capstone.seat.model.seatAllocation.SeatAllocationModelForEmployee;
import capstone.is4103capstone.seat.model.seatAllocationRequest.CreateSeatAllocationRequestModel;
import capstone.is4103capstone.seat.model.seatAllocationRequest.SeatAllocationRequestModel;
import capstone.is4103capstone.seat.repository.SeatRequestAdminMatchRepository;
import capstone.is4103capstone.util.enums.ApprovalTypeEnum;
import capstone.is4103capstone.util.enums.HierarchyTypeEnum;
import capstone.is4103capstone.util.enums.ScheduleRecurringBasisEnum;
import capstone.is4103capstone.util.exception.EmployeeNotFoundException;
import capstone.is4103capstone.util.exception.EntityModelConversionException;
import capstone.is4103capstone.util.exception.ScheduleValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    // ---------------------------------- Seat Allocation -----------------------------------



    // ---------------------------------- Seat Allocation Request -----------------------------------

    // Unfinished
//    public SeatAllocationRequest convertSeatAllocationRequestModelToExistingEntity(SeatAllocationRequestModel seatAllocationRequestModel) {
//        SeatAllocationRequest seatAllocationRequest = new SeatAllocationRequest();
//        //     private String id;
//        //    private GroupModel team;
//        // private String escalatedHierarchyLevel;
//        // private String escalatedHierarchyId;
//        //    private SeatAllocationModelForEmployee seatAllocationRequested;
//        //    private EmployeeModel requester;
//        //    private List<ApprovalTicketModel> approvalTickets;
//        //    private ApprovalTicketModel currentPendingTicket;
//        //    private boolean resolved;
//        if (seatAllocationRequestModel.getId() == null || seatAllocationRequestModel.getId().trim().length() == 0) {
//            throw new EntityModelConversionException("Convert seat allocation request model to entity failed: seat allocation request ID is missing!");
//        }
//        seatAllocationRequest.setId(seatAllocationRequestModel.getId());
//
//        if (seatAllocationRequestModel.getTeam().getId() == null || seatAllocationRequestModel.getTeam().getId().trim().length() == 0) {
//            throw new EntityModelConversionException("Convert seat allocation request model to entity failed: seat allocation request ID is missing!");
//        }
//        Team team = teamService.retrieveTeamById(seatAllocationRequestModel.getTeam().getId());
//        seatAllocationRequest.setTeam(team);
//
//        if (seatAllocationRequestModel.getEscalatedHierarchyLevel() == null || seatAllocationRequestModel.getEscalatedHierarchyLevel().trim().length() == 0) {
//            throw new EntityModelConversionException("Convert seat allocation request model to entity failed: escalated hierarchy level is missing!");
//        }
//        if (seatAllocationRequestModel.getEscalatedHierarchyLevel().equals("BUSINESS_UNIT")) {
//            seatAllocationRequest.setEscalatedHierarchyLevel(HierarchyTypeEnum.BUSINESS_UNIT);
//            Optional<BusinessUnit> optionalBusinessUnit = businessUnitRepository.findByIdNonDeleted(seatAllocationRequestModel.getEscalatedHierarchyId());
//            if (!optionalBusinessUnit.isPresent()) {
//                throw new EntityModelConversionException("Convert seat allocation request model to entity failed: invalid escalated hierarchy ID!");
//            }
//            seatAllocationRequest.setEscalatedHierarchyId(seatAllocationRequestModel.getEscalatedHierarchyId());
//        } else if (seatAllocationRequestModel.getEscalatedHierarchyLevel().equals("COMPANY_FUNCTION")) {
//            seatAllocationRequest.setEscalatedHierarchyLevel(HierarchyTypeEnum.COMPANY_FUNCTION);
//            CompanyFunction companyFunction = companyFunctionService.retrieveCompanyFunctionById(seatAllocationRequestModel.getEscalatedHierarchyId());
//            seatAllocationRequest.setEscalatedHierarchyId(companyFunction.getId());
//        } else {
//            throw new EntityModelConversionException("Convert seat allocation request model to entity failed: invalid escalated hierarchy level!");
//        }
//
//        return seatAllocationRequest;
//    }

    public SeatAllocationRequestModel convertSeatAllocationRequestEntityToModel(SeatAllocationRequest seatAllocationRequest) {
        SeatAllocationRequestModel seatAllocationRequestModel = new SeatAllocationRequestModel();

        seatAllocationRequestModel.setId(seatAllocationRequest.getId());

        Team team = seatAllocationRequest.getTeam();
        GroupModel teamModel = new GroupModel();
        teamModel.setId(team.getId());
        teamModel.setName(team.getObjectName());

        seatAllocationRequestModel.setEscalatedHierarchyLevel(seatAllocationRequest.getEscalatedHierarchyLevel().toString());
        seatAllocationRequestModel.setEscalatedHierarchyId(seatAllocationRequest.getEscalatedHierarchyId());

        Employee employeeOfAllocation = seatAllocationRequest.getEmployeeOfAllocation();
        EmployeeModel employeeModelOfAllocation = new EmployeeModel();
        employeeModelOfAllocation.setId(employeeOfAllocation.getId());
        employeeModelOfAllocation.setFullName(employeeOfAllocation.getFullName());
        employeeModelOfAllocation.setUsername(employeeOfAllocation.getUserName());
        seatAllocationRequestModel.setEmployeeOfAllocation(employeeModelOfAllocation);

        seatAllocationRequestModel.setSeatAllocationSchedule(convertScheduleEntityToModel(seatAllocationRequest.getSeatAllocationSchedule()));
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
        ApprovalTicketModel currentPendingApprovalTicketModel = convertExistingApprovalTicketEntityToModel(seatAllocationRequest.getCurrentPendingTicket());
        seatAllocationRequestModel.setCurrentPendingTicket(currentPendingApprovalTicketModel);

        seatAllocationRequestModel.setResolved(seatAllocationRequest.isResolved());

        return seatAllocationRequestModel;
    }

    public ApprovalTicketModel convertExistingApprovalTicketEntityToModel(ApprovalForRequest approvalForRequest) {
        ApprovalTicketModel approvalTicketModel = new ApprovalTicketModel();
        approvalTicketModel.setId(approvalForRequest.getId());
        approvalTicketModel.setComment(approvalForRequest.getCommentByApprover());
        approvalTicketModel.setCreatedDateTime(approvalForRequest.getCreatedDateTime().toString());
        approvalTicketModel.setReviewerUsername(approvalForRequest.getApprover().getUserName());
        approvalTicketModel.setReviewedDateTime(approvalForRequest.getLastModifiedDateTime().toString());
        approvalTicketModel.setTicketResult(approvalForRequest.getApprovalStatus().toString());
        return approvalTicketModel;
    }
}
