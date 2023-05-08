package com.madi.msdztest.signup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.madi.msdztest.signup.SignupArtisan;
import com.madi.msdztest.signup.SignupClient;

public class SignupFragmentAdapter extends FragmentStateAdapter {
    public SignupFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0){
            return new SignupClient();
        }
        return new SignupArtisan();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
