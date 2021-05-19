package com.epacheco.reports.tools.customView;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.epacheco.reports.R;
import com.epacheco.reports.databinding.CustomBotonMenuBinding;

public class ButtonMenu extends RelativeLayout {
  private CustomBotonMenuBinding binding;

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
    binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.custom_boton_menu, this, true);
    TypedArray ta = getContext().obtainStyledAttributes(set, R.styleable.ButtonMenu);
    binding.imgProduct.setImageResource(ta.getResourceId(R.styleable.ButtonMenu_image ,0));
    binding.lblTitleAction.setText(ta.getText(R.styleable.ButtonMenu_title));
    binding.lblSubtitleAction.setText(ta.getText(R.styleable.ButtonMenu_subTitle));
    ta.recycle();
  }


}
