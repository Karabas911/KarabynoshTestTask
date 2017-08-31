package com.example.karabushka.karabynoshtesttask;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnFragmentButtonSelected{

    public static final String TAG = "MyLog";
    private FragmentFirst mFragmentFirst;
    private FragmentSecond mFragmentSecond;
    private FragmentThird mFragmentThird;
    private FragmentManager mManager;
    private FragmentTransaction mTransaction;
    private String mCurrentFragmentTag; //shows which Fragment is open, identificator
    private boolean mDoubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentFirst = new FragmentFirst();
        mFragmentSecond = new FragmentSecond();
        mFragmentThird = new FragmentThird();
        mManager = getSupportFragmentManager();

        //Set up launch UI, make first transaction
        mTransaction = mManager.beginTransaction();
        mCurrentFragmentTag = FragmentFirst.TAG;
        mTransaction.add(R.id.fragment_container, mFragmentFirst, FragmentFirst.TAG);
        mTransaction.addToBackStack(null);
        mTransaction.commit();
    }

    //Override method which handles requests from Fragments, switches screens
    @Override
    public void onButtonSelected(int buttonId) {

        mTransaction = mManager.beginTransaction();

        switch (buttonId) {

            //switch to second screen(FragmentSecond)
            case R.id.btn_go_to_2:
                mCurrentFragmentTag = FragmentSecond.TAG;
                mTransaction.replace(R.id.fragment_container, mFragmentSecond,FragmentSecond.TAG);
                mTransaction.addToBackStack(null);
                mTransaction.commit();

                // set UP button visible
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                break;

            //switch to third screen(FragmentThird)
            case R.id.btn_go_to_3:
                mCurrentFragmentTag = FragmentThird.TAG;
                mTransaction.replace(R.id.fragment_container, mFragmentThird,FragmentThird.TAG);
                mTransaction.addToBackStack(null);
                mTransaction.commit();
                break;

            //close application
            case R.id.btn_quit:
                this.finish();
                Log.d(TAG,"Application closed");
                break;
        }
    }

    // implements correct work of BACK button
    @Override
    public void onBackPressed() {
        //double click Close Operation
        if (mCurrentFragmentTag == FragmentFirst.TAG) {
            if (mDoubleBackToExitPressedOnce) {
                this.finish();
                Log.d(TAG,"onBackPressed, application closed");
                return;
            }
            this.mDoubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Tap again to quit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDoubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }

        // nothing happens when tap on back button on second screen
        if (mCurrentFragmentTag == FragmentSecond.TAG){
            Log.d(TAG,"onBackPressed, user try to press BACK from screen 2");
            return;
        }

        //back to first screen
        if (mCurrentFragmentTag == FragmentThird.TAG){
            mCurrentFragmentTag = FragmentFirst.TAG;
            mManager.popBackStack();
            mManager.popBackStack();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            Log.d(TAG,"onBackPressed, back to screen 1 from 3");
            return;
        }
    }

    // implements correct work of UP button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            //back to second screen
            if(mCurrentFragmentTag ==FragmentThird.TAG){
                mCurrentFragmentTag = FragmentSecond.TAG;
                mManager.popBackStack();
                Log.d(TAG,"onUpButtonSelected, back to screen 2");
                return true;
            }
            //back to first screen
            else if(mCurrentFragmentTag == FragmentSecond.TAG){
                mCurrentFragmentTag = FragmentFirst.TAG;
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                mManager.popBackStack();
                Log.d(TAG,"onUpButtonSelected, back to screen 1");
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
