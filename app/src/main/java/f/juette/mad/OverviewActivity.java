package f.juette.mad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OverviewActivity extends AppCompatActivity {

    private TextView welcomeText;
    private Button callDetailviewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        // Read the view elements
        welcomeText = (TextView) findViewById(R.id.welcome_text);
        callDetailviewButton = (Button) findViewById(R.id.call_detailview_button);

        // Actions with the elements
        callDetailviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OverviewActivity.this, "OnClick on Call!", Toast.LENGTH_SHORT).show();
            }
        });

        // Set values/content on elements
        welcomeText.setText(R.string.welcome2);
    }
}