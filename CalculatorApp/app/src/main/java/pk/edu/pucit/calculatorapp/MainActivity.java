package pk.edu.pucit.calculatorapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.udojava.evalex.Expression;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    Expression e = null;
    private Stack equationHandlingStack = new Stack();
    private Stack equationHandlingStack2 = new Stack();
    private TextView tv_equeation;
    private TextView tv_result;
    private TextView tv_1 ;
    private TextView tv_2 ;
    private TextView tv_3 ;
    private TextView tv_4 ;
    private TextView tv_5 ;
    private TextView tv_6 ;
    private TextView tv_7 ;
    private TextView tv_8 ;
    private TextView tv_9 ;
    private TextView tv_0 ;
    private TextView tv_00;
    private TextView tv_sub;
    private TextView tv_add;
    private TextView tv_mul;
    private TextView tv_div;
    private TextView tv_rmd;
    private TextView tv_equals;
    private TextView tv_ac ;
    private TextView tv_del;
    private TextView tv_dot;
    private String number ;
    private final String ADD = "+";
    private final String SUB = "-";
    private final String MUL = "x";
    private final String DIV = "/";
    private final String RMD = "%";
    private Double operationResultDouble = 0.0;
    private Integer operationResultInt = 0;

    private View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            tv_equeation = (TextView)findViewById(R.id.tv_equation);
            switch(v.getId()){
                case R.id.tv_1:
                    tv_equeation.setText(tv_equeation.getText().toString() + "1");
                    if(!equationHandlingStack.empty()){
                        handleEquationOnStack("1");
                    }else{
                        equationHandlingStack.push("1");
                        tv_result.setText("1");
                    }
                    break;
                case R.id.tv_2:
                    tv_equeation.setText(tv_equeation.getText().toString() + "2");
                    if(!equationHandlingStack.empty()){
                        handleEquationOnStack("2");
                    }else{
                        equationHandlingStack.push("2");
                        tv_result.setText("2");
                    }
                    break;
                case R.id.tv_3:
                    tv_equeation.setText(tv_equeation.getText().toString() + "3");
                    if(!equationHandlingStack.empty()){
                        handleEquationOnStack("3");
                    }else{
                        equationHandlingStack.push("3");
                        tv_result.setText("3");
                    }
                    break;
                case R.id.tv_4:
                    tv_equeation.setText(tv_equeation.getText().toString() + "4");
                    if(!equationHandlingStack.empty()){
                        handleEquationOnStack("4");
                    }else{
                        equationHandlingStack.push("4");
                        tv_result.setText("4");
                    }
                    break;
                case R.id.tv_5:
                    tv_equeation.setText(tv_equeation.getText().toString() + "5");
                    if(!equationHandlingStack.empty()){
                        handleEquationOnStack("5");
                    }else{
                        equationHandlingStack.push("5");
                        tv_result.setText("5");
                    }
                    break;
                case R.id.tv_6:
                    tv_equeation.setText(tv_equeation.getText().toString() + "6");
                    if(!equationHandlingStack.empty()){
                        handleEquationOnStack("6");
                    }else{
                        equationHandlingStack.push("6");
                        tv_result.setText("6");
                    }
                    break;
                case R.id.tv_7:
                    tv_equeation.setText(tv_equeation.getText().toString() + "7");
                    if(!equationHandlingStack.empty()){
                        handleEquationOnStack("7");
                    }else{
                        equationHandlingStack.push("7");
                        tv_result.setText("7");
                    }
                    break;
                case R.id.tv_8:
                    tv_equeation.setText(tv_equeation.getText().toString() + "8");
                    if(!equationHandlingStack.empty()){
                        handleEquationOnStack("8");
                    }else{
                        equationHandlingStack.push("8");
                        tv_result.setText("8");
                    }
                    break;
                case R.id.tv_9:
                    tv_equeation.setText(tv_equeation.getText().toString() + "9");
                    if(!equationHandlingStack.empty()){
                        handleEquationOnStack("9");
                    }else{
                        equationHandlingStack.push("9");
                        tv_result.setText("9");
                    }
                    break;
                case R.id.tv_singleZero:
                    tv_equeation.setText(tv_equeation.getText().toString() + "0");
                    if(!equationHandlingStack.empty()){
                        handleEquationOnStack("0");
                    }else{
                        equationHandlingStack.push("0");
                        tv_result.setText("0");
                    }
                    break;
                case R.id.tv_doubleZero:
                    tv_equeation.setText(tv_equeation.getText().toString() + "00");
                    if(!equationHandlingStack.empty()){
                        handleEquationOnStack("00");
                    }else{
                        equationHandlingStack.push("00");
                        tv_result.setText("0");
                    }
                    break;
                case R.id.tv_addition:
                    if(!equationHandlingStack.empty()){
                        handleEquationOnStack("+");
                        if(!tv_equeation.getText().toString().equals("")){
                            tv_equeation.setText(tv_equeation.getText().toString() + "+");
                        }else{
                            tv_equeation.setText(tv_equeation.getText().toString()+tv_result.getText().toString() + "+");
                        }

                    }
                    break;
                case R.id.tv_subtract:
                    if(!equationHandlingStack.empty()){
                        handleEquationOnStack("-");
                        if(!tv_equeation.getText().toString().equals("")){
                            tv_equeation.setText(tv_equeation.getText().toString() + "-");
                        }else{
                            tv_equeation.setText(tv_equeation.getText().toString()+tv_result.getText().toString() + "-");
                        }
                    }else{
                        tv_equeation.setText(tv_equeation.getText().toString() + "-");
                        equationHandlingStack.push("-");
                        tv_result.setText("-");
                    }
                    break;
                case R.id.tv_divide:
                    if(!equationHandlingStack.empty()){
                        handleEquationOnStack("/");
                        if(!tv_equeation.getText().toString().equals("")){
                            tv_equeation.setText(tv_equeation.getText().toString() + "/");
                        }else{
                            tv_equeation.setText(tv_equeation.getText().toString()+tv_result.getText().toString() + "/");
                        }

                    }
                    break;
                case R.id.tv_multiply:
                    if(!equationHandlingStack.empty()){
                        handleEquationOnStack("x");
                        if(!tv_equeation.getText().toString().equals("")){
                            tv_equeation.setText(tv_equeation.getText().toString() + "x");
                        }else{
                            tv_equeation.setText(tv_equeation.getText().toString()+tv_result.getText().toString() + "x");
                        }
                    }
                    break;
                case R.id.tv_remainder:
                    if(!equationHandlingStack.empty()){
                        handleEquationOnStack("%");
                        if(!tv_equeation.getText().toString().equals("")){
                            tv_equeation.setText(tv_equeation.getText().toString() + "%");
                        }else{
                            tv_equeation.setText(tv_equeation.getText().toString()+tv_result.getText().toString() + "%");
                        }
                    }
                    break;
                case R.id.tv_dot:
                    if(!(tv_equeation.getText().toString().indexOf(".") >=0)){
                        tv_equeation.setText(tv_equeation.getText().toString() + ".");
                        if(!equationHandlingStack.empty()){
                            handleEquationOnStack(".");
                        }else{
                            equationHandlingStack.push(".");
                            tv_result.setText("0.0");
                        }
                    }

                    break;
            }
        }
    };

    private void handleEquationOnStack(String val){
        if(!val.equals("+")&&!val.equals("-")&&!val.equals("x")&&!val.equals("/")&&!val.equals("%")){
            String stTop = equationHandlingStack.pop().toString();
            if(!stTop.equals("+") &&!stTop.equals("-") && !stTop.equals("x")&& !stTop.equals("/") && !stTop.equals("%")){
                stTop = stTop+val;
                equationHandlingStack.push(stTop);
                if(stTop.toString().indexOf(".") == stTop.toString().length()-1){
                    tv_result.setText(stTop+"0");
                }else{
                    tv_result.setText(stTop);
                }
            }else{
                equationHandlingStack.push(stTop);
                equationHandlingStack.push(val);
                tv_result.setText(val);
            }
        }else{
            String stTop = equationHandlingStack.peek().toString();
            if(!stTop.equals("+") &&!stTop.equals("-") && !stTop.equals("x")&& !stTop.equals("/") && !stTop.equals("%")){
                equationHandlingStack.push(val);
            }else{
                //equationHandlingStack.pop();
                if(!val.equals("-")){
                    tv_del.performClick();
                }

                equationHandlingStack.push(val);
            }
            tv_result.setText(val);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setUpTextViews();
        tv_equeation.setText("");
        tv_result.setText("");

        tv_ac.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                tv_equeation.setText("");
                tv_result.setText("");
                //handleFloat = 1;
                equationHandlingStack.clear();
            }
        });

        tv_del.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(tv_equeation.getText().length() > 0){
                    CharSequence equatoion = tv_equeation.getText().toString();
                    tv_equeation.setText("" +equatoion.subSequence(0,equatoion.length() - 1));
                    if(!equationHandlingStack.empty()){
                        String stTop = equationHandlingStack.pop().toString();
                        if(!stTop.equals("+")&&!stTop.equals("-")&&!stTop.equals("x")&&!stTop.equals("/")&&!stTop.equals("%")){
                            CharSequence top = stTop;
                            if(!top.equals("")){
                                String valueToBePushed = top.subSequence(0,top.length()-1).toString();
                                if(!valueToBePushed.equals("")){
                                    equationHandlingStack.push(valueToBePushed);
                                }
                            }
                        }
                    }
                }else{
                    tv_equeation.setText("");
                }
            }
        });

        tv_equals.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
               if(!equationHandlingStack.peek().equals("-") && equationHandlingStack.size() != 1){
                   switchDataFromStakc1ToStack2();
                   String result = computeEquation();
                   tv_result.setText(result);
                   tv_equeation.setText("");
//                equationHandlingStack.clear();
                   equationHandlingStack2.clear();
               }else{
                   tv_ac.performClick();
               }
