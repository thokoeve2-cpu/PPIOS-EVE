package models;

import java.util.ArrayList;
import java.util.List;

public class WarehouseZone {
    private String zoneId;
    private String zoneName;
    private String temperatureRange;
    private int capacity;
    private boolean restricted;
    private String purpose;
    private final List<WarehouseSection> sections=new ArrayList<>();

    public WarehouseZone(String id,String name,String temperatureRange,int capacity,boolean restricted,String purpose) {
        this.zoneId=id;
        this.zoneName=name;
        this.temperatureRange=temperatureRange;
        this.capacity=capacity;
        this.restricted=restricted;
        this.purpose=purpose;
    }
    public boolean addSection(WarehouseSection section) {
        if(section==null || sections.size()>=capacity || sections.contains(section)) return false;
        sections.add(section);
        return true;
    }
    public boolean removeSection(WarehouseSection section) { return sections.remove(section); }
    public boolean isFull() { return sections.size()>=capacity; }
    public int availableCapacity() { return Math.max(0,capacity-sections.size()); }
    public boolean canStoreTemperatureSensitiveGoods() { return temperatureRange!=null &&
        !temperatureRange.equalsIgnoreCase("ambient"); }
    public void changePurpose(String value) { if(value!=null && !value.isBlank()) purpose=value; }
    public int sectionCount() { return sections.size(); }
}
