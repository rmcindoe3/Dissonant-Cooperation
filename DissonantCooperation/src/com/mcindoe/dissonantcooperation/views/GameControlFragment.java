package com.mcindoe.dissonantcooperation.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mcindoe.dissonantcooperation.R;

public class GameControlFragment extends Fragment {

	private Button mLeftButton;
	private Button mRightButton;
	private Button mUpButton;
	private Button mDownButton;

	public GameControlFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_game, container, false);
		
		mLeftButton = (Button)rootView.findViewById(R.id.button_left);
		mRightButton = (Button)rootView.findViewById(R.id.button_right);
		mUpButton = (Button)rootView.findViewById(R.id.button_up);
		mDownButton = (Button)rootView.findViewById(R.id.button_down);
		
		return rootView;
	}

}
