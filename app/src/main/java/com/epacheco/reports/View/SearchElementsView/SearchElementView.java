package com.epacheco.reports.View.SearchElementsView;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.epacheco.reports.R;

public class SearchElementView extends AppCompatActivity {

  public final static String FROM_SEARCH = "fromSearch";
  public final static int FROM_SEARCH_CLIENT = 0;
  public final static int FROM_SEARCH_PRODUCT = 1;
  private int fromSearch;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_element_view);
    inicializateElements();

  }

  private void inicializateElements() {
    if(getIntent()==null) return;
    fromSearch = getIntent().getIntExtra(FROM_SEARCH,-1);
    createFragment(fromSearch);
  }
  private void createFragment(int fromSearch) {

  }

  private void loadFragment(Fragment fragment) {
    if (fragment != null) {
      getSupportFragmentManager()
          .beginTransaction()
          .replace(R.id.container_fragments, fragment)
          .commit();
    }
  }








}
