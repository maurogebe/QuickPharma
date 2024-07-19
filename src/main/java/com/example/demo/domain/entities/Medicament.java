package com.example.demo.domain.entities;
import lombok.Data;
@Data
public class Medicament {

    private Long id;
    private String name;
    private String description;
    private String form;
    private int stocks;
    private Double cost;
    private boolean prescriptionRequired;

    public Medicament(Long id, String name, String description, String form, int stocks, Double cost, boolean prescriptionRequired) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.form = form;
        this.stocks = stocks;
        this.cost = cost;
        this.prescriptionRequired = prescriptionRequired;
    }

    public Medicament() {
    }

    public void createMedicament() {

    }
    public void updateMedicament() {

    }
    public void printMedicament(Long id) {

    }
    public void searchMedicament(Long id) {

    }
}
