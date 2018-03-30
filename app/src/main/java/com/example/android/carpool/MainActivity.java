package com.example.android.carpool;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    AdapterViewFlipper adapterViewFlipper;
    String texts[]={"Connect","Beat Traffic","Save Money"};
    int images[]={R.drawable.connect, R.drawable.early, R.drawable.save};
    TextView signup;
    private TextView login;
    private FirebaseAuth auth;
    private static final int RC_SIGN_IN = 200;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String PATH_TOS ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapterViewFlipper =findViewById(R.id.adapterflip);
        signup=findViewById(R.id.signup);
        login=findViewById(R.id.login);

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.connect);
        Bitmap bitmap2 =BitmapFactory.decodeResource(getResources(),R.drawable.save);
        Bitmap bitmap3 =BitmapFactory.decodeResource(getResources(),R.drawable.early);
        RoundedBitmapDrawable roundedBitmapDrawable2 =circa(bitmap2);
        RoundedBitmapDrawable roundedBitmapDrawable3 =circa(bitmap3);
        RoundedBitmapDrawable roundedBitmapDrawable1 = circa(bitmap1);
        RoundedBitmapDrawable mages[]={roundedBitmapDrawable1, roundedBitmapDrawable3, roundedBitmapDrawable2};

        FlipperAdapter adapter = new FlipperAdapter(this, texts,mages);
        adapterViewFlipper.setAdapter(adapter);
        adapterViewFlipper.setAutoStart(true);
        adapterViewFlipper.setInAnimation(getApplicationContext(), R.animator.left_in);
        adapterViewFlipper.setOutAnimation(getApplicationContext(), R.animator.right_out);

        auth = FirebaseAuth.getInstance();
        if (isUserLogin()){
            loginUser();
        }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, signUpActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                .setTosUrl(PATH_TOS)
                .build(),RC_SIGN_IN);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RC_SIGN_IN){
            if(resultCode==RESULT_OK){
                loginUser();
            }
        }
        if (resultCode==RESULT_CANCELED){
            displayMessage("Log In Failed");
        }
        displayMessage("Unknown Response");

    }

    private boolean isUserLogin(){
        if(auth.getCurrentUser() !=null){
            return true;
        }
        return false;
    }

    private void loginUser(){
        Intent loginIntent= new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }
    private void displayMessage(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }


    public RoundedBitmapDrawable circa (Bitmap bitmap){
        RoundedBitmapDrawable roundedBitmapDrawable1 = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable1.setCircular(true);

    return roundedBitmapDrawable1;
    }
}

class FlipperAdapter extends BaseAdapter {
    Context ctx;
    int [] images;
    String [] texts;
    RoundedBitmapDrawable [] mages;
    LayoutInflater inflater;


    public FlipperAdapter(Context context, String[] myText, RoundedBitmapDrawable [] mymages){
        this.ctx=context;
        this.mages=mymages;
        this.texts=myText;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return texts.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view =inflater.inflate(R.layout.flipper_items, null);
        TextView txtName = (TextView) view.findViewById(R.id.ftext);
        ImageView txtImage =(ImageView) view.findViewById(R.id.fimage);
        txtName.setText(texts[i]);
        txtImage.setImageDrawable(mages[i]);
        return view;
    }


}
