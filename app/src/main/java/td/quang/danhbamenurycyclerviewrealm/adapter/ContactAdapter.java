package td.quang.danhbamenurycyclerviewrealm.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import td.quang.danhbamenurycyclerviewrealm.R;
import td.quang.danhbamenurycyclerviewrealm.model.Contact;
import td.quang.danhbamenurycyclerviewrealm.realm.RealmController;

/**
 * Created by Quang_TD on 11/10/2016.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private Context context;
    private List<Contact> contactList;
    private int prePosition;

    public ContactAdapter(Context context, List<Contact> contactList) {

        this.contactList = contactList;
        this.context = context;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, final int position) {

        final Contact contact = contactList.get(position);

        holder.txtName.setText(contact.getName());
        holder.txtNumber.setText(contact.getNumber());
        Picasso.with(context).load(contact.getImageurl()).into(holder.imageView);

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                notifyDataSetChanged();

                Realm realm = RealmController.with((Activity) context).getRealm();
                Log.e("TAGG", position + "");
                realm.beginTransaction();
                contact.deleteFromRealm();
                realm.commitTransaction();
                contactList.remove(position);

                notifyItemRemoved(position);

               /* if (position > prePosition) notifyItemRemoved(prePosition);
                else if (position < prePosition) notifyItemRemoved(position - 1);*/



                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView txtName;
        protected ImageView imageView;
        protected TextView txtNumber;
        protected CardView cardView;

        public ContactViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            imageView = (ImageView) itemView.findViewById(R.id.imageAvatar);
            txtNumber = (TextView) itemView.findViewById(R.id.txtNumber);
        }

    }

   /* public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        protected TextView txtNameGroup;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            txtNameGroup = (TextView) itemView.findViewById(R.id.txtNameGroup);
        }

    }*/
}
