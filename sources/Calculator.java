import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.stream.*;
import java.util.function.*;
import java.util.OptionalDouble.*;

public class Calculator {
  JFrame frame;
  JPanel main_panel;
  JPanel aside_panel;

  ArrayList<JButton> buttons = new ArrayList<>();

  Label output;

  ArrayList<String> operations = new ArrayList<>(
    Arrays.asList("+", "-", "x", "/", "=")
  );

  String ops = "+";

  OptionalDouble value = OptionalDouble.empty();

  java.util.List<String> main = java.util.List
    .of(
        "x", "/", "⬅️",
        "7", "8", "9",
        "4", "5", "6",
        "1", "2", "3",
        "0", ".", "="
    );

  java.util.List<String> aside = java.util.List
    .of("C", "+", "-");


  public static void main (String [] args) {
    new Calculator().gui();
  }


  public void gui() {

    frame = new JFrame("Mini Calculator");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    // output display
    output = new Label("", Label.RIGHT);

    Font font = new Font("sanSerif", Font.BOLD, 16);
    output.setFont(font);

    frame.getContentPane().add(BorderLayout.NORTH, output);


    GridLayout aside_grid = new GridLayout(3, 1, 1, 1);
    aside_panel = new JPanel(aside_grid);

    Font btn_font = new Font("sanSerif", Font.BOLD, 18);

    // aside
    aside.forEach(el -> {

      JButton btn = new JButton(el);
      btn.setFont(btn_font);


      aside_panel.add(btn);
      buttons.add(btn);
    });

    frame.getContentPane().add(BorderLayout.EAST, aside_panel);


    // main
    GridLayout main_grid = new GridLayout(5, 3, 1, 1);
    main_panel = new JPanel(main_grid);

    main.forEach(el -> {

      JButton btn = new JButton(el);
      btn.setFont(btn_font);

      main_panel.add(btn);
      buttons.add(btn);
    }); // end of forEach statement


    buttons.forEach(btn -> {
      btn.addActionListener((ActionEvent a) -> {

        String current = output.getText();
        String btnText = btn.getText();
        int length = current.length();

        if (btnText.equals("C")) {
          output.setText("");
          value = OptionalDouble.empty();

        } else if (btnText.equals("⬅️")) {
          output.setText(current.equals("") ? current
            : current.substring(0, length - 1));
          value = OptionalDouble.empty();

        } else if (btnText.equals("=")) {

          System.out.println(output.getText());
          double d = this.evaluate(output.getText());

          output.setText(d == (int) d ? "" + (int) d : "" + d);
          value = OptionalDouble.empty();
          
        } else if (operations.contains(btnText)) {

          // inner conditonal statement
          if (current.equals(""))
            output.setText(btnText.equals("-") ? "-" : current);

          else if (operations.contains(current.substring(length - 1)))
            output.setText(current.substring(0, length - 1) + btnText);

          else
            output.setText(current + btnText);
          // end of inner conditional statements

        } else if (btnText == ".") {
            output.setText(current.equals("") ? "0."
              : current + btnText);

        } else {
          output.setText(current + btnText);// do nothing
        } // end of conditonal statement

      }); // end of addActionListener
    }); // end of for loop


    frame.getContentPane().add(BorderLayout.CENTER, main_panel);

    frame.setSize(250, 300);
    frame.setVisible(true);
  } // end of gui method

  public double evaluate (String inputString) {

    if (inputString == "") return 0;

    String acc = "0";

    ArrayList<String> arrayOfInputString =
      new ArrayList<>(Arrays.asList(inputString.split("")));

    for (int i = 0; i < arrayOfInputString.size(); i++) {
      String l = arrayOfInputString.get(i);

      if (!operations.contains(l) &&
          i != arrayOfInputString.size() - 1) {
        acc += l;

      } else if (i == arrayOfInputString.size() - 1 ||
                 operations.contains(l)) {

        if (i == arrayOfInputString.size() - 1)
          acc += l;
        // end if statement

        double number = Double.parseDouble(acc);

        // inner conditonal statement
        if (value.isEmpty())
          value = OptionalDouble.of(number);

        else 
          value = ops.equals("+")
                  ? OptionalDouble.of(value.getAsDouble() + number)
                : ops.equals("-")
                  ? OptionalDouble.of(value.getAsDouble() - number)
                : ops.equals("x")
                  ? OptionalDouble.of(value.getAsDouble() * number)
                : OptionalDouble.of(value.getAsDouble() / number);
        // end of inner conditional statement

        acc = "0";

        if (operations.contains(l))
          ops = l;
      } // end of conditonal statement;
    } // end of loop

    return value.getAsDouble();
  } // end of method

} // end of class
