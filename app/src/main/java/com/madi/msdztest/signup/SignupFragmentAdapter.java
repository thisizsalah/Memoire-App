package com.madi.msdztest.signup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SignupFragmentAdapter extends FragmentStateAdapter {
    public SignupFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0){
            return new SignupParticulier();
        }
        return new SignupArtisan();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
