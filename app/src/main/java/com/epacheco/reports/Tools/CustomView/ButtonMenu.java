package com.epacheco.reports.Tools.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.epacheco.reports.R;

public class ButtonMenu extends RelativeLayout {
  private View rootView;
  private ImageView imgButton;
  private TextView lblTitle;
  private TextView lblSubTitle;

  public ButtonMenu(Context context) {
    super(context);
    init(context,null);
  }

  public ButtonMenu(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context,attrs);
  }

  public ButtonMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context,attrs);
  }


  private void init(Context context,@Nullable AttributeSet set){
    if(set==null) return;
    rootView = inflate(context, R.layout.custom_boton_menu, this);
    TypedArray ta = getContext().obtainStyledAttributes(set, R.styleable.ButtonMenu);
    imgButton = rootView.findViewById(R.id.img_product);
    lblTitle = rootView.findViewById(R.id.lbl_title_action);
    lblSubTitle = rootView.findViewById(R.id.lbl_subtitle_action);
    imgButton.setImageResource(ta.getResourceId(R.styleable.ButtonMenu_image ,0));
    lblTitle.setText(ta.getText(R.styleable.ButtonMenu_title));
    lblSubTitle.setText(ta.getText(R.styleable.ButtonMenu_subTitle));
    ta.recycle();
  }


}
