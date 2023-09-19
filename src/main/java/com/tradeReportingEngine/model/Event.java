package main.java.com.tradeReportingEngine.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sellerParty;
    private String premiumCurrency;
    private String buyerParty;

    public Event(String sellerParty, String premiumCurrency, String buyerParty) {
        this.sellerParty = sellerParty;
        this.premiumCurrency = premiumCurrency;
        this.buyerParty = buyerParty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSellerParty() {
        return sellerParty;
    }

    public void setSellerParty(String sellerParty) {
        this.sellerParty = sellerParty;
    }

    public String getPremiumCurrency() {
        return premiumCurrency;
    }

    public void setPremiumCurrency(String premiumCurrency) {
        this.premiumCurrency = premiumCurrency;
    }

    public String getBuyerParty() {
        return buyerParty;
    }

    public void setBuyerParty(String buyerParty) {
        this.buyerParty = buyerParty;
    }
}