//                e=new Expression(tv_equeation.getText().toString());
//                String result = e.eval().toString();
//                tv_result.setText(result);

            }
        });

    }

     private void setUpTextViews(){

         // equation and result
         tv_equeation = findViewById(R.id.tv_equation);
         tv_result = findViewById(R.id.tv_result);

         // getting numbera
         tv_1 = (TextView)findViewById(R.id.tv_1);
         tv_2 = (TextView)findViewById(R.id.tv_2);
         tv_3 = (TextView)findViewById(R.id.tv_3);
         tv_4 = (TextView)findViewById(R.id.tv_4);
         tv_5 = (TextView)findViewById(R.id.tv_5);
         tv_6 = (TextView)findViewById(R.id.tv_6);
         tv_7 = (TextView)findViewById(R.id.tv_7);
         tv_8 = (TextView)findViewById(R.id.tv_8);
         tv_9 = (TextView)findViewById(R.id.tv_9);
         tv_0 = (TextView)findViewById(R.id.tv_singleZero);
         tv_00 = (TextView)findViewById(R.id.tv_doubleZero);

         //getting symbols
         tv_sub = (TextView)findViewById(R.id.tv_subtract);
         tv_add = (TextView)findViewById(R.id.tv_addition);
         tv_mul = (TextView)findViewById(R.id.tv_multiply);
         tv_div = (TextView)findViewById(R.id.tv_divide);
         tv_rmd = (TextView)findViewById(R.id.tv_remainder);
         tv_equals = (TextView)findViewById(R.id.tv_equals_to);;

         //getting additional buttons
         tv_ac = (TextView) findViewById(R.id.tv_ac);
         tv_del = (TextView)findViewById(R.id.tv_delete);
         tv_dot = (TextView)findViewById(R.id.tv_dot);

         // Setting listenerstv_1.setOnClickListener(onClickListener);
         tv_1.setOnClickListener(onClickListener);
         tv_2.setOnClickListener(onClickListener);
         tv_3.setOnClickListener(onClickListener);
         tv_4.setOnClickListener(onClickListener);
         tv_5.setOnClickListener(onClickListener);
         tv_6.setOnClickListener(onClickListener);
         tv_7.setOnClickListener(onClickListener);
         tv_8.setOnClickListener(onClickListener);
         tv_9.setOnClickListener(onClickListener);
         tv_0.setOnClickListener(onClickListener);
         tv_00.setOnClickListener(onClickListener);
         tv_add.setOnClickListener(onClickListener);
         tv_sub.setOnClickListener(onClickListener);
         tv_mul.setOnClickListener(onClickListener);
         tv_div.setOnClickListener(onClickListener);
         tv_rmd.setOnClickListener(onClickListener);
         tv_dot.setOnClickListener(onClickListener);;
     }

     private void switchDataFromStakc1ToStack2(){
        String stTop = null;
        while(! equationHandlingStack.empty()){
            stTop = equationHandlingStack.pop().toString();
            equationHandlingStack2.push(stTop);
        }
        tv_result.setText(equationHandlingStack2.peek().toString());
     }

     private String computeEquation(){
        String finalVlue = "";
        String stTop = "";
        String val2 = "";
        String opertion = "";
        int counter = 0;
        while(!equationHandlingStack2.empty()){
            stTop = equationHandlingStack2.pop().toString();
            if(stTop.equals("-")){
                stTop = "-"+equationHandlingStack2.pop().toString();
                if(counter != 0){
                    equationHandlingStack.push("+");
                }
                equationHandlingStack.push(stTop);

            }
            else if(!stTop.equals("x")&&!stTop.equals("/")&&!stTop.equals("%")){
                equationHandlingStack.push(stTop);
            }
            else{
                opertion = stTop;
                stTop = equationHandlingStack.pop().toString();
                val2 = equationHandlingStack2.pop().toString();
                if(val2.equals("-")){
                    val2 = "-"+equationHandlingStack2.pop().toString();
                }
                if(opertion.equals(MUL)){
                    if(stTop.indexOf(".")>= 0 || val2.indexOf(".")>= 0){
                        operationResultDouble = Double.parseDouble(stTop) * Double.parseDouble(val2);
                        equationHandlingStack.push(operationResultDouble.toString());
                    }else{
                        operationResultInt = Integer.parseInt(stTop) * Integer.parseInt(val2);
                        equationHandlingStack.push(operationResultInt.toString());
                    }
                }else if(opertion.equals(DIV)){
                    if(Double.parseDouble(val2)!= 0.0 && Integer.parseInt(val2)!=0){
                        operationResultDouble = Double.parseDouble(stTop) / Double.parseDouble(val2);
                        equationHandlingStack.push(operationResultDouble.toString());
                    }else{
                        return "Error";
                        //Toast.makeText(this, "Divide by 0 is not allowed", Toast.LENGTH_LONG).show();
                    }
                }else if(opertion.equals(RMD)){
                    if(Double.parseDouble(val2)!= 0.0 && Integer.parseInt(val2)!=0){
                        if(stTop.indexOf(".")>= 0 || val2.indexOf(".")>= 0){
                            operationResultDouble = Double.parseDouble(stTop) % Double.parseDouble(val2);
                            equationHandlingStack.push(operationResultDouble.toString());
                        }else{
                            operationResultInt = Integer.parseInt(stTop) % Integer.parseInt(val2);
                            equationHandlingStack.push(operationResultInt.toString());
                        }
                    }else{
                        return "Error";
                        //Toast.makeText(this, "Remainder with 0 is not allowed", Toast.LENGTH_LONG).show();
                    }
                    //equationHandlingStack2.push("1");
                }
            }
            counter++;
        }
        while(!equationHandlingStack.empty()){
            stTop = equationHandlingStack.pop().toString();
            if(!stTop.equals("+")&&!stTop.equals("-")){
                equationHandlingStack2.push(stTop);
            }else{
                opertion = stTop;
                stTop = equationHandlingStack.pop().toString();
                val2 = equationHandlingStack2.pop().toString();
                if(stTop.indexOf(".")>= 0 || val2.indexOf(".")>= 0){
                    operationResultDouble = Double.parseDouble(stTop) + Double.parseDouble(val2);
                    equationHandlingStack2.push(operationResultDouble.toString());
                }else{
                    operationResultInt = Integer.parseInt(stTop) + Integer.parseInt(val2);
                    equationHandlingStack2.push(operationResultInt.toString());
                }
            }
        }
        if(!equationHandlingStack2.empty()){
            finalVlue = equationHandlingStack2.pop().toString();
            equationHandlingStack.push(finalVlue);
        }
        else{
            finalVlue="Hello";
        }

        return finalVlue;
     }
}
