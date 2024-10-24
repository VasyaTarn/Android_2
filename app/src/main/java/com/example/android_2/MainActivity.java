package com.example.android_2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private StringBuilder currentExpression;
    private double firstOperand;
    private String operator;
    private boolean isOperatorPressed;
    private boolean continueTyping;

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

        textViewResult = findViewById(R.id.textViewResult);
        currentExpression = new StringBuilder();
        isOperatorPressed = false;

        currentExpression.append("0");

        setNumericButtonListeners();
        setOperatorButtonListeners();
        setClearButtonListener();
        setEqualsButtonListener();
    }

    private void setNumericButtonListeners() {
        int[] numericButtons = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9, R.id.buttonDot
        };

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Button button = (Button) v;

                if (isOperatorPressed)
                {
                    currentExpression.setLength(0);
                    isOperatorPressed = false;
                }
                if(v.getId() == R.id.buttonDot)
                {
                    continueTyping = true;
                }
                if(v.getId() != R.id.buttonDot && !continueTyping)
                {
                    currentExpression.setLength(0);
                    continueTyping = true;
                }
                currentExpression.append(button.getText().toString());
                textViewResult.setText(currentExpression.toString());
            }
        };

        for (int id : numericButtons) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setOperatorButtonListeners() {
        int[] operatorButtons = {
                R.id.buttonPlus, R.id.buttonMinus,
                R.id.buttonMultiply, R.id.buttonDivide
        };

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                operator = button.getText().toString();
                firstOperand = Double.parseDouble(currentExpression.toString());
                isOperatorPressed = true;
            }
        };

        for (int id : operatorButtons) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setClearButtonListener() {
        findViewById(R.id.buttonC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentExpression = new StringBuilder();
                currentExpression.append("0");
                continueTyping = false;
                textViewResult.setText("0");
                firstOperand = 0;
                operator = "";
            }
        });
    }

    private void setEqualsButtonListener() {
        findViewById(R.id.buttonEqual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operator == null || operator.isEmpty()) return;

                double secondOperand = Double.parseDouble(currentExpression.toString());
                double result = 0;

                switch (operator)
                {
                    case "+":
                        result = firstOperand + secondOperand;
                        break;
                    case "-":
                        result = firstOperand - secondOperand;
                        break;
                    case "*":
                        result = firstOperand * secondOperand;
                        break;
                    case "/":
                        if (secondOperand != 0)
                        {
                            result = firstOperand / secondOperand;
                        }
                        else
                        {
                            textViewResult.setText("Error");
                            return;
                        }

                        break;
                }

                textViewResult.setText(String.valueOf(result));
                currentExpression.setLength(0);
                currentExpression.append(result);
                isOperatorPressed = false;
                firstOperand = 0;
            }
        });
    }
}