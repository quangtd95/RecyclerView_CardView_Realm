package td.quang.danhbamenurycyclerviewrealm.realm;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;
import td.quang.danhbamenurycyclerviewrealm.model.Contact;

/**
 * Created by Quang_TD on 11/10/2016.
 */
public class RealmController {
    private static RealmController instance;
    private final Realm realm;

    public RealmController(Context context) {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    public void createExample(int number) {
        String[] randomAvatar = {
                "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcT_bXw-vvAQOcMuKeOTGTgr3kAOUDOmVNaPaGBiL8qibTDN4u3s",
                "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQDEZnVsbROzFqx0xxkeQ9X9bPVhy9l2b5ywZTk7-GEPF1QwhkZ",
                "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQkE8ULMffMWAI7D3eVPiW_SzZrBSssKs82Vqa1fFInBnAjyaVLWg",
                "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTlsmWK0dypowinoCKfk-fpqHRpoU9yyuvPsmT0-r6r3otve2la",
                "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcSN9AwKpLLiC0Gyq_-NTMKNSOJe_2N3qr08WmSlTfpZ68gHdUNT",
                "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSz0GdcFgXUB2i-3c5UF9LNCUCO0vYr7qdg1Wl4eSWA4DNPmJzvqQ",
                "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTcJCpioMmzAODONfopDmNPL1YvFiaolTT3W5MgzazoS6Uv0qJf"
        };
        realm.beginTransaction();
        for (int i = 0; i < number; i++) {
            Contact contact = realm.createObject(Contact.class);

            contact.setName((char) (i % 26 + 'a') + " "+ i);
            contact.setNumber(String.valueOf(new Random().nextInt(89999999)+100000000));
            contact.setImageurl(randomAvatar[new Random().nextInt(randomAvatar.length)]);
        }
        realm.commitTransaction();

    }

    public RealmResults<Contact> getAlls() {
        return realm.where(Contact.class).findAll();
    }


}
