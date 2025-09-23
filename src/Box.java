public class Box {
    private int[] box;

    public Box(int size){
        box = new int[size];
    }

    public int getValue(int index){
        return box[index-1];
    }
    public void setValue(int index, int value){
        box[index-1] = value;
    }
    
} 