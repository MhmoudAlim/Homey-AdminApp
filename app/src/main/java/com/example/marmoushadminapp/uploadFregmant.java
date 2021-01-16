package com.example.marmoushadminapp;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.ArrayList;
import java.util.Collections;


public class uploadFregmant extends Fragment  {
    Spinner TitleSpinner;
    EditText etDescription, etCode;
    ImageView imageTake;
    Button takeButton, saveButton;
    Bitmap b;

    ArrayList<String>Titles=new ArrayList<>();
    public uploadFregmant() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_upload_fregmant, container, false);
        TitleSpinner = view.findViewById(R.id.titleSpinner);
        etCode = view.findViewById(R.id.etCode);
        etDescription = view.findViewById(R.id.etDescription);
        imageTake = view.findViewById(R.id.imagetaken);
        takeButton = view.findViewById(R.id.takeButton);
        saveButton = view.findViewById(R.id.saveButton);
        Collections.addAll(Titles,"Select Title","Receptions","Corridors",
                "Bedrooms", "Kitchens","Bathrooms","UnitsAndLibrarys","Livingrooms");
        ArrayAdapter adapter=new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,Titles);
        TitleSpinner.setAdapter(adapter);

        takeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                b=r.getBitmap();
                                imageTake.setImageBitmap(b);
                                saveButton.setEnabled(true);
                            }
                        })
                        .setOnPickCancel(new IPickCancel() {
                            @Override
                            public void onCancelClick() {
                                //TODO: do what you have to if user clicked cancel
                            }
                        }).show(getActivity());

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCode.getText().length() == 0 || etDescription.getText().length() == 0||TitleSpinner.getSelectedItem().toString().equals("Select Title")) {
                    Toast.makeText(getActivity(), "Please fill all Data", Toast.LENGTH_SHORT).show();
                } else{
                    Backendless.Files.Android.upload(b, Bitmap.CompressFormat.WEBP, 30, etCode.getText().toString(), "images", new AsyncCallback<BackendlessFile>() {
                        @Override
                        public void handleResponse(BackendlessFile response) {

                            com.examples.marmoush.data.Showitem showitem=new com.examples.marmoush.data.Showitem();
                            showitem.setCode(Integer.parseInt(etCode.getText().toString()));
                            showitem.setDesc(etDescription.getText().toString());
                            showitem.setTitle(TitleSpinner.getSelectedItem().toString());
                            showitem.setImageUrl(response.getFileURL());
                            Backendless.Data.of(com.examples.marmoush.data.Showitem.class).save(showitem, new AsyncCallback<com.examples.marmoush.data.Showitem>() {
                                @Override
                                public void handleResponse(com.examples.marmoush.data.Showitem response) {
                                    imageTake.setImageBitmap(null);
                                    etCode.setText("");
                                    etDescription.setText("");
                                    TitleSpinner.setSelection(0);
                                    Toast.makeText(getActivity(), "Item Uploaded", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {

                                }
                            });

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                        }
                    });
                }
            }
        });


        return view;
    }


}
