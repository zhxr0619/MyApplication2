package app.univs.cn.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class TabBFm extends Fragment {

    private String tag="TabBfm";
    private View currentView;

    private Context mContext;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(tag,"onAttach");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(tag,"onCreateView");
        currentView = inflater.inflate(R.layout.tab_a, container, false);
        mContext=getActivity();
        return currentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(tag,"onActivityCreated");
        ((TextView) getView().findViewById(R.id.tv)).setText(tag);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();

    }

    private void init(){
        currentView.findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,Main2Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(tag,"onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(tag,"onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(tag,"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(tag,"onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(tag,"onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(tag,"onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(tag,"onDetach");
    }
}


