package capstone.is4103capstone.seat;

import capstone.is4103capstone.admin.repository.OfficeRepository;
import capstone.is4103capstone.entities.Office;
import capstone.is4103capstone.entities.seat.Seat;
import capstone.is4103capstone.entities.seat.SeatMap;
import capstone.is4103capstone.seat.repository.SeatMapRepository;
import capstone.is4103capstone.seat.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class SeatInitialization {

    @Autowired
    private SeatMapRepository seatMapRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private OfficeRepository officeRepository;

    @PostConstruct
    public void init() {
        List<SeatMap> seatMaps = seatMapRepository.findAllUndeleted();
        if (seatMaps == null || seatMaps.size() == 0) {
            createSeatmaps();
        }
    }

    private void createSeatmaps() {
        Optional<Office> office = officeRepository.findByName("One Raffles Quay");
        if (office.isPresent()) {
            try {
                SeatMap newSeatMap = new SeatMap("SG-ORQ-26","SG-ORQ-26","SG-ORQ-26", office.get(), "26");

                Seat seat1 = new Seat("SG-ORQ-26-01", "SG-ORQ-26-01", "SG-ORQ-26-01", 0, 0, newSeatMap);
                Seat seat2 = new Seat("SG-ORQ-26-02", "SG-ORQ-26-02", "SG-ORQ-26-02", 100, 0, newSeatMap);
                Seat seat3 = new Seat("SG-ORQ-26-03", "SG-ORQ-26-03", "SG-ORQ-26-03", 200, 0, newSeatMap);
                Seat seat4 = new Seat("SG-ORQ-26-04", "SG-ORQ-26-04", "SG-ORQ-26-04", 300, 0, newSeatMap);

                seat1.setSerialNumber(1);
                seat2.setSerialNumber(2);
                seat3.setSerialNumber(3);
                seat4.setSerialNumber(4);

                newSeatMap.getSeats().add(seat1);
                newSeatMap.getSeats().add(seat2);
                newSeatMap.getSeats().add(seat3);
                newSeatMap.getSeats().add(seat4);

                newSeatMap.setNumOfSeats(newSeatMap.getSeats().size());
                newSeatMap = seatMapRepository.save(newSeatMap);

                for (Seat seat: newSeatMap.getSeats()) {
                    seat.setOriginalSeatMapId(newSeatMap.getId());
                    seatRepository.save(seat);
                }
            } catch (Exception ex) {
                System.out.println("Seat Management Initialisation failed: " + ex.getMessage());
            }
        }
    }
}
