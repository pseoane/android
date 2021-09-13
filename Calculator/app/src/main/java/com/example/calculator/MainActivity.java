package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button sum;
    Button divide;
    Button multiply;
    Button minus;
    EditText input1, input2;
    TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
    }

    private void setupUI() {
        sum = findViewById(R.id.sum);
        sum.setOnClickListener(new OperationClickListener(Operation.SUM));

        divide = findViewById(R.id.divide);
        divide.setOnClickListener(new OperationClickListener(Operation.DIVIDE));

        multiply = findViewById(R.id.multiply);
        multiply.setOnClickListener(new OperationClickListener(Operation.MULTIPLY));

        minus = findViewById(R.id.minus);
        minus.setOnClickListener(new OperationClickListener((Operation.MINUS)));

        input1 = findViewById(R.id.operand1);
        input2 = findViewById(R.id.operand2);
        resultView = findViewById(R.id.result);
    }

    private enum Operation {
        SUM, MULTIPLY, MINUS, DIVIDE;
    }

    class OperationClickListener implements View.OnClickListener {
        private Operation operation;

        public OperationClickListener(Operation op) {
            operation = op;
        }

        @Override
        public void onClick(View view) {
            int operand1 = Integer.parseInt(input1.getText().toString());
            int operand2 = Integer.parseInt(input2.getText().toString());
            double result;
            switch (operation) {
                case SUM:
                    result = operand1 + operand2;
                    break;
                case MULTIPLY:
                    result = operand1 * operand2;
                    break;
                case MINUS:
                    result = operand1 - operand2;
                    break;
                case DIVIDE:
                    result = operand1 / operand2;
                default:
                    result = 0.0;
            }
            resultView.setText(Double.toString(result));
        }
    }
}