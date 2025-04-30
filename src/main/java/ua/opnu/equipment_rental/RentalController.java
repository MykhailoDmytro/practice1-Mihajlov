package ua.opnu.equipment_rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping
    public ResponseEntity<Rental> addRental(@RequestBody Rental rental) {
        return new ResponseEntity<>(rentalService.save(rental), HttpStatus.CREATED);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Rental>> getRentalsByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(rentalService.getRentalsByCustomer(customerId));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Rental>> getRentalsByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(rentalService.getRentalsByEmployee(employeeId));
    }

    @PutMapping("/return/{id}")
    public ResponseEntity<Rental> returnRental(@PathVariable Long id) {
        return ResponseEntity.ok(rentalService.returnRental(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        rentalService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active")
    public ResponseEntity<List<Rental>> getActiveRentals() {
        return ResponseEntity.ok(rentalService.getActiveRentals());
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Rental>> getOverdueRentals() {
        return ResponseEntity.ok(rentalService.getOverdueRentals());
    }

    @GetMapping("/available")
    public ResponseEntity<List<Equipment>> getAvailableEquipment(@RequestParam LocalDate date) {
        return ResponseEntity.ok(rentalService.getAvailableEquipmentOnDate(date));
    }

    @GetMapping("/equipment/{equipmentId}/count")
    public ResponseEntity<Long> countRentalsByEquipment(@PathVariable Long equipmentId) {
        return ResponseEntity.ok(rentalService.countRentalsByEquipment(equipmentId));
    }

    @GetMapping("/revenue")
    public ResponseEntity<Double> getTotalRevenue() {
        return ResponseEntity.ok(rentalService.getTotalRevenue());
    }

    @GetMapping("/most-rented")
    public ResponseEntity<List<Equipment>> getMostRentedEquipment() {
        return ResponseEntity.ok(rentalService.getMostRentedEquipment());
    }
}
