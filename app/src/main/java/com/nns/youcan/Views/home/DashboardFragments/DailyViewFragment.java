package com.nns.youcan.Views.home.DashboardFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nns.youcan.R;


public class DailyViewFragment extends Fragment {
    private ProgressBar saturdayProgressBar;
    private ProgressBar sundayProgressBar;
    private ProgressBar mondayProgressBar;
    private ProgressBar thursdayProgressBar;
    private ProgressBar wednesdayProgressBar;
    private ProgressBar tuesdayProgressBar;
    private ProgressBar fridayProgressBar;
    private View[]weightProgress;
    private View[]caloriesProgress;
    private View[]stepProgress;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.daily_view_fragment,container,false);
        saturdayProgressBar=view.findViewById(R.id.Saturday_progressBar);
        sundayProgressBar=view.findViewById(R.id.sunday_progressBar);
        mondayProgressBar=view.findViewById(R.id.monday_progressBar);
        thursdayProgressBar=view.findViewById(R.id.thursday_progressBar);
        wednesdayProgressBar=view.findViewById(R.id.wednesday_progressBar);
        tuesdayProgressBar=view.findViewById(R.id.tuesday_progressBar);
        fridayProgressBar=view.findViewById(R.id.friday_progressBar);
        caloriesProgress= new View[]{view.findViewById(R.id.vc1),
                view.findViewById(R.id.vc2),
                view.findViewById(R.id.vc3),
                view.findViewById(R.id.vc4),
                view.findViewById(R.id.vc5),
                view.findViewById(R.id.vc6),
                view.findViewById(R.id.vc7),
                view.findViewById(R.id.vc8),
                view.findViewById(R.id.vc9),
                view.findViewById(R.id.vc10),
                view.findViewById(R.id.vc11),
                view.findViewById(R.id.vc12),
                view.findViewById(R.id.vc13),
                view.findViewById(R.id.vc14),
                view.findViewById(R.id.vc15),
                view.findViewById(R.id.vc16),
                view.findViewById(R.id.vc17),
                view.findViewById(R.id.vc18),
                view.findViewById(R.id.vc19),
                view.findViewById(R.id.vc20),
                view.findViewById(R.id.vc21),
                view.findViewById(R.id.vc22),
                view.findViewById(R.id.vc23),
                view.findViewById(R.id.vc24)};
        weightProgress= new View[]{view.findViewById(R.id.vw1),
                view.findViewById(R.id.vw2),
                view.findViewById(R.id.vw3),
                view.findViewById(R.id.vw4),
                view.findViewById(R.id.vw5),
                view.findViewById(R.id.vw6),
                view.findViewById(R.id.vw7),
                view.findViewById(R.id.vw8),
                view.findViewById(R.id.vw9),
                view.findViewById(R.id.vw10),
                view.findViewById(R.id.vw11),
                view.findViewById(R.id.vw12),
                view.findViewById(R.id.vw13),
                view.findViewById(R.id.vw14),
                view.findViewById(R.id.vw15),
                view.findViewById(R.id.vw16),
                view.findViewById(R.id.vw17),
                view.findViewById(R.id.vw18),
                view.findViewById(R.id.vw19),
                view.findViewById(R.id.vw20),
                view.findViewById(R.id.vw21),
                view.findViewById(R.id.vw22),
                view.findViewById(R.id.vw23),
                view.findViewById(R.id.vw24)};
        stepProgress= new View[]{view.findViewById(R.id.vs1),
                view.findViewById(R.id.vs2),
                view.findViewById(R.id.vs3),
                view.findViewById(R.id.vs4),
                view.findViewById(R.id.vs5),
                view.findViewById(R.id.vs6),
                view.findViewById(R.id.vs7),
                view.findViewById(R.id.vs8),
                view.findViewById(R.id.vs9),
                view.findViewById(R.id.vs10),
                view.findViewById(R.id.vs11),
                view.findViewById(R.id.vs12),
                view.findViewById(R.id.vs13),
                view.findViewById(R.id.vs14),
                view.findViewById(R.id.vs15),
                view.findViewById(R.id.vs16),
                view.findViewById(R.id.vs17),
                view.findViewById(R.id.vs18),
                view.findViewById(R.id.vs19),
                view.findViewById(R.id.vs20),
                view.findViewById(R.id.vs21),
                view.findViewById(R.id.vs22),
                view.findViewById(R.id.vs23),
                view.findViewById(R.id.vs24)};
        setWeightProgressAnimation(1,.5);
        setCaloriesProgressAnimation(4,3);
        setStepProgressAnimation(34,20);
        setEmojiStatus(new ProgressBar[]{saturdayProgressBar,sundayProgressBar,mondayProgressBar,thursdayProgressBar,wednesdayProgressBar,tuesdayProgressBar,fridayProgressBar},
                new ImageView[]{view.findViewById(R.id.saturday_emoji),view.findViewById(R.id.sunday_emoji),view.findViewById(R.id.monday_emoji),view.findViewById(R.id.tuesday_emoji),
                        view.findViewById(R.id.wednesday_emoji),view.findViewById(R.id.thursday_emoji),view.findViewById(R.id.friday_emoji)});
        return view;
    }

    private void setEmojiStatus( ProgressBar[] progressBars, ImageView[] emojies) {
        for (int i=0;i<progressBars.length;i++) {
            if (progressBars[i].getProgress() >= 50)
               emojies[i].setImageResource(R.drawable.ic_slightly_smilling);
            else if (progressBars[i].getProgress() < 50 && progressBars[i].getProgress() > 0)
                emojies[i].setImageResource(R.drawable.ic_sad);
            else
                emojies[i].setImageResource(R.drawable.null_icon);
        }
    }
    private void setCaloriesProgressAnimation(final double actual, final double done) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                int fillRatio= (int) Math.ceil(((done/actual)*24));
                for (int i=0;i<fillRatio;i++){
                    try {
                        Thread.sleep(100);
                        final int finalI = i;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                caloriesProgress[finalI].setBackground(getResources().getDrawable(R.drawable.active_dot_red));
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    private void setWeightProgressAnimation(final double actual, final double done) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                int fillRatio= (int) Math.ceil(((done/actual)*24));
                for (int i=0;i<fillRatio;i++){
                    try {
                        Thread.sleep(100);
                        final int finalI = i;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                weightProgress[finalI].setBackground(getResources().getDrawable(R.drawable.active_dot_green));
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
    private void setStepProgressAnimation(final double actual, final double done) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                int fillRatio = (int) Math.ceil(((done / actual) * 24));
                for (int i = 0; i < fillRatio; i++) {
                    try {
                        Thread.sleep(100);
                        final int finalI = i;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stepProgress[finalI].setBackground(getResources().getDrawable(R.drawable.active_dot_gold));
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
