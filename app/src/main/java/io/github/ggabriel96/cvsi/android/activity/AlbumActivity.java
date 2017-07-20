package io.github.ggabriel96.cvsi.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.ggabriel96.cvsi.android.R;
import io.github.ggabriel96.cvsi.android.sql.SQLiteContract;

public class AlbumActivity extends AppCompatActivity {

  @BindView(R.id.app_bar_image)
  ImageView cover;
  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.album_description)
  TextView description;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.activity_album);
    ButterKnife.bind(this);
    Intent intent = this.getIntent();
    Bundle extras = intent.getExtras();
    this.toolbar.setTitle(extras.getString(SQLiteContract.AlbumEntry.COLUMN_TITLE));
    Glide.with(this).load(extras.getString(SQLiteContract.AlbumEntry.COLUMN_COVER)).transition(DrawableTransitionOptions.withCrossFade(R.anim.alpha_on)).into(this.cover);
    this.description.setText(extras.getString(SQLiteContract.AlbumEntry.COLUMN_DESCRIPTION));
  }
}
