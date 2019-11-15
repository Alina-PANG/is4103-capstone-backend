package capstone.is4103capstone.seat.service;

import capstone.is4103capstone.admin.repository.*;
import capstone.is4103capstone.entities.*;
import capstone.is4103capstone.entities.helper.DateHelper;
import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatAdminMatch;
import capstone.is4103capstone.entities.seat.SeatMap;
import capstone.is4103capstone.entities.seat.SeatUtilisationLog;
import capstone.is4103capstone.seat.repository.SeatAdminMatchRepository;
import capstone.is4103capstone.seat.repository.SeatMapRepository;
import capstone.is4103capstone.seat.repository.SeatRepository;
import capstone.is4103capstone.seat.repository.SeatUtilisationLogRepository;
import capstone.is4103capstone.util.enums.HierarchyTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SeatInitializationService {

    @Autowired
    private SeatMapRepository seatMapRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private SeatUtilisationLogRepository seatUtilisationLogRepository;
    @Autowired
    private SeatAdminMatchRepository seatAdminMatchRepository;
    @Autowired
    private OfficeRepository officeRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private BusinessUnitRepository businessUnitRepository;
    @Autowired
    private FunctionRepository functionRepository;
    @Autowired
    private EmployeeRepository employeeRepository;


    public void initialiseSeatManagement() {
        createSeatmaps();
        initialiseSeatUtilisationLog();
    }

    private void createSeatmaps() {
        List<SeatMap> seatMaps = seatMapRepository.findAllUndeleted();
        if (!seatMaps.isEmpty()) {
            return;
        }

        Optional<Office> office = officeRepository.findByName("One Raffles Quay");
        Employee admin = employeeRepository.findEmployeeByUserName("admin");
        if (office.isPresent()) {
            try {

                // ORQ Floor 26
                SeatMap newSeatMapLvl26 = new SeatMap("SG-ORQ-26","SG-ORQ-26",":APAC:SG:ORQ:SM-SG-ORQ-26", office.get(), "26");

                Seat row1Col1 = new Seat("SG-ORQ-26-01", "SG-ORQ-26-01", ":APAC:SG:ORQ:ST-SG-ORQ-26-01", 0, 0, newSeatMapLvl26, 1);
                Seat row1Col2 = new Seat("SG-ORQ-26-02", "SG-ORQ-26-02", ":APAC:SG:ORQ:ST-SG-ORQ-26-02", 100, 0, newSeatMapLvl26, 2);
                Seat row1Col3 = new Seat("SG-ORQ-26-03", "SG-ORQ-26-03", ":APAC:SG:ORQ:ST-SG-ORQ-26-03", 200, 0, newSeatMapLvl26, 3);
                Seat row1Col4 = new Seat("SG-ORQ-26-04", "SG-ORQ-26-04", ":APAC:SG:ORQ:ST-SG-ORQ-26-04", 300, 0, newSeatMapLvl26, 4);
                Seat row1Col5 = new Seat("SG-ORQ-26-05", "SG-ORQ-26-05", ":APAC:SG:ORQ:ST-SG-ORQ-26-05", 400, 0, newSeatMapLvl26, 5);
                Seat row1Col6 = new Seat("SG-ORQ-26-06", "SG-ORQ-26-06", ":APAC:SG:ORQ:ST-SG-ORQ-26-06", 500, 0, newSeatMapLvl26, 6);

                Seat row2Col1 = new Seat("SG-ORQ-26-07", "SG-ORQ-26-07", ":APAC:SG:ORQ:ST-SG-ORQ-26-07", 0, 100, newSeatMapLvl26, 7);
                Seat row2Col2 = new Seat("SG-ORQ-26-08", "SG-ORQ-26-08", ":APAC:SG:ORQ:ST-SG-ORQ-26-08", 100, 100, newSeatMapLvl26, 8);
                Seat row2Col3 = new Seat("SG-ORQ-26-09", "SG-ORQ-26-09", ":APAC:SG:ORQ:ST-SG-ORQ-26-09", 200, 100, newSeatMapLvl26, 9);
                Seat row2Col4 = new Seat("SG-ORQ-26-10", "SG-ORQ-26-10", ":APAC:SG:ORQ:ST-SG-ORQ-26-10", 300, 100, newSeatMapLvl26, 10);
                Seat row2Col5 = new Seat("SG-ORQ-26-11", "SG-ORQ-26-11", ":APAC:SG:ORQ:ST-SG-ORQ-26-11", 400, 100, newSeatMapLvl26, 11);
                Seat row2Col6 = new Seat("SG-ORQ-26-12", "SG-ORQ-26-12", ":APAC:SG:ORQ:ST-SG-ORQ-26-12", 500, 100, newSeatMapLvl26, 12);

                Seat row3Col1 = new Seat("SG-ORQ-26-13", "SG-ORQ-26-13", ":APAC:SG:ORQ:ST-SG-ORQ-26-13", 0, 200, newSeatMapLvl26, 13);
                Seat row3Col2 = new Seat("SG-ORQ-26-14", "SG-ORQ-26-14", ":APAC:SG:ORQ:ST-SG-ORQ-26-14", 100, 200, newSeatMapLvl26, 14);
                Seat row3Col3 = new Seat("SG-ORQ-26-15", "SG-ORQ-26-15", ":APAC:SG:ORQ:ST-SG-ORQ-26-15", 200, 200, newSeatMapLvl26, 15);
                Seat row3Col4 = new Seat("SG-ORQ-26-16", "SG-ORQ-26-16", ":APAC:SG:ORQ:ST-SG-ORQ-26-16", 300, 200, newSeatMapLvl26, 16);
                Seat row3Col5 = new Seat("SG-ORQ-26-17", "SG-ORQ-26-17", ":APAC:SG:ORQ:ST-SG-ORQ-26-17", 400, 200, newSeatMapLvl26, 17);
                Seat row3Col6 = new Seat("SG-ORQ-26-18", "SG-ORQ-26-18", ":APAC:SG:ORQ:ST-SG-ORQ-26-18", 500, 200, newSeatMapLvl26, 18);

                Seat row4Col1 = new Seat("SG-ORQ-26-19", "SG-ORQ-26-19", ":APAC:SG:ORQ:ST-SG-ORQ-26-19", 0, 300, newSeatMapLvl26, 19);
                Seat row4Col2 = new Seat("SG-ORQ-26-20", "SG-ORQ-26-20", ":APAC:SG:ORQ:ST-SG-ORQ-26-20", 100, 300, newSeatMapLvl26, 20);
                Seat row4Col3 = new Seat("SG-ORQ-26-21", "SG-ORQ-26-21", ":APAC:SG:ORQ:ST-SG-ORQ-26-21", 200, 300, newSeatMapLvl26, 21);
                Seat row4Col4 = new Seat("SG-ORQ-26-22", "SG-ORQ-26-22", ":APAC:SG:ORQ:ST-SG-ORQ-26-22", 300, 300, newSeatMapLvl26, 22);
                Seat row4Col5 = new Seat("SG-ORQ-26-23", "SG-ORQ-26-23", ":APAC:SG:ORQ:ST-SG-ORQ-26-23", 400, 300, newSeatMapLvl26, 23);
                Seat row4Col6 = new Seat("SG-ORQ-26-24", "SG-ORQ-26-24", ":APAC:SG:ORQ:ST-SG-ORQ-26-24", 500, 300, newSeatMapLvl26, 24);

                Seat row5Col1 = new Seat("SG-ORQ-26-25", "SG-ORQ-26-25", ":APAC:SG:ORQ:ST-SG-ORQ-26-25", 0, 400, newSeatMapLvl26, 25);
                Seat row5Col2 = new Seat("SG-ORQ-26-26", "SG-ORQ-26-26", ":APAC:SG:ORQ:ST-SG-ORQ-26-26", 100, 400, newSeatMapLvl26, 26);
                Seat row5Col3 = new Seat("SG-ORQ-26-27", "SG-ORQ-26-27", ":APAC:SG:ORQ:ST-SG-ORQ-26-27", 200, 400, newSeatMapLvl26, 27);
                Seat row5Col4 = new Seat("SG-ORQ-26-28", "SG-ORQ-26-28", ":APAC:SG:ORQ:ST-SG-ORQ-26-28", 300, 400, newSeatMapLvl26, 28);
                Seat row5Col5 = new Seat("SG-ORQ-26-29", "SG-ORQ-26-29", ":APAC:SG:ORQ:ST-SG-ORQ-26-29", 400, 400, newSeatMapLvl26, 29);
                Seat row5Col6 = new Seat("SG-ORQ-26-30", "SG-ORQ-26-30", ":APAC:SG:ORQ:ST-SG-ORQ-26-30", 500, 400, newSeatMapLvl26, 30);

                Seat row6Col1 = new Seat("SG-ORQ-26-31", "SG-ORQ-26-31", ":APAC:SG:ORQ:ST-SG-ORQ-26-31", 0, 500, newSeatMapLvl26, 31);
                Seat row6Col2 = new Seat("SG-ORQ-26-32", "SG-ORQ-26-32", ":APAC:SG:ORQ:ST-SG-ORQ-26-32", 100, 500, newSeatMapLvl26, 32);
                Seat row6Col3 = new Seat("SG-ORQ-26-33", "SG-ORQ-26-33", ":APAC:SG:ORQ:ST-SG-ORQ-26-33", 200, 500, newSeatMapLvl26, 33);
                Seat row6Col4 = new Seat("SG-ORQ-26-34", "SG-ORQ-26-34", ":APAC:SG:ORQ:ST-SG-ORQ-26-34", 300, 500, newSeatMapLvl26, 34);
                Seat row6Col5 = new Seat("SG-ORQ-26-35", "SG-ORQ-26-35", ":APAC:SG:ORQ:ST-SG-ORQ-26-35", 400, 500, newSeatMapLvl26, 35);
                Seat row6Col6 = new Seat("SG-ORQ-26-36", "SG-ORQ-26-36", ":APAC:SG:ORQ:ST-SG-ORQ-26-36", 500, 500, newSeatMapLvl26, 36);

                Seat row7Col1 = new Seat("SG-ORQ-26-37", "SG-ORQ-26-37", ":APAC:SG:ORQ:ST-SG-ORQ-26-37", 0, 600, newSeatMapLvl26, 37);
                Seat row7Col2 = new Seat("SG-ORQ-26-38", "SG-ORQ-26-38", ":APAC:SG:ORQ:ST-SG-ORQ-26-38", 100, 600, newSeatMapLvl26, 38);
                Seat row7Col3 = new Seat("SG-ORQ-26-39", "SG-ORQ-26-39", ":APAC:SG:ORQ:ST-SG-ORQ-26-39", 200, 600, newSeatMapLvl26, 39);
                Seat row7Col4 = new Seat("SG-ORQ-26-40", "SG-ORQ-26-40", ":APAC:SG:ORQ:ST-SG-ORQ-26-40", 300, 600, newSeatMapLvl26, 40);
                Seat row7Col5 = new Seat("SG-ORQ-26-41", "SG-ORQ-26-41", ":APAC:SG:ORQ:ST-SG-ORQ-26-41", 400, 600, newSeatMapLvl26, 41);
                Seat row7Col6 = new Seat("SG-ORQ-26-42", "SG-ORQ-26-42", ":APAC:SG:ORQ:ST-SG-ORQ-26-42", 500, 600, newSeatMapLvl26, 42);

                Seat row8Col1 = new Seat("SG-ORQ-26-43", "SG-ORQ-26-43", ":APAC:SG:ORQ:ST-SG-ORQ-26-43", 0, 700, newSeatMapLvl26, 43);
                Seat row8Col2 = new Seat("SG-ORQ-26-44", "SG-ORQ-26-44", ":APAC:SG:ORQ:ST-SG-ORQ-26-44", 100, 700, newSeatMapLvl26, 44);
                Seat row8Col3 = new Seat("SG-ORQ-26-45", "SG-ORQ-26-45", ":APAC:SG:ORQ:ST-SG-ORQ-26-45", 200, 700, newSeatMapLvl26, 45);
                Seat row8Col4 = new Seat("SG-ORQ-26-46", "SG-ORQ-26-46", ":APAC:SG:ORQ:ST-SG-ORQ-26-46", 300, 700, newSeatMapLvl26, 46);
                Seat row8Col5 = new Seat("SG-ORQ-26-47", "SG-ORQ-26-47", ":APAC:SG:ORQ:ST-SG-ORQ-26-47", 400, 700, newSeatMapLvl26, 47);
                Seat row8Col6 = new Seat("SG-ORQ-26-48", "SG-ORQ-26-48", ":APAC:SG:ORQ:ST-SG-ORQ-26-48", 500, 700, newSeatMapLvl26, 48);

                Seat row9Col1 = new Seat("SG-ORQ-26-49", "SG-ORQ-26-49", ":APAC:SG:ORQ:ST-SG-ORQ-26-49", 0, 800, newSeatMapLvl26, 49);
                Seat row9Col2 = new Seat("SG-ORQ-26-50", "SG-ORQ-26-50", ":APAC:SG:ORQ:ST-SG-ORQ-26-50", 100, 800, newSeatMapLvl26, 50);
                Seat row9Col3 = new Seat("SG-ORQ-26-51", "SG-ORQ-26-51", ":APAC:SG:ORQ:ST-SG-ORQ-26-51", 200, 800, newSeatMapLvl26, 51);
                Seat row9Col4 = new Seat("SG-ORQ-26-52", "SG-ORQ-26-52", ":APAC:SG:ORQ:ST-SG-ORQ-26-52", 300, 800, newSeatMapLvl26, 52);
                Seat row9Col5 = new Seat("SG-ORQ-26-53", "SG-ORQ-26-53", ":APAC:SG:ORQ:ST-SG-ORQ-26-53", 400, 800, newSeatMapLvl26, 53);
                Seat row9Col6 = new Seat("SG-ORQ-26-54", "SG-ORQ-26-54", ":APAC:SG:ORQ:ST-SG-ORQ-26-54", 500, 800, newSeatMapLvl26, 54);

                Seat row10Col1 = new Seat("SG-ORQ-26-55", "SG-ORQ-26-55", ":APAC:SG:ORQ:ST-SG-ORQ-26-55", 0, 900, newSeatMapLvl26, 55);
                Seat row10Col2 = new Seat("SG-ORQ-26-56", "SG-ORQ-26-56", ":APAC:SG:ORQ:ST-SG-ORQ-26-56", 100, 900, newSeatMapLvl26, 56);
                Seat row10Col3 = new Seat("SG-ORQ-26-57", "SG-ORQ-26-57", ":APAC:SG:ORQ:ST-SG-ORQ-26-57", 200, 900, newSeatMapLvl26, 57);
                Seat row10Col4 = new Seat("SG-ORQ-26-58", "SG-ORQ-26-58", ":APAC:SG:ORQ:ST-SG-ORQ-26-58", 300, 900, newSeatMapLvl26, 58);
                Seat row10Col5 = new Seat("SG-ORQ-26-59", "SG-ORQ-26-59", ":APAC:SG:ORQ:ST-SG-ORQ-26-59", 400, 900, newSeatMapLvl26, 59);
                Seat row10Col6 = new Seat("SG-ORQ-26-60", "SG-ORQ-26-60", ":APAC:SG:ORQ:ST-SG-ORQ-26-60", 500, 900, newSeatMapLvl26, 60);

                newSeatMapLvl26.getSeats().add(row1Col1);
                newSeatMapLvl26.getSeats().add(row1Col2);
                newSeatMapLvl26.getSeats().add(row1Col3);
                newSeatMapLvl26.getSeats().add(row1Col4);
                newSeatMapLvl26.getSeats().add(row1Col5);
                newSeatMapLvl26.getSeats().add(row1Col6);

                newSeatMapLvl26.getSeats().add(row2Col1);
                newSeatMapLvl26.getSeats().add(row2Col2);
                newSeatMapLvl26.getSeats().add(row2Col3);
                newSeatMapLvl26.getSeats().add(row2Col4);
                newSeatMapLvl26.getSeats().add(row2Col5);
                newSeatMapLvl26.getSeats().add(row2Col6);

                newSeatMapLvl26.getSeats().add(row3Col1);
                newSeatMapLvl26.getSeats().add(row3Col2);
                newSeatMapLvl26.getSeats().add(row3Col3);
                newSeatMapLvl26.getSeats().add(row3Col4);
                newSeatMapLvl26.getSeats().add(row3Col5);
                newSeatMapLvl26.getSeats().add(row3Col6);

                newSeatMapLvl26.getSeats().add(row4Col1);
                newSeatMapLvl26.getSeats().add(row4Col2);
                newSeatMapLvl26.getSeats().add(row4Col3);
                newSeatMapLvl26.getSeats().add(row4Col4);
                newSeatMapLvl26.getSeats().add(row4Col5);
                newSeatMapLvl26.getSeats().add(row4Col6);

                newSeatMapLvl26.getSeats().add(row5Col1);
                newSeatMapLvl26.getSeats().add(row5Col2);
                newSeatMapLvl26.getSeats().add(row5Col3);
                newSeatMapLvl26.getSeats().add(row5Col4);
                newSeatMapLvl26.getSeats().add(row5Col5);
                newSeatMapLvl26.getSeats().add(row5Col6);

                newSeatMapLvl26.getSeats().add(row6Col1);
                newSeatMapLvl26.getSeats().add(row6Col2);
                newSeatMapLvl26.getSeats().add(row6Col3);
                newSeatMapLvl26.getSeats().add(row6Col4);
                newSeatMapLvl26.getSeats().add(row6Col5);
                newSeatMapLvl26.getSeats().add(row6Col6);

                newSeatMapLvl26.getSeats().add(row7Col1);
                newSeatMapLvl26.getSeats().add(row7Col2);
                newSeatMapLvl26.getSeats().add(row7Col3);
                newSeatMapLvl26.getSeats().add(row7Col4);
                newSeatMapLvl26.getSeats().add(row7Col5);
                newSeatMapLvl26.getSeats().add(row7Col6);

                newSeatMapLvl26.getSeats().add(row8Col1);
                newSeatMapLvl26.getSeats().add(row8Col2);
                newSeatMapLvl26.getSeats().add(row8Col3);
                newSeatMapLvl26.getSeats().add(row8Col4);
                newSeatMapLvl26.getSeats().add(row8Col5);
                newSeatMapLvl26.getSeats().add(row8Col6);

                newSeatMapLvl26.getSeats().add(row9Col1);
                newSeatMapLvl26.getSeats().add(row9Col2);
                newSeatMapLvl26.getSeats().add(row9Col3);
                newSeatMapLvl26.getSeats().add(row9Col4);
                newSeatMapLvl26.getSeats().add(row9Col5);
                newSeatMapLvl26.getSeats().add(row9Col6);

                newSeatMapLvl26.getSeats().add(row10Col1);
                newSeatMapLvl26.getSeats().add(row10Col2);
                newSeatMapLvl26.getSeats().add(row10Col3);
                newSeatMapLvl26.getSeats().add(row10Col4);
                newSeatMapLvl26.getSeats().add(row10Col5);
                newSeatMapLvl26.getSeats().add(row10Col6);

                newSeatMapLvl26.setNumOfSeats(newSeatMapLvl26.getSeats().size());
                newSeatMapLvl26 = seatMapRepository.save(newSeatMapLvl26);

                for (Seat seat: newSeatMapLvl26.getSeats()) {
                    seat.setOriginalSeatMapId(newSeatMapLvl26.getId());
                    seatRepository.save(seat);
                }

                // Assign company functions, business units and teams
                Collections.sort(newSeatMapLvl26.getSeats());
                Team dataCenOprTeam = teamRepository.findTeamByCode("T-SG-InfraTech-DataCenOpr");
                Team networksTeam = teamRepository.findTeamByCode("T-SG-InfraTech-Networks");
                Team devTeam = teamRepository.findTeamByCode("T-SG-FixIncTech-Dev");
                Team prodSuppTeam = teamRepository.findTeamByCode("T-SG-FixIncTech-ProdSupp");
                for (Seat seat : newSeatMapLvl26.getSeats()) {
                    Integer serialNumber = seat.getSerialNumber();
                    if (serialNumber <= 48) {
                        seat.setFunctionAssigned(dataCenOprTeam.getBusinessUnit().getFunction());
                        if (serialNumber > 6 && serialNumber < 25) {
                            seat.setBusinessUnitAssigned(dataCenOprTeam.getBusinessUnit());
                            if (serialNumber < 13) {
                                seat.setTeamAssigned(dataCenOprTeam);
                            } else {
                                seat.setTeamAssigned(networksTeam);
                            }
                        } else if (serialNumber > 24 && serialNumber < 49) {
                            seat.setBusinessUnitAssigned(devTeam.getBusinessUnit());
                            if (serialNumber > 30 && serialNumber < 43){
                                seat.setTeamAssigned(devTeam);
                            } else if (serialNumber > 42) {
                                seat.setTeamAssigned(prodSuppTeam);
                                if (serialNumber == 48) {
                                    break;
                                }
                            }
                        }
                    }
                }

                for (Seat seat: newSeatMapLvl26.getSeats()) {
                    seat.setOriginalSeatMapId(newSeatMapLvl26.getId());
                    seatRepository.save(seat);
                }
                office.get().getFloors().add("26");
                officeRepository.save(office.get());

                SeatAdminMatch newSeatAdminMatch = new SeatAdminMatch();
                newSeatAdminMatch.setHierarchyId(newSeatMapLvl26.getId());
                newSeatAdminMatch.setHierarchyType(HierarchyTypeEnum.OFFICE_FLOOR);
                newSeatAdminMatch.setSeatAdmin(admin);
                seatAdminMatchRepository.save(newSeatAdminMatch);
            } catch (Exception ex) {
                System.out.println("Seat Management Initialisation failed: " + ex.getMessage());
            }
        }
        if (office.isPresent()) {
            try {

                // ORQ Floor 27
                SeatMap newSeatMapLvl27 = new SeatMap("SG-ORQ-27","SG-ORQ-27",":APAC:SG:ORQ:SM-SG-ORQ-27", office.get(), "27");

                Seat row1Col1 = new Seat("SG-ORQ-27-01", "SG-ORQ-27-01", ":APAC:SG:ORQ:ST-SG-ORQ-27-01", 0, 0, newSeatMapLvl27, 1);
                Seat row1Col2 = new Seat("SG-ORQ-27-02", "SG-ORQ-27-02", ":APAC:SG:ORQ:ST-SG-ORQ-27-02", 100, 0, newSeatMapLvl27, 2);
                Seat row1Col3 = new Seat("SG-ORQ-27-03", "SG-ORQ-27-03", ":APAC:SG:ORQ:ST-SG-ORQ-27-03", 200, 0, newSeatMapLvl27, 3);
                Seat row1Col4 = new Seat("SG-ORQ-27-04", "SG-ORQ-27-04", ":APAC:SG:ORQ:ST-SG-ORQ-27-04", 300, 0, newSeatMapLvl27, 4);

                Seat row2Col1 = new Seat("SG-ORQ-27-05", "SG-ORQ-27-05", ":APAC:SG:ORQ:ST-SG-ORQ-27-05", 0, 100, newSeatMapLvl27, 5);
                Seat row2Col2 = new Seat("SG-ORQ-27-06", "SG-ORQ-27-06", ":APAC:SG:ORQ:ST-SG-ORQ-27-06", 100, 100, newSeatMapLvl27, 6);
                Seat row2Col3 = new Seat("SG-ORQ-27-07", "SG-ORQ-27-07", ":APAC:SG:ORQ:ST-SG-ORQ-27-07", 200, 100, newSeatMapLvl27, 7);
                Seat row2Col4 = new Seat("SG-ORQ-27-08", "SG-ORQ-27-08", ":APAC:SG:ORQ:ST-SG-ORQ-27-08", 300, 100, newSeatMapLvl27, 8);

                Seat row3Col1 = new Seat("SG-ORQ-27-09", "SG-ORQ-27-09", ":APAC:SG:ORQ:ST-SG-ORQ-27-09", 0, 200, newSeatMapLvl27, 9);
                Seat row3Col2 = new Seat("SG-ORQ-27-10", "SG-ORQ-27-10", ":APAC:SG:ORQ:ST-SG-ORQ-27-10", 100, 200, newSeatMapLvl27, 10);
                Seat row3Col3 = new Seat("SG-ORQ-27-11", "SG-ORQ-27-11", ":APAC:SG:ORQ:ST-SG-ORQ-27-11", 200, 200, newSeatMapLvl27, 11);
                Seat row3Col4 = new Seat("SG-ORQ-27-12", "SG-ORQ-27-12", ":APAC:SG:ORQ:ST-SG-ORQ-27-12", 300, 200, newSeatMapLvl27, 12);

                Seat row4Col1 = new Seat("SG-ORQ-27-13", "SG-ORQ-27-13", ":APAC:SG:ORQ:ST-SG-ORQ-27-13", 0, 300, newSeatMapLvl27, 13);
                Seat row4Col2 = new Seat("SG-ORQ-27-14", "SG-ORQ-27-14", ":APAC:SG:ORQ:ST-SG-ORQ-27-14", 100, 300, newSeatMapLvl27, 14);
                Seat row4Col3 = new Seat("SG-ORQ-27-15", "SG-ORQ-27-15", ":APAC:SG:ORQ:ST-SG-ORQ-27-15", 200, 300, newSeatMapLvl27, 15);
                Seat row4Col4 = new Seat("SG-ORQ-27-16", "SG-ORQ-27-16", ":APAC:SG:ORQ:ST-SG-ORQ-27-16", 300, 300, newSeatMapLvl27, 16);

                Seat row5Col1 = new Seat("SG-ORQ-27-17", "SG-ORQ-27-17", ":APAC:SG:ORQ:ST-SG-ORQ-27-17", 0, 400, newSeatMapLvl27, 17);
                Seat row5Col2 = new Seat("SG-ORQ-27-18", "SG-ORQ-27-18", ":APAC:SG:ORQ:ST-SG-ORQ-27-18", 100, 400, newSeatMapLvl27, 18);
                Seat row5Col3 = new Seat("SG-ORQ-27-19", "SG-ORQ-27-19", ":APAC:SG:ORQ:ST-SG-ORQ-27-19", 200, 400, newSeatMapLvl27, 19);
                Seat row5Col4 = new Seat("SG-ORQ-27-20", "SG-ORQ-27-20", ":APAC:SG:ORQ:ST-SG-ORQ-27-20", 300, 400, newSeatMapLvl27, 20);

                Seat row6Col1 = new Seat("SG-ORQ-27-21", "SG-ORQ-27-21", ":APAC:SG:ORQ:ST-SG-ORQ-27-21", 0, 500, newSeatMapLvl27, 21);
                Seat row6Col2 = new Seat("SG-ORQ-27-22", "SG-ORQ-27-22", ":APAC:SG:ORQ:ST-SG-ORQ-27-22", 100, 500, newSeatMapLvl27, 22);
                Seat row6Col3 = new Seat("SG-ORQ-27-23", "SG-ORQ-27-23", ":APAC:SG:ORQ:ST-SG-ORQ-27-23", 200, 500, newSeatMapLvl27, 23);
                Seat row6Col4 = new Seat("SG-ORQ-27-24", "SG-ORQ-27-24", ":APAC:SG:ORQ:ST-SG-ORQ-27-24", 300, 500, newSeatMapLvl27, 24);

                Seat row7Col1 = new Seat("SG-ORQ-27-25", "SG-ORQ-27-25", ":APAC:SG:ORQ:ST-SG-ORQ-27-25", 0, 600, newSeatMapLvl27, 25);
                Seat row7Col2 = new Seat("SG-ORQ-27-26", "SG-ORQ-27-26", ":APAC:SG:ORQ:ST-SG-ORQ-27-26", 100, 600, newSeatMapLvl27, 26);
                Seat row7Col3 = new Seat("SG-ORQ-27-27", "SG-ORQ-27-27", ":APAC:SG:ORQ:ST-SG-ORQ-27-27", 200, 600, newSeatMapLvl27, 27);
                Seat row7Col4 = new Seat("SG-ORQ-27-28", "SG-ORQ-27-28", ":APAC:SG:ORQ:ST-SG-ORQ-27-28", 300, 600, newSeatMapLvl27, 28);

                Seat row8Col1 = new Seat("SG-ORQ-27-29", "SG-ORQ-27-29", ":APAC:SG:ORQ:ST-SG-ORQ-27-29", 0, 700, newSeatMapLvl27, 29);
                Seat row8Col2 = new Seat("SG-ORQ-27-30", "SG-ORQ-27-30", ":APAC:SG:ORQ:ST-SG-ORQ-27-30", 100, 700, newSeatMapLvl27, 30);
                Seat row8Col3 = new Seat("SG-ORQ-27-31", "SG-ORQ-27-31", ":APAC:SG:ORQ:ST-SG-ORQ-27-31", 200, 700, newSeatMapLvl27, 31);
                Seat row8Col4 = new Seat("SG-ORQ-27-32", "SG-ORQ-27-32", ":APAC:SG:ORQ:ST-SG-ORQ-27-32", 300, 700, newSeatMapLvl27, 32);

                Seat row9Col1 = new Seat("SG-ORQ-27-33", "SG-ORQ-27-33", ":APAC:SG:ORQ:ST-SG-ORQ-27-33", 0, 800, newSeatMapLvl27, 33);
                Seat row9Col2 = new Seat("SG-ORQ-27-34", "SG-ORQ-27-34", ":APAC:SG:ORQ:ST-SG-ORQ-27-34", 100, 800, newSeatMapLvl27, 34);
                Seat row9Col3 = new Seat("SG-ORQ-27-35", "SG-ORQ-27-35", ":APAC:SG:ORQ:ST-SG-ORQ-27-35", 200, 800, newSeatMapLvl27, 35);
                Seat row9Col4 = new Seat("SG-ORQ-27-36", "SG-ORQ-27-36", ":APAC:SG:ORQ:ST-SG-ORQ-27-36", 300, 800, newSeatMapLvl27, 36);

                Seat row10Col1 = new Seat("SG-ORQ-27-37", "SG-ORQ-27-37", ":APAC:SG:ORQ:ST-SG-ORQ-27-37", 0, 900, newSeatMapLvl27, 37);
                Seat row10Col2 = new Seat("SG-ORQ-27-38", "SG-ORQ-27-38", ":APAC:SG:ORQ:ST-SG-ORQ-27-38", 100, 900, newSeatMapLvl27, 38);
                Seat row10Col3 = new Seat("SG-ORQ-27-39", "SG-ORQ-27-39", ":APAC:SG:ORQ:ST-SG-ORQ-27-39", 200, 900, newSeatMapLvl27, 39);
                Seat row10Col4 = new Seat("SG-ORQ-27-40", "SG-ORQ-27-40", ":APAC:SG:ORQ:ST-SG-ORQ-27-40", 300, 900, newSeatMapLvl27, 40);

                newSeatMapLvl27.getSeats().add(row1Col1);
                newSeatMapLvl27.getSeats().add(row1Col2);
                newSeatMapLvl27.getSeats().add(row1Col3);
                newSeatMapLvl27.getSeats().add(row1Col4);

                newSeatMapLvl27.getSeats().add(row2Col1);
                newSeatMapLvl27.getSeats().add(row2Col2);
                newSeatMapLvl27.getSeats().add(row2Col3);
                newSeatMapLvl27.getSeats().add(row2Col4);

                newSeatMapLvl27.getSeats().add(row3Col1);
                newSeatMapLvl27.getSeats().add(row3Col2);
                newSeatMapLvl27.getSeats().add(row3Col3);
                newSeatMapLvl27.getSeats().add(row3Col4);

                newSeatMapLvl27.getSeats().add(row4Col1);
                newSeatMapLvl27.getSeats().add(row4Col2);
                newSeatMapLvl27.getSeats().add(row4Col3);
                newSeatMapLvl27.getSeats().add(row4Col4);

                newSeatMapLvl27.getSeats().add(row5Col1);
                newSeatMapLvl27.getSeats().add(row5Col2);
                newSeatMapLvl27.getSeats().add(row5Col3);
                newSeatMapLvl27.getSeats().add(row5Col4);

                newSeatMapLvl27.getSeats().add(row6Col1);
                newSeatMapLvl27.getSeats().add(row6Col2);
                newSeatMapLvl27.getSeats().add(row6Col3);
                newSeatMapLvl27.getSeats().add(row6Col4);

                newSeatMapLvl27.getSeats().add(row7Col1);
                newSeatMapLvl27.getSeats().add(row7Col2);
                newSeatMapLvl27.getSeats().add(row7Col3);
                newSeatMapLvl27.getSeats().add(row7Col4);

                newSeatMapLvl27.getSeats().add(row8Col1);
                newSeatMapLvl27.getSeats().add(row8Col2);
                newSeatMapLvl27.getSeats().add(row8Col3);
                newSeatMapLvl27.getSeats().add(row8Col4);

                newSeatMapLvl27.getSeats().add(row9Col1);
                newSeatMapLvl27.getSeats().add(row9Col2);
                newSeatMapLvl27.getSeats().add(row9Col3);
                newSeatMapLvl27.getSeats().add(row9Col4);

                newSeatMapLvl27.getSeats().add(row10Col1);
                newSeatMapLvl27.getSeats().add(row10Col2);
                newSeatMapLvl27.getSeats().add(row10Col3);
                newSeatMapLvl27.getSeats().add(row10Col4);

                newSeatMapLvl27.setNumOfSeats(newSeatMapLvl27.getSeats().size());
                newSeatMapLvl27 = seatMapRepository.save(newSeatMapLvl27);

                for (Seat seat: newSeatMapLvl27.getSeats()) {
                    seat.setOriginalSeatMapId(newSeatMapLvl27.getId());
                    seatRepository.save(seat);
                }

                // Assign company functions, business units and teams
                Collections.sort(newSeatMapLvl27.getSeats());
                Team interviewTeam = teamRepository.findTeamByCode("T-SG-RCR-INT");
                for (Seat seat :
                        newSeatMapLvl27.getSeats()) {
                    Integer serialNumber = seat.getSerialNumber();
                    if (serialNumber == 1 || serialNumber == 5 || serialNumber == 9 || serialNumber == 13 ||
                            serialNumber == 17 || serialNumber == 21 || serialNumber == 25 || serialNumber == 29 ||
                            serialNumber == 33 || serialNumber == 37) {
                        seat.setFunctionAssigned(interviewTeam.getBusinessUnit().getFunction());
                    } else if (serialNumber == 2 || serialNumber == 6 || serialNumber == 10 || serialNumber == 14) {
                        seat.setBusinessUnitAssigned(interviewTeam.getBusinessUnit());
                        seat.setFunctionAssigned(interviewTeam.getBusinessUnit().getFunction());
                    } else if (serialNumber == 18 || serialNumber == 22 || serialNumber == 26 || serialNumber == 30 ||
                            serialNumber == 34 || serialNumber == 38) {
                        seat.setTeamAssigned(interviewTeam);
                        seat.setBusinessUnitAssigned(interviewTeam.getBusinessUnit());
                        seat.setFunctionAssigned(interviewTeam.getBusinessUnit().getFunction());
                    }
                }

                for (Seat seat: newSeatMapLvl27.getSeats()) {
                    seat.setOriginalSeatMapId(newSeatMapLvl27.getId());
                    seatRepository.save(seat);
                }
                office.get().getFloors().add("27");
                officeRepository.save(office.get());

                SeatAdminMatch newSeatAdminMatch = new SeatAdminMatch();
                newSeatAdminMatch.setHierarchyId(newSeatMapLvl27.getId());
                newSeatAdminMatch.setHierarchyType(HierarchyTypeEnum.OFFICE_FLOOR);
                newSeatAdminMatch.setSeatAdmin(admin);
                seatAdminMatchRepository.save(newSeatAdminMatch);
            } catch (Exception ex) {
                System.out.println("Seat Management Initialisation failed: " + ex.getMessage());
            }
        }
    }

    // Generate logs for the past month
    private void initialiseSeatUtilisationLog(){

        Date yesterday = DateHelper.getDaysBefore(DateHelper.getDateWithoutTimeUsingCalendar(new Date()), 1);
        int yesterdayYear = DateHelper.getYearFromDate(yesterday);
        int yesterdayMonth = DateHelper.getMonthFromDate(yesterday);
        int yesterdayDayOfMonth = DateHelper.getDayOfMonthFromDate(yesterday);
        yesterday = DateHelper.getDateByYearMonthDateHourMinuteSecond(yesterdayYear, yesterdayMonth, yesterdayDayOfMonth, 23, 59, 0);

        // Use Fix Income Development team as the finder
        Team devTeam = teamRepository.findTeamByCode("T-SG-FixIncTech-Dev");
        Optional<SeatUtilisationLog> optionalSeatUtilisationLog = seatUtilisationLogRepository.findOneByBusinessEntityIdAndDate(devTeam.getId(), yesterdayYear, yesterdayMonth, yesterdayDayOfMonth);
        if (!optionalSeatUtilisationLog.isPresent()) {
            try{
                seatUtilisationLogRepository.deleteAll();
                initialiseTeamSeatUtilisationLog(yesterday, 32);
                initialiseBusinessUnitUtilisationLog(yesterday, 32);
                initialiseCompanyFunctionUtilisationLog(yesterday, 32);
                initialiseOfficeAndOfficeFloorUtilisationLog(yesterday, 32);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private void initialiseTeamSeatUtilisationLog(Date date, int periodLength) {
        // SG-Tech-FixIncTech-Dev
        Team devTeam = teamRepository.findTeamByCode("T-SG-FixIncTech-Dev");
        if (devTeam != null) {
            int yesterdayYear = DateHelper.getYearFromDate(date);
            int yesterdayMonth = DateHelper.getMonthFromDate(date);
            int yesterdayDayOfMonth = DateHelper.getDayOfMonthFromDate(date);
            for(int count = 0; count < periodLength; count++) {
                Date dateCounter = DateHelper.getDateByYearMonthDateHourMinuteSecond(yesterdayYear, yesterdayMonth, yesterdayDayOfMonth, 23, 59, 0);
                dateCounter = DateHelper.getDaysBefore(dateCounter, count);
                int year = DateHelper.getYearFromDate(dateCounter);
                int month = DateHelper.getMonthFromDate(dateCounter);
                int dayOfMonth = DateHelper.getDayOfMonthFromDate(dateCounter);

                SeatUtilisationLog newLog = new SeatUtilisationLog();
                newLog.setLevelEntityId(devTeam.getId());
                newLog.setHierarchyType(HierarchyTypeEnum.TEAM);
                newLog.setCreatedTime(dateCounter);
                newLog.setYear(year);
                newLog.setMonth(month);
                newLog.setDayOfMonth(dayOfMonth);
                newLog.setOfficeId(devTeam.getOffice().getId());
                newLog.setHierarchyPath(devTeam.getHierachyPath());
                if (count < 10) {
                    newLog.setInventoryCount(12);
                    newLog.setOccupancyCount(10);
                } else if ( count < 17) {
                    newLog.setInventoryCount(12);
                    newLog.setOccupancyCount(5);
                } else {
                    newLog.setInventoryCount(12);
                    newLog.setOccupancyCount(10);
                }
                seatUtilisationLogRepository.save(newLog);
            }
        }

        // SG-Tech-FixIncTech-ProdSupp
        Team prodSuppTeam = teamRepository.findTeamByCode("T-SG-FixIncTech-ProdSupp");
        if (prodSuppTeam != null) {
            int yesterdayYear = DateHelper.getYearFromDate(date);
            int yesterdayMonth = DateHelper.getMonthFromDate(date);
            int yesterdayDayOfMonth = DateHelper.getDayOfMonthFromDate(date);
            for(int count = 0; count < periodLength; count++) {
                Date dateCounter = DateHelper.getDateByYearMonthDateHourMinuteSecond(yesterdayYear, yesterdayMonth, yesterdayDayOfMonth, 23, 59, 0);
                dateCounter = DateHelper.getDaysBefore(dateCounter, count);
                int year = DateHelper.getYearFromDate(dateCounter);
                int month = DateHelper.getMonthFromDate(dateCounter);
                int dayOfMonth = DateHelper.getDayOfMonthFromDate(dateCounter);

                SeatUtilisationLog newLog = new SeatUtilisationLog();
                newLog.setLevelEntityId(prodSuppTeam.getId());
                newLog.setHierarchyType(HierarchyTypeEnum.TEAM);
                newLog.setCreatedTime(dateCounter);
                newLog.setYear(year);
                newLog.setMonth(month);
                newLog.setDayOfMonth(dayOfMonth);
                newLog.setOfficeId(prodSuppTeam.getOffice().getId());
                newLog.setHierarchyPath(prodSuppTeam.getHierachyPath());

                if (count < 10) {
                    newLog.setInventoryCount(6);
                    newLog.setOccupancyCount(6);
                } else if (count < 21) {
                    newLog.setInventoryCount(6);
                    newLog.setOccupancyCount(4);
                } else {
                    newLog.setInventoryCount(6);
                    newLog.setOccupancyCount(6);
                }

                seatUtilisationLogRepository.save(newLog);
            }
        }


        // SG-Tech-InfraTech-DataCenOpr
        Team dataCenOpeTeam = teamRepository.findTeamByCode("T-SG-InfraTech-DataCenOpr");
        if (dataCenOpeTeam != null) {
            int yesterdayYear = DateHelper.getYearFromDate(date);
            int yesterdayMonth = DateHelper.getMonthFromDate(date);
            int yesterdayDayOfMonth = DateHelper.getDayOfMonthFromDate(date);
            for(int count = 0; count < periodLength; count++) {
                Date dateCounter = DateHelper.getDateByYearMonthDateHourMinuteSecond(yesterdayYear, yesterdayMonth, yesterdayDayOfMonth, 23, 59, 0);
                dateCounter = DateHelper.getDaysBefore(dateCounter, count);
                int year = DateHelper.getYearFromDate(dateCounter);
                int month = DateHelper.getMonthFromDate(dateCounter);
                int dayOfMonth = DateHelper.getDayOfMonthFromDate(dateCounter);

                SeatUtilisationLog newLog = new SeatUtilisationLog();
                newLog.setLevelEntityId(dataCenOpeTeam.getId());
                newLog.setHierarchyType(HierarchyTypeEnum.TEAM);
                newLog.setCreatedTime(dateCounter);
                newLog.setYear(year);
                newLog.setMonth(month);
                newLog.setDayOfMonth(dayOfMonth);
                newLog.setOfficeId(dataCenOpeTeam.getOffice().getId());
                newLog.setHierarchyPath(dataCenOpeTeam.getHierachyPath());

                if (count < 10) {
                    newLog.setInventoryCount(12);
                    newLog.setOccupancyCount(11);
                } else if (count < 25) {
                    newLog.setInventoryCount(12);
                    newLog.setOccupancyCount(8);
                } else {
                    newLog.setInventoryCount(12);
                    newLog.setOccupancyCount(5);
                }

                seatUtilisationLogRepository.save(newLog);
            }
        }

        // SG-Tech-InfraTech-Networks
        Team networksTeam = teamRepository.findTeamByCode("T-SG-InfraTech-Networks");
        if (networksTeam != null) {
            int yesterdayYear = DateHelper.getYearFromDate(date);
            int yesterdayMonth = DateHelper.getMonthFromDate(date);
            int yesterdayDayOfMonth = DateHelper.getDayOfMonthFromDate(date);
            for(int count = 0; count < periodLength; count++) {
                Date dateCounter = DateHelper.getDateByYearMonthDateHourMinuteSecond(yesterdayYear, yesterdayMonth, yesterdayDayOfMonth, 23, 59, 0);
                dateCounter = DateHelper.getDaysBefore(dateCounter, count);
                int year = DateHelper.getYearFromDate(dateCounter);
                int month = DateHelper.getMonthFromDate(dateCounter);
                int dayOfMonth = DateHelper.getDayOfMonthFromDate(dateCounter);

                SeatUtilisationLog newLog = new SeatUtilisationLog();
                newLog.setLevelEntityId(networksTeam.getId());
                newLog.setHierarchyType(HierarchyTypeEnum.TEAM);
                newLog.setCreatedTime(dateCounter);
                newLog.setYear(year);
                newLog.setMonth(month);
                newLog.setDayOfMonth(dayOfMonth);
                newLog.setOfficeId(networksTeam.getOffice().getId());
                newLog.setHierarchyPath(networksTeam.getHierachyPath());
                if (count < 5) {
                    newLog.setInventoryCount(6);
                    newLog.setOccupancyCount(6);
                } else if (count < 18) {
                    newLog.setInventoryCount(6);
                    newLog.setOccupancyCount(5);
                } else if (count < 27) {
                    newLog.setInventoryCount(6);
                    newLog.setOccupancyCount(4);
                }
                seatUtilisationLogRepository.save(newLog);
            }
        }

        // SG-Tech-InfraTech-DBAdmin
        Team dbAdminTeam = teamRepository.findTeamByCode("T-SG-InfraTech-DBAdmin");
        if (dbAdminTeam != null) {
            int yesterdayYear = DateHelper.getYearFromDate(date);
            int yesterdayMonth = DateHelper.getMonthFromDate(date);
            int yesterdayDayOfMonth = DateHelper.getDayOfMonthFromDate(date);
            for(int count = 0; count < periodLength; count++) {
                Date dateCounter = DateHelper.getDateByYearMonthDateHourMinuteSecond(yesterdayYear, yesterdayMonth, yesterdayDayOfMonth, 23, 59, 0);
                dateCounter = DateHelper.getDaysBefore(dateCounter, count);
                int year = DateHelper.getYearFromDate(dateCounter);
                int month = DateHelper.getMonthFromDate(dateCounter);
                int dayOfMonth = DateHelper.getDayOfMonthFromDate(dateCounter);

                SeatUtilisationLog newLog = new SeatUtilisationLog();
                newLog.setLevelEntityId(dbAdminTeam.getId());
                newLog.setHierarchyType(HierarchyTypeEnum.TEAM);
                newLog.setCreatedTime(dateCounter);
                newLog.setYear(year);
                newLog.setMonth(month);
                newLog.setDayOfMonth(dayOfMonth);
                newLog.setOfficeId(dbAdminTeam.getOffice().getId());
                newLog.setHierarchyPath(dbAdminTeam.getHierachyPath());
                newLog.setInventoryCount(0);
                newLog.setOccupancyCount(0);
                seatUtilisationLogRepository.save(newLog);
            }
        }

        // SG-Tech-InfraTech-EndUserCom
        Team endUserComTeam = teamRepository.findTeamByCode("T-SG-InfraTech-EndUserCom");
        if (endUserComTeam != null) {
            int yesterdayYear = DateHelper.getYearFromDate(date);
            int yesterdayMonth = DateHelper.getMonthFromDate(date);
            int yesterdayDayOfMonth = DateHelper.getDayOfMonthFromDate(date);
            for(int count = 0; count < periodLength; count++) {
                Date dateCounter = DateHelper.getDateByYearMonthDateHourMinuteSecond(yesterdayYear, yesterdayMonth, yesterdayDayOfMonth, 23, 59, 0);
                dateCounter = DateHelper.getDaysBefore(dateCounter, count);
                int year = DateHelper.getYearFromDate(dateCounter);
                int month = DateHelper.getMonthFromDate(dateCounter);
                int dayOfMonth = DateHelper.getDayOfMonthFromDate(dateCounter);

                SeatUtilisationLog newLog = new SeatUtilisationLog();
                newLog.setLevelEntityId(endUserComTeam.getId());
                newLog.setHierarchyType(HierarchyTypeEnum.TEAM);
                newLog.setCreatedTime(dateCounter);
                newLog.setYear(year);
                newLog.setMonth(month);
                newLog.setDayOfMonth(dayOfMonth);
                newLog.setOfficeId(endUserComTeam.getOffice().getId());
                newLog.setHierarchyPath(endUserComTeam.getHierachyPath());
                newLog.setInventoryCount(0);
                newLog.setOccupancyCount(0);
                seatUtilisationLogRepository.save(newLog);
            }
        }


        // SG-HR-RCR-INT
        Team intTeam = teamRepository.findTeamByCode("T-SG-RCR-INT");
        if (intTeam != null) {
            int yesterdayYear = DateHelper.getYearFromDate(date);
            int yesterdayMonth = DateHelper.getMonthFromDate(date);
            int yesterdayDayOfMonth = DateHelper.getDayOfMonthFromDate(date);
            for(int count = 0; count < periodLength; count++) {
                Date dateCounter = DateHelper.getDateByYearMonthDateHourMinuteSecond(yesterdayYear, yesterdayMonth, yesterdayDayOfMonth, 23, 59, 0);
                dateCounter = DateHelper.getDaysBefore(dateCounter, count);
                int year = DateHelper.getYearFromDate(dateCounter);
                int month = DateHelper.getMonthFromDate(dateCounter);
                int dayOfMonth = DateHelper.getDayOfMonthFromDate(dateCounter);

                SeatUtilisationLog newLog = new SeatUtilisationLog();
                newLog.setLevelEntityId(intTeam.getId());
                newLog.setHierarchyType(HierarchyTypeEnum.TEAM);
                newLog.setCreatedTime(dateCounter);
                newLog.setYear(year);
                newLog.setMonth(month);
                newLog.setDayOfMonth(dayOfMonth);
                newLog.setOfficeId(intTeam.getOffice().getId());
                newLog.setHierarchyPath(intTeam.getHierachyPath());
                if (count < 16) {
                    newLog.setInventoryCount(6);
                    newLog.setOccupancyCount(5);
                } else {
                    newLog.setInventoryCount(6);
                    newLog.setOccupancyCount(3);
                }
                seatUtilisationLogRepository.save(newLog);
            }
        }
    }

    private void initialiseBusinessUnitUtilisationLog(Date date, int periodLength) {
        Optional<Office> office = officeRepository.findByName("One Raffles Quay");

        // SG-Tech-FixIncTech
        Optional<BusinessUnit> optionalFixIncTech = businessUnitRepository.findByCodeNonDeleted("BU-SG-FixIncTech");
        Team devTeam = teamRepository.findTeamByCode("T-SG-FixIncTech-Dev");
        Team prodSuppTeam = teamRepository.findTeamByCode("T-SG-FixIncTech-ProdSupp");
        if (optionalFixIncTech.isPresent()) {
            BusinessUnit fixIncTech = optionalFixIncTech.get();
            int yesterdayYear = DateHelper.getYearFromDate(date);
            int yesterdayMonth = DateHelper.getMonthFromDate(date);
            int yesterdayDayOfMonth = DateHelper.getDayOfMonthFromDate(date);
            for(int count = 0; count < periodLength; count++) {
                Date dateCounter = DateHelper.getDateByYearMonthDateHourMinuteSecond(yesterdayYear, yesterdayMonth, yesterdayDayOfMonth, 23, 59, 0);
                dateCounter = DateHelper.getDaysBefore(dateCounter, count);
                int year = DateHelper.getYearFromDate(dateCounter);
                int month = DateHelper.getMonthFromDate(dateCounter);
                int dayOfMonth = DateHelper.getDayOfMonthFromDate(dateCounter);
                SeatUtilisationLog newLog = new SeatUtilisationLog();
                Integer inventory = 0;
                Integer occupancy = 0;
                newLog.setLevelEntityId(fixIncTech.getId());
                newLog.setHierarchyType(HierarchyTypeEnum.BUSINESS_UNIT);
                newLog.setCreatedTime(dateCounter);
                newLog.setYear(year);
                newLog.setMonth(month);
                newLog.setDayOfMonth(dayOfMonth);
                newLog.setOfficeId(office.get().getId());
                newLog.setHierarchyPath(fixIncTech.getHierachyPath());

                Optional<SeatUtilisationLog> optionalDevTeamLog = seatUtilisationLogRepository.findOneByBusinessEntityIdAndDate(devTeam.getId(), year, month, dayOfMonth);
                SeatUtilisationLog devTeamLog = optionalDevTeamLog.get();
                inventory += devTeamLog.getInventoryCount();
                occupancy += devTeamLog.getOccupancyCount();
                Optional<SeatUtilisationLog> optionalProdSuppTeamLog = seatUtilisationLogRepository.findOneByBusinessEntityIdAndDate(prodSuppTeam.getId(), year, month, dayOfMonth);
                SeatUtilisationLog prodSuppTeamLog = optionalProdSuppTeamLog.get();
                inventory += prodSuppTeamLog.getInventoryCount();
                occupancy += prodSuppTeamLog.getOccupancyCount();
                newLog.setInventoryCount(inventory);
                newLog.setOccupancyCount(occupancy);
                seatUtilisationLogRepository.save(newLog);
            }
        }


        // SG-Tech-InfraTech
        Optional<BusinessUnit> optionalInfraTech = businessUnitRepository.findByCodeNonDeleted("BU-SG-InfraTech");
        Team networksTeam = teamRepository.findTeamByCode("T-SG-InfraTech-Networks");
        Team dataCtrOprTeam = teamRepository.findTeamByCode("T-SG-InfraTech-DataCenOpr");
        if (optionalInfraTech.isPresent()) {
            BusinessUnit fixIncTech = optionalInfraTech.get();
            int yesterdayYear = DateHelper.getYearFromDate(date);
            int yesterdayMonth = DateHelper.getMonthFromDate(date);
            int yesterdayDayOfMonth = DateHelper.getDayOfMonthFromDate(date);
            for(int count = 0; count < periodLength; count++) {
                Date dateCounter = DateHelper.getDateByYearMonthDateHourMinuteSecond(yesterdayYear, yesterdayMonth, yesterdayDayOfMonth, 23, 59, 0);
                dateCounter = DateHelper.getDaysBefore(dateCounter, count);
                int year = DateHelper.getYearFromDate(dateCounter);
                int month = DateHelper.getMonthFromDate(dateCounter);
                int dayOfMonth = DateHelper.getDayOfMonthFromDate(dateCounter);

                Integer inventory = 0;
                Integer occupancy = 0;
                SeatUtilisationLog newLog = new SeatUtilisationLog();
                newLog.setLevelEntityId(fixIncTech.getId());
                newLog.setHierarchyType(HierarchyTypeEnum.BUSINESS_UNIT);
                newLog.setCreatedTime(dateCounter);
                newLog.setYear(year);
                newLog.setMonth(month);
                newLog.setDayOfMonth(dayOfMonth);
                newLog.setOfficeId(office.get().getId());
                newLog.setHierarchyPath(fixIncTech.getHierachyPath());

                Optional<SeatUtilisationLog> optionalNetworksTeamLog = seatUtilisationLogRepository.findOneByBusinessEntityIdAndDate(networksTeam.getId(), year, month, dayOfMonth);
                SeatUtilisationLog networksTeamLog = optionalNetworksTeamLog.get();
                inventory += networksTeamLog.getInventoryCount();
                occupancy += networksTeamLog.getOccupancyCount();
                Optional<SeatUtilisationLog> optionalDataCtrOprTeamLog = seatUtilisationLogRepository.findOneByBusinessEntityIdAndDate(dataCtrOprTeam.getId(), year, month, dayOfMonth);
                SeatUtilisationLog dataCtrOprTeamLog = optionalDataCtrOprTeamLog.get();
                inventory += dataCtrOprTeamLog.getInventoryCount();
                occupancy += dataCtrOprTeamLog.getOccupancyCount();
                newLog.setInventoryCount(inventory);
                newLog.setOccupancyCount(occupancy);
                seatUtilisationLogRepository.save(newLog);
            }
        }


        // SG-HR-RCR
        Optional<BusinessUnit> optionalRecruiting = businessUnitRepository.findByCodeNonDeleted("BU-SG-RCR");
        Team interviewTeam = teamRepository.findTeamByCode("T-SG-RCR-INT");
        if (optionalRecruiting.isPresent()) {
            BusinessUnit recruitingUnit = optionalRecruiting.get();
            int yesterdayYear = DateHelper.getYearFromDate(date);
            int yesterdayMonth = DateHelper.getMonthFromDate(date);
            int yesterdayDayOfMonth = DateHelper.getDayOfMonthFromDate(date);
            for(int count = 0; count < periodLength; count++) {
                Date dateCounter = DateHelper.getDateByYearMonthDateHourMinuteSecond(yesterdayYear, yesterdayMonth, yesterdayDayOfMonth, 23, 59, 0);
                dateCounter = DateHelper.getDaysBefore(dateCounter, count);
                int year = DateHelper.getYearFromDate(dateCounter);
                int month = DateHelper.getMonthFromDate(dateCounter);
                int dayOfMonth = DateHelper.getDayOfMonthFromDate(dateCounter);

                Integer inventory = 0;
                Integer occupancy = 0;
                SeatUtilisationLog newLog = new SeatUtilisationLog();
                newLog.setLevelEntityId(recruitingUnit.getId());
                newLog.setHierarchyType(HierarchyTypeEnum.BUSINESS_UNIT);
                newLog.setCreatedTime(dateCounter);
                newLog.setYear(year);
                newLog.setMonth(month);
                newLog.setDayOfMonth(dayOfMonth);
                newLog.setOfficeId(office.get().getId());
                newLog.setHierarchyPath(recruitingUnit.getHierachyPath());

                Optional<SeatUtilisationLog> optionalIntTeamLog = seatUtilisationLogRepository.findOneByBusinessEntityIdAndDate(interviewTeam.getId(), year, month, dayOfMonth);
                SeatUtilisationLog interviewTeamLog = optionalIntTeamLog.get();
                inventory += interviewTeamLog.getInventoryCount();
                occupancy += interviewTeamLog.getOccupancyCount();
                newLog.setInventoryCount(inventory);
                newLog.setOccupancyCount(occupancy);
                seatUtilisationLogRepository.save(newLog);
            }
        }
    }

    private void initialiseCompanyFunctionUtilisationLog(Date date, int periodLength) {
        Optional<Office> office = officeRepository.findByName("One Raffles Quay");

        // SG-Tech
        Optional<CompanyFunction> optionalTech = functionRepository.findByCode("F-SG-TECH");
        if (optionalTech.isPresent()) {
            CompanyFunction tech = optionalTech.get();
            BusinessUnit fixIncTech = businessUnitRepository.findByCodeNonDeleted("BU-SG-FixIncTech").get();
            BusinessUnit infraTech = businessUnitRepository.findByCodeNonDeleted("BU-SG-InfraTech").get();
            int yesterdayYear = DateHelper.getYearFromDate(date);
            int yesterdayMonth = DateHelper.getMonthFromDate(date);
            int yesterdayDayOfMonth = DateHelper.getDayOfMonthFromDate(date);
            for(int count = 0; count < periodLength; count++) {
                Date dateCounter = DateHelper.getDateByYearMonthDateHourMinuteSecond(yesterdayYear, yesterdayMonth, yesterdayDayOfMonth, 23, 59, 0);
                dateCounter = DateHelper.getDaysBefore(dateCounter, count);
                int year = DateHelper.getYearFromDate(dateCounter);
                int month = DateHelper.getMonthFromDate(dateCounter);
                int dayOfMonth = DateHelper.getDayOfMonthFromDate(dateCounter);

                Integer inventory = 0;
                Integer occupancy = 0;
                SeatUtilisationLog newLog = new SeatUtilisationLog();
                newLog.setLevelEntityId(tech.getId());
                newLog.setHierarchyType(HierarchyTypeEnum.COMPANY_FUNCTION);
                newLog.setCreatedTime(dateCounter);
                newLog.setYear(year);
                newLog.setMonth(month);
                newLog.setDayOfMonth(dayOfMonth);
                newLog.setOfficeId(office.get().getId());
                newLog.setHierarchyPath(tech.getHierachyPath());

                Optional<SeatUtilisationLog> optionalFixIncTechLog = seatUtilisationLogRepository.findOneByBusinessEntityIdAndDate(fixIncTech.getId(), year, month, dayOfMonth);
                SeatUtilisationLog fixIncTechLog = optionalFixIncTechLog.get();
                inventory += fixIncTechLog.getInventoryCount();
                occupancy += fixIncTechLog.getOccupancyCount();
                Optional<SeatUtilisationLog> optionalInfraTechLog = seatUtilisationLogRepository.findOneByBusinessEntityIdAndDate(infraTech.getId(), year, month, dayOfMonth);
                SeatUtilisationLog infraTechLog = optionalInfraTechLog.get();
                inventory += infraTechLog.getInventoryCount();
                occupancy += infraTechLog.getOccupancyCount();
                newLog.setInventoryCount(inventory);
                newLog.setOccupancyCount(occupancy);
                seatUtilisationLogRepository.save(newLog);
            }
        }

        // SG-HR
        Optional<CompanyFunction> optionalHR = functionRepository.findByCode("F-SG-HR");
        if (optionalHR.isPresent()) {
            CompanyFunction hr = optionalHR.get();
            BusinessUnit recruitingUnit = businessUnitRepository.findByCodeNonDeleted("BU-SG-RCR").get();
            int yesterdayYear = DateHelper.getYearFromDate(date);
            int yesterdayMonth = DateHelper.getMonthFromDate(date);
            int yesterdayDayOfMonth = DateHelper.getDayOfMonthFromDate(date);
            for(int count = 0; count < periodLength; count++) {
                Date dateCounter = DateHelper.getDateByYearMonthDateHourMinuteSecond(yesterdayYear, yesterdayMonth, yesterdayDayOfMonth, 23, 59, 0);
                dateCounter = DateHelper.getDaysBefore(dateCounter, count);
                int year = DateHelper.getYearFromDate(dateCounter);
                int month = DateHelper.getMonthFromDate(dateCounter);
                int dayOfMonth = DateHelper.getDayOfMonthFromDate(dateCounter);

                Integer inventory = 0;
                Integer occupancy = 0;
                SeatUtilisationLog newLog = new SeatUtilisationLog();
                newLog.setLevelEntityId(hr.getId());
                newLog.setHierarchyType(HierarchyTypeEnum.COMPANY_FUNCTION);
                newLog.setCreatedTime(dateCounter);
                newLog.setYear(year);
                newLog.setMonth(month);
                newLog.setDayOfMonth(dayOfMonth);
                newLog.setOfficeId(office.get().getId());
                newLog.setHierarchyPath(hr.getHierachyPath());

                Optional<SeatUtilisationLog> optionalHRLog = seatUtilisationLogRepository.findOneByBusinessEntityIdAndDate(recruitingUnit.getId(), year, month, dayOfMonth);
                SeatUtilisationLog hrLog = optionalHRLog.get();
                inventory += hrLog.getInventoryCount();
                occupancy += hrLog.getOccupancyCount();
                newLog.setInventoryCount(inventory);
                newLog.setOccupancyCount(occupancy);
                seatUtilisationLogRepository.save(newLog);
            }
        }
    }

    private void initialiseOfficeAndOfficeFloorUtilisationLog(Date date, int periodLength) {
        // Office floor
        // Floor 26 only concerns tech function
        Optional<SeatMap> optionalSeatMapLvl26 = seatMapRepository.findByCode("SG-ORQ-26");
        if (optionalSeatMapLvl26.isPresent()) {
            SeatMap level26 = optionalSeatMapLvl26.get();
            CompanyFunction techFunction = functionRepository.findByCode("F-SG-TECH").get();
            int yesterdayYear = DateHelper.getYearFromDate(date);
            int yesterdayMonth = DateHelper.getMonthFromDate(date);
            int yesterdayDayOfMonth = DateHelper.getDayOfMonthFromDate(date);
            for(int count = 0; count < periodLength; count++) {
                Date dateCounter = DateHelper.getDateByYearMonthDateHourMinuteSecond(yesterdayYear, yesterdayMonth, yesterdayDayOfMonth, 23, 59, 0);
                dateCounter = DateHelper.getDaysBefore(dateCounter, count);
                int year = DateHelper.getYearFromDate(dateCounter);
                int month = DateHelper.getMonthFromDate(dateCounter);
                int dayOfMonth = DateHelper.getDayOfMonthFromDate(dateCounter);

                SeatUtilisationLog newLog = new SeatUtilisationLog();
                newLog.setLevelEntityId(level26.getId());
                newLog.setHierarchyType(HierarchyTypeEnum.OFFICE_FLOOR);
                newLog.setCreatedTime(dateCounter);
                newLog.setYear(year);
                newLog.setMonth(month);
                newLog.setDayOfMonth(dayOfMonth);
                newLog.setOfficeId(level26.getOffice().getId());
                newLog.setHierarchyPath(level26.getHierachyPath());

                Optional<SeatUtilisationLog> optionalTechLog = seatUtilisationLogRepository.findOneByBusinessEntityIdAndDate(techFunction.getId(), year, month, dayOfMonth);
                SeatUtilisationLog techLog = optionalTechLog.get();
                newLog.setInventoryCount(level26.getNumOfSeats());
                newLog.setOccupancyCount(techLog.getInventoryCount());
                seatUtilisationLogRepository.save(newLog);
            }
        }

        // Floor 27 only concerns HR function
        Optional<SeatMap> optionalSeatMapLvl27 = seatMapRepository.findByCode("SG-ORQ-27");
        if (optionalSeatMapLvl27.isPresent()) {
            SeatMap level27 = optionalSeatMapLvl27.get();
            CompanyFunction hrFunction = functionRepository.findByCode("F-SG-HR").get();
            int yesterdayYear = DateHelper.getYearFromDate(date);
            int yesterdayMonth = DateHelper.getMonthFromDate(date);
            int yesterdayDayOfMonth = DateHelper.getDayOfMonthFromDate(date);
            for(int count = 0; count < periodLength; count++) {
                Date dateCounter = DateHelper.getDateByYearMonthDateHourMinuteSecond(yesterdayYear, yesterdayMonth, yesterdayDayOfMonth, 23, 59, 0);
                dateCounter = DateHelper.getDaysBefore(dateCounter, count);
                int year = DateHelper.getYearFromDate(dateCounter);
                int month = DateHelper.getMonthFromDate(dateCounter);
                int dayOfMonth = DateHelper.getDayOfMonthFromDate(dateCounter);

                SeatUtilisationLog newLog = new SeatUtilisationLog();
                newLog.setLevelEntityId(level27.getId());
                newLog.setHierarchyType(HierarchyTypeEnum.OFFICE_FLOOR);
                newLog.setCreatedTime(dateCounter);
                newLog.setYear(year);
                newLog.setMonth(month);
                newLog.setDayOfMonth(dayOfMonth);
                newLog.setOfficeId(level27.getOffice().getId());
                newLog.setHierarchyPath(level27.getHierachyPath());

                Optional<SeatUtilisationLog> optionalTechLog = seatUtilisationLogRepository.findOneByBusinessEntityIdAndDate(hrFunction.getId(), year, month, dayOfMonth);
                SeatUtilisationLog techLog = optionalTechLog.get();
                newLog.setInventoryCount(level27.getNumOfSeats());
                newLog.setOccupancyCount(techLog.getInventoryCount());
                seatUtilisationLogRepository.save(newLog);
            }
        }

        // ORQ
        Office office = officeRepository.findByName("One Raffles Quay").get();
        SeatMap level26 = seatMapRepository.findByCode("SG-ORQ-26").get();
        SeatMap level27 = seatMapRepository.findByCode("SG-ORQ-27").get();
        int yesterdayYear = DateHelper.getYearFromDate(date);
        int yesterdayMonth = DateHelper.getMonthFromDate(date);
        int yesterdayDayOfMonth = DateHelper.getDayOfMonthFromDate(date);
        for(int count = 0; count < periodLength; count++) {
            Date dateCounter = DateHelper.getDateByYearMonthDateHourMinuteSecond(yesterdayYear, yesterdayMonth, yesterdayDayOfMonth, 23, 59, 0);
            dateCounter = DateHelper.getDaysBefore(dateCounter, count);
            int year = DateHelper.getYearFromDate(dateCounter);
            int month = DateHelper.getMonthFromDate(dateCounter);
            int dayOfMonth = DateHelper.getDayOfMonthFromDate(dateCounter);

            Integer inventory = 0;
            Integer occupancy = 0;
            SeatUtilisationLog newLog = new SeatUtilisationLog();
            newLog.setLevelEntityId(office.getId());
            newLog.setHierarchyType(HierarchyTypeEnum.OFFICE);
            newLog.setCreatedTime(dateCounter);
            newLog.setYear(year);
            newLog.setMonth(month);
            newLog.setDayOfMonth(dayOfMonth);
            newLog.setOfficeId(office.getId());
            newLog.setHierarchyPath(office.getHierachyPath());

            Optional<SeatUtilisationLog> optionalLvl26Log = seatUtilisationLogRepository.findOneByBusinessEntityIdAndDate(level26.getId(), year, month, dayOfMonth);
            SeatUtilisationLog lvl26Log = optionalLvl26Log.get();
            inventory += lvl26Log.getInventoryCount();
            occupancy += lvl26Log.getOccupancyCount();
            Optional<SeatUtilisationLog> optionalLvl27Log = seatUtilisationLogRepository.findOneByBusinessEntityIdAndDate(level27.getId(), year, month, dayOfMonth);
            SeatUtilisationLog lvl27Log = optionalLvl27Log.get();
            inventory += lvl27Log.getInventoryCount();
            occupancy += lvl27Log.getOccupancyCount();
            newLog.setInventoryCount(inventory);
            newLog.setOccupancyCount(occupancy);
            seatUtilisationLogRepository.save(newLog);
        }
    }
}
