import sun.awt.Symbol;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

class SMToken{
    public boolean isOperand;
    public float value;

    public SMToken(float val){
        value = val;
        isOperand = false;
    }

    public SMToken(char val){
        value = (float)val;
        isOperand = true;
    }
}

public class Calculator {
    private static byte priority(char s)
    {
        switch (s)
        {
            case '(': return 0;
            case ')': return 1;
            case '+': return 2;
            case '-': return 3;
            case '*': return 4;
            case '/': return 4;
            case '^': return 5;
            default: return 6;
        }
    }

    private static float execute(List<SMToken> tokens){
        float result = 0;
        Stack<Float> temp = new Stack<Float>();

        for (SMToken token : tokens)
            if (token.isOperand)
            {
                float a = temp.pop();
                float b = temp.pop();
                switch ((char)token.value)
                {
                    case '+': result = b + a; break;
                    case '-': result = b - a; break;
                    case '*': result = b * a; break;
                    case '/': result = b / a; break;
                    case '^': result = (float)Math.pow(a, b); break;
                }
                temp.push(result);
            }
            else
                temp.push(token.value);
        return temp.peek();
    }

    private static List<SMToken>prepare(String expression) throws BadExpression{
        List<SMToken> output = new LinkedList<>(); //Строка для хранения выражения
        Stack<SMToken> operStack = new Stack<SMToken>(); //Стек для хранения операторов
        // для отлова ошибок
        String startExpr = expression;
        int index = 0;
        int brackets = 0;
        char pred = 'a';

        while(!expression.isEmpty()){
            index++;
            char ch = expression.charAt(0);
            if (ch == ' '){
                expression = expression.substring(1);
                continue;
            }
            if ("0123456789.".indexOf(ch) >= 0){
                int k = 0;
                while(k < expression.length() && "0123456789.".indexOf(expression.charAt(k)) >= 0)
                    k++;
                output.add(new SMToken(Float.parseFloat(expression.substring(0, k))));
                pred = ch;
                expression = expression.substring(k);
            } else if ("+-*/^()".indexOf(ch) >= 0) {
                if (ch == '('){
                    operStack.push(new SMToken(ch));
                    brackets++;
                }
                else if (ch == ')')
                {
                    if (--brackets < 0)
                        throw new BadExpression("Закрывающих скобок больше нужного", startExpr, index);
                    SMToken s = operStack.pop();

                    while ((char)s.value != '(')
                    {
                        output.add(s);
                        s = operStack.pop();
                    }
                }
                else
                {
                    if ("+-*/^".indexOf(pred) >= 0)
                        throw new BadExpression("Повторение символа операции", startExpr, index);

                    if (operStack.size() > 0) //Если в стеке есть элементы
                        if (priority(ch) <= priority((char)operStack.peek().value)) //И если приоритет нашего оператора меньше или равен приоритету оператора на вершине стека
                            output.add(operStack.pop()); //То добавляем последний оператор из стека в строку с выражением

                    operStack.push(new SMToken(ch)); //Если стек пуст, или же приоритет оператора выше - добавляем операторов на вершину стека
                }
                pred = ch;
                expression = expression.substring(1);
            }
            else
                throw new BadExpression("Неразрешенный символ", startExpr, index);

        }

        if (brackets != 0)
            throw new BadExpression("Неправильное количество скобок", startExpr, index);
        while (operStack.size() > 0)
            output.add(operStack.pop());

        return output;
    }

    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            while(true) {
                try {
                    System.out.print("Выражение: ");
                    String str = scan.nextLine();
                    if (str == "")
                        break;
                    System.out.println(str + " = " + execute(prepare(str)));
                }
                catch (BadExpression e){
                    e.print();
                }
            }
        }
    }
}
