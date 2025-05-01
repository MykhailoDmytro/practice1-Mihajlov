package ua.opnu.equipment_rental;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final EquipmentRepository equipmentRepository;

    public RentalService(RentalRepository rentalRepository, EquipmentRepository equipmentRepository) {
        this.rentalRepository = rentalRepository;
        this.equipmentRepository = equipmentRepository;
    }
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Rental save(Rental rental) {
        return rentalRepository.save(rental);
    }

    public List<Rental> getRentalsByCustomer(Long customerId) {
        return rentalRepository.findByCustomerId(customerId);
    }

    public List<Rental> getRentalsByEmployee(Long employeeId) {
        return rentalRepository.findByEmployeeId(employeeId);
    }

    public Rental returnRental(Long id) {
        Rental rental = rentalRepository.findById(id).orElseThrow();
        rental.setReturned(true);
        return rentalRepository.save(rental);
    }

    public void delete(Long id) {
        rentalRepository.deleteById(id);
    }

    public List<Rental> getActiveRentals() {
        return rentalRepository.findByReturnedFalse();
    }

    public List<Rental> getOverdueRentals() {
        return rentalRepository.findOverdueRentals();
    }

    public List<Equipment> getAvailableEquipmentOnDate(LocalDate date) {
        List<Long> unavailableIds = rentalRepository.findUnavailableEquipmentIdsByDate(date);
        return equipmentRepository.findAll()
                .stream()
                .filter(e -> !unavailableIds.contains(e.getId()) && Boolean.TRUE.equals(e.getAvailability()))
                .collect(Collectors.toList());
    }

    public Long countRentalsByEquipment(Long equipmentId) {
        return rentalRepository.countByEquipmentId(equipmentId);
    }

    // Обчислюємо загальний дохід
    public Double getTotalRevenue() {
        List<Rental> rentals = rentalRepository.findReturnedRentals(); // Отримуємо всі повернуті оренди
        return rentals.stream() // Перетворюємо на потік
                .mapToDouble(rental -> rental.calculateRentalCost().doubleValue()) // Розраховуємо дохід для кожної оренди
                .sum();
    }

    public List<Equipment> getMostRentedEquipment() {
        return rentalRepository.findMostRentedEquipment();
    }
}
