package com.jkld.first;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView predText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        predText = findViewById(R.id.prediction_display);
        FortunePrediction predictions;
    }

    public void selectPrediction(View view) {
        String prediction = FortunePrediction.getRandomPredictionText();
        if (prediction == FortunePrediction.PREDICTION_20.getPredictionText())
        {
            view.setEnabled(false);
        }
        predText.setText(prediction);
    }
}
enum FortunePrediction {
    PREDICTION_01("Удача ждет вас за углом, проявите инициативу."),
    PREDICTION_02("Скоро вас ждет приятный и неожиданный сюрприз."),
    PREDICTION_03("Будьте смелы и решительны, и успех обязательно придет."),
    PREDICTION_04("Сегодня вам следует доверять своей интуиции и внутреннему голосу."),
    PREDICTION_05("Новая и весьма перспективная возможность уже на горизонте."),
    PREDICTION_06("Это ваш день для великих дел и важных свершений."),
    PREDICTION_07("Терпение и настойчивость принесут вам неожиданные плоды."),
    PREDICTION_08("Ваша креативность достигнет своего пика – используйте это!"),
    PREDICTION_09("Найдите время для полноценного отдыха и восстановления сил."),
    PREDICTION_10("Старый, забытый друг принесет вам очень хорошие новости."),
    PREDICTION_11("Путешествие (даже короткое) изменит ваш взгляд на мир."),
    PREDICTION_12("В ближайшее время ваше финансовое положение улучшится."),
    PREDICTION_13("Все ваши недавние усилия будут щедро вознаграждены."),
    PREDICTION_14("Смело идите вперед к намеченной цели, не сомневаясь."),
    PREDICTION_15("Ожидайте важного звонка, письма или судьбоносного сообщения."),
    PREDICTION_16("Маленький жест доброты вернется к вам сторицей."),
    PREDICTION_17("Сегодня благоприятный день для любых новых начинаний и проектов."),
    PREDICTION_18("Не бойтесь перемен: они несут только положительные сдвиги."),
    PREDICTION_19("Вы встретите человека, который станет для вас источником вдохновения."),
    PREDICTION_20("Выпало 20. Здесь это критический провал.");

    private final String predictionText;
    FortunePrediction(String predictionText) {
        this.predictionText = predictionText;
    }
    public String getPredictionText() {
        return predictionText;
    }
    public static String getRandomPredictionText() {
        FortunePrediction[] predictions = FortunePrediction.values();
        int randomIndex = (int) (Math.random() * predictions.length);
        return predictions[randomIndex].getPredictionText();
    }
}