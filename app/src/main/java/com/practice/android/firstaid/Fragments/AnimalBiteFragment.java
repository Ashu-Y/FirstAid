package com.practice.android.firstaid.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.practice.android.firstaid.Adapters.ContentAdapter;
import com.practice.android.firstaid.Models.Content_Row_Item;
import com.practice.android.firstaid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnimalBiteFragment extends Fragment {

    public static final String ARG_PAGE = "page";
    public RecyclerView recyclerView;
    public ContentAdapter adapter;
    public List<Content_Row_Item> content;
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

        recyclerView = view.findViewById(R.id.animalrecycle);
        content = new ArrayList<>();

        prepareMovieData();

        adapter = new ContentAdapter(content);

        RecyclerView.LayoutManager recyclerLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(recyclerLayout);
        recyclerView.setAdapter(adapter);


//        ProgressBar progressBar = view.findViewById(R.id.progressAnimalBite);
//        progressBar.setProgress(mPageNumber+1);
//        TextView progressText = view.findViewById(R.id.progressText);
//        progressText.setText("Page " + (mPageNumber+1) + "of 2");

        LinearLayout linearLayout = view.findViewById(R.id.scrollBite);
//        linearLayout.setVerticalScrollBarEnabled(false);

//        TextView titleBite = view.findViewById(R.id.titleBite);
//        TextView text1 = view.findViewById(R.id.text1);
//        TextView text2 = view.findViewById(R.id.text2);
//        TextView text3 = view.findViewById(R.id.text3);
//        ImageView biteImage = view.findViewById(R.id.biteImage);
//        if (mPageNumber == 0) {
//            biteImage.setImageResource(R.drawable.spider1);
//            titleBite.setText("1\t Apply cold treatment");
//            text1.setText("\u2022 \t Wash the bitten area well to remove any remaining venom from the skin.");
//            text2.setText("\u2022 \t Keep the patient still to reduce the toxic effects of the venom.");
//            text3.setText("\u2022 \t Apply a wrapped ice pack for up to 10 minutes at a time, or a cold\n" +
//                    "compress that has been soaked in water to which a few ice cubes have been\n" +
//                    "added.");
//
//        } else if (mPageNumber == 1) {
//            biteImage.setImageResource(R.drawable.spider2);
//            titleBite.setText("2\t Raise a bitten limb");
//            text1.setText("\u2022 \t If the bite is on a limb, raise it to limit swelling.");
//            text2.setText("\u2022 \t If an arm or hand is involved, apply an elevation sling to provide comfort \n" +
//                    "and support.");
//            text3.setVisibility(View.GONE);
//        } else {
//            biteImage.setImageResource(R.drawable.spider3);
//            titleBite.setText("3\t Raise a bitten limb");
//            text1.setText("\u2022 \t If the bite is on a limb, raise it to limit swelling.");
//            text2.setText("\u2022 \t If an arm or hand is involved, apply an elevation sling to provide comfort \n" +
//                    "and support.");
//            text3.setVisibility(View.GONE);
//        }


        adapter.notifyDataSetChanged();

        return view;
    }

    private void prepareMovieData() {
        Content_Row_Item movie;

        if (mPageNumber == 0) {

            content.clear();

            movie = new Content_Row_Item(R.drawable.spider1, "Apply cold treatment",
                    "Wash the bitten area well to remove any remaining venom from the skin.",
                    "Keep the patient still to reduce the toxic effects of the venom.",
                    "Apply a wrapped ice pack for up to 10 minutes at a time, or a cold compress that has been soaked in water to which a few ice cubes have been  added.");
            content.add(movie);
        }

        if (mPageNumber == 1) {

            content.clear();
            movie = new Content_Row_Item(R.drawable.spider2, "Raise a bitten limb",
                    "If the bite is on a limb, raise it to limit swelling.",
                    "If an arm or hand is involved, apply an elevation sling to provide comfort",
                    "and support.");

            content.add(movie);
        }

        if (mPageNumber == 2) {

            content.clear();
            movie = new Content_Row_Item(R.drawable.spider3,
                    "Raise a bitten limb",
                    "If the bite is on a limb, raise it to limit swelling.",
                    "If an arm or hand is involved, apply an elevation sling to provide comfort",
                    "and support.");

            content.add(movie);
        }
    }


    public int getPageNumber() {
        return mPageNumber;
    }

}
