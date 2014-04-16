/* TimeSpectra
 * Capture time spectra, from Canberra example code
 * adapted by Raul Ocampo, 3/24/2014
 */

import java.io.IOException; 
import Canberra.DSA3K.DataTypes.*;
import Canberra.DSA3K.DataTypes.ParameterValueTypes.*;

public class TimeSpectra {
    public static void main(String[] args) {
        // Use input 1
        short input = 1;
        System.out.println("Creating lynx IDevice");
        IDevice lynx=(IDevice) DeviceFactory.createInstance(DeviceFactory.DeviceInterfaces.IDevice);
        try {
            lynx.open(null, "10.0.0.3");
            System.out.println("Connected to " +
                lynx.getParameter(ParameterCodes.Network_MachineName, (short) 0).toString());
        } catch(Exception ex) {}
        try {
            lynx.lock("administrator","password", input);
        } catch(Exception ex) {}
        // disable aquisition
        try {
            lynx.control(CommandCodes.Abort, input);
        } catch(Exception ex) {}
        try {
            lynx.setParameter(ParameterCodes.Input_SCAstatus, new Variant((int) 0), (short) 1);
        } catch(Exception ex) {}
        // read parameters
        System.out.println("Getting parameter");
        try {
            // Input_VoltageLimit, 167 Input, 1 ParameterCodes from Lynx Communications Library p. 141
            System.out.println(lynx.getParameter(167, input));
        } catch (Exception ex) {
            System.out.println("Unable to get parameter");
            System.exit(0);
        }
        try {
            // Set the acq mode
            lynx.setParameter(ParameterCodes.Input_Mode, InputModes.Tlist, input);
            // Set external sync to master mode and disable
            lynx.setParameter(ParameterCodes.Input_ExternalSyncMode, ExternalSyncMode.SyncMaster, input);
            lynx.setParameter(ParameterCodes.Input_ExternalSyncStatus, (int) 0, input);
            // Setup presets
            lynx.setParameter(ParameterCodes.Preset_Options, PresetModes.PresetRealTime, input);
            // Clear data and time
            lynx.control(CommandCodes.Clear, input);
            // Start high voltage
            lynx.setParameter(ParameterCodes.Input_VoltageStatus, true, input);
            // Wait until ready
            while((Boolean) lynx.getParameter(ParameterCodes.Input_VoltageRamping, input)) {
                System.out.println("HVPS ramping...");
                java.lang.Thread.sleep(200);
            }
            // Set memory group
            short group = 1;
            lynx.setParameter(ParameterCodes.Input_CurrentGroup, group, input);
            // Start acquisition
            lynx.control(CommandCodes.Start, input);
            // Increase java priority
            java.lang.Thread.currentThread().setPriority(java.lang.Thread.MAX_PRIORITY);
            // Stop acquisition
            lynx.control(CommandCodes.Stop, input);
            // Turn off high voltage
            lynx.setParameter(ParameterCodes.Input_VoltageStatus, false, input);
        } catch (Exception ex) {}
        System.out.println("Closing communications with Lynx");
        try {
            lynx.close();
        } catch (Exception ex) {
            System.out.println("Unable to close communications");
            System.exit(0);
        }
    }
}