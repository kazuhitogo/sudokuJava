import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        int counter;
        int[][] data;
        SudokuSolve q;
        boolean couldSolve;
        data = readCsv(args[0]);
        SudokuCheckQuestion question = new SudokuCheckQuestion(data);

        if (question.getValidQuestion()){
            System.out.println("The question is ...");
            question.printQuestion();
            Deque<SudokuSolve> Q = new ArrayDeque<SudokuSolve>();
            for (int i = question.size; i > 0; i--) {
                q = new SudokuSolve(question.getQuestion());
                q.setNextValue(i);
                Q.push(q);
            }
            counter = 0;
            couldSolve = false;

            while (!Q.isEmpty()) {
                q = Q.pop();
                if(q.checkInsert()){
                    q.setNextValueToQuestion();

                    counter++;
                    if (counter%10000==0){
                        System.out.println(counter);
                        q.printQuestion();
                    }
                    if (q.checkComplete()){
                        couldSolve = true;
                        System.out.println("\nThe answer is ...");
                        q.printQuestion();
                        System.out.print("counter: ");
                        System.out.println(counter);
                        break;
                    }
                    for (int i = q.size; i > 0; i--) {
                        q = new SudokuSolve(q.getQuestion());
                        q.setNextValue(i);
                        Q.push(q);
                    }
                }
            }
            if (!couldSolve){
                System.out.println("It could not resolved");
            }
        }else{
            System.out.println("This question is invalid");
        }
    }
    public static int[][] readCsv(String path){
        int data[][] = new int[1][1];
        try {
            File f = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line; // 読み込み行
            String[] line_array;
            int row = 0;
            while ((line = br.readLine()) != null){
                line_array = line.split(",");
                if (row==0) {
                    data = new int[line_array.length][line_array.length];
                }
                for (int i = 0; i < line_array.length; i++) data[row][i] = Integer.parseInt(line_array[i].replace(" ",""));
                row++;
            }
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Check your input file " + path + ".");
            data = new int[1][1];
            data[0][0] = -1;
        }
        return data;
    }
}
