package com.example.kennykao;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by cuser on 2017/8/11.
 */

public class CoaAlbumActivity extends AppCompatActivity {
    private class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder>  {

        @Override
        public ImageGalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View photoView = inflater.inflate(R.layout.item_photo, parent, false);
            ImageGalleryAdapter.MyViewHolder viewHolder = new ImageGalleryAdapter.MyViewHolder(photoView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ImageGalleryAdapter.MyViewHolder holder, int position) {

            CoaAlbum coaAlbum = mCoaAlbums[position];
            ImageView imageView = holder.mPhotoImageView;
        }

        @Override
        public int getItemCount() {
            return (mCoaAlbums.length);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public ImageView mPhotoImageView;

            public MyViewHolder(View itemView) {

                super(itemView);
                mPhotoImageView = (ImageView) itemView.findViewById(R.id.iv_photo);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {

                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    CoaAlbum coaAlbum = mCoaAlbums[position];
                    Intent intent = new Intent(mContext, CoaAlbumActivity.class);
                    intent.putExtra(CoaAlbumActivity.EXTRA_SPACE_PHOTO, coaAlbum);
                    startActivity(intent);
                }
            }
        }

        private CoaAlbum[] mCoaAlbums;
        private Context mContext;

        public ImageGalleryAdapter(Context context, CoaAlbum[] coaAlbums) {
            mContext = context;
            mCoaAlbums = coaAlbums;
        }
    }
}
