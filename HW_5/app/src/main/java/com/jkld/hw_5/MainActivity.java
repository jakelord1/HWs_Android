package com.jkld.hw_5;

import android.annotation.*;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.*;
import android.content.Context;
import android.graphics.*;
import android.view.*;

class MyDrawer extends View {
    // такий конструктор необхідний, оскільки у View немає конструктора за замовчуванням
    public MyDrawer(Context context) {
        super(context);
    }

    // https://developer.android.com/reference/android/graphics/Paint.html
    // клас Paint зберігає стиль та колірну інформацію для малювання геометрій, тексту та бітмапів.
    private final Paint p = new Paint();

    @SuppressLint("DrawAllocation")
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        // небо блакитне
        p.setStyle(Paint.Style.FILL); // також є STROKE
        p.setColor(Color.CYAN);
        canvas.drawPaint(p);

        // заливка градієнтом
        @SuppressLint("DrawAllocation") Shader shader = new LinearGradient(0, 0, 0, MainActivity.height, Color.BLUE, Color.CYAN, Shader.TileMode.CLAMP);
        p.setShader(shader);
        canvas.drawRect(new RectF(0, 0, MainActivity.width, MainActivity.height), p);
        p.setShader(null);

        // зірочки
        p.setStrokeCap(Paint.Cap.ROUND);
        for (int i = 0; i < 200; i++) {
            p.setARGB((int) (Math.random() * 128), 255, 255, 255);
            p.setStrokeWidth((int) (Math.random() * 15));
            canvas.drawPoint((int) (Math.random() * MainActivity.width), (int) (Math.random() * MainActivity.height), p);
        }

        // сонечко
        int centerX = 250;
        int centerY = 100;
        p.setAntiAlias(true);
        p.setColor(Color.YELLOW);
        canvas.drawCircle(centerX, centerY, 40, p);

        // промінчики
        p.setARGB(64, 255, 255, 255);
        p.setStrokeWidth(1);
        float degree = 0.125f;
        for (float i = 0; i < 360; i += degree) {
            canvas.drawLine(centerX, centerY, -1500, centerY, p);
            canvas.rotate(degree, centerX, centerY);
        }

        // травка
        p.setColor(Color.parseColor("#A4C639"));
        canvas.drawRect(0, 550, MainActivity.width, MainActivity.height, p);
        p.setStrokeCap(Paint.Cap.ROUND);

        // кульбабки
        for (int i = 0; i < 1000; i++) {
            float y = (int) (Math.random() * MainActivity.height) + 560;

            p.setARGB(32, 255, 255, 0);
            p.setStrokeWidth((int) ((y / 130) * (y / 130)));
            canvas.drawPoint((int) (Math.random() * MainActivity.width), y, p);
        }

        // текст
        p.setColor(Color.BLUE);
        p.setTextSize(40);
        canvas.rotate(-20, 30, 675);
        canvas.drawText("Ведроїд", 30, 675, p);

    }
}

public class MainActivity extends AppCompatActivity {
    public static int width;
    public static int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // встановлюємо ландшафтну орієнтацію
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // прибираємо ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // розширюємо контент під системні панелі
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        // приховуємо системні бари (status bar та navigation bar)
        var windowInsetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());

        // immersive mode - бари показуються при свайпі (для кращого UX)
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);

        // отримуємо розміри екрана (враховуючи повноекранний режим)
        WindowMetrics windowMetrics = getWindowManager().getCurrentWindowMetrics();
        Rect bounds = windowMetrics.getBounds();
        width = bounds.width();
        height = bounds.height();

        // встановлюємо кастомний View як вміст
        var d = new MyDrawer(this);
        setContentView(d);
    }
}