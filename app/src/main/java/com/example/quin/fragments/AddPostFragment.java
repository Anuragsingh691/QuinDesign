package com.example.quin.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quin.Home;
import com.example.quin.Prevalent.Prevalent;
import com.example.quin.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;

import static android.app.Activity.RESULT_OK;


public class AddPostFragment extends Fragment {
    View myFragment;
    private String CategoryName,Description,Price,PName,saveCurrentDate,saveCurrentTime;
    private ProgressDialog mProProgress;
    private Button AddNewProduct;
    private ImageView InputProductImage;
    private EditText InputProductName,InputProductDesc;
    private static final int gallerypic=1;
    private Uri ImageUri;
    private String parentDbName="Users";
    private String productRandomKey,downloadImageUrl;
    private StorageReference ProductImageRefs;
    private DatabaseReference ProductsRefs,sellersRefs;
    private String sName,sAddress,sPhone,sEmail,sId;
    public AddPostFragment() {
        // Required empty public constructor
    }

    public static AddPostFragment newInstance(String param1, String param2) {
        AddPostFragment fragment = new AddPostFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment= inflater.inflate(R.layout.fragment_add_post, container, false);
        Paper.init(getActivity());
        sPhone=Paper.book().read(Prevalent.UserPhoneKey);
        sName=Prevalent.currentOnlineUsers.getName();
        ProductImageRefs= FirebaseStorage.getInstance().getReference().child("Post Images");
        ProductsRefs=FirebaseDatabase.getInstance().getReference().child("Posts");
        sellersRefs= FirebaseDatabase.getInstance().getReference().child("Sellers");
        AddNewProduct=myFragment.findViewById(R.id.add_new_product_btn);
        mProProgress = new ProgressDialog(getActivity());
        InputProductDesc=myFragment.findViewById(R.id.select_product_desc);
        InputProductName=myFragment.findViewById(R.id.select_product_name);
        InputProductImage=myFragment.findViewById(R.id.select_product_img);
        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        AddNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
            }
        });
        return myFragment;
    }

    private void ValidateProductData() {
        Description=InputProductDesc.getText().toString();
        PName=InputProductName.getText().toString();
        if (TextUtils.isEmpty(Description))
        {
            Toast.makeText(getActivity(), "Please , enter the location", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(PName))
        {
            Toast.makeText(getActivity(), "Please , Write the caption", Toast.LENGTH_SHORT).show();
        }
        else {
            StoreProductInfo();
        }
    }
    private void StoreProductInfo() {
        mProProgress.setTitle("Adding New Post");
        mProProgress.setMessage("Please wait while we are adding new product !");
        mProProgress.setCanceledOnTouchOutside(false);
        mProProgress.show();
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());
        productRandomKey=saveCurrentDate+saveCurrentTime;
        if(ImageUri!=null){
            final StorageReference filepath=ProductImageRefs.child(ImageUri.getLastPathSegment()+ productRandomKey + ".jpg" );
            final UploadTask uploadTask=filepath.putFile(ImageUri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String message= e.toString();
                    Toast.makeText(getActivity(), "Error:" + message, Toast.LENGTH_SHORT).show();
                    mProProgress.dismiss();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getActivity(), "Post Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful())
                            {
                                throw task.getException();

                            }
                            downloadImageUrl=filepath.getDownloadUrl().toString();
                            return filepath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful())
                            {
                                downloadImageUrl=task.getResult().toString();
                                Toast.makeText(getActivity(), "got Post Image Saved to Database Successfully", Toast.LENGTH_SHORT).show();
                                saveProductInfoToDatabase();
                            }
                        }
                    });
                }
            });
        }
        else{
            saveProductInfoToDatabase();
        }

    }

    private void saveProductInfoToDatabase() {
        HashMap<String,Object> productMap=new HashMap<>();
        productMap.put("pid",productRandomKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("location",Description);
        productMap.put("Uname",sName);
        productMap.put("search",sName.toLowerCase());
        productMap.put("Uphone",sPhone);
        if (downloadImageUrl!=null){
            productMap.put("image",downloadImageUrl);
        }
        productMap.put("Pcaption",PName);
        ProductsRefs.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    startActivity(new Intent(getActivity(), Home.class));
                    mProProgress.dismiss();
                    Toast.makeText(getActivity(), "Post is added successfully", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mProProgress.dismiss();
                    String message=task.getException().toString();
                    Toast.makeText(getActivity(), "Error:" + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openGallery() {
        Intent galleryintent=new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,gallerypic);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==gallerypic && resultCode==RESULT_OK && data!=null)
        {
            ImageUri=data.getData();
            InputProductImage.setImageURI(ImageUri);
        }
    }
    private void SellerInfo()
    {

    }
}