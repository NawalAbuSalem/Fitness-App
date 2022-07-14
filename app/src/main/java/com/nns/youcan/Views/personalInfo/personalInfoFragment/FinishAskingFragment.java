package com.nns.youcan.Views.personalInfo.personalInfoFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.nns.youcan.R;
import com.nns.youcan.Views.personalInfo.PersonalInfoInterface;
import com.github.abdularis.buttonprogress.DownloadButtonProgress;
import com.github.jinatonic.confetti.CommonConfetti;

import static java.lang.Thread.sleep;


public class FinishAskingFragment extends Fragment {
    PersonalInfoInterface personalInfoInterface;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PersonalInfoInterface){
            personalInfoInterface= (PersonalInfoInterface) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.finish_asking_fragment, container, false);
        final DownloadButtonProgress btn = view.findViewById(R.id.progress_answer);
        btn.setDeterminate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                for (i=0;i<11;i++){
                    try {
                        sleep(100);
                        final int finalI = i;
                        btn.post(new Runnable() {
                            @Override
                            public void run() {
                                btn.setProgress(finalI *10);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                try {
                    sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                btn.post(new Runnable() {
                    @Override
                    public void run() {
                        btn.setFinish();
                    }
                });
            }
        }).start();
        CommonConfetti.rainingConfetti(container, new int[] { getResources().getColor(R.color.primaryColor), getResources().getColor(R.color.primaryColorLight), getResources().getColor(R.color.primarymostLight)})
               .stream(2000).setAccelerationY(5f).setAccelerationX(5f);
        Button finishButton =view.findViewById(R.id.finish_btn);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personalInfoInterface.finishAsking();
            }
        });
        return view;
    }
}
