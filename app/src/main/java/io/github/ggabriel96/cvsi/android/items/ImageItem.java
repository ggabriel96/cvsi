package io.github.ggabriel96.cvsi.android.items;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.ggabriel96.cvsi.android.R;

/**
 * Created by gbrl on 20/07/17.
 */

public class ImageItem extends AbstractItem<ImageItem, ImageItem.ViewHolder> {

  public String url;
  private RequestManager glide;

  public ImageItem withImage(String imageUrl) {
    this.url = imageUrl;
    return this;
  }

  /**
   * defines the type defining this item. must be unique. preferably an id
   *
   * @return the type
   */
  @Override
  public int getType() {
    return R.id.fastadapter_image_item_id;
  }

  /**
   * defines the layout which will be used for this item in the list
   *
   * @return the layout for this item
   */
  @Override
  public int getLayoutRes() {
    return R.layout.image_item;
  }

  /**
   * binds the data of this item onto the viewHolder
   *
   * @param viewHolder the viewHolder of this item
   */
  @Override
  public void bindView(ViewHolder viewHolder, List<Object> payloads) {
    super.bindView(viewHolder, payloads);

    //get the context
    Context ctx = viewHolder.itemView.getContext();

    //define our data for the view
    viewHolder.imageView.setImageBitmap(null);

    //load glide
    this.glide = Glide.with(ctx);
    this.glide.load(url).transition(DrawableTransitionOptions.withCrossFade(R.anim.alpha_on)).into(viewHolder.imageView);
  }

  @Override
  public void unbindView(ViewHolder holder) {
    super.unbindView(holder);
    this.glide.clear(holder.imageView);
    holder.imageView.setImageDrawable(null);
  }


  @Override
  public ViewHolder getViewHolder(View v) {
    return new ViewHolder(v);
  }

  /**
   * our ViewHolder
   */
  public static class ViewHolder extends RecyclerView.ViewHolder {
    protected View view;
    @BindView(R.id.item_image_img)
    protected ImageView imageView;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
      this.view = view;

      //optimization to preset the correct height for our device
      int columns = view.getContext().getResources().getInteger(R.integer.gallery_columns);
      int screenWidth = view.getContext().getResources().getDisplayMetrics().widthPixels;
      int finalHeight = (int) (screenWidth / 1.5);
      imageView.setMinimumHeight(finalHeight / columns);
      imageView.setMaxHeight(finalHeight / columns);
      imageView.setAdjustViewBounds(false);
      //set height as layoutParameter too
      FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) imageView.getLayoutParams();
      lp.height = finalHeight / columns;
      imageView.setLayoutParams(lp);
    }
  }
}
