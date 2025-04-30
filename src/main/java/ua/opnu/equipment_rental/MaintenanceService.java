package ua.opnu.equipment_rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaintenanceService {
    private final MaintenanceRepository maintenanceRepository;
    private final EquipmentRepository equipmentRepository; // додаємо сюди

    @Autowired
    public MaintenanceService(MaintenanceRepository maintenanceRepository, EquipmentRepository equipmentRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.equipmentRepository = equipmentRepository;
    }

    public Maintenance addMaintenance(Maintenance maintenance) {
        Equipment equipment = equipmentRepository.findById(maintenance.getEquipment().getId())
                .orElseThrow(() -> new RuntimeException("Обладнання не знайдено"));

        maintenance.setEquipment(equipment);

        return maintenanceRepository.save(maintenance);
    }

    public List<Maintenance> getByEquipmentId(Long equipmentId) {
        return maintenanceRepository.findByEquipmentId(equipmentId);
    }
}
