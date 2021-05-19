package com.epacheco.reports.Model.FinanceActivityModel;

import com.epacheco.reports.Controller.FinanceActivityController.FinanceActivityController;
import com.epacheco.reports.Pojo.Sales.SalesDetail;
import com.epacheco.reports.view.financeActivityView.FinanceAcitivityView;

import java.util.List;

public class FinanceActitvityModel implements FinanceActivityModelInterface{

    private FinanceActivityController financeActivityController;
    private FinanceAcitivityView financeAcitivityView;

    public FinanceActitvityModel(FinanceAcitivityView financeAcitivityView) {
        this.financeAcitivityView = financeAcitivityView;
        this.financeActivityController = new FinanceActivityController(this);
    }

    @Override
    public void successGetSales(List<SalesDetail> salesDetails) {
        if (financeAcitivityView != null) {
            financeAcitivityView.successGetSales(salesDetails);
        }
    }

    @Override
    public void errorGetSales(String error) {
        if (financeAcitivityView != null) {
            financeAcitivityView.errorGetSales(error);
        }
    }

    @Override
    public void successCancelSale() {
        if (financeAcitivityView != null) {
            financeAcitivityView.successCancelSale();
        }
    }

    @Override
    public void errorCancelSale(String error) {
        if (financeAcitivityView != null) {
            financeAcitivityView.errorCancelSale(error);
        }
    }

    @Override
    public void getSales(long firstDate, long secondDate) {
        if (financeActivityController != null) {
            financeActivityController.getSales(firstDate,secondDate);
        }
    }

    @Override
    public void cancelSale(String saleiId) {
        if (financeActivityController != null) {
            financeActivityController.cancelSale(saleiId);
        }
    }
}
