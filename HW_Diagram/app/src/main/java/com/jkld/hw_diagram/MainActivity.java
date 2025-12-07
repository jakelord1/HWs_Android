package com.jkld.hw_diagram;

import static com.anychart.AnyChart.line;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private AnyChartView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.diagramm);

        Cartesian cartesian = AnyChart.line();

        List<DataEntry> seriesData = new ArrayList<>();
        Random random = new Random();
        String[] days = {"Mon", "Tue", "Wen", "Thu", "Fri", "Sat", "Sun"};

        for (int i = 0; i < days.length; i++) {
            seriesData.add(new ValueDataEntry(days[i], random.nextInt(50) + 50));
        }

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping seriesMapping = set.mapAs("{ x: 'x', value: 'value' }");

        Line line = cartesian.line(seriesMapping);
        line.name("C°");
        line.hovered().markers().enabled(true);
        line.hovered().markers()
                .type("circle")
                .size(4d);

        line.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .format("{%Value}°C");

        cartesian.animation(true);
        cartesian.title("Avg temperature");
        cartesian.yScale().minimum(0d);
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        cartesian.legend().enabled(true);
        cartesian.crosshair(true);

        view.setChart(cartesian);
    }
}