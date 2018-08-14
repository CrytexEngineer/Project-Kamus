package com.example.aqil.projectkamus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.aqil.projectkamus.Adapter.KamusAdapter;
import com.example.aqil.projectkamus.Model.KamusItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.detail_title)
    TextView title;
    @BindView(R.id.detail_translation)
    TextView translation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        KamusItem kamusItem = getIntent().getParcelableExtra(KamusAdapter.KAMUS_ITEM);
        title.setText(kamusItem.getTitle());
        translation.setText(kamusItem.getTranslation());

    }
}
