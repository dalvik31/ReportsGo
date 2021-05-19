package com.epacheco.reports.Controller.FinanceActivityController;

public interface FinanceActivityControllerInterface {
    void getSales(long firstDate, long secondDate);

    void cancelSale(String saleiId);
}
