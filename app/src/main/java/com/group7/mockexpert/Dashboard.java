package com.group7.mockexpert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.group7.mockexpert.models.ReadingPassageQuestion; // Import your model class
import com.group7.mockexpert.models.SharedPreferencesManager;

import java.util.Arrays;


public class Dashboard extends AppCompatActivity {

    // A reference to the FrameLayout that will host the fragment
    private View fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        fragmentContainer = findViewById(R.id.dashboard_fragment_container);
    }

    public void openSpeakingModule(View view) {
        Intent intent = new Intent(Dashboard.this, PartSelectionSpeaking.class);
        startActivity(intent);
    }

    public void openListeningModule(View view) {
        Toast.makeText(this, "Listening Module Clicked!", Toast.LENGTH_SHORT).show();
    }

    // This method is called when the "Reading" CardView is clicked (android:onClick="openReadingModule")
    public void openReadingModule(View view) {
        // Hide the main dashboard elements (cards, logout button)
        hideDashboardElements();
        // Make the fragment container visible
        fragmentContainer.setVisibility(View.VISIBLE);

        // Load the Reading Fragment into the container
        loadReadingFragment();
    }

    // Helper method to load the FragmentReadingQuestion
    private void loadReadingFragment() {
//        Fragment fragmentToLoad = new FragmentReadingMatching(); // directly load the matching fragment
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.dashboard_fragment_container, fragmentToLoad);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
        Intent intent = new Intent(Dashboard.this, ReadingHomeActivity.class);
        startActivity(intent);
    }



    public void logout(View view){
        if (SharedPreferencesManager.logout(this)){
            Intent intent = new Intent(Dashboard.this, Login.class);
            startActivity(intent);
            finish(); // Finish Dashboard activity so user can't go back to it after logout
        }
    }

    public void openWritingModule(View view) {
        Intent intent = new Intent(Dashboard.this, PartSelectionWriting.class);
        startActivity(intent);
    }

    private void hideDashboardElements() {
        findViewById(R.id.card_speaking).setVisibility(View.GONE);
        findViewById(R.id.card_writing).setVisibility(View.GONE);
        findViewById(R.id.card_reading).setVisibility(View.GONE);
        findViewById(R.id.card_listening).setVisibility(View.GONE);
        findViewById(R.id.btn_logout).setVisibility(View.GONE);
        // Add any other elements you want to hide when a fragment is active
    }

    /**
     * Shows the main dashboard elements.
     * This is called when returning from a module fragment.
     */
    private void showDashboardElements() {
        findViewById(R.id.card_speaking).setVisibility(View.VISIBLE);
        findViewById(R.id.card_writing).setVisibility(View.VISIBLE);
        findViewById(R.id.card_reading).setVisibility(View.VISIBLE);
        findViewById(R.id.card_listening).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_logout).setVisibility(View.VISIBLE);
        fragmentContainer.setVisibility(View.GONE); // Hide the fragment container itself
        // Add any other elements you want to show
    }

    /**
     * Overrides the default back button behavior to handle fragment back stack.
     * If fragments are in the back stack, it pops them.
     * If the back stack is empty, it allows the default system behavior (e.g., exiting the app).
     */
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            // If there are fragments in the back stack, pop the top one
            fragmentManager.popBackStack();
            // After popping, check if the back stack is now empty.
            // If it is, we've returned to the main dashboard view, so show its elements.
            if (fragmentManager.getBackStackEntryCount() == 0) {
                showDashboardElements();
            }
        } else {
            // If no fragments in back stack, let the system handle back press (exit app)
            super.onBackPressed();
        }
    }
}
