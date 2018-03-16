/**
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/15
 * @description
 */
public class Cal {

    public static void main(String[] args) {

        int[] data={1,4,5,8,15,20,18,-3,11};
        int min=minDifference(data);
        System.out.println(min);
    }

    public static int minDifference(int[] data){
        if(data==null||data.length==0){
            return Integer.MIN_VALUE;
        }
        sort(data,0,data.length-1);
        int len=data.length;
        int[] diff=new int[len-1];
        for(int i=0;i<len-1;i++){
            diff[i]=data[i+1]-data[i];
        }
        //System.out.println(Arrays.toString(diff));
        return min(diff);
    }
    public static int min(int[] diff){
        if(diff==null||diff.length==0){
            return Integer.MIN_VALUE;
        }
        int min=diff[0];
        for(int i=0,len=diff.length;i<len;i++){
            //not necessary,since 'int[] data' is sorted,so 'int[] diff' is progressively increased.
            //int tmp=diff[i]>0?diff[i]:(-diff[i]);
            if(min>diff[i]){
                min=diff[i];
            }
        }
        return min;
    }

    //QuickSort.Of course we can use Arrays.sort(),but I write it for practice.
    public static void sort(int[] x,int s,int e){
        if(s>=e){
            return;
        }
        int i=s;
        int j=e;
        boolean flag=false;
        while(i!=j){
            if(x[i]>x[j]){
                swap(x,i,j);
                flag=!flag;
            }
            if(flag){
                i++;
            }else{
                j--;
            }
        }
        sort(x,s,i-1);
        sort(x,j+1,e);
    }

    public static void swap(int[] x,int i,int j){
        int tmp=x[i];
        x[i]=x[j];
        x[j]=tmp;
    }

}
