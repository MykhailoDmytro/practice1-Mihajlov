package ua.opnu.equipment_rental;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {
    private final EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public Equipment save(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    public Equipment update(Long id, Equipment equipment) {
        Equipment existing = equipmentRepository.findById(id).orElseThrow();
        existing.setName(equipment.getName());
        existing.setType(equipment.getType());
        existing.setDailyRate(equipment.getDailyRate());
        existing.setAvailability(equipment.getAvailability());
        return equipmentRepository.save(existing);
    }

    public void delete(Long id) {
        equipmentRepository.deleteById(id);
    }
}
