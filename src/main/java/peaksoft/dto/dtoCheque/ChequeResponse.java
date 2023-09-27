package peaksoft.dto.dtoCheque;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public class ChequeResponse {
    private Long id;
    private String waiterFullName;
    private List<String> items;
    private int priceAverage;
    private int service;
    private int grandTotal;
    private LocalDateTime createdAt;

    public ChequeResponse(Long id, String waiterFullName, List<String> items, int priceAverage, int service, int grandTotal, LocalDateTime createdAt) {
        this.id = id;
        this.waiterFullName = waiterFullName;
        this.items = items;
        this.priceAverage = priceAverage;
        this.service = service;
        this.grandTotal = grandTotal;
        this.createdAt = createdAt;
    }

    public ChequeResponse withUpdatedItems(List<String> newItems) {
        return new ChequeResponse(this.id, this.waiterFullName, newItems, this.priceAverage, this.service, this.grandTotal, this.createdAt);
    }

    public ChequeResponse(Long id, int priceAverage) {
        this(id, null, null, priceAverage, 0, 0, null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWaiterFullName() {
        return waiterFullName;
    }

    public void setWaiterFullName(String waiterFullName) {
        this.waiterFullName = waiterFullName;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public int getPriceAverage() {
        return priceAverage;
    }

    public void setPriceAverage(int priceAverage) {
        this.priceAverage = priceAverage;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public int getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(int grandTotal) {
        this.grandTotal = grandTotal;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
