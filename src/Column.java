public class Column {
    private int[] column;

    public Column(int size){
        column = new int[size];
    }

    public int getValue(int index){
        System.out.println("Getting Column Value: " + column[index] + "At index " + index);
        return column[index];
    }
    public void setValue(int index, int value){
        column[index] = value;
    }
    
} 
