package com.practice.android.firstaid.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.practice.android.firstaid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnimalBiteFragment extends Fragment {

    public static final String ARG_PAGE = "page";
    private int mPageNumber;


    public AnimalBiteFragment() {
        // Required empty public constructor
    }

    public static AnimalBiteFragment newInstance(int pageNumber) {
        AnimalBiteFragment fragment = new AnimalBiteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_animalbite, container, false);

//        ProgressBar progressBar = view.findViewById(R.id.progressAnimalBite);
//        progressBar.setProgress(mPageNumber+1);
//        TextView progressText = view.findViewById(R.id.progressText);
//        progressText.setText("Page " + (mPageNumber+1) + "of 2");

        TextView titleBite = view.findViewById(R.id.titleBite);
        TextView text1 = view.findViewById(R.id.text1);
        TextView text2 = view.findViewById(R.id.text2);
        TextView text3 = view.findViewById(R.id.text3);

        ImageView biteImage = view.findViewById(R.id.biteImage);
        if (mPageNumber == 0) {
            biteImage.setImageResource(R.drawable.spider1);
            titleBite.setText("1\t Apply cold treatment");
            text1.setText("\u2022 \t Wash the bitten area well to remove any remaining venom from the skin.");
            text2.setText("\u2022 \t Keep the patient still to reduce the toxic effects of the venom.");
            text3.setText("\u2022 \t Apply a wrapped ice pack for up to 10 minutes at a time, or a cold\n" +
                    "compress that has been soaked in water to which a few ice cubes have been\n" +
                    "added.");

        } else if (mPageNumber == 1) {
            biteImage.setImageResource(R.drawable.spider2);
            titleBite.setText("2\t Raise a bitten limb");
            text1.setText("\u2022 \t If the bite is on a limb, raise it to limit swelling.");
            text2.setText("\u2022 \t If an arm or hand is involved, apply an elevation sling to provide comfort \n" +
                    "and support.");
            text3.setVisibility(View.GONE);
        } else {
            biteImage.setImageResource(R.drawable.spider3);
            titleBite.setText("3\t Raise a bitten limb");
            text1.setText("\u2022 \t If the bite is on a limb, raise it to limit swelling.");
            text2.setText("\u2022 \t If an arm or hand is involved, apply an elevation sling to provide comfort \n" +
                    "and support.");
            text3.setVisibility(View.GONE);
        }

        return view;
    }

    public int getPageNumber() {
        return mPageNumber;
    }

}
