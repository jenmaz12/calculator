
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays; 
import java.text.NumberFormat;

public class Calculator extends Application
{
    private double num1 = 0;
    private double num2 = 0;
    private String operation = "";
    private String prevBtn = "";
    private boolean replace = true;
    private double result = 0;
    private double m = 0;
    private String resultText;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage)
    {
       Label result = new Label();
       resultText = "";
       result.setText(resultText);
       result.setAlignment(Pos.CENTER_RIGHT);
       result.setMaxWidth(Double.MAX_VALUE);
       
       ArrayList<Button> buttonArr = new ArrayList<Button>(20);
       GridPane buttons = new GridPane();
       
       for(ButtonText b: ButtonText.values()) {
           Button button = new Button(b.label);
           button.setOnAction(event -> {
               buttonClick(button.getText());
               result.setText(resultText);
           });
           button.setAlignment(Pos.CENTER);
           button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
           
           buttonArr.add(button);
        }
        
       for (int i = 0; i < buttonArr.size(); i++) {
           if(i>=0 && i<=3) {
               buttons.add(buttonArr.get(i),i,0);
           }
           else if(i>=4 && i<=7) {
               buttons.add(buttonArr.get(i),(i - 4),1);
           }
           else if(i>=8 && i<=11) {
               buttons.add(buttonArr.get(i),(i - 8),2);
           }
           else if(i>=12 && i<=15) {
               buttons.add(buttonArr.get(i),(i - 12),3);
           }
           else {
               buttons.add(buttonArr.get(i),(i - 16),4);
           }
       }
       
       buttons.setHgap(5);
       buttons.setVgap(5);
       VBox vbox = new VBox(10, result, buttons);
       vbox.setPadding(new Insets(20));
       
       Scene scene = new Scene(vbox);
       
       scene.setOnKeyTyped(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                buttonClick(ke.getCharacter());
                result.setText(resultText);
            }
       });
       
       stage.setScene(scene);

       stage.show();
    }

    private void buttonClick(String text)
    {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(4);
        if (isNumber(text)) {
            numberClick(text);
        }
        else if(text.compareTo(".") == 0 ) {
            if(resultText == ""|| resultText == "Error" || isEquals(prevBtn)) {
                resultText = "0" + text;
            }
            else if (isNumber(resultText) && resultText.indexOf(".") == -1) {
                resultText += text;
            }
        }
        else if(isOperator(text)) {
            operatorClick(text);
        }
        else if(isEquals(text)) {
            equalsClick();
            if (Double.isInfinite(result)) {
                resultText = "Error";
                reset();
            }
            else {
                resultText = nf.format(result);  
            }
        }
        else if(text.toUpperCase().compareTo("C")== 0) {
            reset();
            resultText="";
        }
        else if(text.toUpperCase().compareTo("M+")== 0) {
            if (resultText != "") {
                m += Double.parseDouble(resultText);
                reset();
                resultText = "";
            }
        }
        else if(text.toUpperCase().compareTo("M-")== 0) {
            if (resultText != "") {
                m -= Double.parseDouble(resultText);
                reset();
                resultText = "";
            }
        }
        else if(text.toUpperCase().compareTo("MRC")== 0) {
            resultText = nf.format(m);
            if (!isOperator(prevBtn)) {
                reset();
            }
        }
        prevBtn = text;
    }
    
    private boolean isNumber(String input)
    {
        try {
            int i = Integer.parseInt(input);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    
    private boolean isOperator(String input) {
        if (Arrays.asList("\u00F7","/","-","x","+","\u002A").contains(input)) {
            return true;
        }
        else {
            return false;
        }
    }
    
    private boolean isEquals(String input) {
        if (input.compareTo("=")== 0) {
            return true;
        }
        else {
            return false;
        }
    }
    
    private void numberClick(String text) {
        if(resultText == "" || resultText == "Error" || isEquals(prevBtn)) {
            resultText = text;
        }
        else {
            resultText += text;
        }
    }
    
    private void operatorClick(String text) {
        if (text.compareTo("-") == 0 && resultText == "") {
            resultText = text;
            replace = false;
        }
        else if (resultText != "") {
           if (operation == "") {
               num1 = Double.parseDouble(resultText);
           }
           else {
               num2 = Double.parseDouble(resultText);
               calculate(operation);
           }
           operation = text;
           resultText = "";
           replace = true; 
        }
    }
    
    private void equalsClick() {
        if (operation != "" && resultText != "" && prevBtn.compareTo("=") != 0) {
            num2 = Double.parseDouble(resultText);
            calculate(operation);
            replace = true;
        }
        else if (operation != "" && isEquals(prevBtn)) {
            calculate(operation);
        }
        else if(operation == "" && isOperator(prevBtn)) {
            result = num1;
        }
        else if(operation == "" && resultText != "") {
            result = Double.parseDouble(resultText);
        }
    }
    
    private void reset() {
        result = 0;
        operation = "";
        num1 = 0;
        num2 = 0;
        replace = true;
    }
    
    private void calculate(String operation) 
    {
        switch (operation) {
            case "\u00F7":
            case "/":
                result = num1 / num2;
                break;
            case  "-":
                result = num1 - num2;
                break;
            case "x" :
            case "\u002A":
                result = num1 * num2;
                break;
            case "+":
                result = num1 + num2;
                break;
        }
        num1 = result;
    }
}

