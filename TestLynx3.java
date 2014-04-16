/* TestLynx3
 * more concious copying Canberra example code
 * adapted by Raul Ocampo, 3/20/2014
 */

import java.io.IOException; 
import Canberra.DSA3K.DataTypes.*;

public class TestLynx3 {
    public static void main(String[] args) {
        System.out.println("Creating lynx IDevice");
        IDevice lynx=(IDevice) DeviceFactory.createInstance(DeviceFactory.DeviceInterfaces.IDevice);
        try {
            lynx.open(null, "10.0.0.3");
            System.out.println("Connected to " +
                lynx.getParameter(ParameterCodes.Network_MachineName, (short) 0).toString());
        } catch(Exception ex) {}
        try {
            lynx.lock("administrator","password", (short) 1);
        } catch(Exception ex) {}
        // disable aquisition
        try {
            lynx.control(CommandCodes.Abort, (short) 1);
        } catch(Exception ex) {}
        try {
            lynx.setParameter(ParameterCodes.Input_SCAstatus, new Variant((int) 0), (short) 1);
        } catch(Exception ex) {}
        // read parameters
        System.out.println("Getting parameter");
        try {
            // Input_VoltageLimite, 167 Input, 1 ParameterCodes from Lynx Communications Library p. 141
            System.out.println(lynx.getParameter(167, (short) 1));
        } catch (Exception ex) {
            System.out.println("Unable to get parameter");
            System.exit(0);
        }
        System.out.println("Closing communications with Lynx");
        try {
            lynx.close();
        } catch (Exception ex) {
            System.out.println("Unable to close communications");
            System.exit(0);
        }
    }
}