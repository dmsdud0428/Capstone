package com.saenaegi.lfree;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

public class ChoiceActivity extends AppCompatActivity {
    String introductionText = "시각장애인과 청각장애 및 비장애인을 구분하기 위한 유아이입니다. 만일 본인께서 시각 장애인이신 경우," +
            "중앙을 기준으로 상단에 위치한 '안보여요' 버튼을, 청각장애 및 비장애인이신 경우, 하단에 위치한 '잘보여요' 버튼을 클릭해주시길 바랍니다." +
            "엘프리 동영상 플랫폼 애플리케이션을 사용해주셔서 진심으로 감사드립니다.";
    String noText = "'안보여요' 버튼을 클릭하셨습니다. 시각장애인용 유아이로 진입합니다.";
    String yesText = "'잘보여요' 버튼을 클릭하셨습니다. 청각장애 및 비장애인용 유아이로 진입합니다.";
    private TextToSpeech tts;              // TTS 변수 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_choice);

        ImageButton noButton = (ImageButton)findViewById(R.id.no);
        ImageButton yesButton = (ImageButton)findViewById(R.id.yes);

        noButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tts.speak(noText, TextToSpeech.QUEUE_FLUSH, null, "TextToSpeech_ID");
                } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    tts.speak(noText, TextToSpeech.QUEUE_FLUSH, null);
                }
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        Intent intent=new Intent(ChoiceActivity.this, aLfreeMainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 6000);
            }
        });

        yesButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tts.speak(yesText, TextToSpeech.QUEUE_FLUSH, null, "TextToSpeech_ID");
                } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    tts.speak(yesText, TextToSpeech.QUEUE_FLUSH, null);
                }
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        Intent intent=new Intent(ChoiceActivity.this, LfreeMainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 6000);
            }
        });

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.KOREAN);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(getApplication(), "TTS : Korean Language Not Supported!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(getApplication(), "TTS : TTS's Initialization is Failed!", Toast.LENGTH_SHORT).show();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tts.speak(introductionText, TextToSpeech.QUEUE_FLUSH, null, "TextToSpeech_ID");
                } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    tts.speak(introductionText, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TTS 객체가 남아있다면 실행을 중지하고 메모리에서 제거한다.
        if(tts != null){
            tts.stop();
            tts.shutdown();
            tts = null;
        }
    }
}
