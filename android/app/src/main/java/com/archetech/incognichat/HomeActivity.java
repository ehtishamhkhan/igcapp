package com.archetech.incognichat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

public class HomeActivity extends AppCompatActivity {

    public final String TAG = this.getClass().getSimpleName();



    BubbleNavigationLinearView navbar;
    Fragment fragmentChats, fragmentProfile,fragmentBadges;
    FrameLayout fragmentHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navbar=findViewById(R.id.navbar);

        initializeFragments();
        LoadFragment(fragmentChats);




        navbar.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                switch (position) {
                    case 0:
                    {
                        LoadFragment(fragmentChats);
                    }
                    break;
                    case 1:
                    {
                        LoadFragment(fragmentBadges);
                    }
                    break;
                    case 2:
                    {
                        LoadFragment(fragmentProfile);
                    }
                    break;
                }
            }
        });

    }



    private void initializeFragments()
    {
        fragmentChats=new ChatsFragment();
        fragmentProfile =new ProfileFragment();
        fragmentBadges=new BadgesFragment();
    }

    private boolean LoadFragment(Fragment fragment) {
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit();
        return true;
    }
}
