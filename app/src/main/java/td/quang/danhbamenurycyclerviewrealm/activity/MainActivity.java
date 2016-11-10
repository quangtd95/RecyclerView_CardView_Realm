package td.quang.danhbamenurycyclerviewrealm.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.LinkedList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import td.quang.danhbamenurycyclerviewrealm.R;
import td.quang.danhbamenurycyclerviewrealm.adapter.ContactAdapter;
import td.quang.danhbamenurycyclerviewrealm.adapter.SpinnerAdapter;
import td.quang.danhbamenurycyclerviewrealm.model.Contact;
import td.quang.danhbamenurycyclerviewrealm.realm.RealmController;

/**
 * Created by Quang_TD on 11/10/2016.
 */
public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private RealmController realmController;
    private Realm realm;
    private LinkedList<Contact> contactArrayList;
    private ContactAdapter contactAdapter;
    private ShareActionProvider actionProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        final TextView textNameGroup = (TextView) findViewById(R.id.txtNameGroup);

        /*final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);*/
        final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);


        realmController = new RealmController(this);
        realm = realmController.getRealm();

        realmController.createExample(100);

        contactArrayList = new LinkedList<>(realmController.getAlls().sort("name", Sort.ASCENDING));
        contactAdapter = new ContactAdapter(this, contactArrayList);


        recyclerView.setAdapter(contactAdapter);

        textNameGroup.setText(contactArrayList.get(0).getName().charAt(0) + "");

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int[] firstItemIndex = new int[3];
                staggeredGridLayoutManager.findFirstVisibleItemPositions(firstItemIndex);
                if (!textNameGroup.getText().toString().equalsIgnoreCase(contactArrayList.get(firstItemIndex[0]).getName().charAt(0) + ""))
                    textNameGroup.setText(contactArrayList.get(firstItemIndex[0]).getName().charAt(0) + "");

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        addSearchAction(menu);
        addShareAction(menu);
        addContactAction(menu);


        return true;
    }

    private void addContactAction(Menu menu) {
        MenuItem menuItemAdd = menu.findItem(R.id.menu_add);
        menuItemAdd.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Add  a Contact")
                        .setTitle("Input information!");
                final AlertDialog alertDialog = builder.create();
                LayoutInflater layoutInflater = alertDialog.getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.dialog_addcontact, null);
                final EditText txtName = (EditText) view.findViewById(R.id.txtName);
                final EditText txtNumber = (EditText) view.findViewById(R.id.txtNumber);
                final Spinner spinner = (Spinner) view.findViewById(R.id.spinerAvatar);
                final String[] randomAvatar = {
                        "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcT_bXw-vvAQOcMuKeOTGTgr3kAOUDOmVNaPaGBiL8qibTDN4u3s",
                        "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQDEZnVsbROzFqx0xxkeQ9X9bPVhy9l2b5ywZTk7-GEPF1QwhkZ",
                        "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQkE8ULMffMWAI7D3eVPiW_SzZrBSssKs82Vqa1fFInBnAjyaVLWg",
                        "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTlsmWK0dypowinoCKfk-fpqHRpoU9yyuvPsmT0-r6r3otve2la",
                        "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcSN9AwKpLLiC0Gyq_-NTMKNSOJe_2N3qr08WmSlTfpZ68gHdUNT",
                        "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSz0GdcFgXUB2i-3c5UF9LNCUCO0vYr7qdg1Wl4eSWA4DNPmJzvqQ",
                        "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTcJCpioMmzAODONfopDmNPL1YvFiaolTT3W5MgzazoS6Uv0qJf"
                };

                SpinnerAdapter spinnerAdapter = new SpinnerAdapter(MainActivity.this, randomAvatar);
                spinner.setAdapter(spinnerAdapter);
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        realm.beginTransaction();
                        Contact contact = realm.createObject(Contact.class);
                        contact.setImageurl(randomAvatar[spinner.getSelectedItemPosition()]);
                        contact.setName(txtName.getText().toString());
                        contact.setNumber(txtNumber.getText().toString());
                        realm.commitTransaction();
                        for (int j = 0 ; j < contactArrayList.size(); j++){
                            if (contact.getName().compareTo(contactArrayList.get(j).getName())<0){
                                contactArrayList.add(j,contact);
                                break;
                            }
                            if (j == contactArrayList.size() - 1){
                                contactArrayList.add(contact);
                                break;
                            }
                        }

                        contactAdapter.notifyDataSetChanged();
                        alertDialog.dismiss();

                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.setView(view);
                alertDialog.show();
                return false;
            }
        });
    }

    private void addSearchAction(Menu menu) {
        MenuItem menuItemSearch = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) menuItemSearch.getActionView();
        searchView.setOnQueryTextListener(this);
    }

    private void addShareAction(Menu menu) {
        MenuItem menuItemShare = menu.findItem(R.id.menu_share);
        actionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItemShare);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < contactArrayList.size(); i++) {
            stringBuilder.append(contactArrayList.get(i).getName() + "\n" + contactArrayList.get(i).getNumber() + "\n");
        }
        intent.putExtra(Intent.EXTRA_TEXT, stringBuilder.toString());
        intent.setType("text/plain");
        actionProvider.setShareIntent(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
//        if (newText.length()==0) return false;
        realm.beginTransaction();
        RealmResults<Contact> results = realm.where(Contact.class).contains("name", newText).findAll();
        contactArrayList.clear();
        contactArrayList.addAll(results);
        realm.commitTransaction();
        contactAdapter.notifyDataSetChanged();
        return true;
    }
}
