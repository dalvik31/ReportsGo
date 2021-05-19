package com.epacheco.reports.Model.FinanceActivityModel;

import com.epacheco.reports.Pojo.Sales.SalesDetail;

import java.util.List;

public interface FinanceActivityModelInterface {
    void successGetSales(List<SalesDetail> salesDetails);
    void errorGetSales(String error);

    void getSales(long firstDate, long secondDate);
}
