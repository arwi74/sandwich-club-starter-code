package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    ActivityDetailBinding mBinding;
    Sandwich mSandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_detail);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_detail);

        ImageView ingredientsIv = mBinding.imageIv;

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        mSandwich = JsonUtils.parseSandwichJson(json);
        if (mSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(mSandwich.getImage())
                .error(R.drawable.error_image)
                .placeholder(R.drawable.place_holder_image)
                .into(ingredientsIv);

        setTitle(mSandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        mBinding.originTv.setText( mSandwich.getPlaceOfOrigin() );
        mBinding.descriptionTv.setText( mSandwich.getDescription() );


        mBinding.alsoKnownTv.setText( ListToString( mSandwich.getAlsoKnownAs() ) );
        mBinding.ingredientsTv.setText( ListToString( mSandwich.getIngredients() ) );
    }

    private String ListToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<list.size();i++){
            sb.append(list.get(i));
            if ( i<list.size()-1) sb.append(", ");
        }
        return sb.toString();
    }
}
