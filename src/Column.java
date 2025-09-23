public class Column {
    private int[] column;

    public Column(int size){
        column = new int[size];
    }

    public int getValue(int index){
        return column[index-1];
    }
    public void setValue(int index, int value){
        column[index-1] = value;
    }
    
} 
