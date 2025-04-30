package ua.opnu.equipment_rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maintenance")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @Autowired
    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @PostMapping
    public ResponseEntity<Maintenance> addMaintenance(@RequestBody Maintenance maintenance) {
        Maintenance created = maintenanceService.addMaintenance(maintenance);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/equipment/{equipmentId}")
    public ResponseEntity<List<Maintenance>> getMaintenanceByEquipment(@PathVariable Long equipmentId) {
        return ResponseEntity.ok(maintenanceService.getByEquipmentId(equipmentId));
    }
}
