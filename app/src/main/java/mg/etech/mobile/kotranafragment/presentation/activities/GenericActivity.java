package mg.etech.mobile.kotranafragment.presentation.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GenericActivity extends FragmentActivity {

    protected static List<GenericActivity> activities = new ArrayList<>();

    /**
     * retourne l'activity qui est actuellement à l'écran
     *
     * @return
     */
    public static GenericActivity getCurrentActivity() {
        if(activities.size()>0){
            GenericActivity fragmentActivity = activities.get(activities.size() - 1);
            if(fragmentActivity != null) {
                return fragmentActivity;
            }
            return null;
        }
        return null;
    }

    @Override
    public void onStart() {
        super.onStart();
        activities.add(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onStop() {
        super.onStop();
        activities.remove(this);
    }

    /**
     * Gestion orientation selon de device
     */
    protected void setScreenOrientation() {

    }

    /**
     * afficher un nouvel activity
     *
     * @param activity : la classe de l'activity
     * @param animated : inclure une animation de transition ou pas
     */
    protected void showActivity(Class<?> activity, boolean animated) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        if (animated) {
//            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    /**
     * remplace le fragment actuellement par un nouveau fragment
     *
     * @param id             : id de la vue sur laquelle le nouveau fragment doit etre mis
     * @param fragment       : le nouveau fragment a mettre
     * @param animated       : true si on doit animer le remplacement, false sinon
     * @param addToBackStack : true si on doit permettre le retour au fragment precedent, false sinon
     */
    protected void replaceFragment(int id, Fragment fragment, boolean animated, boolean addToBackStack) {
        if(fragment == null) return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(id, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        try{
            transaction.commitAllowingStateLoss();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * ajout d'un fragment par dessus celui qui est visible a l'ecran actuellement
     *
     * @param id             : id de la vue sur laquelle le nouveau fragment doit etre mis
     * @param fragment       : le nouveau fragment a mettre
     * @param animated       : true si on doit animer le remplacement, false sinon
     * @param addToBackStack : true si on doit permettre le retour au fragment precedent, false sinon
     */
    protected void addFragment(int id, Fragment fragment, boolean animated, boolean addToBackStack) {
        if(fragment == null) return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(id, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        try{
            transaction.commitAllowingStateLoss();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Afficher un Toast
     */
    void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void popBackStackFragment(int number) {
        if (getSupportFragmentManager() != null
                && getBackStackEntryCount() > number) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    public void clearBackStack() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public int getBackStackEntryCount() {
        return getSupportFragmentManager().getBackStackEntryCount();
    }

}
