package net.breezeware.dynamo.drools.kjar.entity;

import java.util.List;
import java.util.Map;

public class Employee {
    private Map<String, List<Integer>> employeeLeavesMap;
    private boolean deductSalary;

    public boolean isDeductSalary() {
        return deductSalary;
    }

    public void setDeductSalary(boolean deductSalary) {
        this.deductSalary = deductSalary;
    }

    public Map<String, List<Integer>> getEmployeeLeavesMap() {
        return employeeLeavesMap;
    }

    public void setEmployeeLeavesMap(Map<String, List<Integer>> employeeLeavesMap) {
        this.employeeLeavesMap = employeeLeavesMap;
    }
}
