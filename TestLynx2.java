import Canberra.DSA3K.DataTypes.*;

public class TestLynx2 {
    public static void main(String[] args) {
        System.out.println("Creating lynx IDevice");
        IDevice lynx=(IDevice) DeviceFactory.createInstance(DeviceFactory.DeviceInterfaces.IDevice);
        System.out.println("Choosing default port");
        lynx.setPort(16387);   // per Lynx Communications Libraries p. 7
        System.out.println("Opening communications with Lynx");
        try {
            java.net.Inet4Address.getByName("10.0.0.3");
        } catch (Exception ex) {
            System.out.println("Address does not exist");
            System.exit(0);
        }
        try {
            lynx.open(null, "10.0.0.3");   // per Lynx Users Manual p. 157
        } catch (Exception ex) {
            System.out.println("Unable to open communications");
            System.exit(0);
        }
        System.out.println("Getting parameter");
        try {
            // Input_VoltageLimite, 167 Input, 1 ParameterCodes from Lynx Communications Library p. 141
            System.out.println((String) lynx.getParameter(203, (short)0));
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