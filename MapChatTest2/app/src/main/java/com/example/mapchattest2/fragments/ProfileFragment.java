package com.example.mapchattest2.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapchattest2.R;
import com.example.mapchattest2.activities.RegisterActivity;
import com.example.mapchattest2.utils.AuthenticationUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PICK_IMAGE_REQUEST = 2;
    @BindView(R.id.iv_edit)
    ImageView ivEdit;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ll_view_info)
    LinearLayout llViewInfo;
    @BindView(R.id.iv_save)
    ImageView ivSave;
    @BindView(R.id.iv_edit_avatar)
    ImageView ivEditAvatar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.ll_edit_info)
    LinearLayout llEditInfo;
    @BindView(R.id.iv_icon_change_password)
    ImageView ivIconChangePassword;
    @BindView(R.id.iv_icon_save_password)
    ImageView ivIconSavePassword;
    @BindView(R.id.et_old_password)
    EditText etOldPassword;
    @BindView(R.id.ll_old_password)
    LinearLayout llOldPassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.ll_new_password)
    LinearLayout llNewPassword;
    @BindView(R.id.et_re_enter_password)
    EditText etReEnterPassword;
    @BindView(R.id.ll_re_enter_password)
    LinearLayout llReEnterPassword;
    @BindView(R.id.ll_expand_change_password)
    LinearLayout llExpandChangePassword;
    Unbinder unbinder;
    private static final String TAG = "ProfileFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ;
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;


        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();


            try {
                long size = AuthenticationUtils.getSizefromUri(getContext(), uri);
                Log.d(TAG, "onActivityResult: " + size);
                if (size > 2048) {
                    Toast.makeText(getActivity(), "The image is langer than 2Mb", Toast.LENGTH_SHORT).show();
                } else {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                    // Log.d(TAG, String.valueOf(bitmap));;
                         ivEditAvatar.setImageBitmap(bitmap);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick({R.id.iv_edit, R.id.iv_save, R.id.iv_edit_avatar, R.id.iv_icon_change_password, R.id.iv_icon_save_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_edit:
                llViewInfo.setVisibility(View.GONE);
                llEditInfo.setVisibility(View.VISIBLE);
                ivEditAvatar.setImageDrawable(ivAvatar.getDrawable());
                etName.setText(tvName.getText());
                break;
            case R.id.iv_save:
                llViewInfo.setVisibility(View.VISIBLE);
                llEditInfo.setVisibility(View.GONE);
                ivAvatar.setImageDrawable(ivEditAvatar.getDrawable());
                tvName.setText(etName.getText());
                break;
            case R.id.iv_edit_avatar:
                    openGalley();
                break;
            case R.id.iv_icon_change_password:
                ivIconChangePassword.setVisibility(View.GONE);
                ivIconSavePassword.setVisibility(View.VISIBLE);
                llExpandChangePassword.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_icon_save_password:
                ivIconSavePassword.setVisibility(View.GONE);
                llExpandChangePassword.setVisibility(View.GONE);
                ivIconChangePassword.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void openGalley() {

        if(ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    2000);
        }
        else {
            Intent intent = new Intent();
            // Show only images, no videos or anything else
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            // Always show the chooser (if there are multiple options available)
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }

    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
