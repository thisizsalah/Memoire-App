package com.madi.msdztest.signup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.madi.msdztest.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupArtisan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupArtisan extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Spinner spinner;

    public SignupArtisan() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupArtisan.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupArtisan newInstance(String param1, String param2) {
        SignupArtisan fragment = new SignupArtisan();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSpinner();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_signup_artisan, container, false);
        initializeView(rootView);


        return  rootView;
    }
    public void initializeView(View rootView){
        spinner = rootView.findViewById(R.id.spinner_wilaya);
    }
    public void initSpinner() {
        ArrayList<String> wilayas = new ArrayList<String>();
        wilayas.add("Selectionner Wilaya");
        wilayas.add("1. Adrar");
        wilayas.add("2. Chlef");
        wilayas.add("3. Laghouat");
        wilayas.add("4. Oum El Bouaghi");
        wilayas.add("5. Batna");
        wilayas.add("6. Béjaïa");
        wilayas.add("7. Biskra");
        wilayas.add("8. Béchar");
        wilayas.add("9. Blida");
        wilayas.add("10. Bouira");
        wilayas.add("11. Tamanrasset");
        wilayas.add("12. Tébessa");
        wilayas.add("13. Tlemcen");
        wilayas.add("14. Tiaret");
        wilayas.add("15. Tizi Ouzou");
        wilayas.add("16. Alger");
        wilayas.add("17. Djelfa");
        wilayas.add("18. Jijel");
        wilayas.add("19. Sétif");
        wilayas.add("20. Saïda");
        wilayas.add("21. Skikda");
        wilayas.add("22. Sidi Bel Abbès");
        wilayas.add("23. Annaba");
        wilayas.add("24. Guelma");
        wilayas.add("25. Constantine");
        wilayas.add("26. Médéa");
        wilayas.add("27. Mostaganem");
        wilayas.add("28. M'Sila");
        wilayas.add("29. Mascara");
        wilayas.add("30. Ouargla");
        wilayas.add("31. Oran");
        wilayas.add("32. El Bayadh");
        wilayas.add("33. Illizi");
        wilayas.add("34. Bordj Bou Arréridj");
        wilayas.add("35. Boumerdès");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,wilayas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}