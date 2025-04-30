package ua.opnu.equipment_rental;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    // Отримати оренди за клієнтом
    List<Rental> findByCustomerId(Long customerId);

    // Отримати оренди за співробітником
    List<Rental> findByEmployeeId(Long employeeId);

    // Отримати неповернуті оренди
    List<Rental> findByReturnedFalse();

    // Запит на отримання прострочених оренд
    @Query("SELECT r FROM Rental r WHERE r.returned = false AND r.endDate < CURRENT_DATE")
    List<Rental> findOverdueRentals();

    // Підрахувати кількість оренд для конкретної техніки
    @Query("SELECT COUNT(r) FROM Rental r WHERE r.equipment.id = :equipmentId")
    Long countByEquipmentId(Long equipmentId);

    // Отримати id техніки, яка зайнята на певну дату
    @Query("SELECT r.equipment.id FROM Rental r WHERE :date BETWEEN r.startDate AND r.endDate AND r.returned = false")
    List<Long> findUnavailableEquipmentIdsByDate(LocalDate date);

    // Запит на повернуті оренди
    @Query("SELECT r FROM Rental r WHERE r.returned = true")
    List<Rental> findReturnedRentals();

    // Знайти найорендованішу техніку
    @Query("SELECT r.equipment FROM Rental r GROUP BY r.equipment ORDER BY COUNT(r) DESC")
    List<Equipment> findMostRentedEquipment();
}
