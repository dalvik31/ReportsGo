package com.epacheco.reports.view.orderView.orderCreateView;

import androidx.fragment.app.FragmentActivity;
import com.epacheco.reports.Pojo.Client.Client;

public interface OrderCreateViewInterface {
  FragmentActivity getMyActivity();
  void successCreateOrder();
  void errorCreateOrder(String error);

  void successGetClient(Client client);
  void errorGetClient(String error);
}
