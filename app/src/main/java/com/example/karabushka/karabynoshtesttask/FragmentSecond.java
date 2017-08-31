package com.example.karabushka.karabynoshtesttask;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Karabushka on 31.08.2017.
 */

public class FragmentSecond extends Fragment {

    public static final String TAG = FragmentSecond.class.getName();
    private Button mButtonGoTo3;
    private OnFragmentButtonSelected mOnFragmentButtonSelected;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_second, null);

        mButtonGoTo3 = (Button) v.findViewById(R.id.btn_go_to_3);

        mButtonGoTo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //give activity signal to change screen
                mOnFragmentButtonSelected.onButtonSelected(mButtonGoTo3.getId());
            }
        });
        Log.d(TAG,"onCreateView, FragmentSecond created");
        return v;
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
