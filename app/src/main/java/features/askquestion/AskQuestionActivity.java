package features.askquestion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import utils.Format;

public class AskQuestionActivity extends AppCompatActivity {

    public static void show(Context context){
        context.startActivity(new Intent(context,AskQuestionActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Format.Font.size(
                Format.SetText(new TextView(this),"What is the capital city of Uganda?"),
                20
        ));
    }
}