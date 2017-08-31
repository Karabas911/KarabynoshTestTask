package com.example.karabushka.karabynoshtesttask;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;


/**
 * Created by Karabushka on 31.08.2017.
 */

public class FragmentFirst extends Fragment {
    public static final String TAG = FragmentFirst.class.getName();
    private static final int COUNT_1 = 1;
    private static final int COUNT_2 = 2;
    private static final int COUNT_3 = 3;
    private static final int GO_TO_SCREEN_2 = 4;

    private Button mButtonGoTo2;
    private Handler mHandler;
    private OnFragmentButtonSelected mOnFragmentButtonSelected;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_first, null);

        mHandler = createHandler();

        mButtonGoTo2 = (Button) v.findViewById(R.id.btn_go_to_2);
        mButtonGoTo2.setClickable(true);
        mButtonGoTo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //blocks user from over tapping, disable program crashing
                mButtonGoTo2.setClickable(false);
                //starts background thread
                Log.d(TAG,"onClick, start count");
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        try {
                            mHandler.sendEmptyMessage(COUNT_3);
                            TimeUnit.SECONDS.sleep(1);

                            mHandler.sendEmptyMessage(COUNT_2);
                            TimeUnit.SECONDS.sleep(2);

                            mHandler.sendEmptyMessage(COUNT_1);
                            TimeUnit.SECONDS.sleep(3);

                            mHandler.sendEmptyMessage(GO_TO_SCREEN_2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                t.start();
            }
        });
        Log.d(TAG,"onCreateView, FragmentFirst created");
        return v;
    }

    //creates Handler, handle massages from background thread
    private Handler createHandler() {
        Log.d(TAG,"createHandler, handler created");
        return new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case COUNT_1:
                        Toast.makeText(getActivity(), "1", Toast.LENGTH_SHORT).show();
                        break;
                    case COUNT_2:
                        Toast.makeText(getActivity(), "2", Toast.LENGTH_SHORT).show();
                        break;
                    case COUNT_3:
                        Toast.makeText(getActivity(), "3", Toast.LENGTH_SHORT).show();
                        break;
                    case GO_TO_SCREEN_2:
                        //give activity signal to change screen
                        mOnFragmentButtonSelected.onButtonSelected(mButtonGoTo2.getId());
                        break;
                }
            }
        };
    }

    //checks whether this class implements OnFragmentButtonSelected interface
    @Override
    public void onAttach(Context context) {
            super.onAttach(context);
            try {
                mOnFragmentButtonSelected = (OnFragmentButtonSelected) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString()
                        + " must implement OnHeadlineSelectedListener");
            }
        }

}
