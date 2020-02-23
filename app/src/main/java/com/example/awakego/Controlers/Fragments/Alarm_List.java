package com.example.awakego.Controlers.Fragments;

import android.content.Context;

import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.awakego.Models.Alarm;
import com.example.awakego.Models.AlarmViewModel;
import com.example.awakego.R;
import com.example.awakego.Views.AlarmAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class Alarm_List extends Fragment {
    private RecyclerView mRecyclerView;
    private AlarmViewModel alarmViewModel;
    private AlarmAdapter mAlarmAdapter;
    private FloatingActionButton floating_button;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    //The onCreate method is called when the Fragment instance is being created, or re-created.
    // Use onCreate for any standard setup that does not require the activity to be fully created

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Alarm> mAlarms = new ArrayList<>();
        mAlarmAdapter = new AlarmAdapter(mAlarms);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.alarm_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView = view.findViewById(R.id.recycler);
        floating_button = view.findViewById(R.id.fab);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAlarmAdapter);

        floating_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AlarmFragment()).addToBackStack(null).commit();
            }
        });

        insertNestedFragment();
    }

    private void insertNestedFragment()
    {
        Fragment motivationFragment = new MotivationFragment();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.child_fragment_container, motivationFragment).commit();
    }

    @Nullable
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        //alarmViewModel = ViewModelProviders.of(getActivity()).get(AlarmViewModel.class);



        alarmViewModel = new ViewModelProvider(getActivity()).get(AlarmViewModel.class);
        alarmViewModel.getMallAlarms().observe(getViewLifecycleOwner(), new Observer<List<Alarm>>() {
            @Override
            public void onChanged(List<Alarm> alarms) {
                mAlarmAdapter.setAlarms(alarms);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
        {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target)
            {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
            {
                alarmViewModel.delete(mAlarmAdapter.getAlarmAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Alarm deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(mRecyclerView);

        mAlarmAdapter.setOnItemClickListener(new AlarmAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(Alarm alarm) {
                AlarmFragment alarmFragment = new AlarmFragment();
                Bundle args = new Bundle();
                Uri song = Uri.parse(alarm.getAlarm_song());
                String title = RingtoneManager.getRingtone(getActivity(),song).getTitle(getActivity());

                args.putLong(alarmFragment.EXTRA_ID, alarm.getAlarm_id());
                args.putString(alarmFragment.EXTRA_TITLE, alarm.getAlarm_name());
                args.putInt(alarmFragment.EXTRA_HOUR, alarm.getAlarm_hour());
                args.putInt(alarmFragment.EXTRA_MINUTE, alarm.getAlarm_minute());
                args.putInt(alarmFragment.EXTRA_DAY, alarm.getDay());
                args.putString(alarmFragment.EXTRA_SONG,title);
                alarmFragment.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, alarmFragment).commit();
            }
        });
    }
    // -------------------------------ActionBar Menu Items-----------------------------//

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.alarm_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all:
                alarmViewModel.deleteAllAlarms();
                Toast.makeText(getActivity(), "Alarms deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
//----------------------------------------------------------------------------------------//
    @Override
    public void onDetach() {
        super.onDetach();
    }
}

