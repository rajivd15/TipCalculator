package com.vervibe.tipcalculator;

import java.text.NumberFormat;
import java.util.Locale;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	//Get the device locale and update the currency formatter accordingly
	//Format Currency and Percent values in the App
	private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
	private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

	//Static TextView's
	private TextView amountTextView;
	private TextView customPercentTextView;
	private TextView splitTextView;
	private TextView percent15TextView;
	private TextView tipTextView;
	private TextView totalTextView;

	//Initialize & declare variables
	public static final int MIN_VALUE = 1;
	private double billAmountEntered = 0.0;
	private double customPercentEntered = 0.18;
	private double splitNumber = 1.0;
	private TextView amountDisplayTextView;
	private TextView percentCustomTextView;
	private TextView tip15PercentTextView;
	private TextView total15PercentTextView;
	private TextView tipCustomTextView;
	private TextView totalCustomTextView;
	private TextView totalPerPerson15PercentTextView;
	private TextView totalPerPersonCustomTextView;
	private TextView splitNumberDisplayTextView;

	//Main Activity onCreate 
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Initialize all the Views in the Activity
		amountTextView = (TextView) findViewById(R.id.amountTextView);
		customPercentTextView = (TextView) findViewById(R.id.customPercentTextView);
		splitTextView = (TextView) findViewById(R.id.splitTextView);
		percent15TextView = (TextView) findViewById(R.id.percent15TextView);
		tipTextView = (TextView) findViewById(R.id.tipTextView);
		totalTextView = (TextView) findViewById(R.id.totalTextView);
		amountDisplayTextView = (TextView) findViewById(R.id.amountDisplayTextView);
		percentCustomTextView = (TextView) findViewById(R.id.percentCustomTextView);
		tip15PercentTextView = (TextView) findViewById(R.id.tip15PercentTextView);
		total15PercentTextView = (TextView) findViewById(R.id.total15PercentTextView);
		tipCustomTextView = (TextView) findViewById(R.id.tipCustomTextView);
		totalCustomTextView = (TextView) findViewById(R.id.totalCustomTextView);
		totalPerPerson15PercentTextView = (TextView) findViewById(R.id.totalPerPerson15PercentTextView);
		totalPerPersonCustomTextView = (TextView) findViewById(R.id.totalPerPersonCustomTextView);
		splitNumberDisplayTextView = (TextView) findViewById(R.id.splitNumberDisplayTextView);

		//Set Font Style for all TextViews
		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/DancingScript-Regular.otf");
		amountTextView.setTypeface(font);
		amountDisplayTextView.setTypeface(font);
		customPercentTextView.setTypeface(font);
		splitTextView.setTypeface(font);
		percent15TextView.setTypeface(font);
		percentCustomTextView.setTypeface(font);
		tipTextView.setTypeface(font);
		tip15PercentTextView.setTypeface(font);
		tipCustomTextView.setTypeface(font);
		totalTextView.setTypeface(font);
		total15PercentTextView.setTypeface(font);
		totalCustomTextView.setTypeface(font);
		splitNumberDisplayTextView.setTypeface(font);
		totalPerPerson15PercentTextView.setTypeface(font);
		totalPerPersonCustomTextView.setTypeface(font);

		//Update the Split Number TextView
		splitNumberDisplayTextView.setText("Split by " + Integer.toString((int) splitNumber));
		amountDisplayTextView.setText(currencyFormat.format(billAmountEntered));
		//Standard 15% tip Calculations
		updateStandard();
		//Custom Input % tip Calculations
		updateCustom();

		//Update the Bill Amount with user input
		EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
		amountEditText.addTextChangedListener(amountEditTextWatcher);

		//Update the Custom Tip SeekBar with user input
		SeekBar customTipSeekBar = (SeekBar) findViewById(R.id.customTipSeekBar);
		customTipSeekBar.setOnSeekBarChangeListener(customSeekBarListener);

		//Update the Split by SeekBar with user input
		SeekBar splitNumberSeekbar = (SeekBar) findViewById(R.id.splitNumberSeekBar);
		splitNumberSeekbar.setOnSeekBarChangeListener(splitNumberSeekBarListener);
	}

	//Updates the Standard 15% values
	private void updateStandard() {

		double fifteenPercentTip = billAmountEntered * 0.15;
		double fifteenPercentTotal = billAmountEntered + fifteenPercentTip;
		double fifteenPercentPerPersonTotal = fifteenPercentTotal / splitNumber;

		tip15PercentTextView.setText(currencyFormat.format(fifteenPercentTip));
		total15PercentTextView.setText(currencyFormat.format(fifteenPercentTotal));
		totalPerPerson15PercentTextView.setText(currencyFormat.format(fifteenPercentPerPersonTotal));
	}

	//Updates the Custom Tip % values entered by user
	private void updateCustom() {

		percentCustomTextView.setText(percentFormat.format(customPercentEntered));
		splitNumberDisplayTextView.setText("Split by " + Integer.toString((int) splitNumber));

		double customTip = billAmountEntered * customPercentEntered;
		double customTotal = billAmountEntered + customTip;
		double customPerPersonTotal = customTotal / splitNumber;

		tipCustomTextView.setText(currencyFormat.format(customTip));
		totalCustomTextView.setText(currencyFormat.format(customTotal));
		totalPerPersonCustomTextView.setText(currencyFormat.format(customPerPersonTotal));
	}

	//Bill Amount Text watcher implementation
	private TextWatcher amountEditTextWatcher = new TextWatcher() 
	{
		// called when the user enters a number
		@Override
		public void onTextChanged(CharSequence s, int start, 
				int before, int count) 
		{         
			// convert amountEditText's text to a double
			try
			{
				billAmountEntered = Double.parseDouble(s.toString()) / 100.0;
			} // end try
			catch (NumberFormatException e)
			{
				billAmountEntered = 0.0; // default if an exception occurs
			} // end catch 

			// display currency formatted bill amount
			amountDisplayTextView.setText(currencyFormat.format(billAmountEntered));
			updateStandard(); // update the 15% tip TextViews
			updateCustom(); // update the custom tip TextViews
		} // end method onTextChanged

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}
	};

	//Custom SeekBar listener implementation
	private OnSeekBarChangeListener customSeekBarListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {

			customPercentEntered = progress / 100.0;
			updateCustom();
		}
	};

	//Split number SeekBar listener implementation
	private OnSeekBarChangeListener splitNumberSeekBarListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			//SeekBar minimum value set to 1
			if(progress == 0){
				progress = progress+1;
				seekBar.setProgress(progress);
			}
			splitNumber = progress / 1.0;
			updateStandard();
			updateCustom();
		}
	};
}
