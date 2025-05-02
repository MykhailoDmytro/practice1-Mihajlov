package ua.opnu.equipment_rental;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final EquipmentRepository equipmentRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    public RentalService(
            RentalRepository rentalRepository,
            EquipmentRepository equipmentRepository,
            CustomerRepository customerRepository,
            EmployeeRepository employeeRepository) {
        this.rentalRepository = rentalRepository;
        this.equipmentRepository = equipmentRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
    }

    public Rental save(Rental rental) {
        Equipment equipment = equipmentRepository.findById(rental.getEquipment().getId())
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
        Customer customer = customerRepository.findById(rental.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Employee employee = employeeRepository.findById(rental.getEmployee().getId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        rental.setEquipment(equipment);
        rental.setCustomer(customer);
        rental.setEmployee(employee);

        return rentalRepository.save(rental);
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
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

    public Double getTotalRevenue() {
        List<Rental> rentals = rentalRepository.findReturnedRentals();
        return rentals.stream()
                .mapToDouble(r -> r.calculateRentalCost().doubleValue())
                .sum();
    }

    public List<Equipment> getMostRentedEquipment() {
        return rentalRepository.findMostRentedEquipment();
    }
}
