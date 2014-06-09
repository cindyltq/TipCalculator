package com.clearsky.tipcalculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;

import org.apache.commons.lang3.math.NumberUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class TipMainActivity extends Activity
{
    private TextView tvPercentage;
    private EditText etAmount;
    private TextView tvTipValue;
    private SeekBar sbPercentage;
    private int percentSelected;
    private static String SIGN = " %";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_tip_main);

	tvPercentage = (TextView) findViewById(R.id.tvPercentage);
	etAmount = (EditText) findViewById(R.id.etAmount);
	tvTipValue = (TextView) findViewById(R.id.tvTipValue);
	sbPercentage = (SeekBar) findViewById(R.id.sbPercentage);

	sbPercentage.setOnSeekBarChangeListener(getSeekBarChangeListener());
	etAmount.setOnEditorActionListener(getOnEditorActionListener());
    }

    public void calculateTip10(View v)
    {	
	calculateTips(10);
    }

    public void calculateTip15(View v)
    {
	calculateTips(15);
    }

    public void calculateTip20(View v)
    {
	calculateTips(20);
    }

    private void calculateTips(int percent)
    {
	percentSelected = percent;
	tvPercentage.setText(percent + SIGN);
	sbPercentage.setProgress(percent);
	
	String amount = etAmount.getText().toString();

	BigDecimal tips = BigDecimal.ZERO;

	if (amount != null && NumberUtils.isNumber(amount))
	{
	    BigDecimal baseValue = NumberUtils.createBigDecimal(amount);
	    BigDecimal percentValue = new BigDecimal(percent);

	    if (baseValue != null)
	    {
		tips = (percentValue.divide(new BigDecimal(100)))
			.multiply(baseValue, new MathContext(2, RoundingMode.HALF_UP));
	    }
	}

	String tipString = NumberFormat.getCurrencyInstance().format(new Double(tips.toPlainString()));

	tvTipValue.setText("  " + tipString);
    }

    private OnSeekBarChangeListener getSeekBarChangeListener()
    {
	return new OnSeekBarChangeListener()
	{
	    @Override
	    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
	    {
		tvPercentage.setText(progress + SIGN);
		calculateTips(progress);
	    }

	    @Override
	    public void onStartTrackingTouch(SeekBar seekBar)
	    {
		
	    }

	    @Override
	    public void onStopTrackingTouch(SeekBar seekBar)
	    {

	    }
	};
    }

    private OnEditorActionListener getOnEditorActionListener()
    {
	return new OnEditorActionListener()
	{
	    @Override
	    public boolean onEditorAction(TextView view, int actionId, KeyEvent event)
	    {
		int result = actionId & EditorInfo.IME_MASK_ACTION;
		switch (result)
		{
		   case EditorInfo.IME_ACTION_DONE:		      
		       if (percentSelected != 0)
		   	   calculateTips( percentSelected);
		    break; 
		}
		return false;
	    }
	};
    }

}
