/**
 * Created by hadoop on 17-6-30.
 */
public class Driver {
    private static int times = 10;
    public static void main(String[] args) throws Exception{
        String[] argPr = {"",""};
        for(int i=0;i<times;i++){
            argPr[0] = args[1] + "/times"+i;
            argPr[1] = args[1] + "/times" + String.valueOf(i+1);
            PageRankMain.main(argPr);
        }
    }
    /**
     卜垣	1.1240876609310058#戚长发 0.25,戚芳 0.5,狄云 0.25
     戚芳	1.1240876609310058#戚长发 0.25,卜垣 0.5,狄云 0.25
     戚长发	0.87591245849455#卜垣 0.33333334,戚芳 0.33333334,狄云 0.33333334
     狄云	0.8759124584945499#戚长发 0.33333334,卜垣 0.33333334,戚芳 0.33333334
     */

}
