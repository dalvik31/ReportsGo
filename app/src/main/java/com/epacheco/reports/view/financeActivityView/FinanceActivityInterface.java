package com.epacheco.reports.view.financeActivityView;

import com.epacheco.reports.Pojo.Sales.SalesDetail;

import java.util.List;

public interface FinanceActivityInterface {
    void successGetSales(List<SalesDetail> salesDetails);
    void errorGetSales(String error);
}
