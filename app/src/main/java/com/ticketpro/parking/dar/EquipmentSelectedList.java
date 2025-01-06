package com.ticketpro.parking.dar;

import com.ticketpro.parking.dar.model.DarChildEquipments;
import com.ticketpro.parking.dar.model.Equipments;

import java.util.List;
import java.util.Map;

public interface EquipmentSelectedList {
    void push(Map<Equipments, List<DarChildEquipments>> aList);
}
