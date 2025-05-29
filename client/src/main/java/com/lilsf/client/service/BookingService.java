package com.lilsf.client.service;

import com.lilsf.client.models.Booking;
import com.lilsf.client.models.Client;
import com.lilsf.client.models.Room;
import com.lilsf.client.models.enums.BookingStatus;
import com.lilsf.client.repositories.BookingRepository;
import com.lilsf.client.repositories.ClientRepository;
import com.lilsf.client.repositories.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final ClientRepository clientRepository;

    public BookingService(BookingRepository bookingRepository, RoomRepository roomRepository, ClientRepository clientRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional
    public void makeReservations(int clientId, int roomId, Date startDate, Date endDate) {
        log.info("Attempting to reserve room ID: {} for client ID: {} from {} to {}", roomId, clientId, startDate, endDate);
        Optional<Client> client = clientRepository.findById(clientId);
        Optional<Room> room = roomRepository.findById(roomId);

        if (client.isEmpty() || room.isEmpty()) {
            log.error("Reservation failed: The client or hotel room does not exist. Client ID: {}, Room ID: {}", clientId, roomId);
            throw new RuntimeException("The client or hotel room does not exist");
        }

        // if (roomRepository.findAvailableRoom(startDate, endDate, clientId).isPresent()) {
        //     log.error("Reservation failed: The room is already booked. Room ID: {}", roomId);
        //     throw new RuntimeException("The room is already booked");
        // }

        double roomRate = room.get().getCurrentPrice().doubleValue() * billForHotelRoom(startDate, endDate);

        Booking booking = Booking.builder()
                .startDate(startDate)
                .endDate(endDate)
                .discountPercent(BigDecimal.valueOf(0))
                .totalPrice(BigDecimal.valueOf(roomRate))
                .bookingStatus(BookingStatus.CONFIRMED)
                .client(client.get())
                .room(room.get())
                .build();

        bookingRepository.save(booking);
        log.info("Successfully reserved Room ID: {} for Client ID: {}. Total Price: {}", roomId, clientId, roomRate);
        /*
        Booking booking = new Booking();

        booking.setStartDate(new Date()); // Устанавливаем текущую дату как дату начала
        booking.setEndDate(new Date(System.currentTimeMillis() + 86400000)); // Устанавливаем дату окончания на следующий день
        booking.setDiscountPercent(BigDecimal.valueOf(10)); // Устанавливаем скидку 10%
        booking.setTotalPrice(BigDecimal.valueOf(100)); // Устанавливаем общую цену
        booking.setClient(client.get()); // Устанавливаем клиента
        booking.setRoom(room.get()); // Устанавливаем комнату
        bookingRepository.save(booking);
        //roomRepository.findById(roomId).ifPresent(room -> {});
         */
    }

    private int billForHotelRoom(Date startDate, Date endDate) {
        LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Вычисление периода между датами
        Period period = Period.between(startLocalDate, endLocalDate);

        return period.getDays();
    }
}