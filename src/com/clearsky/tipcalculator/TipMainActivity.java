package com.clearsky.tipcalculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.apache.commons.lang3.math.NumberUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class TipMainActivity extends Activity
{
    private TextView tvPercentage;
    private EditText etAmount;
    private TextView tvTipValue;
    private SeekBar sbPercentage;

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
	String amount = etAmount.getText().toString();

	BigDecimal tips = BigDecimal.ZERO;

	if (amount != null && NumberUtils.isNumber(amount))
	{
	    BigDecimal baseValue = NumberUtils.createBigDecimal(amount);
	    BigDecimal percentValue = new BigDecimal(percent);

	    if (baseValue != null)
	    {
		 tips = ( percentValue.divide(new BigDecimal(100)))
			.multiply(baseValue, new MathContext(2, RoundingMode.HALF_UP));
	    }
	}

	tvTipValue.setText("  " + tips.toPlainString());
    }

    private OnSeekBarChangeListener getSeekBarChangeListener()
    {
	return new OnSeekBarChangeListener()
	{
	    @Override
	    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
	    {
		tvPercentage.setText(progress + " %");
		
		calculateTips(progress);
	    }

	    @Override
	    public void onStartTrackingTouch(SeekBar seekBar)
	    {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void onStopTrackingTouch(SeekBar seekBar)
	    {
		seekBar.setSecondaryProgress(seekBar.getProgress()); // set the shade of the previous value.


	    }
	};
    }

}
