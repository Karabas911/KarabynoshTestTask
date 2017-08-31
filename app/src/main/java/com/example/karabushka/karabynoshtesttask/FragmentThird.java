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

import java.util.concurrent.TimeUnit;

/**
 * Created by Karabushka on 31.08.2017.
 */

public class FragmentThird extends Fragment {

    public static final String TAG = FragmentThird.class.getName();
    private static final int QUIT = 1;
    private Button mButtonQuit;
    private OnFragmentButtonSelected mOnFragmentButtonSelected;
    private Handler mHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_third, null);

        mHandler = createHandler();

        mButtonQuit = (Button) v.findViewById(R.id.btn_quit);

        mButtonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //waits 2 seconds to close app
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        try {
                            TimeUnit.SECONDS.sleep(2);
                            mHandler.sendEmptyMessage(QUIT);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                t.start();
            }
        });
        Log.d(TAG,"onCreateView, FragmentThird created");
        return v;
    }

    private Handler createHandler() {
        return new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case QUIT:
                        //give activity signal to close application
                        mOnFragmentButtonSelected.onButtonSelected(mButtonQuit.getId());
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
                    + " must implement OnFragmentButtonSelected");
        }
    }
}
