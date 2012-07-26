package br.com.euandroid.timersocket;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import br.com.euandroid.timersocket.ElectricSocketHttpClient;

public class TimerSocketActivity extends Activity {
    /** Called when the activity is first created. */
	
	private TextView mTimeDisplay;
    private Button mPickTime;

    private int mHour;
    private int mMinute;
    
    private Button mSendTime;

    static final int TIME_DIALOG_ID = 0;
    
    private ElectricSocketHttpClient electricSocketHttpClient = new ElectricSocketHttpClient();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
     // capture our View elements
        mTimeDisplay = (TextView) findViewById(R.id.timeDisplay);
        mPickTime = (Button) findViewById(R.id.pickTime);
        mSendTime = (Button) findViewById(R.id.sendTime);

        // add a click listener to the button
        mPickTime.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });
        
        // add a click listener to the button
        mSendTime.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v){
        		try {
					sendTimeToEletricSocket();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });

        // get the current time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // display the current date
        updateDisplay();

    }
    
    public void sendTimeToEletricSocket() throws ParseException{
    	Date now = new Date();
    	
    	Date data = new Date();
    	data.setHours(mHour);
    	data.setMinutes(mMinute);
    	
    	long interval = data.getTime() - now.getTime();
    	
    	try {
			electricSocketHttpClient.executeHttpGet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
 // updates the time we display in the TextView
    private void updateDisplay() {
        mTimeDisplay.setText(
            new StringBuilder()
                    .append(pad(mHour)).append(":")
                    .append(pad(mMinute)));
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
    
 // the callback received when the user "sets" the time in the dialog
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
        new TimePickerDialog.OnTimeSetListener() {
            @Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
                updateDisplay();
            }
        };
    
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case TIME_DIALOG_ID:
            return new TimePickerDialog(this,
                    mTimeSetListener, mHour, mMinute, false);
        }
        return null;
    }
}