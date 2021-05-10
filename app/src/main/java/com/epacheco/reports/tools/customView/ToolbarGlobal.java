package com.epacheco.reports.tools.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.epacheco.reports.R;
import com.epacheco.reports.databinding.CustomBotonMenuBinding;
import com.epacheco.reports.databinding.CustomToolbarBinding;
import com.google.android.material.appbar.AppBarLayout;

public class ToolbarGlobal extends AppBarLayout {
    private CustomToolbarBinding binding;
    public ToolbarGlobal(@NonNull Context context) {
        super(context);
        init(context,null);
    }

    public ToolbarGlobal(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public ToolbarGlobal(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }


    private void init(Context context,@Nullable AttributeSet set){
        if(set==null) return;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.custom_toolbar, this, true);
        TypedArray ta = getContext().obtainStyledAttributes(set, R.styleable.ToolbarGlobal);
        binding.imgProfile.setImageResource(ta.getResourceId(R.styleable.ToolbarGlobal_imageProfile ,0));
        binding.appBar.setTitle(ta.getText(R.styleable.ToolbarGlobal_toolbarTitle));
        binding.appBar.setSubtitle(ta.getText(R.styleable.ToolbarGlobal_toolbarSubtitle));
        ta.recycle();
    }

    public ImageView getImageView(){
        return binding.imgProfile;
    }


    public void setTitle(String title){
        binding.appBar.setTitle(title);
    }

    public void setTitle(int title){
        binding.appBar.setTitle(title);
    }
}
