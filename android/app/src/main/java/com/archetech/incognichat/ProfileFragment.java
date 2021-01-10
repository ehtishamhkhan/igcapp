package com.archetech.incognichat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import tyrantgit.explosionfield.ExplosionField;

public class ProfileFragment extends Fragment {

    public final String TAG = this.getClass().getSimpleName() +" ---> ";
    ImageView ShareFB,ShareWA,ShareIG,ShareTW,ShareOther,ShareMSG;
    TextView TvReputation, TvViews, TvUpdatedAt;
    TimeFormatter timeFormatter;


    ApplicationController applicationController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        applicationController=new ApplicationController(getActivity());
        timeFormatter=new TimeFormatter();
        return inflater.inflate(R.layout.fragment_profile,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ShareFB=getActivity().findViewById(R.id.item_share_fb);
        ShareIG=getActivity().findViewById(R.id.item_share_ig);
        ShareWA=getActivity().findViewById(R.id.item_share_wa);
        ShareTW=getActivity().findViewById(R.id.item_share_tr);
        ShareMSG=getActivity().findViewById(R.id.item_share_mg);
        ShareOther=getActivity().findViewById(R.id.item_share_other);
        TvReputation=getActivity().findViewById(R.id.tv_reputation);
        TvUpdatedAt=getActivity().findViewById(R.id.tv_updated_at);
        TvViews=getActivity().findViewById(R.id.tv_views);


        ShareOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareLink();
            }
        });

        updateProfile();
    }

    private void updateStatsUI()
    {
        applicationController = new ApplicationController(getActivity());
        TvViews.setText(applicationController.getViews());
        TvReputation.setText(applicationController.getReputation()+"/100");
        TvUpdatedAt.setText("Last updated: "+timeFormatter.FormatTimestamp(applicationController.getSyncedAt(),false, TimeFormatter.FormatType.ConversationLongTimestamp));
    }

    private void updateProfile()
    {
        final Request request = new JsonObjectRequest(Request.Method.POST, API.URL_GET_PROFILE_STATS,new JSONObject(new HashMap<String, String>()
        {{ }}), new Response.Listener<JSONObject>()
        {
            @Override public void onResponse(JSONObject response)
            {
                String status = Helper.getValueFromJSONObject(response,"status");
                if(status.equals("200"))
                {
                    String reputation = Helper.getValueFromJSONObject(response,"reputation");
                    String views = Helper.getValueFromJSONObject(response,"views");
                    if(new ApplicationController(getActivity()).syncProfile(reputation,views))
                    {
                        updateStatsUI();
                    }
                }
            }
        }, new Response.ErrorListener()
        {
            @Override public void onErrorResponse(VolleyError error)
            {
                Log.d(TAG,error.toString());
            }
        })
        {
            @Override public Map<String, String> getHeaders() throws AuthFailureError
            {
                return new ApplicationController(getActivity()).getAuthenticationHeader();
            }
        };
        RequestQueueSingleton.getInstance(getActivity()).addToRequestQueue(request);
    }


    private void ShareLink()
    {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareMessage= "https://incognichat.arche-tech.com/i/" + applicationController.getLink();
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Share your handle"));
        } catch(Exception e) {
            //e.toString();
        }
    }

}
