package com.clearsky.tipcalculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;

import org.apache.commons.lang3.math.NumberUtils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class TipMainActivity extends Activity
{
    private Button button10;
    private Button button15;
    private Button button20;
    private TextView tvPercentage;
    private EditText etAmount;
    private TextView tvTipValue;
    private TextView tvTotalValue;
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
	tvTotalValue = (TextView) findViewById(R.id.tvTotalValue);
	sbPercentage = (SeekBar) findViewById(R.id.sbPercentage);
	button10 = (Button) findViewById(R.id.bt10);
	button15 = (Button) findViewById(R.id.bt15);
	button20 = (Button) findViewById(R.id.bt20);

	sbPercentage.setOnSeekBarChangeListener(getSeekBarChangeListener());
	etAmount.setOnEditorActionListener(getOnEditorActionListener());
    }

    public void onButtonClick(View v)
    {
	if(v.getTag().equals("10"))
	    calculateTip10();
	else if (v.getTag().equals("15"))
	    calculateTip15();
	else if (v.getTag().equals("20"))
	    calculateTip20();
    }
    
    public void calculateTip10()
    {	
	selectButton(button10);
	deSelectButton(button15);
	deSelectButton(button20);	 
	
	calculateTips(10);
    }

    public void calculateTip15()
    {
	selectButton(button15);
	deSelectButton(button10);
	deSelectButton(button20);	
	
	calculateTips(15);
    }

    public void calculateTip20()
    {
	selectButton(button20);
	deSelectButton(button15);
	deSelectButton(button10);	
	
	calculateTips(20);
    }
    
    private void selectButton(Button button)
    {
	button.setBackgroundColor(Color.parseColor("#FFA500")); //orange
	button.setTextColor(Color.parseColor("#33B5E5"));  //blue
    }
    
    private void deSelectButton(Button button)
    {
	button.setBackgroundColor(Color.parseColor("#33B5E5")); //blue		
	button.setTextColor(Color.parseColor("#FFFFFF")); //white
    }

    private void calculateTips(int percent)
    {
	percentSelected = percent;
	tvPercentage.setText(percent + SIGN);
	sbPercentage.setProgress(percent);
	
	String baseAmount = etAmount.getText().toString();

	BigDecimal tips = BigDecimal.ZERO;
	BigDecimal totalAmount = BigDecimal.ZERO;

	if (baseAmount != null && NumberUtils.isNumber(baseAmount))
	{
	    BigDecimal baseValue = NumberUtils.createBigDecimal(baseAmount);
	    BigDecimal percentValue = new BigDecimal(percent);

	    if (baseValue != null)
	    {
		tips = (percentValue.divide(new BigDecimal(100)))
			.multiply(baseValue, new MathContext(2, RoundingMode.HALF_UP));
		
		totalAmount = baseValue.add(tips);
	    }
	}

	String tipString = NumberFormat.getCurrencyInstance().format(new Double(tips.toPlainString()));
	String totalString = NumberFormat.getCurrencyInstance().format(new Double(totalAmount.toPlainString()));

	tvTipValue.setText("  " + tipString);	
	tvTotalValue.setText("  " + totalString);
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
		deSelectButton(button15);
		deSelectButton(button10);
		deSelectButton(button20);
		
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
