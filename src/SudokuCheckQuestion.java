public class SudokuCheckQuestion extends SudokuBase{
    private boolean isValidQuestion = true;
    public SudokuCheckQuestion(int[][] question){
        super(question);
        this.isValidQuestion = checkValidQuestion();
    }
    private boolean checkValidQuestion(){
        boolean isValidQuestion = true;

        if ((int)Math.pow(this.unit,2) != this.size){
            if (this.verbose){System.out.println("square num check false");}
            isValidQuestion = false;
        }
        // 全て長さが同じかチェック
        for (int i=0;i<this.size;i++){
            if (this.question[i].length != this.size){
                if (this.verbose){System.out.println("length consistency check false");}
                isValidQuestion =  false;
            }
        }

        // 重複した数字がないかチェック
        if(!checkUnique()){
            if( verbose ){System.out.println("duplicate check false");}
            isValidQuestion =  false;
        }

        return isValidQuestion;
    }
    public boolean getValidQuestion(){
        return this.isValidQuestion;
    }
}