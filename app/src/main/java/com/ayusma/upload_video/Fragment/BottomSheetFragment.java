package com.ayusma.upload_video.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ayusma.upload_video.HistoryActivity;
import com.ayusma.upload_video.R;


public class BottomSheetFragment extends BottomSheetDialogFragment {
    String mTag;

    public static BottomSheetDialogFragment newInstance(String tag){

        BottomSheetFragment f = new BottomSheetFragment();
        Bundle args = new Bundle();
        args.putString("TAG",tag);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTag = getArguments().getString("TAG");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.options_layout,container,false);
        Button btn_remove = view.findViewById(R.id.btn_remove);
        Button btn_report = view.findViewById(R.id.btn_report);
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HistoryActivity)getActivity()).gun();
                Toast.makeText(getContext(),"Remove",Toast.LENGTH_LONG).show();
                Log.d("Removed","History Removed");
            }
        });

        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Report",Toast.LENGTH_LONG).show();
                Log.d("Report","Reported");
            }
        });

        return view;


    }
}
