package com.munifahsan.retribusiapp.Profile.presenter;

import com.munifahsan.retribusiapp.Profile.repo.ProfileRepo;
import com.munifahsan.retribusiapp.Profile.repo.ProfileRepoInt;
import com.munifahsan.retribusiapp.Profile.view.ProfileViewInt;

public class ProfilePres implements ProfilePresInt {

    private ProfileViewInt profileViewInt;
    private ProfileRepoInt profileRepoInt;

    public ProfilePres(ProfileViewInt profileViewInt) {
        this.profileViewInt = profileViewInt;
        profileRepoInt = new ProfileRepo();
    }
}
